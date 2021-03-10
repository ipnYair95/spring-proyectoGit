package com.proyecto.alumnos.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.alumnos.feign.client.EscuelaClient;
import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.entity.Historial;
import com.proyecto.alumnos.services.AlumnoService;
import com.proyecto.alumnos.services.HistorialService;

@RestController
@RequestMapping( "/historial" )
public class HistorialController {
	
	@Autowired
	private HistorialService historialService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Autowired
	private EscuelaClient escuelaService;
	
	Map<String, Object> respuesta = new HashMap<>();

	// Logger
	private static Logger log = LoggerFactory.getLogger(HistorialController.class);
	
	@GetMapping("/listar-alumnos/{idGrupo}")
	public ResponseEntity<?> listarAlumnosByGrupo( @PathVariable Long idGrupo ){
		List<Historial> historiales = this.historialService.listarAlumnosByGrupo(idGrupo);
		
		return ResponseEntity.ok().body( historiales );
	}
	
	
	@GetMapping("/buscar-estatus-alumno/{idAlumno}")
	public ResponseEntity<?> buscarEstatusAlumno( @PathVariable Long idAlumno){
		
		List<Historial> historiales = this.historialService.listarHistorialAlumno(idAlumno);	
		
		if( historiales.size() > 0 ) {
			return enviarMensaje("El alumno si tiene", HttpStatus.OK);
		}
		
		return enviarMensaje("El alumno no tiene historial academico",  HttpStatus.NOT_FOUND );
		
	}
	
	@PutMapping("/cambio-grupo/{idHistorial}")
	public ResponseEntity<?> cambiarGrupo( @PathVariable Long idHistorial, @RequestBody Long idGrupo  ){
		
		Historial historialDb = null;
		try {
			
			historialDb = this.historialService.buscarPorId(idHistorial);
			
			if( historialDb == null ) {
				
				return enviarMensaje("Registro no encontrado",  HttpStatus.NOT_FOUND );
			}
			
			historialDb.setIdGrupo(idGrupo);
			this.historialService.guardar(historialDb);
			
		}catch( DataException e ) {
			return enviarMensaje("Error interno",  HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return enviarMensaje("Alumno asignado a nuevo grupo",  HttpStatus.OK );
	}
	
	@PutMapping("/baja-alumno/{idHistorial}")
	public ResponseEntity<?> bajaAlumno( @PathVariable Long idHistorial  ){
		
		Historial historialDb = null;
		try {
			
			historialDb = this.historialService.buscarPorId(idHistorial);
			
			if( historialDb == null ) {
				
				return enviarMensaje(" Registro no encontrado ",  HttpStatus.NOT_FOUND );
			}
			
			historialDb.setEstaActivo(false);	
			this.historialService.guardar(historialDb);
			
		}catch( DataException e ) {
			return enviarMensaje("Error interno",  HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return enviarMensaje("Alumno dado de baja",  HttpStatus.OK );
	}
	
	@PutMapping("/cambio-escuela/{idHistorial}")
	public ResponseEntity<?> cambioDeEscuela(
			@PathVariable Long idHistorial, @Valid @RequestBody Historial historial	, BindingResult validacion	){
		
		if( validacion.hasErrors() ) {
			return this.validar(validacion);
		}
		
		log.info( historial.toString() );
		Historial historialDb = null;
		
		try {
			
			historialDb = this.historialService.buscarPorId( idHistorial );
			
			if( historialDb == null ) {
				return enviarMensaje("No existe registro del alumno", HttpStatus.NOT_FOUND);
			}
			
			historialDb.setEstaActivo(historial.isEstaActivo());
			historialDb.setIdGrupo( historial.getIdGrupo() );
			
			historialDb = this.historialService.guardar( historialDb );
			
		}catch (DataAccessException e) {
			return enviarMensaje("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return enviarMensaje("Cambio de escuela exitoso",  HttpStatus.OK );
		
	}
	
	@PostMapping( "/asignar-historial/{idAlumno}" )
	public ResponseEntity<?>  asignarHistorial( @PathVariable Long idAlumno, @Valid @RequestBody Historial historial, BindingResult validacion ){
				
		if( validacion.hasErrors() ) {
			return validar( validacion );
		}
		
		Historial historialDb = null;
		
		try {
			
			Alumno alumnoDb = this.alumnoService.buscarPorId(idAlumno);
			
			if( alumnoDb == null ) {
				return enviarMensaje( "El alumno no existe" , HttpStatus.NOT_FOUND);
			}			
			
			historial.setFechaAlta( LocalDate.now() );
			historial.setAlumno(alumnoDb);
			
			log.info( historial.toString() );
			
			historialDb = this.historialService.guardar( historial );
			
			
		}catch (DataException e) {
			enviarMensaje("Error interno",  HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		return ResponseEntity.ok().body( historialDb );
		
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
