package com.proyecto.alumnos.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proyecto.alumnos.validadores.CurpValid;
import com.proyecto.alumnos.validadores.ResponsableValid;


@Entity 
@Table( name = "alumno" )
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties( value = {"domicilio","tutores"}, allowGetters = true, allowSetters = true)
public class Alumno extends Persona implements Serializable{
	
	private static final long serialVersionUID = 1L;	
	
	 
	
	@NotBlank
	@Pattern( regexp = "^[0-9]*$", message = "Solo enteros")
	private String peso;
	
	@NotBlank
	@Pattern( regexp = "^[0-2][.][0-9]{2}$" , message = "Formato 1.75")
	private String estatura;
	
	@NotBlank
	private String tipoSangre;
	
	@NotNull
	private boolean usaLentes;
	
	//// tablas compartidas
		
	@OneToOne( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn( name = "domicilio_id" )
	// prevenir ciclo en caso de que no ignore el atributo
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  
	private Domicilio domicilio;		 
	
	
	@OneToMany(cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.LAZY )
	@JoinColumn( name = "alumno_fid" , referencedColumnName = "id" )
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	// validacion para la lista asociada
	@NotNull
	@Valid  
	@Size( min = 1 , message = "Debe existir al menos 1 tutor" )
	@ResponsableValid
	private List<Tutor > tutores = new ArrayList<>();  
	
	@OneToMany(  cascade = CascadeType.ALL ,orphanRemoval = true, fetch = FetchType.LAZY )
	@JoinColumn( name = "alumno_id" )
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Historial> historiales;	
	
	private boolean inscritoEscuela;

	///////////////////7
	
	public Alumno() {
		super();
		// TODO Auto-generated constructor stub
	}


	///////////////


	public Domicilio getDomicilio() {
		return domicilio;
	}

	

	public List<Tutor> getTutores() {
		return tutores;
	}


	public void setTutores(List<Tutor> tutores) {
		this.tutores = tutores;
	}


	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Alumno [peso=" + peso + ", estatura=" + estatura + ", tipoSangre=" + tipoSangre + ", usaLentes="
				+ usaLentes + ", domicilio=" + domicilio + ", tutores=" + tutores + "]";
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getEstatura() {
		return estatura;
	}

	public void setEstatura(String estatura) {
		this.estatura = estatura;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public boolean isUsaLentes() {
		return usaLentes;
	}

	public void setUsaLentes(boolean usaLentes) {
		this.usaLentes = usaLentes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	public List<Historial> getHistoriales() {
		return historiales;
	}


	public void setHistoriales(List<Historial> historiales) {
		this.historiales = historiales;
	}
	
	


	public boolean isInscritoEscuela() {
		return inscritoEscuela;
	}


	public void setInscritoEscuela(boolean inscritoEscuela) {
		this.inscritoEscuela = inscritoEscuela;
	}


	public void replaceAllAttributes( Alumno alumno ) {
		
		super.nombre = alumno.getNombre();
		super.apePaterno = alumno.getApePaterno();
		super.apeMaterno = alumno.getApeMaterno();
		super.fechaNacimiento = alumno.getFechaNacimiento();
		super.curp = alumno.getCurp();
		super.sexo = alumno.getSexo();
		
		this.peso = alumno.getPeso();
		this.estatura = alumno.getEstatura();
		this.tipoSangre = alumno.getTipoSangre();
		this.usaLentes = alumno.isUsaLentes();	
		this.domicilio.replaceAllAttributes( alumno.getDomicilio() );
		
	}
	
	public void addATutor(  Tutor tutor) {
		this.tutores.add(tutor);
	}
	
	public void removeTutor(  Tutor tutor ) {
		this.tutores.remove(tutor);
	}
	
	
	
	


	
	
	

}
