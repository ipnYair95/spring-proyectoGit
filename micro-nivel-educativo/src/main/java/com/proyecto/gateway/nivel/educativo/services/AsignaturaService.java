package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import com.proyecto.gateway.nivel.educativo.models.entity.Asignatura;
import com.proyecto.gateway.nivel.educativo.models.entity.Carrera;

public interface AsignaturaService {
	
	public List<Asignatura> listar();
	
	public Asignatura guardar( Asignatura asignatura );
	
	public Asignatura buscarPorId(Long id);
	
	public void eliminar(Long id);

}
