package com.proyecto.cct.services;

import java.util.List;

import com.proyecto.cct.models.entity.CentroDeTrabajo;

public interface CctService {

	public List<CentroDeTrabajo> listar();

	public CentroDeTrabajo guardar(CentroDeTrabajo centroDeTrabajo);

	public CentroDeTrabajo buscarPorId(Long id);
	
	public CentroDeTrabajo buscarPörCct( String cct );

	public void eliminar(Long id);
	
	public boolean existeEscuela(Long id);

}
