package es.upm.dit.isst.isstgrupo07flores.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import org.springframework.ui.Model;
import java.util.UUID;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.ProductoRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/producto")
public class ProductoViewController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FloricultorRepository floricultorRepository;

    @Autowired
    private ProductoService productoService;

    public ProductoViewController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public String verProducto(@PathVariable UUID id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        return "producto"; // This will resolve to producto.html in templates folder
    }

    @GetMapping("/add")
    public String mostrarFormularioNuevoProducto(Model model) {
        // Añade un objeto Producto vacío al modelo para el formulario
        if (!model.containsAttribute("producto")) {
            model.addAttribute("producto", new Producto());
        }
        return "newProductForm"; // Nombre del archivo HTML (sin extensión)
    }

    @PostMapping("/add")
    public String guardarProducto(@RequestParam("imagenArchivo") MultipartFile imagenArchivo, Producto producto, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            // Asignar un ID único al producto
            producto.setId(UUID.randomUUID());

            // Obtener el correo del floricultor logueado
            String email = authentication.getName();

            // Buscar el floricultor por su correo
            Optional<Floricultor> floricultorOpt = floricultorRepository.findByCorreoElectronico(email);
            if (floricultorOpt.isEmpty()) {
                throw new Exception("No se encontró un floricultor con el correo: " + email);
            }

            // Asignar el ID del floricultor al producto
            producto.setFloricultorId(floricultorOpt.get().getId());

            if (!imagenArchivo.isEmpty()) {
                producto.setImagen(imagenArchivo.getBytes()); // Save uploaded image as byte array
            }

            // Guardar el producto en la base de datos
            productoRepository.save(producto);

            // Redirigir a la página principal si se guarda correctamente
            return "redirect:/";
        } catch (Exception e) {
            // Añadir un mensaje de error al modelo
            redirectAttributes.addFlashAttribute("error", "Error al guardar el producto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("producto", producto); // Mantener los datos del formulario
            return "redirect:/productos/add";
        }
    }

    
}
