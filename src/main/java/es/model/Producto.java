package es.model;
import java.util.UUID;

public class Producto {
    private UUID id; 
    private UUID userId; 
    private String nombre; 

    // private Colores color; // UN RAMO TIENE TIENE COLOR O COLORES???

    private String descripcion; 
    private float precio; 
    private int stock; 
    private String urlImg; 
    private Ocasiones ocasion;

    // Enum para las ocasiones
    public enum Ocasiones {
        CUMPLEAÑOS, CONDOLENCIAS, ANIVERSARIOS, SAN_VALENTIN
    }

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(UUID id, UUID userId, String nombre, String descripcion, float precio, int stock, String urlImg, Ocasiones ocasion) {
        this.id = id;
        this.userId = userId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
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
                ", userId=" + userId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", urlImg='" + urlImg + '\'' +
                ", ocasion=" + ocasion +
                '}';
    }
}
