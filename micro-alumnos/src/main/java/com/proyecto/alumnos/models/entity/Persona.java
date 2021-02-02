package com.proyecto.alumnos.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.proyecto.alumnos.validadores.CurpValid;

//@Entity
//@Inheritance( strategy = InheritanceType.JOINED)
@MappedSuperclass //herencia para crear tablas
public class Persona {	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;
	 
	@NotBlank
	protected String nombre;	
	
	@NotBlank
	protected String apePaterno;
	
	@NotBlank
	protected String apeMaterno;
	
	@NotNull
	@Temporal(TemporalType.DATE  )
	protected Date fechaNacimiento;
	
	@Column( unique = true )
	@NotBlank
	@CurpValid
	protected String curp;	
	
	@NotBlank
	protected String sexo;

	public Long getId() {
		return id;
	}	 

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApePaterno() {
		return apePaterno;
	}

	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}

	public String getApeMaterno() {
		return apeMaterno;
	}

	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Persona(@NotBlank String nombre, @NotBlank String apePaterno, @NotBlank String apeMaterno,
			@NotNull Date fechaNacimiento, @NotBlank String curp, @NotBlank String sexo) {
		super();
		this.nombre = nombre;
		this.apePaterno = apePaterno;
		this.apeMaterno = apeMaterno;
		this.fechaNacimiento = fechaNacimiento;
		this.curp = curp;
		this.sexo = sexo;
	}

	public Persona() {
		super();
	}

	public Persona(@NotBlank String nombre, @NotBlank String apePaterno, @NotBlank String apeMaterno) {
		this.nombre = nombre;
		this.apePaterno = apePaterno;
		this.apeMaterno = apeMaterno;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	 
	
	

}
