package com.proyecto.cct.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.cct.models.entity.Salon;
import com.proyecto.cct.models.repository.SalonRepository;

@Service
public class SalonServiceImpl implements SalonService{
	
	@Autowired
	private SalonRepository sr;

	@Override
	public Salon buscarPorId(String id) {		
		return this.sr.findById( id ).orElse(null);
	}

}
