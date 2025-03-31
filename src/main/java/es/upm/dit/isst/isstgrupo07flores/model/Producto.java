package es.upm.dit.isst.isstgrupo07flores.model;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Producto {
    @Id
    private UUID id; 

    @Column(nullable = false)
    private UUID floricultorId; 

    private String nombre; 

    private String descripcion; 

    private BigDecimal precio; 

    @PositiveOrZero
    private int stock; 

    private String urlImg; 

    @Lob
    private byte[] imagen; // Store image as byte array

    @Enumerated(EnumType.ORDINAL) // Guardar como TINYINT (ordinal del enum)
    private Ocasiones ocasion;

    // Enum para las ocasiones
    public enum Ocasiones {
        CUMPLEANOS, CONDOLENCIAS, ANIVERSARIOS, SAN_VALENTIN    // USANDO LA Ñ A LO MEJOR DA PROBLEMAS, HABRIA QUE MIRARLO
    }

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(UUID id, UUID floricultorId, String nombre, String descripcion, BigDecimal precio, int stock, String urlImg, Ocasiones ocasion) {
        this.id = id;
        this.floricultorId = floricultorId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.urlImg = urlImg;
        this.ocasion = ocasion;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFloricultorId() {
        return floricultorId;
    }

    public void setFloricultorId(UUID floricultorId) {
        this.floricultorId = floricultorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Ocasiones getOcasion() {
        return ocasion;
    }

    public void setOcasion(Ocasiones ocasion) {
        this.ocasion = ocasion;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", floricultorId=" + floricultorId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", urlImg='" + urlImg + '\'' +
                ", ocasion=" + ocasion +
                '}';
    }
}
