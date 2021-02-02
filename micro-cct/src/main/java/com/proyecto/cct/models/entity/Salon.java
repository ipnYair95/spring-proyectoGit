package com.proyecto.cct.models.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore; 

import javax.persistence.Id;
import javax.persistence.ManyToOne; 

@Entity
@Table(name = "salon")
public class Salon {
 
	@Id	
	private String id;
	
	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas y numeros")
	private String nombre;

	@NotNull( message = "No debe ser nulos o solo enteros" )	
	private Long capacidad;

	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String estatus;

	@ManyToOne
	@JsonIgnore
	private CentroDeTrabajo centroDeTrabajo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CentroDeTrabajo getCentroDeTrabajo() {
		return centroDeTrabajo;
	}

	public void setCentroDeTrabajo(CentroDeTrabajo centroDeTrabajo) {
		this.centroDeTrabajo = centroDeTrabajo;
	}

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	@Override
	public boolean equals(Object obj) {

		// comparacion de collection
		if (this == obj) {
			return true; // son iguales
		}

		// si no es una instancia
		if (!(obj instanceof Salon)) {
			return false;
		}

		// comparamos los id

		Salon c = (Salon) obj;

		return this.id != null && this.id.equals(c.getId());

	}
	
	public void replaceAll( Salon salon ) {
		
		this.nombre = salon.getNombre();
		this.capacidad = salon.getCapacidad();
		this.estatus = salon.getEstatus();		
		
	}

}
