package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.UUID;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Lob;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

@Entity
public class Flor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private UUID floricultorId; 

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Lob
    private byte[] imagen; // URL o ruta de la imagen

    @OneToMany(mappedBy = "florId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FloresEnPersonalizado> floresEnPersonalizados;

    // Constructor vacío
    public Flor() {}

    // Constructor con parámetros
    public Flor(String nombre, String descripcion, Double precio, Integer stock, byte[] imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getFloricultorId() {
        return floricultorId;
    }

    public void setFloricultorId(UUID floricultorId) {
        this.floricultorId = floricultorId;
    }
    
    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<FloresEnPersonalizado> getFloresEnPersonalizados() {
        return floresEnPersonalizados;
    }

    public void setFloresEnPersonalizados(List<FloresEnPersonalizado> floresEnPersonalizados) {
        this.floresEnPersonalizados = floresEnPersonalizados;
    }
 
    
}
