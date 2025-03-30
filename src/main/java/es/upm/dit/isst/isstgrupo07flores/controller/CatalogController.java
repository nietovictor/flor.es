package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.service.CatalogService;

@Controller
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
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
            model.addAttribute("floricultores", floricultores);
            model.addAttribute("postalCode", postalCode);
            return "catalogResults";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "postalCodeForm";
        }
    }
}

