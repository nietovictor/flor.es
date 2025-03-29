package es.upm.dit.isst.isstgrupo07flores.controller;

import es.upm.dit.isst.isstgrupo07flores.repository.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

@RestController
@RequestMapping("/myApi")
public class FlorEsController {
    private final ClienteRepository clienteRepository;
    private final FloricultorRepository floricultorRepository;
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;

    public FlorEsController(ClienteRepository cliente, FloricultorRepository floricultor, ProductoRepository producto, PedidoRepository pedido) {
        this.clienteRepository = cliente;
        this.floricultorRepository = floricultor;
        this.productoRepository = producto;
        this.pedidoRepository = pedido;
    }


}


 