package com.proyecto.alumnos.models.auxiliarEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.entity.Tutor;

public class AlumnoAux {

	private String nombre;
	
	private String apePaterno;
	
	private String apeMaterno;
	
	private String curp;
	
	private Date fechaNacimiento;
	  
	private String sexo;
	
	private String usaLentes;
	
	private String estatura;
	
	private String tipoSangre;
	
	private String peso;
	
	private List<DomicilioAux> domicilio = new ArrayList<>();
	
	private List<TutorAux> tutor = new ArrayList<>();
	
	 

	public AlumnoAux(String nombre, String apePaterno, String apeMaterno, String curp, Date fechaNacimiento,
			String sexo, String usaLentes, String estatura, String peso, List<DomicilioAux> domicilio,
			List<TutorAux> tutor) {
		super();
		this.nombre = nombre;
		this.apePaterno = apePaterno;
		this.apeMaterno = apeMaterno;
		this.curp = curp;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.usaLentes = usaLentes;
		this.estatura = estatura;
		this.peso = peso;
		this.domicilio = domicilio;
		this.tutor = tutor;
	}

	public AlumnoAux() {
		super();
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getUsaLentes() {
		return usaLentes;
	}

	public void setUsaLentes(String usaLentes) {
		this.usaLentes = usaLentes;
	}

	public String getEstatura() {
		return estatura;
	}

	public void setEstatura(String estatura) {
		this.estatura = estatura;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public List<DomicilioAux> getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(List<DomicilioAux> domicilio) {
		this.domicilio = domicilio;
	}

	public List<TutorAux> getTutor() {
		return tutor;
	}

	public void setTutor(List<TutorAux> tutor) {
		this.tutor = tutor;
	}
	
	
	
	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public void rellenarCampos( Alumno alumno ) {
		
		this.nombre = alumno.getNombre();
		this.apePaterno = alumno.getApePaterno();
		this.apeMaterno = alumno.getApeMaterno();
		this.curp = alumno.getCurp();
		this.fechaNacimiento = alumno.getFechaNacimiento();
		this.sexo = alumno.getSexo();
		this.usaLentes = alumno.isUsaLentes() ? "Si" : "No" ;
		this.estatura = alumno.getEstatura();
		this.peso = alumno.getPeso() + "";	
		this.tipoSangre = alumno.getTipoSangre();
				
		this.domicilio.add( new DomicilioAux() );
		
		this.domicilio.forEach( d ->{
			d.replaceAll( alumno.getDomicilio() );
		});
		
		this.tutor.add( new TutorAux() );		
		Tutor tutor = alumno.getTutores().get(0);
		
		this.tutor.forEach( t ->{
			t.replaceAll( tutor );
		});
		
		
		
		
	}
	



	 

}
