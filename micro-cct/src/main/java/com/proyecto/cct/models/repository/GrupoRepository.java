package com.proyecto.cct.models.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.cct.models.entity.Grupo;

public interface GrupoRepository  extends JpaRepository<Grupo, Long> {

	@Query( "select g from Grupo g join g.salon s where s.id = ?1 and g.idCicloEscolar = ?2" )
	public Optional<Grupo> salonAsignadoEnCiclo( String idSalon, Long idCiclo );

	
}
