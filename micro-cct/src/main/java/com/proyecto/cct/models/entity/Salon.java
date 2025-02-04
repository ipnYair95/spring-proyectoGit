package com.proyecto.cct.models.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany; 

@Entity
@Table(name = "salon")
public class Salon {
 
	@Id	
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas y numeros")
	private String nombre;

	@NotNull( message = "No debe ser nulos o solo enteros" )	
	private Long capacidad;

	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String estatus;
	
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn( name = "salon_id", referencedColumnName = "id" )
	private List<Grupo> grupos;

	@ManyToOne
	//@JsonIgnore
	@JsonIgnoreProperties( "salones" )
	private CentroDeTrabajo centroDeTrabajo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
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
