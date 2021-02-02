package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.proyecto.gateway.nivel.educativo.models.entity.Grado;
import com.proyecto.gateway.nivel.educativo.models.repository.GradoRepository; 

@Service
public class GradoServiceImpl implements GradoService {

	@Autowired
	private GradoRepository gr;

	@Override
	@Transactional
	public List<Grado> listar() {
		return this.gr.findAll();
	}

	@Override
	@Transactional
	public Grado guardar(Grado grado) {
		return this.gr.save(grado);
	}

	@Override
	@Transactional( readOnly = true )
	public Grado buscarPorId(Long id) {
		return this.gr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.gr.deleteById(id);
	}


}
