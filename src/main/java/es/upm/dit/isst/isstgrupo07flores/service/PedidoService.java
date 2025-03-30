package es.upm.dit.isst.isstgrupo07flores.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.dit.isst.isstgrupo07flores.model.Pedido;
import es.upm.dit.isst.isstgrupo07flores.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> obtenerPedidosPorCliente(UUID clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> obtenerPedidosPorFloricultor(UUID floricultorId) {
        return pedidoRepository.findByFloricultorId(floricultorId);
    }

    public List<Pedido> getProductosByFloricultor(UUID floricultorId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}