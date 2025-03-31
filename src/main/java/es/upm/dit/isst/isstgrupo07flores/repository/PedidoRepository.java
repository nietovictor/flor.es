package es.upm.dit.isst.isstgrupo07flores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import es.upm.dit.isst.isstgrupo07flores.model.*;
import java.util.List;
import java.util.UUID;
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByClienteId(UUID clienteId);
    
    @Query("SELECT p FROM Pedido p JOIN Producto pr ON p.productoId = pr.id WHERE pr.floricultorId = :floricultorId")
    List<Pedido> findByFloricultorId(UUID floricultorId);
}