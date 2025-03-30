package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;

import org.springframework.ui.Model;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/floricultorId/{id}")
    public List<Producto> getProductosByFloricultor(@PathVariable("id") UUID floricultorId) {
        return productoService.getProductosByFloricultor(floricultorId);
    }
}


