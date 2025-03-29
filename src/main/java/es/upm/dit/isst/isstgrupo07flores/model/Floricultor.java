package es.model;

import java.util.UUID;

public class Floricultor {
    private UUID id; // Identificador único del usuario
    private String nombre; // Nombre escogido al crear la cuenta
    private String correoElectronico; // Correo electrónico usado para crear la cuenta
    private String contrasena; // Contraseña del usuario cifrada
    private String direccion; // Dirección del negocio del floricultor
    private String cp; // Código postal de la dirección
    private float valoracion; // Valoración media del floricultor

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
        if (correoElectronico == null || !correoElectronico.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
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