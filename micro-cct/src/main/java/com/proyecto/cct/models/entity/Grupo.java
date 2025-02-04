package com.proyecto.cct.models.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table( name = "grupo" )
public class Grupo {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;	
	
	@NotNull
	private Long idCicloEscolar;
	
	@NotNull
	private Long idGrado;
	
	@NotBlank
	@Pattern( regexp = "^[1-9][A-Z]$", message = "FORMATO: 1A, 4B" )
	private String nombreGrupo;
	
	@ManyToOne	
	@JsonIgnoreProperties( "grupos" )
	private Salon salon;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Long getIdCicloEscolar() {
		return idCicloEscolar;
	}

	public void setIdCicloEscolar(Long idCicloEscolar) {
		this.idCicloEscolar = idCicloEscolar;
	}

	public Long getIdGrado() {
		return idGrado;
	}

	public void setIdGrado(Long idGrado) {
		this.idGrado = idGrado;
	}
	

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public Salon getSalon() {
		return salon;
	}

	public void setSalon(Salon salon) {
		this.salon = salon;
	}
	
	public void replaceAll( Grupo grupo ) {
		this.idCicloEscolar = grupo.getIdCicloEscolar();
		this.nombreGrupo = grupo.getNombreGrupo();
		this.idGrado = grupo.getIdGrado();
		this.setSalon( grupo.getSalon() );
		
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", idCicloEscolar=" + idCicloEscolar + ", idGrado=" + idGrado + ", nombreGrupo="
				+ nombreGrupo + ", salon=" + salon + "]";
	}
	
	

	 

}
