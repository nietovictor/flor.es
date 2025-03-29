package es.upm.dit.isst.isstgrupo07flores.repository;

import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);  // No se si son necesarios
    Cliente findByEmail(String email);                              // No se si son necesarios
}