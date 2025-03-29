package es.model;

import java.util.UUID;
import java.sql.Timestamp;

public class Pedido {
    private UUID id; 
    private UUID clientId; // PARA QUE SEAN FOREIGN KEYS NO SE SI HAY QUE TOCAR ALGO AQUI
    private UUID sellerId; // PARA QUE SEAN FOREIGN KEYS NO SE SI HAY QUE TOCAR ALGO AQUI
    private float coste;
    private Timestamp fecha;
    private String urlTracking; 

    // Constructor vacío
    public Pedido() {}

    // Constructor con parámetros
    public Pedido(UUID id, UUID clientId, UUID sellerId, float coste, Timestamp fecha, String urlTracking) {
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

    public float getCoste() {
        return coste;
    }

    public void setCoste(float coste) {
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
