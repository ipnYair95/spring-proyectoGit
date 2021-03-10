package com.proyecto.gateway.nivel.educativo.models.entity;

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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.alumnos.models.entity.Tutor;

@Entity
@Table(name = "grado")
public class Grado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Pattern(regexp = "^[1-9][0-9]*[°] [A-Z][a-z]+$", message = "Formato numero ordinal con periodo Ej: 1° Grado o 1° Semestre")
	@NotBlank
	private String nombreGrado;
	
	@NotNull
	private Long jerarquia;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "grado_id", referencedColumnName = "id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@Valid
	//@NotEmpty
	private List<Asignatura> asignaturas;
	
	@ManyToOne
	@JsonIgnoreProperties( "grados" ) 
	private CicloEscolar cicloEscolar;

	public Grado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Grado(@Pattern(regexp = "^[1-9][0-9]*$", message = "Solo enteros") String nombreGrado) {
		super();
		this.nombreGrado = nombreGrado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Long getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(Long jerarquia) {
		this.jerarquia = jerarquia;
	}

	public String getNombreGrado() {
		return nombreGrado;
	}

	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}


	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}
	


	public CicloEscolar getCicloEscolar() {
		return cicloEscolar;
	}

	public void setCicloEscolar(CicloEscolar cicloEscolar) {
		this.cicloEscolar = cicloEscolar;
	}
	
	public void replaceAll( Grado grado ) {
		this.nombreGrado = grado.getNombreGrado();
		this.jerarquia = grado.getJerarquia();
	}
	


}
