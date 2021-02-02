package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;


import com.proyecto.gateway.nivel.educativo.models.entity.Nivel;

public interface NivelService {
	
	public List<Nivel> listar();
	
	public Nivel guardar( Nivel nivel );
	
	public Nivel buscarPorId(Long id);
	
	public void eliminar(Long id);
	
}
