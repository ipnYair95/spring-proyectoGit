package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.gateway.nivel.educativo.models.entity.Asignatura;
import com.proyecto.gateway.nivel.educativo.models.entity.CicloEscolar;
import com.proyecto.gateway.nivel.educativo.models.repository.AsignaturaRepository;
import com.proyecto.gateway.nivel.educativo.models.repository.CicloEscolarRepository;


@Service
public class AsignaturaServiceImpl implements AsignaturaService {

	@Autowired
	private AsignaturaRepository ar;

	@Override
	@Transactional
	public List<Asignatura> listar() {
		return this.ar.findAll();
	}

	@Override
	@Transactional
	public Asignatura guardar(Asignatura asignatura) {
		return this.ar.save(asignatura);
	}

	@Override
	@Transactional( readOnly = true )
	public Asignatura buscarPorId(Long id) {
		return this.ar.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.ar.deleteById(id);
	}


}
