package com.proyecto.cct.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.repository.CctRepository; 

@Service
public class CctServiceImpl  implements CctService {

	@Autowired
	private CctRepository cr;

	@Override
	@Transactional
	public List<CentroDeTrabajo> listar() {
		return this.cr.findAll();
	}

	@Override
	@Transactional
	public CentroDeTrabajo guardar(CentroDeTrabajo centroDeTrabajo) {
		return this.cr.save(centroDeTrabajo);
	}

	@Override
	@Transactional( readOnly = true )
	public CentroDeTrabajo buscarPorId(Long id) {
		return this.cr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.cr.deleteById(id);
	}

	@Override
	@Transactional( readOnly = true )
	public boolean existeEscuela(Long id) {
		return this.cr.existsById(id);
	}

	@Override
	@Transactional( readOnly = true )
	public CentroDeTrabajo buscarPörCct(String cct) {
		return this.cr.findByCct(cct).orElse(null);
	}


}
