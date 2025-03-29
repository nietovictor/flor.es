package es.upm.dit.isst.isstgrupo07flores.repository;

import es.upm.dit.isst.isstgrupo07flores.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    
}