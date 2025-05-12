package es.upm.dit.isst.isstgrupo07flores.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional; // Import the Map class
import java.util.UUID; // Import the HashMap class
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.upm.dit.isst.isstgrupo07flores.model.Cart;
import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.model.FloresEnPersonalizado;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.model.Pedido;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.model.ProductoPersonalizado;
import es.upm.dit.isst.isstgrupo07flores.repository.ClienteRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FlorRepository; // Import the Cart class
import es.upm.dit.isst.isstgrupo07flores.repository.FloresEnPersonalizadoRepository; // Import the Flor class
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository; // Import the ProductoPersonalizado class
import es.upm.dit.isst.isstgrupo07flores.repository.PedidoRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.ProductoPersonalizadoRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.ProductoRepository;
import es.upm.dit.isst.isstgrupo07flores.service.CartService; // Ensure this repository is correctly defined for ProductoPersonalizado
import jakarta.servlet.http.HttpSession; // Import FlorService



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

    @Autowired
    private FloresEnPersonalizadoRepository floresEnPersonalizadoRepository; // Inject FloresEnPersonalizadoRepository

    @Autowired
    private FloresEnPersonalizadoRepository relacionRepository; // Inject FloresEnPersonalizadoRepository

    @Autowired
    private ProductoPersonalizadoRepository productoPersonalizadoRepository; // Inject ProductoPersonalizadoRepository

    @Autowired
    private FlorRepository florRepository; // Inject FlorRepository

    @GetMapping("/new")
    public String mostrarFormularioNuevoPedido() {
        return "newPedidoForm"; 
    }
    

    @PostMapping("/create")
    public String crearPedido(@RequestParam(value = "entregaUrgente", required = false, defaultValue = "false") Boolean entregaUrgente, 
                              @RequestParam(value = "fechaEntrega", required = false) LocalDate fechaEntrega, 
                              @RequestParam(value = "direccionEntrega", required = false) String direccionEntrega, 
                              @RequestParam(value = "dedicatoria", required = false) String dedicatoria,
                              @RequestParam("entregaEnLocal") Boolean entregaEnLocal, Authentication authentication, 
                              HttpSession session, 
                              RedirectAttributes redirectAttributes) {
   
        Cart cart = cartService.getCart(session);

        // Redondear el precio total del carrito a dos decimales
        BigDecimal precioTotal = cart.getPrecioTotal().setScale(2, BigDecimal.ROUND_HALF_UP);
        cart.setPrecioTotal(precioTotal);

        // Buscar el cliente por correo electrónico
        String email = authentication.getName();
        Optional<Cliente> clienteOpt = clienteRepository.findByCorreoElectronico(email);
        if (clienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado con el correo: " + email);
        }

        // Crear Pedido
        Pedido pedido = new Pedido();
        pedido.setUrlTracking("https://example.com/tracking");
        pedido.setEstado(Pedido.Estados.SOLICITADO);
        pedido.setValoracion(null);
        pedido.setDedicatoria(dedicatoria);

        Cliente cliente = clienteOpt.get();
        pedido.setClienteId(cliente.getId()); // Usa el UUID del cliente

        if (entregaUrgente) {
            pedido.setFechaEntrega(LocalDate.now().plusDays(1)); // Entrega urgente para el día siguiente
        }else if (fechaEntrega != null){
            pedido.setFechaEntrega(fechaEntrega);
        }
        

        // Verificar si hay flores en el carrito
        if (cart.getFlores() != null && !cart.getFlores().isEmpty() && cart.getProducto() == null) {
            // Crear ProductoPersonalizado
            ProductoPersonalizado productoPersonalizado = new ProductoPersonalizado();
            productoPersonalizado.setFloricultorId(cart.getFlores().get(0).getFloricultorId());
            Optional<Floricultor> floricultor = floricultorRepository.findById(productoPersonalizado.getFloricultorId());
            productoPersonalizado.setCosteTotal(cart.getPrecioTotal());
            productoPersonalizadoRepository.save(productoPersonalizado);
            
            if (entregaEnLocal) {
            direccionEntrega = floricultor.get().getDireccion();
            } else if (direccionEntrega == null || direccionEntrega.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "La dirección de entrega no puede estar vacía");
                return "redirect:/pedido/new"; // Redirige al formulario si la dirección está vacía
            }

            // Establecer relación entre ProductoPersonalizado y Pedido
            pedido.setProductoId(productoPersonalizado.getId());
            pedido.setCoste(cart.getPrecioTotal());

            // Crear relaciones FloresEnPersonalizado
            cart.getFlores().stream()
                .collect(Collectors.groupingBy(Flor::getId, Collectors.counting()))
                .forEach((florId, cantidad) -> {
                    FloresEnPersonalizado relacion = new FloresEnPersonalizado();
                    relacion.setFlorId(florId);
                    relacion.setProductoPersonalizadoId(productoPersonalizado.getId());
                    relacion.setCantidad(cantidad.intValue());
                    relacionRepository.save(relacion);
                });

        } else if (cart.getProducto() != null) {
            // Si hay un producto en el carrito, usar sus datos
            Producto producto = cart.getProducto();
            Optional<Floricultor> floricultor = floricultorRepository.findById(producto.getFloricultorId());
            pedido.setCoste(producto.getPrecio());
            pedido.setProductoId(producto.getId());
            
            if (entregaEnLocal) {
            direccionEntrega = floricultor.get().getDireccion();
            } else if (direccionEntrega == null || direccionEntrega.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "La dirección de entrega no puede estar vacía");
                return "redirect:/pedido/new"; // Redirige al formulario si la dirección está vacía
            }
        } else {
            // Si no hay nada en el carrito, redirigir con un mensaje de error
            redirectAttributes.addFlashAttribute("error", "No hay productos ni flores en el carrito.");
            return "redirect:/cart";
        }

        

        pedido.setDireccionentrega(direccionEntrega);

        // Guardar el pedido
        pedidoRepository.save(pedido);

        // Limpiar el carrito después de crear el pedido
        cartService.clearCart(session);

        // Agregar un mensaje de éxito
        redirectAttributes.addFlashAttribute("successMessage", "Tu pedido se ha realizado correctamente");
        return "redirect:/"; // Redirige a la página principal
    }

    @GetMapping("/floricultor")
    public String verPedidosFloricultor(Authentication authentication, Model model) {
        String email = authentication.getName();
        Optional<Floricultor> floricultorOpt = floricultorRepository.findByCorreoElectronico(email);

        if (floricultorOpt.isEmpty()) {
            throw new IllegalArgumentException("Floricultor no encontrado con el correo: " + email);
        }

        UUID floricultorId = floricultorOpt.get().getId();
        List<Pedido> pedidos = pedidoRepository.findByFloricultorId(floricultorId);

        // Crear un mapa para almacenar los nombres de productos o flores
        Map<UUID, String> nombresProductos = new HashMap<>();

        for (Pedido pedido : pedidos) {
            if (pedido.getProductoId() != null) {
                // Buscar en Producto
                Optional<Producto> productoOpt = productoRepository.findById(pedido.getProductoId());
                if (productoOpt.isPresent()) {
                    nombresProductos.put(pedido.getId(), productoOpt.get().getNombre());
                } else {
                    // Buscar en ProductoPersonalizado
                    Optional<ProductoPersonalizado> personalizadoOpt = productoPersonalizadoRepository.findById(pedido.getProductoId());
                    if (personalizadoOpt.isPresent()) {
                        ProductoPersonalizado personalizado = personalizadoOpt.get();
                        // Construir la cadena con nombres de flores y cantidades
                        String nombresFloresConCantidad = floresEnPersonalizadoRepository.findByProductoPersonalizadoId(personalizado.getId())
                                .stream()
                                .map(f -> {
                                    String nombreFlor = florRepository.findById(f.getFlorId()).map(Flor::getNombre).orElse("Desconocido");
                                    return nombreFlor + " (x" + f.getCantidad() + ")";
                                })
                                .collect(Collectors.joining(", "));
                        nombresProductos.put(pedido.getId(), nombresFloresConCantidad);
                    } else {
                        nombresProductos.put(pedido.getId(), "Desconocido");
                    }
                }
            } else {
                nombresProductos.put(pedido.getId(), "Sin producto asociado");
            }
        }

        // Pasar los pedidos y los nombres al modelo
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("nombresProductos", nombresProductos);

        return "pedidosFloricultor";
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

        // Crear un mapa para almacenar los nombres de productos o flores
        Map<UUID, String> nombresProductos = new HashMap<>();

        for (Pedido pedido : pedidos) {
            if (pedido.getProductoId() != null) {
                // Buscar en Producto
                Optional<Producto> productoOpt = productoRepository.findById(pedido.getProductoId());
                if (productoOpt.isPresent()) {
                    nombresProductos.put(pedido.getId(), productoOpt.get().getNombre());
                } else {
                    // Buscar en ProductoPersonalizado
                    Optional<ProductoPersonalizado> personalizadoOpt = productoPersonalizadoRepository.findById(pedido.getProductoId());
                    if (personalizadoOpt.isPresent()) {
                        ProductoPersonalizado personalizado = personalizadoOpt.get();
                        // Construir la cadena con nombres de flores y cantidades
                        String nombresFloresConCantidad = floresEnPersonalizadoRepository.findByProductoPersonalizadoId(personalizado.getId())
                                .stream()
                                .map(f -> {
                                    String nombreFlor = florRepository.findById(f.getFlorId()).map(Flor::getNombre).orElse("Desconocido");
                                    return nombreFlor + " (x" + f.getCantidad() + ")";
                                })
                                .collect(Collectors.joining(", "));
                        nombresProductos.put(pedido.getId(), nombresFloresConCantidad);
                    } else {
                        nombresProductos.put(pedido.getId(), "Desconocido");
                    }
                }
            } else {
                nombresProductos.put(pedido.getId(), "Sin producto asociado");
            }
        }

        // Pasar los pedidos y los nombres al modelo
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("nombresProductos", nombresProductos);

        return "pedidosCliente";
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
        UUID floricultorId = null;
        if (productoOpt.isEmpty()) {
            Optional<ProductoPersonalizado> productoPersonalizadoOpt = productoPersonalizadoRepository.findById(productoId);
            if (productoPersonalizadoOpt.isEmpty()) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + productoId);
            }
            ProductoPersonalizado producto = productoPersonalizadoOpt.get();
            floricultorId = producto.getFloricultorId();
        } else {
            Producto producto = productoOpt.get();
            floricultorId = producto.getFloricultorId();
        }

        

        // Obtener el floricultor asociado al producto
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

    @PostMapping("/aceptar/{id}")
    public String aceptarPedido(@PathVariable("id") UUID id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        pedido.setEstado(Pedido.Estados.ACEPTADO);
        pedidoRepository.save(pedido);
        return "redirect:/pedido/floricultor"; // Redirigir a la lista de pedidos del floricultor
    }

    @PostMapping("/rechazar/{id}")
    public String rechazarPedido(@PathVariable("id") UUID id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        pedido.setEstado(Pedido.Estados.DENEGADO);
        pedidoRepository.save(pedido);
        return "redirect:/pedido/floricultor"; // Redirigir a la lista de pedidos del floricultor
    }

    @PostMapping("/entregar/{id}")
    public String entregarPedido(@PathVariable("id") UUID id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        pedido.setEstado(Pedido.Estados.RECOGIDO);
        pedidoRepository.save(pedido);
        return "redirect:/pedido/floricultor"; // Redirigir a la lista de pedidos del floricultor
    }

    @PostMapping("/listo_para_recoger/{id}")
    public String listoParaRecogerPedido(@PathVariable("id") UUID id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
        pedido.setEstado(Pedido.Estados.LISTO_PARA_RECOGIDA);
        pedidoRepository.save(pedido);
        return "redirect:/pedido/floricultor"; // Redirigir a la lista de pedidos del floricultor
    }
}
