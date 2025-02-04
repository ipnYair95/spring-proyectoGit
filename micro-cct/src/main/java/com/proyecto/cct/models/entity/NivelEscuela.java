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
@Table( name = "nivel_escuela")
public class NivelEscuela {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotNull
	private Long idNivel;
	
	@ManyToOne
	@JsonIgnore
	private CentroDeTrabajo centroDeTrabajo;
	
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn( name = "nivel_escuela_id", referencedColumnName = "id" )
	private List<ModalidadNivel> modalidades;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(Long idNivel) {
		this.idNivel = idNivel;
	}

	public CentroDeTrabajo getCentroDeTrabajo() {
		return centroDeTrabajo;
	}

	public void setCentroDeTrabajo(CentroDeTrabajo centroDeTrabajo) {
		this.centroDeTrabajo = centroDeTrabajo;
	}

	public List<ModalidadNivel> getModalidades() {
		return modalidades;
	}

	public void setModalidades(List<ModalidadNivel> modalidades) {
		this.modalidades = modalidades;
	}
	
	
	
	
	
	

}
