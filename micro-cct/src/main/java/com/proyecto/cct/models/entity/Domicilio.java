package com.proyecto.cct.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity 
@Table(name = "domicilio")
public class Domicilio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String estado;
	
	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String municipio;
	
	@NotBlank	
	private String codigoPostal;
	
	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String colonia;
	
	@NotBlank
	private String exterior;	
	
	private String interior;
	
	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String entreCalles;
	
	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String referencia;

	// compartido

	@OneToOne( cascade = CascadeType.ALL, mappedBy = "domicilio")
	@JsonIgnore
	private CentroDeTrabajo centroDeTrabajo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getExterior() {
		return exterior;
	}

	public void setExterior(String exterior) {
		this.exterior = exterior;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public String getEntreCalles() {
		return entreCalles;
	}

	public void setEntreCalles(String entreCalles) {
		this.entreCalles = entreCalles;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public CentroDeTrabajo getCentroDeTrabajo() {
		return centroDeTrabajo;
	}

	public void setCentroDeTrabajo(CentroDeTrabajo centroDeTrabajo) {
		this.centroDeTrabajo = centroDeTrabajo;
	}

	public void replaceAllAttributes(Domicilio domicilio) {

		this.estado = domicilio.getEstado();
		this.municipio = domicilio.getMunicipio();
		this.codigoPostal = domicilio.getCodigoPostal();
		this.colonia = domicilio.getColonia();
		this.exterior = domicilio.getExterior();
		this.interior = domicilio.getInterior();
		this.entreCalles = domicilio.getEntreCalles();
		this.referencia = domicilio.getReferencia();

	}

}
