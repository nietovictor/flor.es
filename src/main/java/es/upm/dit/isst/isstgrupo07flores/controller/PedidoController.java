package es.upm.dit.isst.isstgrupo07flores.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.isstgrupo07flores.model.Pedido;
import es.upm.dit.isst.isstgrupo07flores.repository.PedidoRepository;
import es.upm.dit.isst.isstgrupo07flores.service.CartService;
import es.upm.dit.isst.isstgrupo07flores.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CartService cartService;

    @GetMapping("/clienteId/{id}")
    public List<Pedido> obtenerPedidosPorCliente(@PathVariable("id")  UUID clienteId) {
        return pedidoService.obtenerPedidosPorCliente(clienteId);
    }

    @GetMapping("/floricultorId/{id}")
    public List<Pedido> obtenerPedidosPorFloricultor(@PathVariable("id") UUID floricultorId) {
        return pedidoService.obtenerPedidosPorFloricultor(floricultorId);
    }

}


