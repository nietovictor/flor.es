package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.service.FlorService;

@RestController
@RequestMapping("/flores")
public class FlorController {

    @Autowired
    private FlorService florService;

    @GetMapping
    public List<Flor> getAllFlores() {
        return florService.getAllFlores();
    }


    @GetMapping("/floricultor/{floricultorId}")
    public List<Flor> getFloresByFloricultorId(@PathVariable UUID floricultorId) {
        return florService.getFloresByFloricultorId(floricultorId);
    }

    @PostMapping
    public Flor createFlor(@RequestBody Flor flor) {
        return florService.saveFlor(flor);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlor(@PathVariable UUID id) {
        florService.deleteFlor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> obtenerImagenFlor(@PathVariable UUID id) {
        Flor flor = florService.getFlorById(id);
        if (flor.getImagen() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "image/jpeg");
            return new ResponseEntity<>(flor.getImagen(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
