package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import com.proyecto.gateway.nivel.educativo.models.entity.CicloEscolar;

public interface CicloEscolarService {
	
	public List<CicloEscolar> listar();
	
	public CicloEscolar guardar( CicloEscolar cicloEscolar );
	
	public CicloEscolar buscarPorId(Long id);
	
	public void eliminar(Long id);

}
