package es.upm.dit.isst.isstgrupo07flores.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import es.upm.dit.isst.isstgrupo07flores.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductosByFloricultor(UUID floricultorId) {
        return productoRepository.findByFloricultorId(floricultorId);
    }
}