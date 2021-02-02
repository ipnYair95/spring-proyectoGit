package com.proyecto.alumnos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.entity.Domicilio;
import com.proyecto.alumnos.models.repository.DomicilioRepository;

@Service
public class DomicilioServiceImpl implements DomicilioService{
	
	@Autowired
	private DomicilioRepository dr;

	@Override
	@Transactional( readOnly = true )
	public Domicilio buscarPorId(Long id) {
		return this.dr.findById(id).orElse(null);
	}
	
	

}
