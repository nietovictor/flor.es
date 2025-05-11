package es.upm.dit.isst.isstgrupo07flores.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.dit.isst.isstgrupo07flores.model.Flor;
import es.upm.dit.isst.isstgrupo07flores.repository.FlorRepository;

@Service
public class FlorService {

    @Autowired
    private FlorRepository florRepository;

    public List<Flor> getAllFlores() {
        return florRepository.findAll();
    }

    public Flor getFlorById(UUID id) {
        return florRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    public List<Flor> getFloresByFloricultorId(UUID floricultorId) {
        return florRepository.findByFloricultorId(floricultorId);
    }

    public Flor saveFlor(Flor flor) {
        return florRepository.save(flor);
    }

    public void deleteFlor(UUID id) {
        florRepository.deleteById(id);
    }
}
