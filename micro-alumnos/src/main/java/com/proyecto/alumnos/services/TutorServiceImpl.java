package com.proyecto.alumnos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.alumnos.models.entity.Domicilio;
import com.proyecto.alumnos.models.entity.Tutor;
import com.proyecto.alumnos.models.repository.DomicilioRepository;
import com.proyecto.alumnos.models.repository.TutorRepository;

@Service
public class TutorServiceImpl implements TutorService {
	
	@Autowired
	private TutorRepository tr;

	@Override
	@Transactional( readOnly = true )
	public List<Tutor> listar() {
		return this.tr.findAll();
	}
 

	@Override
	@Transactional
	public Tutor guardar(Tutor tutor) {
		return tr.save(tutor);		
	}



	@Override
	@Transactional( readOnly = true )
	public Tutor buscarPorId(Long id) {
		return tr.findById(id).orElse(null);
	}


}
