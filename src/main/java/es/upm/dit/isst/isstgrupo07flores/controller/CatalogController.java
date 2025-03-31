package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;
import es.upm.dit.isst.isstgrupo07flores.service.CatalogService;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;

@Controller
public class CatalogController {

    @Autowired
    private FloricultorRepository floricultorRepository;

    private final CatalogService catalogService;
    private final ProductoService productoService;

    public CatalogController(CatalogService catalogService, ProductoService productoService) {
        this.catalogService = catalogService;
        this.productoService = productoService;
    }

    // Página inicial para introducir código postal
    @GetMapping("/catalog/search")
    public String postalCodeForm(){
        return "postalCodeForm";
    }

    // Procesar búsqueda de floricultores por CP
    @PostMapping("/catalog/search")
    public String searchByPostalCode(@RequestParam("cp") String postalCode, Model model) {
        try {
            List<Floricultor> floricultores = catalogService.getFloricultoresByPostalCode(postalCode);

        Map<UUID, List<Producto>> productosPorFloricultor = new HashMap<>();
        for (Floricultor f : floricultores) {
            List<Producto> productos = productoService.getProductosByFloricultor(f.getId());
            if (productos == null || productos.isEmpty()) {
                productosPorFloricultor.put(f.getId(), new ArrayList<>()); // lista vacía en vez de null
            } else {
                productosPorFloricultor.put(f.getId(), productos);
            }
        }

        model.addAttribute("floricultores", floricultores);
        model.addAttribute("productosPorFloricultor", productosPorFloricultor);
        model.addAttribute("postalCode", postalCode);

        return "catalogResults";
    } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "postalCodeForm";
        }
    }

    // Procesar búsqueda de floricultores por CP
    @PostMapping("/mycatalog")
    public String searchByFloricultorId(Authentication authentication, Model model) {
        
            String email = authentication.getName();

            Optional<Floricultor> floricultor = floricultorRepository.findByCorreoElectronico(email);
            
            if (floricultor.isEmpty()) {
                throw new IllegalArgumentException("Floricultor no encontrado con el correo: " + email);
            }
            Floricultor floricultorObj = floricultor.get();
            List<Producto> productos = productoService.getProductosByFloricultor(floricultorObj.getId());
            if (productos == null || productos.isEmpty()) {
                productos = new ArrayList<>(); // lista vacía en vez de null
            }
            for (Producto p : productos) {
                System.out.println("Producto ID: " + p.getId());
                System.out.println("Nombre: " + p.getNombre());
                System.out.println("Descripción: " + p.getDescripcion());
                System.out.println("Precio: " + p.getPrecio());
                System.out.println("Stock: " + p.getStock());
            }

        model.addAttribute("floricultor", floricultorObj);
        model.addAttribute("productos", productos);

        return "myCatalog";
        
    } 
}

