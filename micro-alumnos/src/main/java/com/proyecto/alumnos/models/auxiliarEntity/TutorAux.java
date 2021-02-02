package com.proyecto.alumnos.models.auxiliarEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.proyecto.alumnos.models.entity.Tutor;
import com.proyecto.alumnos.validadores.CurpValid;

public class TutorAux {
	
	private String nombreCompleto;
	
	private String nombre;

	private String apePaterno;

	private String apeMaterno;

	private Date fechaNacimiento;

	private String curp;

	private String sexo;

	private String parentesco;

	private String ocupacion;

	private String edoCivil;

	private String email;

	private String telefono;
	
	
	
	public TutorAux(String nombreCompleto, String nombre, String apePaterno, String apeMaterno, Date fechaNacimiento,
			String curp, String sexo, String parentesco, String ocupacion, String edoCivil, String email,
			String telefono) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.nombre = nombre;
		this.apePaterno = apePaterno;
		this.apeMaterno = apeMaterno;
		this.fechaNacimiento = fechaNacimiento;
		this.curp = curp;
		this.sexo = sexo;
		this.parentesco = parentesco;
		this.ocupacion = ocupacion;
		this.edoCivil = edoCivil;
		this.email = email;
		this.telefono = telefono;
	}

	public TutorAux() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getEdoCivil() {
		return edoCivil;
	}

	public void setEdoCivil(String edoCivil) {
		this.edoCivil = edoCivil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public void replaceAll( Tutor tutor ) {
		
		this.nombre = tutor.getNombre();
		this.apePaterno = tutor.getApePaterno();
		this.apeMaterno = tutor.getApeMaterno();
		this.fechaNacimiento = tutor.getFechaNacimiento();
		this.curp = tutor.getCurp();
		this.sexo = tutor.getSexo();
		this.parentesco = tutor.getParentesco();
		this.ocupacion = tutor.getOcupacion();
		this.edoCivil = tutor.getEdoCivil();
		this.email = tutor.getEmail();
		this.telefono = tutor.getTelefono();
		this.nombreCompleto = tutor.getApePaterno() + " " + tutor.getApeMaterno() + " " + tutor.getNombre();
		
	}
	
	
	 

}
