package es.upm.dit.isst.isstgrupo07flores.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import org.springframework.security.core.Authentication;

import es.upm.dit.isst.isstgrupo07flores.service.CartService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import es.upm.dit.isst.isstgrupo07flores.model.Pedido; 
import es.upm.dit.isst.isstgrupo07flores.repository.PedidoRepository; 
import jakarta.servlet.http.HttpSession; 
import es.upm.dit.isst.isstgrupo07flores.model.Cliente; 
import es.upm.dit.isst.isstgrupo07flores.repository.ClienteRepository;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.ProductoRepository;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Collections;

@Controller
@RequestMapping("/pedido")
public class PedidoViewController {
    @Autowired
    private CartService cartService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FloricultorRepository floricultorRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/new")
    public String mostrarFormularioNuevoPedido() {
        return "newPedidoForm"; 
    }
    

    @PostMapping("/create")
    public String crearPedido(@RequestParam("direccionEntrega") String direccionEntrega, Authentication authentication, HttpSession session, RedirectAttributes redirectAttributes) {
        Producto producto = cartService.getCartProduct(session);
        if (producto == null) {
            throw new IllegalStateException("No hay producto en la cesta.");
        }

        // Buscar el cliente por correo electrónico
        String email = authentication.getName();
        Optional<Cliente> clienteOpt = clienteRepository.findByCorreoElectronico(email);
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado con el correo: " + email);
        }

        Cliente cliente = clienteOpt.get();

        Pedido pedido = new Pedido();

        pedido.setClienteId(cliente.getId()); // Usa el UUID del cliente
        pedido.setProductoId(producto.getId());
        pedido.setCoste(producto.getPrecio());
        pedido.setDireccionentrega(direccionEntrega);
        pedido.setUrlTracking("https://example.com/tracking");
        pedido.setEstado(Pedido.Estados.SOLICITADO);
        pedido.setValoracion(null);

        pedidoRepository.save(pedido);
        cartService.clearCart(session); // Limpia la cesta después de crear el pedido

        redirectAttributes.addFlashAttribute("successMessage", "Tu pedido se ha realizado correctamente");
        return "redirect:/"; // Redirige a la página principal
    }

    @GetMapping("/cliente")
    public String verPedidosCliente(Authentication authentication, Model model) {
        // Obtener el correo del cliente autenticado
        String email = authentication.getName();

        // Buscar el cliente por correo electrónico
        Optional<Cliente> clienteOpt = clienteRepository.findByCorreoElectronico(email);
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado con el correo: " + email);
        }

        Cliente cliente = clienteOpt.get();

        // Obtener los pedidos del cliente
        List<Pedido> pedidos = pedidoRepository.findByClienteId(cliente.getId());

        // Invertir el orden de los pedidos
        Collections.reverse(pedidos);

        

        // Pasar los pedidos al modelo
        model.addAttribute("pedidos", pedidos);

        return "pedidosCliente"; // Nombre de la plantilla Thymeleaf
    }
    
    @PostMapping("/valorar/{id}")
    public String valorarPedido(@PathVariable("id") UUID pedidoId, @RequestParam("valoracion") int valoracion) {
        // Buscar el pedido por ID
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        if (pedidoOpt.isEmpty()) {
            throw new IllegalArgumentException("Pedido no encontrado con ID: " + pedidoId);
        }

        // Actualizar la valoración del pedido
        Pedido pedido = pedidoOpt.get();
        pedido.setValoracion(valoracion);
        pedidoRepository.save(pedido);

        // Obtener el producto asociado al pedido
        UUID productoId = pedido.getProductoId();
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + productoId);
        }

        Producto producto = productoOpt.get();

        // Obtener el floricultor asociado al producto
        UUID floricultorId = producto.getFloricultorId();
        Optional<Floricultor> floricultorOpt = floricultorRepository.findById(floricultorId);
        if (floricultorOpt.isEmpty()) {
            throw new IllegalArgumentException("Floricultor no encontrado con ID: " + floricultorId);
        }

        Floricultor floricultor = floricultorOpt.get();

        // Calcular la media de las valoraciones de todos los pedidos del floricultor
        List<Pedido> pedidosDelFloricultor = pedidoRepository.findByFloricultorId(floricultorId);
        double mediaValoraciones = pedidosDelFloricultor.stream()
                .filter(p -> p.getValoracion() != null) // Ignorar pedidos sin valoración
                .mapToInt(Pedido::getValoracion)
                .average()
                .orElse(0.0);

        // Actualizar la media de valoraciones del floricultor
        floricultor.setMediaValoraciones(mediaValoraciones);
        floricultorRepository.save(floricultor);

        // Redirigir a la lista de pedidos
        return "redirect:/pedido/cliente";
    }
}
