package com.proyecto.cct.services.externos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.ModalidadNivel;
import com.proyecto.cct.models.entity.NivelEscuela;
import com.proyecto.cct.models.repository.CctRepository;
import com.proyecto.cct.models.repository.ModalidadNivelRepository;
import com.proyecto.cct.models.repository.NivelEscuelaRepository; 

@Service
public class ModalidadNivelServiceImpl  implements ModalidadNivelService {

	@Autowired
	private ModalidadNivelRepository modalidadNR;

	@Override
	@Transactional
	public List<ModalidadNivel> listar() {
		return this.modalidadNR.findAll();
	}

	@Override
	@Transactional
	public ModalidadNivel guardar(ModalidadNivel modalidadNivel) {
		return this.modalidadNR.save(modalidadNivel);
	}

	@Override
	@Transactional( readOnly = true )
	public ModalidadNivel buscarPorId(Long id) {
		return this.modalidadNR.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.modalidadNR.deleteById(id);
	}

	@Override
	public List<Long> obtenerIdModalides(Long idNivelEscuela) {
		return this.modalidadNR.findModalidadesIdByIdNivelEscuela(idNivelEscuela);
	}


}
