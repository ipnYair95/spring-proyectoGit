package com.proyecto.alumnos.services;

import java.util.List;

import com.proyecto.alumnos.models.entity.Domicilio;
import com.proyecto.alumnos.models.entity.Tutor;

public interface TutorService {
	
	public List<Tutor> listar();
	
	public Tutor buscarPorId( Long id );
	
	public Tutor guardar( Tutor tutor );

}
