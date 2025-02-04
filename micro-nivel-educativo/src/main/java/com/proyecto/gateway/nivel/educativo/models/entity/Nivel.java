package com.proyecto.gateway.nivel.educativo.models.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "nivel")
public class Nivel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String nombreNivel;
	
	@NotNull
	private Long jerarquia;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "nivel_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@Valid
	//@NotEmpty
	private List<Carrera> carreras = new ArrayList<>();	
	

	/// lista

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	

	public Long getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(Long jerarquia) {
		this.jerarquia = jerarquia;
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

	public void addCarrera(Carrera carrera) {
		// grado.setParciales( this.parciales );
		this.carreras.add(carrera);
	}

	public void deleteCarrera(Carrera carrera) {
		this.carreras.remove(carrera);
	}
	
	public void replaceAll( Nivel nivel ) {
		this.nombreNivel = nivel.getNombreNivel();
		this.jerarquia = nivel.getJerarquia();
	}

	@Override
	public String toString() {
		return "Nivel [id=" + id + ", nombreNivel=" + nombreNivel + ", carreras=" + carreras + "]";
	}
	
	

}
