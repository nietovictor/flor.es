package es.upm.dit.isst.isstgrupo07flores;

import es.upm.dit.isst.isstgrupo07flores.model.*;
import es.upm.dit.isst.isstgrupo07flores.repository.*;
import es.upm.dit.isst.isstgrupo07flores.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestsUnitarios {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private FlorService florService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioService usuarioService;
    
    @MockBean
    private CartService cartService; // Usamos MockBean para inyectar un mock del CartService

    @Autowired
    private FlorRepository florRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FloricultorRepository floricultorRepository;

    private Flor flor;
    private Producto producto;
    private Cliente cliente;
    private Floricultor floricultor;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        floricultor = new Floricultor();
        floricultor.setNombre("Flor Test");
        floricultor.setCorreoElectronico("flor@test.com");
        floricultor.setContrasena("1234");
        floricultor.setCp("28001");
        floricultor.setNif("12345678A");
        floricultor.setDireccion("Calle Test");
        floricultor.setVerificado(true);
        floricultor = floricultorRepository.save(floricultor);

        cliente = new Cliente();
        cliente.setCorreoElectronico("cliente@test.com");
        cliente.setContrasena("abcd");
        cliente.setTelefono("666666666");
        cliente = clienteRepository.save(cliente);

        flor = new Flor();
        flor.setNombre("Rosa");
        flor.setDescripcion("Rosa roja");
        flor.setPrecio(10.0);
        flor.setStock(100);
        flor.setImagen(new byte[]{});
        flor.setFloricultorId(floricultor.getId());
        flor = florRepository.save(flor);

        producto = new Producto();
        producto.setNombre("Ramo");
        producto.setDescripcion("Ramo de flores");
        producto.setPrecio(new BigDecimal("20.0"));
        producto.setStock(50);
        producto.setImagen(new byte[]{});
        producto.setFloricultorId(floricultor.getId());
        producto = productoRepository.save(producto);

        pedido = new Pedido();
        pedido.setClienteId(cliente.getId());
        pedido.setProductoId(producto.getId());
        pedido.setCoste(producto.getPrecio());
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));
        pedido.setUrlTracking("http://tracking.com/123");
        pedido.setValoracion(4);
        pedido.setEstado(Pedido.Estados.SOLICITADO);
        pedido.setDireccionentrega("Calle Entrega");
        pedido.setFechaEntrega(LocalDate.of(2025, 6, 15));
        pedido = pedidoRepository.save(pedido);
    }

    @AfterEach
    void tearDown() {
        pedidoRepository.deleteAll();
        productoRepository.deleteAll();
        florRepository.deleteAll();
        clienteRepository.deleteAll();
        floricultorRepository.deleteAll();
    }

    @Test
    // CartService tests
    void testAddProductToCartAndClearCartWithMock() {
        HttpSession session = mock(HttpSession.class);
        Cart cart = new Cart();
        cart.setProducto(producto);

        // Ignorar llamadas redundantes
        when(cartService.getCart(session)).thenReturn(cart);

        cartService.addProductToCart(producto.getId(), session);
        verify(cartService).addProductToCart(producto.getId(), session);

        cartService.clearCart(session);
        verify(cartService).clearCart(session);
    }

    @Test
    void testAddFlowerToCartWithMock() {
        HttpSession session = mock(HttpSession.class);
        Cart cart = new Cart();
        cart.setFlores(new ArrayList<>(List.of(flor)));

        when(cartService.getCart(session)).thenReturn(cart);

        cartService.addFlowerToCart(flor.getId(), session);
        verify(cartService).addFlowerToCart(flor.getId(), session);
    }


    // CatalogService tests
    @Test
    void testGetFloricultoresByPostalCode() {
        List<Floricultor> result = catalogService.getFloricultoresByPostalCode("28001");
        assertFalse(result.isEmpty());
        assertEquals("28001", result.get(0).getCp());
    }

    @Test
    void testGetFloricultoresByPostalCodeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> catalogService.getFloricultoresByPostalCode("123"));
    }

    // FlorService tests
    @Test
    void testGetAllFlores() {
        List<Flor> flores = florService.getAllFlores();
        assertTrue(flores.stream().anyMatch(f -> f.getId().equals(flor.getId())));
    }

    @Test
    void testGetFlorById() {
        Flor found = florService.getFlorById(flor.getId());
        assertEquals(flor.getNombre(), found.getNombre());
    }

    @Test
    void testGetFloresByFloricultorId() {
        List<Flor> flores = florService.getFloresByFloricultorId(floricultor.getId());
        assertTrue(flores.stream().anyMatch(f -> f.getId().equals(flor.getId())));
    }

    @Test
    void testSaveAndDeleteFlor() {
        Flor nuevaFlor = new Flor();
        nuevaFlor.setNombre("Tulipán");
        nuevaFlor.setDescripcion("Flor de primavera");
        nuevaFlor.setPrecio(5.0);
        nuevaFlor.setStock(30);
        nuevaFlor.setImagen(new byte[]{});
        nuevaFlor.setFloricultorId(floricultor.getId());
        Flor saved = florService.saveFlor(nuevaFlor);
        assertNotNull(saved.getId());
        florService.deleteFlor(saved.getId());
        assertFalse(florRepository.findById(saved.getId()).isPresent());
    }

    // PedidoService tests
    @Test
    void testObtenerPedidosPorCliente() {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorCliente(cliente.getId());
        assertTrue(pedidos.stream().anyMatch(p -> p.getId().equals(pedido.getId())));
    }

    @Test
    void testObtenerPedidosPorFloricultor() {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorFloricultor(floricultor.getId());
        assertTrue(pedidos.stream().anyMatch(p -> p.getId().equals(pedido.getId())));
    }

    // ProductoService tests
    @Test
    void testGetProductosByFloricultor() {
        List<Producto> productos = productoService.getProductosByFloricultor(floricultor.getId());
        assertTrue(productos.stream().anyMatch(p -> p.getId().equals(producto.getId())));
    }

    @Test
    void testObtenerPorId() {
        Producto found = productoService.obtenerPorId(producto.getId());
        assertEquals(producto.getNombre(), found.getNombre());
    }

    @Test
    void testObtenerTodos() {
        Iterable<Producto> productos = productoService.obtenerTodos();
        assertTrue(productos.iterator().hasNext());
        assertTrue(StreamSupport.stream(productos.spliterator(), false)
                        .anyMatch(p -> p.getId().equals(producto.getId())));
    }

    // UsuarioService tests
    @Test
    void testLoadUserByUsernameCliente() {
        UserDetails user = usuarioService.loadUserByUsername(cliente.getCorreoElectronico());
        assertEquals(cliente.getCorreoElectronico(), user.getUsername());
        assertTrue(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE")));
    }

    @Test
    void testLoadUserByUsernameFloricultor() {
        UserDetails user = usuarioService.loadUserByUsername(floricultor.getCorreoElectronico());
        assertEquals(floricultor.getCorreoElectronico(), user.getUsername());
        assertTrue(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_FLORICULTOR")));
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> usuarioService.loadUserByUsername("noexiste@test.com"));
    }
}
