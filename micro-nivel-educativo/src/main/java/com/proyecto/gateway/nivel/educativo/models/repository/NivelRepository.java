package com.proyecto.gateway.nivel.educativo.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.gateway.nivel.educativo.models.entity.Nivel;

public interface NivelRepository extends JpaRepository<Nivel, Long>{
	
	
}
