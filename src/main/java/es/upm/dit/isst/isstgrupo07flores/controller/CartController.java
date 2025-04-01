package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.upm.dit.isst.isstgrupo07flores.service.CartService;
import jakarta.servlet.http.HttpSession;


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

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session); // Limpia la cesta
        return "redirect:/cart/view"; // Redirige a la vista de la cesta
    }
}
