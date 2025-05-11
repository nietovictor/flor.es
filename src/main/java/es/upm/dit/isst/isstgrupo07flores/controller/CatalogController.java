package es.upm.dit.isst.isstgrupo07flores.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.PedidoRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FlorRepository;
import es.upm.dit.isst.isstgrupo07flores.service.CatalogService;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;

@Controller
public class CatalogController {

    @Autowired
    private FloricultorRepository floricultorRepository;
    private final CatalogService catalogService;
    private final ProductoService productoService;
    private final PedidoRepository pedidoRepository;

    @Autowired
    private FlorRepository florRepository;

    public CatalogController(CatalogService catalogService, ProductoService productoService, PedidoRepository pedidoRepository) {
        this.catalogService = catalogService;
        this.productoService = productoService;
        this.pedidoRepository = pedidoRepository;
    }

    // Página inicial para introducir código postal
    @GetMapping("/catalog/search")
    public String searchByPostalCode(@RequestParam(value = "cp", required = false) String postalCode, Model model) {
        if (postalCode == null || postalCode.isEmpty()) {
            return "postalCodeForm"; // Si no se proporciona un código postal, m uestra el formulario
        }

        try {
            // Obtener floricultores por código postal
            List<Floricultor> floricultores = catalogService.getFloricultoresByPostalCode(postalCode);

            Map<UUID, List<Producto>> productosPorFloricultor = new HashMap<>();
            Map<UUID, List<Flor>> floresPorFloricultor = new HashMap<>();
            Map<UUID, Long> valoracionesPorFloricultor = new HashMap<>();

            for (Floricultor f : floricultores) {
                // Obtener productos del floricultor
                List<Producto> productos = productoService.getProductosByFloricultor(f.getId());
                productosPorFloricultor.put(f.getId(), productos != null ? productos : new ArrayList<>());

                // Obtener flores del floricultor
                List<Flor> flores = florRepository.findByFloricultorId(f.getId());
                floresPorFloricultor.put(f.getId(), flores != null ? flores : new ArrayList<>());

                // Contar pedidos valorados del floricultor
                long valoraciones = pedidoRepository.findByFloricultorId(f.getId()).stream()
                    .filter(p -> p.getValoracion() != null)
                    .count();
                valoracionesPorFloricultor.put(f.getId(), valoraciones);
            }

            // Agregar datos al modelo
            model.addAttribute("floricultores", floricultores);
            model.addAttribute("productosPorFloricultor", productosPorFloricultor);
            model.addAttribute("floresPorFloricultor", floresPorFloricultor);
            model.addAttribute("valoracionesPorFloricultor", valoracionesPorFloricultor);
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

             // Obtener flores del floricultor
            List<Flor> flores = florRepository.findByFloricultorId(floricultorObj.getId());
            if (flores == null || flores.isEmpty()) {
                flores = new ArrayList<>(); // Lista vacía en lugar de null
            }


            long valoracionesPorFloricultor = pedidoRepository.findByFloricultorId(floricultorObj.getId()).stream()
                    .filter(p -> p.getValoracion() != null)
                    .count();

        model.addAttribute("floricultor", floricultorObj);
        model.addAttribute("productos", productos);
        model.addAttribute("flores", flores);
        model.addAttribute("valoracionesPorFloricultor", valoracionesPorFloricultor);

        return "myCatalog";
        
    } 

    @GetMapping("/catalog/search/filter")
    public String filterForm(){
        return "postalCodeForm";
    }

    // Procesar búsqueda de floricultores por CP
    @PostMapping("/catalog/search/filter")
    public String searchByFilter(@RequestParam("cp") String postalCode,
                            @RequestParam(value = "ocasion", required = false) String ocasion,
                            @RequestParam(value = "price_min", required = false) Double priceMin,
                            @RequestParam(value = "price_max", required = false) Double priceMax,
                            //@RequestParam(value = "flower_type", required = false) List<String> flowerType,
                            @RequestParam(value = "availability", required = false) String availability,
                             Model model) {

        try {
            // Filtrar floricultores por código postal
            List<Floricultor> floricultores_check = catalogService.getFloricultoresByPostalCode(postalCode);

            Map<UUID, List<Producto>> productosPorFloricultor = new HashMap<>();
            Map<UUID, Long> valoracionesPorFloricultor = new HashMap<>();

            List<Floricultor> floricultores = new ArrayList<>();

            for (Floricultor f : floricultores_check) {
                // Obtener productos del floricultor y aplicar filtros
                List<Producto> productos = productoService.getProductosByFloricultor(f.getId()).stream()
                    .filter(p -> (ocasion == null || (p.getOcasion() != null && p.getOcasion().toString().equalsIgnoreCase(ocasion))))
                    //.filter(p -> (flowerType == null || flowerType.contains(p.getTipo())))
                    .filter(p -> (priceMin == null || p.getPrecio().compareTo(BigDecimal.valueOf(priceMin)) >= 0))
                    .filter(p -> (priceMax == null || p.getPrecio().compareTo(BigDecimal.valueOf(priceMax)) <= 0))
                    .filter(p -> {
                        if ("in_stock".equalsIgnoreCase(availability)) {
                            return p.getStock() > 1;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());


                if (!productos.isEmpty()) {
                    productosPorFloricultor.put(f.getId(), productos);
                    floricultores.add(f);
                    // Contar pedidos valorados del floricultor
                    long valoraciones = pedidoRepository.findByFloricultorId(f.getId()).stream()
                        .filter(p -> p.getValoracion() != null)
                        .count();
                    valoracionesPorFloricultor.put(f.getId(), valoraciones);
                }

                
            }

            model.addAttribute("floricultores", floricultores);
            model.addAttribute("productosPorFloricultor", productosPorFloricultor);
            model.addAttribute("valoracionesPorFloricultor", valoracionesPorFloricultor);
            model.addAttribute("postalCode", postalCode);

            return "catalogResults";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            return "postalCodeForm";
        }
    }
}

