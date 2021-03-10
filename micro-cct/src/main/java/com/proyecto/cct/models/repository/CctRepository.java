package com.proyecto.cct.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.cct.models.entity.CentroDeTrabajo;

public interface CctRepository extends JpaRepository<CentroDeTrabajo, Long> {	
	
	public Optional<CentroDeTrabajo> findByCct(String cct);
	
	
	
}
