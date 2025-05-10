package es.upm.dit.isst.isstgrupo07flores.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;

@Service
public class CatalogService {

    private final FloricultorRepository floricultorRepository;

    public CatalogService(FloricultorRepository floricultorRepository) {
        this.floricultorRepository = floricultorRepository;
    }

    // Nuevo método:
    public List<Floricultor> getFloricultoresByPostalCode(String clientPostalCode) {
        if(clientPostalCode.length() != 5){
            throw new IllegalArgumentException("El código postal del cliente debe tener 5 dígitos.");
        }

        String prefix = clientPostalCode.substring(0,2);
        System.out.println("Buscando floricultores con código postal que comienza con: " + prefix);
        return floricultorRepository.findByCpStartingWithAndVerificadoTrue(prefix);
    }
}
