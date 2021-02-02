package com.proyecto.alumnos.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proyecto.alumnos.validadores.CurpValid;

@Entity
@Table(name = "tutor")
public class Tutor implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@NotBlank
	private String nombre;	
	
	@NotBlank
	private String apePaterno;
	
	@NotBlank
	private String apeMaterno;
	
	@NotNull
	private Date fechaNacimiento;

	@NotBlank
	@CurpValid
	private String curp;	
	
	@NotBlank
	private String sexo;

	@NotBlank
	private String parentesco;

	@NotBlank
	private String ocupacion;

	@NotBlank
	private String edoCivil;

	@NotBlank
	private String email;

	@NotBlank
	private String telefono;

	/*@ManyToOne
	private Alumno alumno;
	*/ 
		
	private boolean esResponsable = false;
	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEsResponsable() {
		return esResponsable;
	}

	public void setEsResponsable(boolean esResponsable) {
		this.esResponsable = esResponsable;
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

		Tutor t = (Tutor) obj;

		return this.curp != null && this.curp.equals(t.getCurp());
	}
	
	 

		public void replaceAllAttributes(Tutor tutor) {

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
			
			this.esResponsable = tutor.isEsResponsable();

		}

}
