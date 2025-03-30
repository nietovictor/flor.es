package es.upm.dit.isst.isstgrupo07flores.service;

import es.upm.dit.isst.isstgrupo07flores.model.Pedido;
import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

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
}