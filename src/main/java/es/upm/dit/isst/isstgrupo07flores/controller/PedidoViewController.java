package es.upm.dit.isst.isstgrupo07flores.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import org.springframework.ui.Model;
import java.util.UUID;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.ProductoRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.upm.dit.isst.isstgrupo07flores.service.CartService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import es.upm.dit.isst.isstgrupo07flores.model.Pedido; // Import the Pedido class
import es.upm.dit.isst.isstgrupo07flores.repository.PedidoRepository; // Import the PedidoRepository interface
import jakarta.servlet.http.HttpSession; // Import HttpSession from Jakarta
import es.upm.dit.isst.isstgrupo07flores.model.Cliente; // Import the Cliente class
import es.upm.dit.isst.isstgrupo07flores.repository.ClienteRepository; // Import the ClienteRepository interface

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/pedido")
public class PedidoViewController {
    @Autowired
    private CartService cartService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

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

    
}
