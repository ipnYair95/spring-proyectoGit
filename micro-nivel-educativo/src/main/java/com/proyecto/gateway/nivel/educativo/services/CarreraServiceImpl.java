package com.proyecto.gateway.nivel.educativo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.gateway.nivel.educativo.models.entity.Carrera;
import com.proyecto.gateway.nivel.educativo.models.repository.CarreraRepository;

@Service
public class CarreraServiceImpl implements CarreraService{
	
	@Autowired
	private CarreraRepository cr;

	@Override
	@Transactional( readOnly = true )
	public List<Carrera> listar() {
		return this.cr.findAll();
	}

	@Override
	@Transactional
	public Carrera guardar(Carrera carrera) {
		// TODO Auto-generated method stub
		return this.cr.save(carrera);
	}

	@Override
	@Transactional( readOnly = true )
	public Carrera buscarPorId(Long id) {
		return this.cr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.cr.deleteById(id);		
	}

}
