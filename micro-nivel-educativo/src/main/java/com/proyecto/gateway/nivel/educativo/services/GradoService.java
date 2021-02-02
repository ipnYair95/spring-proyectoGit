package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import com.proyecto.gateway.nivel.educativo.models.entity.Grado;

public interface GradoService {
	
	public List<Grado> listar();
	
	public Grado guardar( Grado grado );
	
	public Grado buscarPorId(Long id);
	
	public void eliminar(Long id);
	
}
