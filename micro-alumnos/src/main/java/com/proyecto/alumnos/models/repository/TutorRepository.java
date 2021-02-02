package com.proyecto.alumnos.models.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.alumnos.models.entity.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long>{
	

}
