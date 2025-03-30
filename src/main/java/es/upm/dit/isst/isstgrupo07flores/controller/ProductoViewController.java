package es.upm.dit.isst.isstgrupo07flores.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import org.springframework.ui.Model;
import java.util.UUID;

@Controller
@RequestMapping("/producto")
public class ProductoViewController {

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
    
}
