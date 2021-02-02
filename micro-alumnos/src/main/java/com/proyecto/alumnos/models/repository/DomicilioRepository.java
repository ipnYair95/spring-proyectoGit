package com.proyecto.alumnos.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.alumnos.models.entity.Domicilio;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {

}
