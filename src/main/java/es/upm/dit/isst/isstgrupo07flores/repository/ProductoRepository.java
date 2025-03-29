package es.upm.dit.isst.isstgrupo07flores.repository;

import es.upm.dit.isst.isstgrupo07flores.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    
    // Buscar productos por nombre (contiene la palabra clave)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Buscar productos en un rango de precios
    List<Producto> findByPrecioBetween(Double min, Double max);

    // Buscar productos por color
    List<Producto> findByColorIgnoreCase(String color);

    // Buscar productos por ocasión
    List<Producto> findByOcasionIgnoreCase(String ocasion);

    // Buscar productos por color y ocasión al mismo tiempo
    List<Producto> findByColorIgnoreCaseAndOcasionIgnoreCase(String color, String ocasion);
}