package com.proyecto.cct.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.cct.feign.clients.ModalidadClient;
import com.proyecto.cct.feign.clients.NivelClient;
import com.proyecto.cct.feign.clients.entity.Carrera;
import com.proyecto.cct.feign.clients.entity.Nivel;
import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.ModalidadNivel;
import com.proyecto.cct.models.entity.NivelEscuela;
import com.proyecto.cct.services.CctService;
import com.proyecto.cct.services.externos.ModalidadNivelService;
import com.proyecto.cct.services.externos.NivelEscuelaService;

import feign.FeignException;

@RestController
@RequestMapping("/nivel/modalidad")
public class ModalidadNivelController {

	@Autowired
	private ModalidadClient modalidadService;

	@Autowired
	private ModalidadNivelService modalidadNivelService;

	@Autowired
	private NivelEscuelaService nivelEscuelaService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(ModalidadNivelController.class);

	@GetMapping("/{nivelEscuelaId}/listar")
	public ResponseEntity<?> listar(@PathVariable(name = "idNivel") Long nivelEscuelaId) {
		return ResponseEntity.ok().body(this.modalidadNivelService.obtenerIdModalides(nivelEscuelaId));
	}

	@PostMapping("/{nivelEscuelaId}/asignar-modalidad")
	public ResponseEntity<?> asignarNivel(@Valid @RequestBody ModalidadNivel modalidadNivel, BindingResult validacion,
			@PathVariable(name = "nivelEscuelaId") Long nivelEscuelaId) {

		ModalidadNivel modalidadNivelDb;

		if (validacion.hasErrors()) {
			return this.validar(validacion);
		}

		try {
			
			Carrera carreraDb = this.modalidadService.buscarPorId( modalidadNivel.getIdModalidad()).orElse(null);
			NivelEscuela relacion = this.nivelEscuelaService.buscarPorId( nivelEscuelaId );
			
			if( carreraDb == null || relacion == null ) {
				return enviarMensaje(  "No existe modalidad o relacion para asignar" , HttpStatus.NOT_FOUND  );
			}
			
			for ( ModalidadNivel relacionDb : relacion.getModalidades() ) {
				if ( modalidadNivel.getIdModalidad()  == relacionDb.getIdModalidad() ) {
					return enviarMensaje("Ya existe esta modalidad", HttpStatus.BAD_REQUEST);
				}
			}
			
			modalidadNivel.setNivelEscuela( relacion  );
			
			modalidadNivelDb = this.modalidadNivelService.guardar(modalidadNivel);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FeignException.NotFound e) {
			return enviarMensaje("No existe modalidad o relacion para asignar", HttpStatus.NOT_FOUND);
		}
 
		return ResponseEntity.ok().body(modalidadNivelDb);
	}

	@DeleteMapping("/{idRelacion}/eliminar-modalidad/{id}")
	public ResponseEntity<?> eliminarNivel(@PathVariable(name = "idRelacion") Long idRelacion,
			@PathVariable(name = "id") Long id) {

		try {
			
			ModalidadNivel mnDb = this.modalidadNivelService.buscarPorId(id);
			NivelEscuela neDb = this.nivelEscuelaService.buscarPorId(idRelacion);

			if (mnDb == null || neDb == null) {
				return enviarMensaje("No existe relacion", HttpStatus.NOT_FOUND);
			}

			this.modalidadNivelService.eliminar(id);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		} 

		return enviarMensaje(" Modalidad eliminada ", HttpStatus.OK );

	}

	private ResponseEntity<?> validar(BindingResult result) {

		Map<String, Object> errores = new HashMap<>();

		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "Campo " + err.getField() + " " + err.getDefaultMessage());
		});

		return ResponseEntity.badRequest().body(errores);

	}


	private ResponseEntity<?> enviarMensaje(String texto, HttpStatus estado) {

		respuesta.put("mensaje", texto);
		log.info(texto);
		return ResponseEntity.status(estado).body(respuesta);

	}

}

