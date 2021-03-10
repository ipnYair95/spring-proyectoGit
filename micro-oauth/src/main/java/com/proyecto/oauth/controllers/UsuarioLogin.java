package com.proyecto.oauth.controllers;

import javax.validation.constraints.NotBlank;

public class UsuarioLogin {
	
	@NotBlank
	private String nombre;
	
	@NotBlank
	private String password;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
