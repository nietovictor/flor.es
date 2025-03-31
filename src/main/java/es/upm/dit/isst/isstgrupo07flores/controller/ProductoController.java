package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.service.ProductoService;

import org.springframework.ui.Model;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/floricultorId/{id}")
    public List<Producto> getProductosByFloricultor(@PathVariable("id") UUID floricultorId) {
        return productoService.getProductosByFloricultor(floricultorId);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> obtenerImagenProducto(@PathVariable UUID id) {
        Producto producto = productoService.obtenerPorId(id);
        if (producto.getImagen() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "image/jpeg");
            return new ResponseEntity<>(producto.getImagen(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}


