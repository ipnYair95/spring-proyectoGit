package com.proyecto.alumnos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/buscar-estatus-alumno/{idAlumno}")
	public ResponseEntity<?> buscarEstatusAlumno( @PathVariable Long idAlumno){
		
		List<Historial> historiales = this.historialService.listarHistorialAlumno(idAlumno);	
		
		if( historiales.size() > 0 ) {
			return enviarMensaje("El alumno si tiene", HttpStatus.OK);
		}
		
		return enviarMensaje("El alumno no tiene historial academico",  HttpStatus.NOT_FOUND );
		
	}
	
	
	
	@PostMapping( "/asignar-historial/{idAlumno}" )
	public ResponseEntity<?>  asignarHistorial( @PathVariable Long idAlumno, @Valid @RequestBody Historial historial, BindingResult validacion ){
		
		if( validacion.hasErrors() ) {
			return validar( validacion );
		}
		
		Historial historialDb = null;
		
		try {
			
			boolean existeAlumno = this.alumnoService.existeId(idAlumno);
			boolean existeEscuela = this.escuelaService.existeEscuela( historial.getIdEscuela() );
			
			if( !existeAlumno || !existeEscuela ) {
				enviarMensaje("No existe alumno o escuela asociado",  HttpStatus.NOT_FOUND );
			}
			
			Alumno alumnoDb = new Alumno();
			alumnoDb.setId(idAlumno);
			
			historial.setAlumno( alumnoDb );
			historialDb = this.historialService.guardar(historial);			
			
			
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
