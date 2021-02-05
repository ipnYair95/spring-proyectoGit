package com.proyecto.cct.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.cct.models.entity.Grupo;
import com.proyecto.cct.models.repository.GrupoRepository;

@Service
public class GrupoServiceImpl implements GrupoService {
	
	@Autowired
	private GrupoRepository gr;

	@Override
	@Transactional( readOnly = true )
	public List<Grupo> listar() {
		return this.gr.findAll();
	}

	@Override
	@Transactional
	public Grupo guardar(Grupo grupo) {
		return this.gr.save(grupo);
	}

	@Override
	@Transactional( readOnly = true )
	public Grupo buscarPorId(Long id) {
		return this.gr.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		this.gr.deleteById(id);		
	}

	@Override
	@Transactional( readOnly = true )
	public Grupo estaAsignado(String idSalon, Long idCicloEscolar) {
		return this.gr.salonAsignadoEnCiclo(idSalon,idCicloEscolar).orElse(null);
	}
	
	

}
