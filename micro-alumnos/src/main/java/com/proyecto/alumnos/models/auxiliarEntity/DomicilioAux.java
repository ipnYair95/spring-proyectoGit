package com.proyecto.alumnos.models.auxiliarEntity;

import org.springframework.lang.Nullable;

import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.entity.Domicilio;

public class DomicilioAux {

	private String estado;
	
	private String municipio;
	
	private String codigoPostal;
	
	private String colonia;
	
	private String exterior;	
	
	private String interior;		
	
	private String entreCalles;	
	
	private String referencia;
	
	

	public DomicilioAux() {
		super();
	}

	public DomicilioAux(String estado, String municipio, String codigoPostal, String colonia, String exterior,
			String interior, String entreCalles, String referencia) {
		super();
		this.estado = estado;
		this.municipio = municipio;
		this.codigoPostal = codigoPostal;
		this.colonia = colonia;
		this.exterior = exterior;
		this.interior = interior;
		this.entreCalles = entreCalles;
		this.referencia = referencia;
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
	
	public void replaceAll( Domicilio domicilio ) {
		
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
