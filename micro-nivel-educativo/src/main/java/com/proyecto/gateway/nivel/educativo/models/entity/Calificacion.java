package com.proyecto.gateway.nivel.educativo.models.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@Entity
//@Table( name = "calificacion" )
public class Calificacion {
	
	@NotNull
	@Size( min = 5, max = 10 )
	private Double calificacionNumero;
	

	public Calificacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Calificacion(@NotNull Double calificacionNumero) {
		super();
		this.calificacionNumero = calificacionNumero;
	}

	public Double getCalificacionNumero() {
		return calificacionNumero;
	}

	public void setCalificacionNumero(Double calificacionNumero) {
		this.calificacionNumero = calificacionNumero;
	}
	
	

}
