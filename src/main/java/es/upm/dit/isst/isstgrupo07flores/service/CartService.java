package es.upm.dit.isst.isstgrupo07flores.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import es.upm.dit.isst.isstgrupo07flores.model.Cart;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.repository.FlorRepository;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";
    private final ProductoService productoService;

    @Autowired
    private FlorService florService; // Inyección de FlorRepository

    public CartService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addProductToCart(UUID productId, HttpSession session) {
        Producto producto = productoService.obtenerPorId(productId); // Obtiene el producto por su ID
        Cart cart = getCart(session);
        cart.setProducto(producto); // Reemplazaría el producto en la cesta
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void addFlowerToCart(UUID florId, HttpSession session) {
        Flor flor = florService.getFlorById(florId); // Obtiene la flor por su ID
        Cart cart = getCart(session);
        cart.addFlor(flor); // Añade la flor a la cesta
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public Producto getCartProduct(HttpSession session) {
        return getCart(session).getProducto(); // Devuelve el producto en la cesta
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}