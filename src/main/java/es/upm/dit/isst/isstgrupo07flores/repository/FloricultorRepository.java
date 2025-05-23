package es.upm.dit.isst.isstgrupo07flores.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;

@Repository
public interface FloricultorRepository extends JpaRepository<Floricultor, UUID> {
    List<Floricultor> findByCpStartingWithAndVerificadoTrue(String cpPrefix);
    Optional<Floricultor> findByCorreoElectronico(String correoElectronico);
}

