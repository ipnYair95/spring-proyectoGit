package com.proyecto.cct.models.entity;

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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name = "modalidad_nivel")
public class ModalidadNivel {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotNull
	private Long idModalidad;
	
	@ManyToOne
	@JsonIgnore
	private NivelEscuela nivelEscuela;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdModalidad() {
		return idModalidad;
	}

	public void setIdModalidad(Long idModalidad) {
		this.idModalidad = idModalidad;
	}

	public NivelEscuela getNivelEscuela() {
		return nivelEscuela;
	}

	public void setNivelEscuela(NivelEscuela nivelEscuela) {
		this.nivelEscuela = nivelEscuela;
	}	
	
	

}
