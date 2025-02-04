package com.proyecto.cct.services;

import java.util.List;

import com.proyecto.cct.models.entity.Grupo;


public interface GrupoService {
	
	public List<Grupo> listar();

	public Grupo guardar(Grupo grupo);

	public Grupo buscarPorId(Long id);

	public void eliminar(Long id);
	
	public Grupo estaAsignado( String idSalon, Long idCicloEscolar );
	

}
