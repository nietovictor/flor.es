package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.UUID;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.upm.dit.isst.isstgrupo07flores.model.Cart;
import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.service.CartService;
import es.upm.dit.isst.isstgrupo07flores.service.FlorService;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private FlorService florService;

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);

        // Asegurarse de que la lista de flores no sea null
        if (cart.getFlores() == null) {
            cart.setFlores(new ArrayList<>()); // Inicializar como lista vacía
        }

        // Agrupar flores por nombre y calcular la cantidad y el precio total
        Map<String, Map<String, Object>> floresAgrupadas = cart.getFlores().stream()
            .collect(Collectors.groupingBy(
                Flor::getNombre,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    flores -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("cantidad", (long) flores.size());
                        data.put("precio", flores.get(0).getPrecio()); // Suponiendo que todas las flores del mismo tipo tienen el mismo precio
                        return data;
                    }
                )
            ));

        // Calcular el precio total
        double precioTotal = floresAgrupadas.values().stream()
            .mapToDouble(data -> (long) data.get("cantidad") * (double) data.get("precio"))
            .sum();
        cart.setPrecioTotal(BigDecimal.valueOf(precioTotal)); // Establecer el precio total en el carrito

        model.addAttribute("cart", cart);
        model.addAttribute("floresAgrupadas", floresAgrupadas); // Pasar flores agrupadas al modelo
        return "cesta"; // Thymeleaf buscará el archivo cesta.html
    }

    @PostMapping("/addProduct/{productId}")
    public String addProductToCart(@PathVariable UUID productId, 
                                    RedirectAttributes redirectAttributes, 
                                    HttpSession session,
                                   @RequestHeader(value = "Referer", required = false) String referer) {
        Cart cart = cartService.getCart(session);

        // Asegurarse de que la lista de flores no sea null
        if (cart.getFlores() == null) {
            cart.setFlores(new ArrayList<>()); // Inicializar como lista vacía
        }   

        if (cart.getFlores().isEmpty()) {
            cartService.addProductToCart(productId, session);
            return "redirect:/cart/view"; // Redirige a la vista de la cesta
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No puedes añadir flores y productos al mismo tiempo");
        }
        // Si el encabezado Referer está presente, redirige a esa URL
        if (referer != null) {
            return "redirect:" + referer;
        }

        // Si no hay Referer, redirige a una página predeterminada
        return "redirect:/catalog/search";
    }

    @PostMapping("/addFlower/{flowerId}")
    public String addFlowerToCart(
            @PathVariable UUID flowerId,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            Cart cart = cartService.getCart(session);

            // Asegurarse de que la lista de flores no sea null
            if (cart.getFlores() == null) {
                cart.setFlores(new ArrayList<>()); // Inicializar como lista vacía
            }   

            // Verificar si ya hay un producto en el carrito
            if (cart.getProducto() != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "No puedes añadir flores si ya hay un producto en el carrito.");
                return "redirect:" + (referer != null ? referer : "/catalog/search");
            }
            
            // Verificar si ya hay flores en el carrito
            if (cartService.getCart(session).getFlores().isEmpty()) {
                cartService.addFlowerToCart(flowerId, session);
                redirectAttributes.addFlashAttribute("successMessage", "Flor añadida al carrito");
            } else {
                Flor florExistente = cartService.getCart(session).getFlores().get(0); // Guarda la primera flor del carrito
                if (!florExistente.getFloricultorId().equals(florService.getFlorById(flowerId).getFloricultorId())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Solo puedes añadir flores del mismo floricultor");
                } else {
                    cartService.addFlowerToCart(flowerId, session);
                    redirectAttributes.addFlashAttribute("successMessage", "Flor añadida al carrito");
                }
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ha ocurrido un error al añadir la flor al carrito");
        }
        
        return "redirect:" + (referer != null ? referer : "/catalog/search");
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session); // Limpia la cesta
        return "redirect:/cart/view"; // Redirige a la vista de la cesta
    }
}
