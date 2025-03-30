package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.UUID;
import java.math.BigDecimal;
import java.sql.Timestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private UUID id; 

    /* @ManyToOne
    @JoinColumn(name = "client_id", nullable = false) // Clave foránea hacia Cliente
    private Cliente cliente; 

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false) // Clave foránea hacia Floricultor
    private Floricultor floricultor; 

    @ManyToOne
    @JoinColumn(name = "producto", nullable = false) // Clave foránea hacia Floricultor
    private Producto producto;  */

    // Relación con Cliente basada en su ID
    @Column(name = "client_id", nullable = false)
    private UUID clienteId; 

    // Relación con Floricultor basada en su ID
    @Column(name = "seller_id", nullable = false)
    private UUID floricultorId; 

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

    // Constructor vacío
    public Pedido() {}

    // Constructor con parámetros
    public Pedido(UUID id, UUID clienteId, UUID floricultorId, UUID productoId, BigDecimal coste, Timestamp fecha, String urlTracking, float valoracion) {
        this.id = id;
        this.clienteId = clienteId;
        this.floricultorId = floricultorId;
        this.productoId = productoId;
        this.coste = coste;
        this.fecha = fecha;
        this.urlTracking = urlTracking;
        this.valoracion = valoracion;
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

    public UUID getFloricultorId() {
        return floricultorId;
    }

    public void setFloricultorId(UUID floricultorId) {
        this.floricultorId = floricultorId;
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

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", floricultorId=" + floricultorId +
                ", productoId=" + productoId +
                ", coste=" + coste +
                ", fecha=" + fecha +
                ", urlTracking='" + urlTracking + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
