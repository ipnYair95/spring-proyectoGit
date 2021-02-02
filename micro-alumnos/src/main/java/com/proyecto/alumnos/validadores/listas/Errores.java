package com.proyecto.alumnos.validadores.listas;

import java.util.HashMap;
import java.util.Map;

public class Errores {
	
	private Integer idRegistro;
	
	Map<String, Object> campos = new HashMap<>();

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Map<String, Object> getCampos() {
		return campos;
	}

	public void setCampos(Map<String, Object> campos) {
		this.campos = campos;
	}

	public Errores(Integer idRegistro, Map<String, Object> campos) {
		super();
		this.idRegistro = idRegistro;
		this.campos = campos;
	}

	public Errores() {
		super();
	}
	
	

	
	
	
	

}
