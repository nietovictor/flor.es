package es.upm.dit.isst.isstgrupo07flores.model;

import java.util.UUID;

public class Cliente {
    private UUID id; // Identificador único del usuario
    private String correoElectronico; // Correo electrónico usado para crear la cuenta
    private String contrasena; // Contraseña del usuario cifrada
    private String telefono; // Teléfono de contacto del cliente

    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(UUID id, String correoElectronico, String contrasena, String telefono) {
        this.id = id;
        setCorreoElectronico(correoElectronico); // Validación en el setter
        this.contrasena = contrasena;
        setTelefono(telefono); // Validación en el setter
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
        if (correoElectronico == null || !correoElectronico.matches("^[\\w_%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }
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
        if (telefono == null || !telefono.matches("\\d{9}")) {
            throw new IllegalArgumentException("El número de teléfono debe contener exactamente 9 dígitos.");
        }
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