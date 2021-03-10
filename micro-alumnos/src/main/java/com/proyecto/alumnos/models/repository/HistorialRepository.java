package com.proyecto.alumnos.models.repository;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.alumnos.models.entity.Historial; 

public interface HistorialRepository extends JpaRepository<Historial, Long> {
	
	@Query( " select h from Historial h join h.alumno a where a.id = ?1  order by h.fechaAlta asc " )
	public List<Historial> listarHistorialAlumno( Long id );	
	
	@Query( " select h from Historial h where h.idGrupo = ?1 " )
	public List<Historial> listarAlumnosByGrupo( Long idGrupo );
	

}
