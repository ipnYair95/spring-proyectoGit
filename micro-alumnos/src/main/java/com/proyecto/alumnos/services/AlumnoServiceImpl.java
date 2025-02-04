package com.proyecto.alumnos.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.alumnos.controllers.AlumnoController;
import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.repository.AlumnoRepository;

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	@Autowired
	private AlumnoRepository ar;
	
	//Logger
	private static Logger log = LoggerFactory.getLogger( AlumnoController.class );
	

	@Override
	@Transactional( readOnly = true )
	public List<Alumno> listar() {
		return  this.ar.findAll();
	}

	@Override
	@Transactional( readOnly = true )
	public Alumno buscarPorId(Long id) {
		return this.ar.findById(id).orElse(null);
	}
	
	@Override
	public List<Alumno> buscarPorApellido(String apellido) {
		return this.ar.findByApellidos(apellido);
	}	

	@Override
	@Transactional
	public Alumno guardar(Alumno alumno) {
		return this.ar.save(alumno);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.ar.deleteById(id);		
	}

	@Override
	@Transactional
	public List<Alumno> guardarVarios(List<Alumno> lista) {
		return (List<Alumno>) this.ar.saveAll(  lista );
	}

	@Override
	@Transactional( readOnly = true )
	public Alumno buscarPorCurp(String curp) {

		Alumno alumnoDb = this.ar.findByCurp(curp);		
		return this.ar.findByCurp(curp);
	}

	@Override
	public boolean existeId(Long id) {
		return this.ar.existsById(id);
	}
	

}
