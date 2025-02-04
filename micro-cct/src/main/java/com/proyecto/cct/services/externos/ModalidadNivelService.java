package com.proyecto.cct.services.externos;

import java.util.List;

import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.ModalidadNivel;
import com.proyecto.cct.models.entity.NivelEscuela;

public interface ModalidadNivelService {
	
	public List<ModalidadNivel> listar();

	public ModalidadNivel guardar(ModalidadNivel modalidadNivel);

	public ModalidadNivel buscarPorId(Long id);

	public void eliminar(Long id);
	
	public List<Long> obtenerIdModalides( Long idNivelEscuela );

}
