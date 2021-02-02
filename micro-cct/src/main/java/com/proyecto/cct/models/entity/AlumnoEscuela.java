package com.proyecto.cct.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "alumno_escuela" )
public class AlumnoEscuela {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotNull
	private Long idNivel;
	
	private Long idEscuela;

}
