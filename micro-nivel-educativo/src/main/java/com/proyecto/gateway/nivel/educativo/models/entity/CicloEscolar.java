package com.proyecto.gateway.nivel.educativo.models.entity;

import java.time.LocalDate;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.alumnos.models.entity.Tutor;
import com.proyecto.gateway.nivel.educativo.validadores.PeriodoValid;

@Entity
@Table(name = "ciclo_escolar")
@PeriodoValid
public class CicloEscolar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	@Pattern(regexp = "^([2][\\d]{3}[-][2][\\d]{3})|[2][\\d]{3}[-]\\d$")
	private String nombreCiclo;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaInicio;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechaTermino;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ciclo_escolar_id", referencedColumnName = "id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	// validacion para la lista asociada
	@Valid
	private List<Grado> grados = new ArrayList<>();
	
	@ManyToOne
	@JsonIgnoreProperties( "ciclosEscolares" )
	private Carrera carrera;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCiclo() {
		return nombreCiclo;
	}

	public void setNombreCiclo(String nombreCiclo) {
		this.nombreCiclo = nombreCiclo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaTermino() {
		return fechaTermino;
	}

	public void setFechaTermino(LocalDate fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public List<Grado> getGrados() {
		return grados;
	}

	public void setGrados(List<Grado> grados) {
		this.grados = grados;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	@Override
	public boolean equals(Object obj) {

		// comparacion de collection
		if (this == obj) {
			return true; // son iguales
		}

		// si no es una instancia
		if (!(obj instanceof CicloEscolar)) {
			return false;
		}

		// comparamos los id

		Grado g = (Grado) obj;

		return this.id != null && this.id.equals(g.getId());
	}

	public void addGrado(Grado grado) {
		this.grados.add(grado);
	}

	public void deleteGrado(Grado grado) {
		this.grados.remove(grado);
	}
	
	public void replaceAll( CicloEscolar cicloEscolar ) {
		
		this.nombreCiclo = cicloEscolar.getNombreCiclo();
		this.fechaInicio = cicloEscolar.getFechaInicio();
		this.fechaTermino = cicloEscolar.getFechaTermino();
		
	}

}
