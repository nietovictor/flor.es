package es.upm.dit.isst.isstgrupo07flores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.upm.dit.isst.isstgrupo07flores.model.FloresEnPersonalizado;
import java.util.UUID;

@Repository
public interface FloresEnPersonalizadoRepository extends JpaRepository<FloresEnPersonalizado, UUID> {}
