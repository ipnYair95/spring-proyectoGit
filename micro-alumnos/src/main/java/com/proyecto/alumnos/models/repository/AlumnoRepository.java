package com.proyecto.alumnos.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.entity.Tutor;

//dao para el entity alumno, extiende de crud para los metodos jpa por debajo

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno,Long>{
	
	@Query( "select a from Alumno a where a.curp = ?1" )
	Alumno findByCurp(String curp);
	
	@Query("select a from Alumno a where ( a.apePaterno = ?1 OR a.apeMaterno = ?1 )  ")
	List<Alumno> findByApellidos(String apellido);
	
	//selecciona los alumnos y jala de una vez sus relaciones, distinct es para evitar repeticiones
	@Query("select distinct a from Alumno a join fetch a.tutores join fetch a.domicilio")
	List<Alumno> findAllFetch();
	
	/**
	 * Alumno a
	 * Domicilio d
	 * Tutor t
	 * @param id
	 * @return
	 */
	@Query(" SELECT a FROM Alumno a JOIN a.domicilio d JOIN a.tutores ts WHERE a.id = ?1 AND ts.esResponsable = TRUE ")
	Alumno getInfoById( Long id );
	 

}
