package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;

@Controller
public class FlorEsController {

    @Autowired
    private FloricultorRepository floricultorRepository;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        String rol = "invitado";
        if (authentication != null) {
            rol = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            String email = authentication.getName();

            Optional<Floricultor> floricultor = floricultorRepository.findByCorreoElectronico(email);

            Floricultor floricultorObj = floricultor.orElse(new Floricultor()); // Devuelve un objeto vacío si no se encuentra
            model.addAttribute("rol", rol);
            model.addAttribute(floricultorObj);
            return "index";  
        }

        model.addAttribute("rol", rol);
        return "index";  
    }

    @GetMapping("/practicas_sostenibles")
    public String practicasSostenibles(Authentication authentication, Model model) {
        return "buenasPracticas";  
    }
}


