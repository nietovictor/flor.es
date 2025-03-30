package es.upm.dit.isst.isstgrupo07flores.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.service.CartService;
import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import es.upm.dit.isst.isstgrupo07flores.model.Cart;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;
import java.util.UUID;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cart", cartService.getCart(session)); // Obtiene la cesta de la sesión
        return "cesta"; // Thymeleaf buscará un archivo "cesta.html" en src/main/resources/templates/
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable UUID productId, HttpSession session) {
        cartService.addProductToCart(productId, session);
        return "redirect:/cart/view"; // Redirige a la vista de la cesta
    }

    @DeleteMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session); // Limpia la cesta
        return "redirect:/cart/view"; // Redirige a la vista de la cesta
    }
}
