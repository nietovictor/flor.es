package es.upm.dit.isst.isstgrupo07flores.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.upm.dit.isst.isstgrupo07flores.model.Flor;

@Repository
public interface FlorRepository extends JpaRepository<Flor, UUID> {
    // Buscar flores por floricultor
    List<Flor> findByFloricultorId(UUID floricultorId);
}
