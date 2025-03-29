package es.upm.dit.isst.isstgrupo07flores.repository;

import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface FloricultorRepository extends JpaRepository<Floricultor, UUID> {
    List<Floricultor> findByComunidad(String localidad); // Esto lo usaremos para comprobar si el floricultor está 
                                                         // en la misma comunidad que el cliente, no tengo claro donde se implementa el metodo
}