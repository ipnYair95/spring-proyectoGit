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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.alumnos.models.entity.Tutor;

@Entity
@Table( name = "asignatura" )
public class Asignatura {
	
	@Id
	@GeneratedValue( strategy =  GenerationType.AUTO )
	private Long id;
	
	@NotBlank
	private String nombreAsignatura;
	
	@ManyToOne 
	@JsonIgnore
	private Grado grado; 
	
	/*
	@NotNull
	private Long parciales;
	*/
	/*
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	@JoinColumn( name = "asignatura_fid", referencedColumnName = "id" )
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@Valid
	private List<Calificacion> Calificaciones = new ArrayList<>();
	
	private Double promedio;	

*/
	public Asignatura() {
		super();
	}

	public Asignatura(@NotBlank String nombre) {
		super();
		this.nombreAsignatura = nombre;
	}
	
	

	public Grado getGrado() {
		return grado;
	}

	public void setGrado(Grado grado) {
		this.grado = grado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getNombreAsignatura() {
		return nombreAsignatura;
	}

	public void setNombreAsignatura(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
	}

	@Override
	public boolean equals(Object obj) {

		// comparacion de collection
		if (this == obj) {
			return true; // son iguales
		}

		// si no es una instancia
		if (!(obj instanceof Tutor)) {
			return false;
		}

		// comparamos los id

		Grado g = (Grado) obj;

		return this.id != null && this.id.equals(g.getId());
	}

	/*
	
	public Long getParciales() {
		return parciales;
	}

	public void setParciales(Long parciales) {
		this.parciales = parciales;
	}

	public List<Calificacion> getCalificaciones() {
		return Calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		Calificaciones = calificaciones;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio() {
		
		Double suma = 0.0;

		for( Calificacion cal : this.getCalificaciones() ) {
			suma += cal.getCalificacionNumero();
		}
		
		this.promedio = suma/this.parciales;

	}
	
	
	*/
	
	

}
