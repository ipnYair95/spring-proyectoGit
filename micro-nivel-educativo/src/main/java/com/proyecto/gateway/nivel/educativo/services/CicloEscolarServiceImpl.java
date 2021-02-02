package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.gateway.nivel.educativo.models.entity.CicloEscolar;
import com.proyecto.gateway.nivel.educativo.models.repository.CicloEscolarRepository;


@Service
public class CicloEscolarServiceImpl implements CicloEscolarService {

	@Autowired
	private CicloEscolarRepository cr;

	@Override
	@Transactional
	public List<CicloEscolar> listar() {
		return this.cr.findAll();
	}

	@Override
	@Transactional
	public CicloEscolar guardar(CicloEscolar cicloEscolar) {
		return this.cr.save(cicloEscolar);
	}

	@Override
	@Transactional( readOnly = true )
	public CicloEscolar buscarPorId(Long id) {
		return this.cr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.cr.deleteById(id);
	}


}
