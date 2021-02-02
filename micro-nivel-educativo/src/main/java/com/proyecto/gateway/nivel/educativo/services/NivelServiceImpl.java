package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.gateway.nivel.educativo.models.entity.Nivel;
import com.proyecto.gateway.nivel.educativo.models.repository.NivelRepository;

@Service
public class NivelServiceImpl implements NivelService{
	
	@Autowired
	private NivelRepository nr;

	@Override
	@Transactional
	public List<Nivel> listar() {
		return this.nr.findAll();
	}

	@Override
	@Transactional
	public Nivel guardar(Nivel nivel) {
		return this.nr.save(nivel);
	}

	@Override
	@Transactional( readOnly = true )
	public Nivel buscarPorId(Long id) {
		return this.nr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.nr.deleteById(id);
	}

}
