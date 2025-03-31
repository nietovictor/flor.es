package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Floricultor extends Usuario {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @Pattern(regexp = "^\\d{5}$", message = "El código postal no es válido")
    private String cp;

    private String nif; // NIF del floricultor (opcional)

    // Constructor vacío
    public Floricultor() {}

    // Constructor con parámetros
    public Floricultor(String nombre, String direccion, String cp, String nif) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cp = cp;
        this.nif = nif; // NIF opcional, se puede dejar como null
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
    
    // Rol
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("FLORICULTOR"));
    }

    /* @Override
    public String toString() {
        return "Floricultor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", direccion='" + direccion + '\'' +
                ", cp='" + cp + '\'' +
                '}';
    } */
}