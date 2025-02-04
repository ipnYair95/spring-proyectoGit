package com.proyecto.cct.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.cct.feign.clients.ContadorClient;
import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.Salon;
import com.proyecto.cct.services.CctService;

@RestController
public class CctControler {

	@Autowired
	private CctService cctService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(CctControler.class);

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(this.cctService.listar());
	}

	@GetMapping("/existe-escuela/{id}")
	public boolean existeEscuela(@PathVariable Long id) {
		return this.cctService.existeEscuela(id);
	}

	@GetMapping("/buscar-por-id/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		CentroDeTrabajo ctDb = null;

		try {

			ctDb = this.cctService.buscarPorId(id);

			if (ctDb == null) {
				return this.enviarMensaje("La CCT no existe", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(ctDb);

	}

	@GetMapping("/buscar-por-cct/{cct}")
	public ResponseEntity<?> buscarPorCct(@PathVariable String cct) {

		CentroDeTrabajo cctDb = null;

		try {

			cctDb = this.cctService.buscarPörCct(cct);

			if (cctDb == null) {
				return this.enviarMensaje("La CCT no existe", HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(cctDb);

	}

	@PostMapping("/crear")
	public ResponseEntity<?> guardar(@Valid @RequestBody CentroDeTrabajo ct, BindingResult validacion) {

		CentroDeTrabajo ctDb;

		if (validacion.hasErrors()) {
			log.info("error antes");
			return this.validar(validacion);
		}

		try {

			/*
			 * ct.getSalones().forEach(salon -> {
			 * 
			 * LocalDate fecha = LocalDate.now(); String fechaString = fecha.getDayOfYear()
			 * + ""; String id = fechaString +
			 * contadorClient.getContadorIncrementar().toString(); salon.setId(id);
			 * 
			 * });
			 */

			ctDb = this.cctService.guardar(ct);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok().body(ctDb);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody CentroDeTrabajo ct, BindingResult validacion,
			@PathVariable Long id) {

		CentroDeTrabajo ctDb;

		if (validacion.hasErrors()) {
			return this.validar(validacion);
		}

		try {

			ctDb = this.cctService.buscarPorId(id);

			if (ctDb == null) {
				return this.enviarMensaje("No existe el CCT", HttpStatus.BAD_REQUEST);
			}

			ctDb.replaceAll(ct);

			if (ct.getSalones().size() == 0) {
				ctDb.getSalones().clear();
				this.cctService.guardar(ctDb);
				return enviarMensaje("CCT actualizado", HttpStatus.OK);

			} else if (ctDb.getSalones().size() > 0) {

				List<Salon> salonesTemporal = ctDb.getSalones();

				for( int i  =  0; i < salonesTemporal.size(); i++ ) {
					
					 if( !ct.getSalones().contains( salonesTemporal.get(i) ) ) {
						 ctDb.getSalones().remove( salonesTemporal.get(i) );
					 }
				}

			}

			for (Salon salon : ct.getSalones()) {

				if (ctDb.getSalones().size() != 0) {
					
					if( salon.getId() != null ) {
						
						ctDb.getSalones().forEach(salonDb -> {
							if (salonDb.getId() ==  salon.getId()) {
								salonDb.replaceAll(salon);
								return;
							}
						});
						
					}else {						
						ctDb.addSalon(salon); 						
					} 
				} 
			}

			this.cctService.guardar(ctDb);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return enviarMensaje("CCT actualizado", HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {

		CentroDeTrabajo ctDb;

		try {

			ctDb = this.cctService.buscarPorId(id);

			if (ctDb == null) {
				return this.enviarMensaje("No existe la CCT", HttpStatus.BAD_REQUEST);
			}

			this.cctService.eliminar(id);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return this.enviarMensaje("CCT eliminado", HttpStatus.OK);

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
