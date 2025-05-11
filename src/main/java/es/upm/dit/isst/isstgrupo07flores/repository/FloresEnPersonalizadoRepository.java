package es.upm.dit.isst.isstgrupo07flores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.upm.dit.isst.isstgrupo07flores.model.FloresEnPersonalizado;
import java.util.UUID;
import java.util.List;

@Repository
public interface FloresEnPersonalizadoRepository extends JpaRepository<FloresEnPersonalizado, UUID> {

    List<FloresEnPersonalizado> findByProductoPersonalizadoId(UUID productoPersonalizadoId);

}
