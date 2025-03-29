package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; 

    @Email 
    private String correoElectronico; 

    private String contrasena; 

    @Pattern(regexp = "^\\d{9}$", message = "El número de teléfono no es válido") // HABRA QUE USAR @Valid PARA ACTIVAR ESTO EN EL CONTROLADOR
    private String telefono; 



    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(UUID id, String correoElectronico, String contrasena, String telefono) {
        this.id = id;
        this.correoElectronico = correoElectronico; 
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "cliente{" +
                "id=" + id +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}