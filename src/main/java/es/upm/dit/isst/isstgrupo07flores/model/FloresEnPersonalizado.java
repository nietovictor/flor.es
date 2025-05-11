package es.upm.dit.isst.isstgrupo07flores.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class FloresEnPersonalizado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "producto_personalizado_id", nullable = false)
    private UUID productoPersonalizadoId;

    @Column(name = "flor_id", nullable = false)
    private UUID florId;

    @Column(nullable = false)
    private int cantidad;

    // Constructor vacío
    public FloresEnPersonalizado() {}

    // Constructor con parámetros
    public FloresEnPersonalizado(UUID productoPersonalizadoId, UUID florId, int cantidad) {
        this.productoPersonalizadoId = productoPersonalizadoId;
        this.florId = florId;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductoPersonalizadoId() {
        return productoPersonalizadoId;
    }

    public void setProductoPersonalizadoId(UUID productoPersonalizadoId) {
        this.productoPersonalizadoId = productoPersonalizadoId;
    }

    public UUID getFlorId() {
        return florId;
    }

    public void setFlorId(UUID florId) {
        this.florId = florId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
