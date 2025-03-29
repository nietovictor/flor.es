package es.upm.dit.isst.isstgrupo07flores.model;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; 

    @ManyToOne
    @JoinColumn(name = "floricultor_id", nullable = false) // Clave foránea hacia Floricultor
    private Floricultor floricultor; 

    private String nombre; 

    private String descripcion; 

    @PositiveOrZero // Dejamos que sea 0 por si hay problemas para añadir cosas como envoltorio/bolsa que no tienen precio
    @Digits(integer = 3, fraction = 2, message = "El precio debe tener hasta 3 dígitos enteros y 2 decimales")
    private BigDecimal precio; 

    @PositiveOrZero
    private int stock; 

    private String urlImg; 

    private Ocasiones ocasion;

    // Enum para las ocasiones
    public enum Ocasiones {
        CUMPLEANOS, CONDOLENCIAS, ANIVERSARIOS, SAN_VALENTIN    // USANDO LA Ñ A LO MEJOR DA PROBLEMAS, HABRIA QUE MIRARLO
    }

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(UUID id, Floricultor floricultor, String nombre, String descripcion, BigDecimal precio, int stock, String urlImg, Ocasiones ocasion) {
        this.id = id;
        this.floricultor = floricultor;
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

    public Floricultor getFloricultor() {
        return floricultor;
    }

    public void setFloricultor(Floricultor floricultor) {
        this.floricultor = floricultor;
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
                ", floricultor=" + floricultor +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", urlImg='" + urlImg + '\'' +
                ", ocasion=" + ocasion +
                '}';
    }
}
