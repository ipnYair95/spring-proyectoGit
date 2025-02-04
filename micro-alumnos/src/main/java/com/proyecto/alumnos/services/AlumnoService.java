package com.proyecto.alumnos.services;

import java.util.List;

import com.proyecto.alumnos.models.entity.Alumno;

// interfaz general para el uso de los metodos crud o sus implementaciones

public interface AlumnoService{

	public List<Alumno> listar();
	
	public Alumno buscarPorId(Long id);
	
	public List<Alumno> buscarPorApellido( String apellido );
	
	public boolean existeId( Long id );
	
	public Alumno guardar(Alumno alumno);
	
	public List<Alumno> guardarVarios( List<Alumno> lista ); 
	
	public void eliminar(Long id);
	
	/////
	
	public Alumno buscarPorCurp(String curp);
	
	
	
}
