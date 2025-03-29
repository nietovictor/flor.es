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

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false) // Clave foránea hacia Cliente
    private Cliente cliente; 

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false) // Clave foránea hacia Floricultor
    private Floricultor floricultor; 

    @ManyToOne
    @JoinColumn(name = "producto", nullable = false) // Clave foránea hacia Floricultor
    private Producto producto; 

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
    public Pedido(UUID id, Cliente cliente, Floricultor floricultor, BigDecimal coste, Timestamp fecha, String urlTracking, float valoracion) {
        this.id = id;
        this.cliente = cliente;
        this.floricultor = floricultor;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Floricultor getFloricultor() {
        return floricultor;
    }

    public void setFloricultor(Floricultor floricultor) {
        this.floricultor = floricultor;
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
                ", cliente=" + cliente +
                ", floricultor=" + floricultor +
                ", coste=" + coste +
                ", fecha=" + fecha +
                ", urlTracking='" + urlTracking + '\'' +
                '}';
    }
}
