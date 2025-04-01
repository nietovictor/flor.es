package es.upm.dit.isst.isstgrupo07flores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;

@MappedSuperclass
public abstract class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; 

    @Email
    @Column(unique = true, nullable = false)
    private String correoElectronico; 

    @Column(nullable = false)
    private String contrasena; 

  

    // Métodos de UserDetails
    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters y setters
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
}