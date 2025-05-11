package es.upm.dit.isst.isstgrupo07flores.controller;

import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.repository.FlorRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/flor")
public class FlorViewController {

    @Autowired
    private FlorRepository florRepository;

    @Autowired
    private FloricultorRepository floricultorRepository;

    @GetMapping("/add")
    public String mostrarFormularioNuevaFlor(Model model) {
        // Añade un objeto Flor vacío al modelo para el formulario
        model.addAttribute("flor", new Flor());
        return "newFlowerForm"; // Nombre del archivo HTML
    }

    

    @PostMapping("/add")
    public String guardarFlor(@RequestParam("imagenArchivo") MultipartFile imagenArchivo, Flor flor, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            // Asignar un ID único a la flor
          

            // Obtener el correo del floricultor logueado
            String email = authentication.getName();

            // Buscar el floricultor por su correo
            Optional<Floricultor> floricultorOpt = floricultorRepository.findByCorreoElectronico(email);
            if (floricultorOpt.isEmpty()) {
                throw new Exception("No se encontró un floricultor con el correo: " + email);
            }

            // Asignar el ID del floricultor a la flor
            flor.setFloricultorId(floricultorOpt.get().getId());

            // Guardar la imagen si se sube
            if (!imagenArchivo.isEmpty()) {
                flor.setImagen(imagenArchivo.getBytes()); // Guardar la imagen como un array de bytes
            }

            // Guardar la flor en la base de datos
            florRepository.save(flor);

            // Redirigir a la página principal si se guarda correctamente
            redirectAttributes.addFlashAttribute("success", "Flor guardada exitosamente.");
            return "redirect:/";
        } catch (Exception e) {
            // Añadir un mensaje de error al modelo
            redirectAttributes.addFlashAttribute("error", "Error al guardar la flor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("flor", flor); // Mantener los datos del formulario
            return "redirect:/flor/add";
        }
    }
}
