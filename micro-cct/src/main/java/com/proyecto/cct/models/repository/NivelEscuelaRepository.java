package com.proyecto.cct.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.cct.models.entity.NivelEscuela;

public interface NivelEscuelaRepository extends JpaRepository<NivelEscuela, Long>{	
	
	
	
	@Query( "select n from NivelEscuela n "
			+ " join n.centroDeTrabajo cct "
			+ " where cct.id = ?1 " ) 
	public List<NivelEscuela>  findNivelesIdByIdEscuela( Long id );

}
