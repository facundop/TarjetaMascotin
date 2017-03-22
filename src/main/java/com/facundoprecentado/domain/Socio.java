package com.facundoprecentado.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "socios")
public class Socio {

    @Id
    private String username;
    private String nombre;
    private String apellido;
    private int dni;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }
}
