package com.proyecto.cct.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.cct.models.entity.ModalidadNivel;
import com.proyecto.cct.models.entity.NivelEscuela;

public interface ModalidadNivelRepository extends JpaRepository<ModalidadNivel, Long>{	
	
	
	@Query( "select m.id from ModalidadNivel m "
			+ " join m.nivelEscuela ne "
			+ " where ne.id = ?1 " ) 
	public List<Long>  findModalidadesIdByIdNivelEscuela( Long id );

}
