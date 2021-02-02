package com.proyecto.alumnos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.alumnos.models.entity.Historial;
import com.proyecto.alumnos.models.repository.HistorialRepository;

@Service
public class HistorialServiceImpl implements HistorialService {
	
	@Autowired
	private HistorialRepository hr;

	@Override	
	@Transactional( readOnly = true )
	public List<Historial> listarHistorialAlumno( Long id) {
		return this.hr.listarHistorialAlumno( id );
	}

	@Override
	@Transactional
	public Historial guardar(Historial historial) {
		return this.hr.save(historial);
	}

	@Override
	@Transactional( readOnly = true )
	public Historial buscarPorId(Long id) {
		return this.hr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.hr.deleteById(id);
		
	}

}
