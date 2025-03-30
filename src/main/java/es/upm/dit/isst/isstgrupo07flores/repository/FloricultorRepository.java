package es.upm.dit.isst.isstgrupo07flores.repository;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Repository
public interface FloricultorRepository extends JpaRepository<Floricultor, UUID> {
    Optional<Floricultor> findByCorreoElectronico(String correoElectronico);
}