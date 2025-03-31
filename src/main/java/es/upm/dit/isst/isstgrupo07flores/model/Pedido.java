package es.upm.dit.isst.isstgrupo07flores.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private UUID id; 

    // Relación con Cliente basada en su ID
    @Column(name = "client_id", nullable = false)
    private UUID clienteId; 

    // Relación con Producto basada en su ID
    @Column(name = "producto_id", nullable = false)
    private UUID productoId; 

    @Positive
    @Digits(integer = 3, fraction = 2, message = "El precio debe tener hasta 3 dígitos enteros y 2 decimales")
    private BigDecimal coste; 

    @CreationTimestamp 
    private Timestamp fecha;

    private String urlTracking; 

    private float valoracion; 

    @Enumerated(EnumType.ORDINAL) // Guardar como TINYINT (ordinal del enum)
    private Estados estado;

    // Enum para los estados
    public enum Estados {
        SOLICITADO, ACEPTADO, DENEGADO, LISTO_PARA_RECOGIDA, RECOGIDO    // USANDO LA Ñ A LO MEJOR DA PROBLEMAS, HABRIA QUE MIRARLO
    }

    // Constructor vacío
    public Pedido() {}

    // Constructor con parámetros
    public Pedido(UUID id, UUID clienteId, UUID productoId, BigDecimal coste, Timestamp fecha, String urlTracking, float valoracion, Estados estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.coste = coste;
        this.fecha = fecha;
        this.urlTracking = urlTracking;
        this.valoracion = valoracion;
        this.estado = estado;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }


    public UUID getProductoId() {
        return productoId;
    }

    public void setProductoId(UUID productoId) {
        this.productoId = productoId;
    }

    public BigDecimal getCoste() {
        return coste;
    }

    public void setCoste(BigDecimal coste) {
        this.coste = coste;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getUrlTracking() {
        return urlTracking;
    }

    public void setUrlTracking(String urlTracking) {
        this.urlTracking = urlTracking;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", productoId=" + productoId +
                ", coste=" + coste +
                ", fecha=" + fecha +
                ", urlTracking='" + urlTracking + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
