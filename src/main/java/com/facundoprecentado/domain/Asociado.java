package com.facundoprecentado.domain;

import javax.persistence.*;

@Entity(name = "asociados")
public class Asociado {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private boolean enabled;
    /*
     * Atributos para el formulario de Asociado
     */
    // El nombre del negocio: SuperPet
    private String nombreNegocio;
    // Descripcion del negocio.
    private String descripcion;
    // La direccion del Asociado: Av. Los Ingenieros 505, Santa Patricia, La Molina
    private String direccion;
    // El horario de atencion: De Lunes a Sabado de 8:30 AM a 8:30 PM
    private String horarioAtencion;
    // El email de atencion al publico: contacto@superpet.com
    private String mailAtencion;
    // El telefono: 475 3364
    private int telefono;
    // El celular: 994 084 642
    private int celular;
    // El sitio web: www.superpet.com
    private String website;
    // Facebook: facebook.com/superpet
    private String facebook;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public String getMailAtencion() {
        return mailAtencion;
    }

    public void setMailAtencion(String mailAtencion) {
        this.mailAtencion = mailAtencion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
