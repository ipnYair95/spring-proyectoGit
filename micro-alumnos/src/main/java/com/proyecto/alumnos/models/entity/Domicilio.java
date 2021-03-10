package com.proyecto.alumnos.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table( name = "domicilio" )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Domicilio.class)
public class Domicilio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;	
	
	@Nullable
	private String estado;
	
	@Nullable
	private String municipio;
	
	@Nullable
	private String codigoPostal;
	
	@Nullable
	private String colonia;
	
	@Nullable
	private String exterior;
	
	@Nullable
	private String interior;	
	
	@Nullable
	private String entreCalles;
	
	@Nullable
	private String referencia;
	
	// compartido
	
	@OneToOne( mappedBy = "domicilio" )	
	private Alumno alumno;
	
	

	public Domicilio() {
		super();
	}

	public Domicilio(String estado, String municipio, String codigoPostal, String colonia, String exterior,
			String interior, String entreCalle, String referencia, Alumno alumno) {
		super();
		this.estado = estado;
		this.municipio = municipio;
		this.codigoPostal = codigoPostal;
		this.colonia = colonia;
		this.exterior = exterior;
		this.interior = interior;
		this.entreCalles = entreCalle;
		this.referencia = referencia;
		this.alumno = alumno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getExterior() {
		return exterior;
	}

	public void setExterior(String exterior) {
		this.exterior = exterior;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}
	
	

	public String getEntreCalles() {
		return entreCalles;
	}

	public void setEntreCalles(String entreCalles) {
		this.entreCalles = entreCalles;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
		

	@Override
	public String toString() {
		return "Domicilio [id=" + id + ", estado=" + estado + ", municipio=" + municipio + ", codigoPostal="
				+ codigoPostal + ", colonia=" + colonia + ", exterior=" + exterior + ", interior=" + interior
				+ ", entreCalle=" + entreCalles + ", referencia=" + referencia + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void replaceAllAttributes(Domicilio domicilio) {
		
		this.estado = domicilio.getEstado();
		this.municipio = domicilio.getMunicipio();
		this.codigoPostal = domicilio.getCodigoPostal();
		this.colonia = domicilio.getColonia();
		this.exterior = domicilio.getExterior();
		this.interior = domicilio.getInterior();
		this.entreCalles = domicilio.getEntreCalles();
		this.referencia = domicilio.getReferencia();
		
	}
	
	
	
	
	

}
