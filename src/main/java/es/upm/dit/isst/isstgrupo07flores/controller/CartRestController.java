package es.upm.dit.isst.isstgrupo07flores.controller;

import es.upm.dit.isst.isstgrupo07flores.model.Cart;
import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.service.CartService;
import es.upm.dit.isst.isstgrupo07flores.service.FlorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import jakarta.servlet.http.HttpSession;



@RestController // Cambia a RestController
@RequestMapping("/api/cart") // Cambia la ruta para diferenciarla de los controladores normales
public class CartRestController {

    @Autowired
    private CartService cartService;
    @Autowired
    private FlorService florService;

    @PostMapping("/addFlower/{flowerId}")
    public ResponseEntity<Map<String, String>> addFlowerToCart(
            @PathVariable UUID flowerId,
            HttpSession session) {
        Map<String, String> response = new HashMap<>();
        try {
            Cart cart = cartService.getCart(session);

            // Asegurarse de que la lista de flores no sea null
            if (cart.getFlores() == null) {
                cart.setFlores(new ArrayList<>()); // Inicializar como lista vacía
            }

            // Verificar si ya hay un producto en el carrito
            if (cart.getProducto() != null) {
                response.put("status", "error");
                response.put("message", "No puedes añadir flores si ya hay un producto en el carrito.");
                return ResponseEntity.badRequest().body(response);
            }

            // Verificar si ya hay flores en el carrito
            if (cart.getFlores().isEmpty()) {
                cartService.addFlowerToCart(flowerId, session);
                response.put("status", "success");
                response.put("message", "Flor añadida al carrito correctamente.");
            } else {
                Flor florExistente = cart.getFlores().get(0); // Guarda la primera flor del carrito
                if (!florExistente.getFloricultorId().equals(florService.getFlorById(flowerId).getFloricultorId())) {
                    response.put("status", "error");
                    response.put("message", "Solo puedes añadir flores del mismo floricultor.");
                    return ResponseEntity.badRequest().body(response);
                } else {
                    cartService.addFlowerToCart(flowerId, session);
                    response.put("status", "success");
                    response.put("message", "Flor añadida al carrito correctamente.");
                }
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Ha ocurrido un error al añadir la flor al carrito.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }
}