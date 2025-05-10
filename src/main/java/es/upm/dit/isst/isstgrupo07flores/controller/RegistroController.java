package es.upm.dit.isst.isstgrupo07flores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.ClienteRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FloricultorRepository floricultorRepository;

    @GetMapping("")
    public String newUserForm() {
        return "newUserForm"; // Nombre de la vista del formulario de registro
    }

    @PostMapping("/cliente")
    public String registrarCliente(Cliente cliente, RedirectAttributes redirectAttributes) {
        // Verificar si el correo electrónico ya está registrado
        if (clienteRepository.findByCorreoElectronico(cliente.getCorreoElectronico()).isPresent() || floricultorRepository.findByCorreoElectronico(cliente.getCorreoElectronico()).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "El correo electrónico ya está registrado.");
            return "redirect:/registro";
        }
        // Verificar si el número de teléfono tiene un formato adecuado
        if (!cliente.getTelefono().matches("\\d{9}")) {
            redirectAttributes.addFlashAttribute("errorMessage", "El número de teléfono debe tener 9 dígitos.");
            return "redirect:/registro";
        }
        
        
        // Encriptar la contraseña con BCrypt
        String encryptedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(cliente.getContrasena());
        cliente.setContrasena(encryptedPassword);
        
        // Guardar el nuevo cliente en la base de datos
        clienteRepository.save(cliente);
        redirectAttributes.addFlashAttribute("successMessage", "Registro exitoso. Puedes iniciar sesión ahora.");
        return "redirect:/login";
    
    }

    @PostMapping("/floricultor")
    public String registrarFloricultor(Floricultor floricultor, RedirectAttributes redirectAttributes) {
        // Verificar si el correo electrónico ya está registrado
        if (floricultorRepository.findByCorreoElectronico(floricultor.getCorreoElectronico()).isPresent() || clienteRepository.findByCorreoElectronico(floricultor.getCorreoElectronico()).isPresent()) {
            // Si el correo electrónico ya está registrado, redirigir con un mensaje de error
            redirectAttributes.addFlashAttribute("errorMessage", "El correo electrónico ya está registrado.");
            return "redirect:/registro";
        }
        // Verificar si el código postal tiene un formato adecuado
        if (!floricultor.getCp().matches("\\d{5}")) {
            redirectAttributes.addFlashAttribute("errorMessage", "El código postal debe tener 5 dígitos.");
            return "redirect:/registro";
        }
        // Verificar si el nombre no está vacío
        if (floricultor.getNombre() == null || floricultor.getNombre().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "El nombre no puede estar vacío.");
            return "redirect:/registro";
        }

        // Verificar si la dirección no está vacía
        if (floricultor.getDireccion() == null || floricultor.getDireccion().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "La dirección no puede estar vacía.");
            return "redirect:/registro";
        }
        
        // Encriptar la contraseña con BCrypt
        String encryptedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(floricultor.getContrasena());
        floricultor.setContrasena(encryptedPassword);

        // Guardar el nuevo floricultor en la base de datos
        floricultorRepository.save(floricultor);
        redirectAttributes.addFlashAttribute("successMessage", "Registro exitoso. Puedes iniciar sesión ahora.");
        return "redirect:/login";
    }
}
