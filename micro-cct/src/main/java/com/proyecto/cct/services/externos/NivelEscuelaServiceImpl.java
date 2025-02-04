package com.proyecto.cct.services.externos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.NivelEscuela;
import com.proyecto.cct.models.repository.CctRepository;
import com.proyecto.cct.models.repository.NivelEscuelaRepository; 

@Service
public class NivelEscuelaServiceImpl  implements NivelEscuelaService {

	@Autowired
	private NivelEscuelaRepository nivelER;

	@Override
	@Transactional
	public List<NivelEscuela> listar() {
		return this.nivelER.findAll();
	}

	@Override
	@Transactional
	public NivelEscuela guardar(NivelEscuela nivelEscuela) {
		return this.nivelER.save(nivelEscuela);
	}

	@Override
	@Transactional( readOnly = true )
	public NivelEscuela buscarPorId(Long id) {
		return this.nivelER.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.nivelER.deleteById(id);
	}

	@Override
	public List<NivelEscuela> obtenerIdNivel(Long idEscuela) {
		return this.nivelER.findNivelesIdByIdEscuela(idEscuela);
	}


}
