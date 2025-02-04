package com.proyecto.cct.services.externos;

import java.util.List;

import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.NivelEscuela;

public interface NivelEscuelaService {
	
	public List<NivelEscuela> listar();

	public NivelEscuela guardar(NivelEscuela nivelEscuela);

	public NivelEscuela buscarPorId(Long id);

	public void eliminar(Long id);
	
	public List<NivelEscuela> obtenerIdNivel( Long idEscuela );

}
