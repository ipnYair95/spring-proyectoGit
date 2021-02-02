package com.proyecto.cct.feign.clients.entity;

import java.util.ArrayList;
import java.util.List;


public class Nivel {

	private Long id;

	private String nombreNivel;

	private List<Carrera> carreras = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreNivel() {
		return nombreNivel;
	}

	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	@Override
	public String toString() {
		return "Nivel [id=" + id + ", nombreNivel=" + nombreNivel + ", carreras=" + carreras + "]";
	}

}
