package es.upm.dit.isst.isstgrupo07flores.controller;

import es.upm.dit.isst.isstgrupo07flores.model.Pedido;
import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/clienteId/{id}")
    public List<Pedido> obtenerPedidosPorCliente(@PathVariable("id")  UUID clienteId) {
        return pedidoService.obtenerPedidosPorCliente(clienteId);
    }

    @GetMapping("/floricultorId/{id}")
    public List<Pedido> obtenerPedidosPorFloricultor(@PathVariable("id") UUID floricultorId) {
        return pedidoService.obtenerPedidosPorFloricultor(floricultorId);
    }
}


