package com.proyecto.alumnos.models.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
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
@Table( name = "historial" )
public class Historial {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private Long idEscuela;
	
	private Long idNivel;
	
	private Long idCarrera;
	
	private Long idCiclo;
	
	private Long idGrado;
	
	private Double promedio;
	
	@NotNull
	private LocalDate fechaAlta;
	
	@ManyToOne
	@JsonIgnore
	private Alumno alumno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEscuela() {
		return idEscuela;
	}

	public void setIdEscuela(Long idEscuela) {
		this.idEscuela = idEscuela;
	}

	public Long getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(Long idNivel) {
		this.idNivel = idNivel;
	}

	public Long getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(Long idCarrera) {
		this.idCarrera = idCarrera;
	}

	public Long getIdCiclo() {
		return idCiclo;
	}

	public void setIdCiclo(Long idCiclo) {
		this.idCiclo = idCiclo;
	}

	

	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}	
	

	public Long getIdGrado() {
		return idGrado;
	}

	public void setIdGrado(Long idGrado) {
		this.idGrado = idGrado;
	}	
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void replaceAll( Historial historial ) {
		this.idEscuela = historial.getIdEscuela();
		this.idNivel = historial.getIdNivel();
		this.idCarrera = historial.getIdCarrera();
		this.idCiclo = historial.getIdCiclo();
		this.idGrado = historial.getIdGrado();
		this.promedio = historial.getPromedio();
	}

	@Override
	public String toString() {
		return "Historial [id=" + id + ", idEscuela=" + idEscuela + ", idNivel=" + idNivel + ", idCarrera=" + idCarrera
				+ ", idCiclo=" + idCiclo + ", idGrado=" + idGrado + ", promedio=" + promedio + ", fechaAlta="
				+ fechaAlta + " alumno=" + alumno + "]";
	}

	 
	
	

}
