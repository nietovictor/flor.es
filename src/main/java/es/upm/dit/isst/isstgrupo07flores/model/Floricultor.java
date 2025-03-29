package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Floricultor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private UUID id; 
    private String nombre; 
    @Email
    private String correoElectronico; 
    private String contrasena; 
    private String direccion; 
    @Pattern(regexp = "^\\d{5}$", message = "El código postal no es válido") // HABRA QUE USAR @Valid PARA ACTIVAR ESTO EN EL CONTROLADOR
    private String cp;
    private float valoracion;

    // Constructor vacío
    public Floricultor() {}

    // Constructor con parámetros
    public Floricultor(UUID id, String nombre, String correoElectronico, String contrasena, String direccion, String cp, float valoracion) {
        this.id = id;
        this.nombre = nombre;
        setCorreoElectronico(correoElectronico); 
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.cp = cp;
        this.valoracion = valoracion;   // LA VALORACIÓN TENDRIA QUE HACERSE CON UNA MEDIA DE VALORACIONES, SEGURAMENTE SE HAGA EN UNO DE LOS METODOS
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Floricultor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", direccion='" + direccion + '\'' +
                ", cp='" + cp + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}