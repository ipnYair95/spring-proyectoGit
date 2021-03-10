package com.proyecto.alumnos.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.proyecto.alumnos.controllers.AlumnoController;
import com.proyecto.alumnos.models.auxiliarEntity.AlumnoAux;
import com.proyecto.alumnos.models.entity.Alumno;
import com.proyecto.alumnos.models.repository.AlumnoRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
       
@Service
public class ReportService {

	@Autowired
	private AlumnoRepository ar;
	
	// Logger
	private static Logger log = LoggerFactory.getLogger(AlumnoController.class);
	
	Map<String, Object> datos = new HashMap<>();


	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		
		List<Alumno> alumnos = this.ar.findAll();
		// cargar y compilar archivo

		File file = ResourceUtils.getFile(  getClass().getClassLoader().getResource("static/alumnos.jrxml") );
		JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(alumnos);

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("createdBy", "Spring");
		
 
		JasperPrint jp = JasperFillManager.fillReport(jr, parametros, ds);

		/*
		if (reportFormat.equals("html")) {
			JasperExportManager.exportReportToHtmlFile( jp, "src/main/resources/alumnos.html" );
		}*/

		if (reportFormat.equals("pdf")) {
			File archivo = ResourceUtils.getFile( getClass().getClassLoader().getResource("alumnos.pdf") );
			JasperExportManager.exportReportToPdfFile( jp, archivo.getAbsolutePath()  );
		}

		return "report generated";

	}
	
	public String exportarAlumnoInformacion(Long id) throws FileNotFoundException, JRException {		
		
		
		Alumno alumno = this.ar.findById( id ).orElse(null);	
		
		AlumnoAux alumnoJasper = new AlumnoAux();
		log.info( alumnoJasper.toString() );
		alumnoJasper.rellenarCampos(alumno);						 
		
		List<AlumnoAux> listaJasperAlumno = new ArrayList<>();		
		listaJasperAlumno.add( alumnoJasper );			 	 	
	 
		//File file = ResourceUtils.getFile(  getClass().getClassLoader().getResource("alumnos.pdf") );
		File file = ResourceUtils.getFile(  getClass().getClassLoader().getResource("static/reporteAlumno/alumnoInfo.jrxml") );
		File ruta = ResourceUtils.getFile(  getClass().getClassLoader().getResource("static/reporteAlumno/subDomicilio.jasper") );
		File ruta2 = ResourceUtils.getFile(  getClass().getClassLoader().getResource("static/reporteAlumno/subTutor.jasper") );
		
		//log.info( "Ruta " + file.getAbsolutePath() );
	 
		JasperReport jr = JasperCompileManager.compileReport( file.getAbsolutePath() );
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaJasperAlumno);
		   
		datos = new HashMap<>();
		//datos.put("param", "parametro" );		
		datos.put("param", ruta.getAbsolutePath() );
		datos.put("param2", ruta2.getAbsolutePath() ); 
		
		log.info( "Campos nombre " + alumno.getTutores().get(0).getNombre() );
 
		JasperPrint jp = JasperFillManager.fillReport(jr, datos, ds);

		File archivo = ResourceUtils.getFile( getClass().getClassLoader().getResource("alumnoInformacion.pdf") );
		//log.info("destino " + archvo.getAbsolutePath()  );
		JasperExportManager.exportReportToPdfFile( jp, archivo.getAbsolutePath() );
		
	
		return "report generated"; 
	 
	}
	 
	

}
