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

import com.proyecto.cct.feign.clients.NivelClient;
import com.proyecto.cct.feign.clients.entity.Nivel;
import com.proyecto.cct.models.entity.CentroDeTrabajo;
import com.proyecto.cct.models.entity.NivelEscuela;
import com.proyecto.cct.services.CctService;
import com.proyecto.cct.services.externos.NivelEscuelaService;

import feign.FeignException;

@RestController
@RequestMapping("/nivel")
public class NivelEscuelaController {

	@Autowired
	private NivelClient nivelService;

	@Autowired
	private NivelEscuelaService nivelEscuelaService;

	@Autowired
	private CctService centroService;

	// Lista de respuestas
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(NivelEscuelaController.class);

	@GetMapping("/{idEscuela}/listar")
	public ResponseEntity<?> listarIdModalidades(@PathVariable(name = "idEscuela") Long IdEscuela) {
		return ResponseEntity.ok().body(this.nivelEscuelaService.obtenerIdNivel(IdEscuela));
	}

	@PostMapping("/{id}/asignar-nivel")
	public ResponseEntity<?> asignarNivel(@Valid @RequestBody NivelEscuela nivelEscuela, BindingResult validacion,
			@PathVariable(name = "id") Long id) {

		NivelEscuela nivelEscuelaDb;

		if (validacion.hasErrors()) {
			return this.validar(validacion);
		}

		try {

			Nivel nivelDb = this.nivelService.buscarPorId(nivelEscuela.getIdNivel()).orElse(null);
			CentroDeTrabajo cctDb = this.centroService.buscarPorId(id);

			if (nivelDb == null || cctDb == null) {
				return enviarMensaje("No existe nivel o CCT asociados", HttpStatus.NOT_FOUND);
			}

			for (NivelEscuela relacionDb : cctDb.getNivelEscuela()) {
				if (nivelEscuela.getIdNivel() == relacionDb.getIdNivel()) {
					return enviarMensaje("Ya existe el nivel en la CCT", HttpStatus.BAD_REQUEST);
				}
			}

			nivelEscuela.setCentroDeTrabajo(cctDb);
			nivelEscuelaDb = this.nivelEscuelaService.guardar(nivelEscuela);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FeignException.NotFound e) {
			return enviarMensaje("No existe nivel o CCT asociados", HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok().body(nivelEscuelaDb);
	}

	@DeleteMapping("/{idEscuela}/eliminar-nivel/{idRelacion}")
	public ResponseEntity<?> eliminarNivel(@PathVariable(name = "idEscuela") Long idEscuela,
			@PathVariable(name = "idRelacion") Long idRelacion) {

		try {
			
			NivelEscuela relacionDb = this.nivelEscuelaService.buscarPorId(idRelacion);
			CentroDeTrabajo cctDb = this.centroService.buscarPorId(idEscuela);

			if (relacionDb == null || cctDb == null) {
				return enviarMensaje("No existe relacion", HttpStatus.NOT_FOUND);
			}

			this.nivelEscuelaService.eliminar(idRelacion);

		} catch (DataException e) {
			return this.enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (FeignException.NotFound e) {
			return enviarMensaje("No existe nivel o CCT asociados", HttpStatus.NOT_FOUND);
		}

		return enviarMensaje(" Nivel eliminado ", HttpStatus.OK );

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

