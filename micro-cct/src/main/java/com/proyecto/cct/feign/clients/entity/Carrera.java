package com.proyecto.cct.feign.clients.entity;

import java.util.ArrayList;
import java.util.List;



public class Carrera {

	private Long id;

	private String nombreCarrera;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	// private List<CicloEscolar> ciclosEscolares = new ArrayList<>();

}
