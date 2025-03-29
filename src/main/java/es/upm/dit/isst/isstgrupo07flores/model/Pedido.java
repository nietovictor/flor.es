package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.UUID;
import java.math.BigDecimal;
import java.sql.Timestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private UUID id; 
    private UUID clientId; // PARA QUE SEAN FOREIGN KEYS NO SE SI HAY QUE TOCAR ALGO AQUI
    private UUID sellerId; // PARA QUE SEAN FOREIGN KEYS NO SE SI HAY QUE TOCAR ALGO AQUI

    // No se si hay que hacerlo con BigDecimal.
    // No se si hay que restringir el tema de los decimales de esta forma.
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    @Digits(integer = 3, fraction = 2, message = "El precio debe tener hasta 3 dígitos enteros y 2 decimales")
    private BigDecimal coste; 

    @CreationTimestamp private Timestamp fecha;
    private String urlTracking; 

    // Constructor vacío
    public Pedido() {}

    // Constructor con parámetros
    public Pedido(UUID id, UUID clientId, UUID sellerId, BigDecimal coste, Timestamp fecha, String urlTracking) {
        this.id = id;
        this.clientId = clientId;
        this.sellerId = sellerId;
        this.coste = coste;
        this.fecha = fecha;
        this.urlTracking = urlTracking;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
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

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", sellerId=" + sellerId +
                ", coste=" + coste +
                ", fecha=" + fecha +
                ", urlTracking='" + urlTracking + '\'' +
                '}';
    }
}
