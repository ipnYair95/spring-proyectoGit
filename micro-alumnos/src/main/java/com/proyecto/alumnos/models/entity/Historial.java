package com.proyecto.alumnos.models.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table( name = "historial" )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Historial.class)
public class Historial implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotNull
	private Long idGrupo;	
	
	private Double promedio;	
	
	private LocalDate fechaAlta;
	
	@NotNull
	private boolean estaActivo;
	
	@ManyToOne
	@JsonIgnoreProperties( "historiales" )
	private Alumno alumno;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getIdGrupo() {
		return idGrupo;
	}


	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}


	public Double getPromedio() {
		return promedio;
	}

	public void setPromedio(Double promedio) {
		this.promedio = promedio;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public boolean isEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}


	@Override
	public String toString() {
		return "Historial [id=" + id + ", idGrupo=" + idGrupo + ", promedio=" + promedio + ", fechaAlta=" + fechaAlta
				+ ", estaActivo=" + estaActivo + ", alumno=" + alumno + "]";
	}
		
	

}
