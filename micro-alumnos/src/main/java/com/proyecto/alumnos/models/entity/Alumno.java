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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Alumno.class)
public class Alumno implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
	
	@OneToMany( fetch = FetchType.LAZY )
	@JoinColumn( name = "alumno_id" )
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Historial> historiales;	
	

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


	public void replaceAllAttributes( Alumno alumno ) {
		
		this.nombre = alumno.getNombre();
		this.apePaterno = alumno.getApePaterno();
		this.apeMaterno = alumno.getApeMaterno();
		this.fechaNacimiento = alumno.getFechaNacimiento();
		this.curp = alumno.getCurp();
		this.sexo = alumno.getSexo();
		
		this.peso = alumno.getPeso();
		this.estatura = alumno.getEstatura();
		this.tipoSangre = alumno.getTipoSangre();
		this.usaLentes = alumno.isUsaLentes();	
		this.domicilio.replaceAllAttributes( alumno.getDomicilio() == null ? new Domicilio() : alumno.getDomicilio() );
		
	}
	
	public void addATutor(  Tutor tutor) {
		this.tutores.add(tutor);
	}
	
	public void removeTutor(  Tutor tutor ) {
		this.tutores.remove(tutor);
	}
	
	
	
	


	
	
	

}
