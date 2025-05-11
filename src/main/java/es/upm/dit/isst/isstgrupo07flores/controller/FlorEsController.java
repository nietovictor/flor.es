package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlorEsController {

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        String rol = "invitado";
        if (authentication != null) {
            rol = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
        }
        model.addAttribute("rol", rol);
        return "index";  
    }

    @GetMapping("/practicas_sostenibles")
    public String practicasSostenibles(Authentication authentication, Model model) {
        return "buenasPracticas";  
    }
}


 