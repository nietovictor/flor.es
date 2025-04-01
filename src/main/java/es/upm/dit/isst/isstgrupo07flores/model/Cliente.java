package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Cliente extends Usuario{
    @Pattern(regexp = "^\\d{9}$", message = "El número de teléfono no es válido") // HABRA QUE USAR @Valid PARA ACTIVAR ESTO EN EL CONTROLADOR
    private String telefono; 

    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(String telefono) {
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Rol
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("CLIENTE"));
    }

   /*  @Override
    public String toString() {
        return "cliente{" +
                "id=" + id +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    } */
}