package com.proyecto.cct.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "centro_de_trabajo")
public class CentroDeTrabajo {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column( unique = true )
	@Pattern(regexp = "^(([0-2][0-9])|([3][0-2]))[A-Z]{3}[0-9]{4}[A-Z]$", message = "Formato de la CCT invalida")
	@NotBlank 
	private String cct;

	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String nombre;

	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String turno;

	@Valid
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "domicilio_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Domicilio domicilio;

	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  orphanRemoval = true)
	@JoinColumn(name = "centro_de_trabajo_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<Salon> salones = new ArrayList<>();

	/* RELACION A NIVEL */

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_de_trabajo_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<NivelEscuela> nivelEscuela;

	@NotBlank
	@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,3}$", message = "Formato invalido del email")
	private String email;

	@NotBlank
	@Pattern(regexp = "^([0-9]|-)*$", message = "Formato invalido del del telefono Ej: 559849 o 777-777")
	private String telefono;

	@Pattern(regexp = "^([0-9]|-)*$", message = "Formato invalido Ej: 84984 o 477-7897")
	private String extension;

	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String sostenimiento;

	@NotBlank
	@Pattern(regexp = "^([^a-z\\s]+ ?)+$", message = "Solo mayusculas")
	private String dependenciaNormativa;

	////////////////////////////

	///////////////////////////	
	

	public String getNombre() {
		return nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getSostenimiento() {
		return sostenimiento;
	}

	public void setSostenimiento(String sostenimiento) {
		this.sostenimiento = sostenimiento;
	}

	public String getDependenciaNormativa() {
		return dependenciaNormativa;
	}

	public void setDependenciaNormativa(String dependenciaNormativa) {
		this.dependenciaNormativa = dependenciaNormativa;
	}

	public String getCct() {
		return cct;
	}

	public void setCct(String cct) {
		this.cct = cct;
	}

	public List<Salon> getSalones() {
		return salones;
	}

	public void setSalones(List<Salon> salones) {
		this.salones = salones;
	}

	public void addSalon(Salon salon) {
		this.salones.add(salon);
	}

	public void removeSalon(Salon salon) {
		this.salones.remove(salon);
	}	
	

	public List<NivelEscuela> getNivelEscuela() {
		return nivelEscuela;
	}

	public void setNivelEscuela(List<NivelEscuela> nivelEscuela) {
		this.nivelEscuela = nivelEscuela;
	}

	public void replaceAll(CentroDeTrabajo centroDeTrabajo) {

		this.cct = centroDeTrabajo.getCct();
		this.nombre = centroDeTrabajo.getNombre();
		this.turno = centroDeTrabajo.getTurno();
		this.email = centroDeTrabajo.getEmail();
		this.telefono = centroDeTrabajo.getTelefono();
		this.extension = centroDeTrabajo.getExtension();
		this.sostenimiento = centroDeTrabajo.getSostenimiento();
		this.dependenciaNormativa = centroDeTrabajo.getDependenciaNormativa();

		this.domicilio.replaceAllAttributes(centroDeTrabajo.getDomicilio());

	}

	@Override
	public String toString() {
		return "CentroDeTrabajo [cct=" + cct + ", nombre=" + nombre + ", turno=" + turno + ", domicilio=" + domicilio
				+ ", salones=" + salones + ", nivelEscuela=" + nivelEscuela + ", email=" + email + ", telefono="
				+ telefono + ", extension=" + extension + ", sostenimiento=" + sostenimiento + ", dependenciaNormativa="
				+ dependenciaNormativa + "]";
	}

}
