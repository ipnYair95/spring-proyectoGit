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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.alumnos.models.entity.Tutor;

@Entity
@Table( name = "carrera" )
public class Carrera {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	private Long id;
	
	@NotEmpty
	private String nombreCarrera;
 
	
	@OneToMany( cascade = CascadeType.ALL,  fetch = FetchType.LAZY )
	@JoinColumn( name = "carrera_id", referencedColumnName = "id" )
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@Valid
	private List<CicloEscolar> ciclosEscolares = new ArrayList<>();
	
	@ManyToOne
	@JsonIgnore
	private Nivel nivel;	
	

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

	public List<CicloEscolar> getCiclosEscolares() {
		return ciclosEscolares;
	}

	public void setCiclosEscolares(List<CicloEscolar> ciclosEscolares) {
		this.ciclosEscolares = ciclosEscolares;
	}
	
	

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	@Override
	public boolean equals(Object obj) {

		// comparacion de collection
		if (this == obj) {
			return true; // son iguales
		}

		// si no es una instancia
		if (!(obj instanceof Carrera)) {
			return false;
		}

		// comparamos los id

		Carrera c = (Carrera) obj;

		return this.id != null && this.id.equals(c.getId());
	}
	
	public void addCicloEscolar(CicloEscolar cicloEscolar) {
		this.ciclosEscolares.add(cicloEscolar);
	}
	
	public void deleteCicloEscolar(CicloEscolar cicloEscolar) {
		this.ciclosEscolares.remove(cicloEscolar);
	}


}
