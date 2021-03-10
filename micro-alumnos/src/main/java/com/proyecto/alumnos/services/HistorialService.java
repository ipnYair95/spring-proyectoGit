package com.proyecto.alumnos.services;

import java.util.List;

import com.proyecto.alumnos.models.entity.Historial;

public interface HistorialService {
	
	public List<Historial> listarHistorialAlumno(Long id);
	
	public Historial guardar( Historial historial );
	
	public Historial buscarPorId(Long id);
	
	public void eliminar(Long id);
	
	
	public List<Historial> listarAlumnosByGrupo( Long idGrupo );

}
