package es.upm.dit.isst.isstgrupo07flores.controller;

import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.repository.FlorRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
            redirectAttributes.addFlashAttribute("successMessage", "Flor guardada exitosamente.");
            return "redirect:/mycatalog";
        } catch (Exception e) {
            // Añadir un mensaje de error al modelo
            redirectAttributes.addFlashAttribute("errorMessage", "Error al guardar la flor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("flor", flor); // Mantener los datos del formulario
            return "redirect:/flor/add";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable UUID id, Model model) {
        // Buscar la flor por su ID
        Flor flor = florRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Flor no encontrada"));
        model.addAttribute("flor", flor); // Añadir la flor al modelo
        return "editFlorForm"; // Nombre del archivo HTML para editar flores
    }

    @PostMapping("/editar/guardar")
    public String guardarFlorEditada(@ModelAttribute Flor florEditada, @RequestParam("imagenArchivo") MultipartFile imagenArchivo, RedirectAttributes redirectAttributes) {
        // Buscar la flor existente en la base de datos
        Flor florExistente = florRepository.findById(florEditada.getId()).orElseThrow(() -> new IllegalArgumentException("Flor no encontrada"));

        // Mantener el ID del floricultor
        florEditada.setFloricultorId(florExistente.getFloricultorId());

        // Procesar la imagen
        if (!imagenArchivo.isEmpty()) {
            try {
                florEditada.setImagen(imagenArchivo.getBytes()); // Guardar la nueva imagen como un array de bytes
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen", e);
            }
        } 

        // Guardar la flor editada en la base de datos
        florRepository.save(florEditada);

        // Añadir un mensaje de éxito
        redirectAttributes.addFlashAttribute("success", "Flor editada exitosamente.");
        return "redirect:/mycatalog"; // Redirigir al catálogo
    }
}
