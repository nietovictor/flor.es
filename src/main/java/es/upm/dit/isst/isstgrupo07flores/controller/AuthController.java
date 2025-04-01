package es.upm.dit.isst.isstgrupo07flores.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AuthController {

    // Página de login (la autenticación la gestiona Spring Security)
    @GetMapping("/login")
    public String login() {
        return "login";  // Corresponde al template login.html
    }

    
}