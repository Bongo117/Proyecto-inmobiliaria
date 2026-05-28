package com.eskere.inmobiliaria.modelo;

import java.io.Serializable;

public class Inquilino implements Serializable {

    private int idInquilino;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String nombreGarante;
    private String telefonoGarante;

    public Inquilino() {
    }
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreGarante() {
        return nombreGarante;
    }

    public void setNombreGarante(String nombreGarante) {
        this.nombreGarante = nombreGarante;
    }

    public String getTelefonoGarante() {
        return telefonoGarante;
    }

    public void setTelefonoGarante(String telefonoGarante) {
        this.telefonoGarante = telefonoGarante;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoFormateado() { return "Código: " + idInquilino; }
    public String getNombreFormateado() { return "Nombre: " + nombre; }
    public String getApellidoFormateado() { return "Apellido: " + apellido; }
    public String getDniFormateado() { return "DNI: " + dni; }
    public String getEmailFormateado() { return "E-mail: " + email; }
    public String getTelefonoFormateado() { return "Teléfono: " + telefono; }
    public String getGaranteFormateado() { return "Garante: " + nombreGarante; }
    public String getTelefonoGaranteFormateado() { return "Teléfono Garante: " + telefonoGarante; }
}