package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import com.proyecto.gateway.nivel.educativo.models.entity.Carrera;

public interface CarreraService {
	
	public List<Carrera> listar();
	
	public Carrera guardar( Carrera carrera );
	
	public Carrera buscarPorId(Long id);
	
	public void eliminar(Long id);

}
