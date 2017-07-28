package com.facundoprecentado.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "asociados")
public class Asociado {

	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private boolean enabled;

	private String nombreNegocio;
	private String descripcion;
	private String direccion;
	private String horarioAtencion;
	private String mailAtencion;
	private String telefono;
	private String celular;
	private String website;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
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
