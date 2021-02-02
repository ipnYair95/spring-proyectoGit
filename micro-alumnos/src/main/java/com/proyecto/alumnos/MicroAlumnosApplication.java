package com.proyecto.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroAlumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroAlumnosApplication.class, args);
	}

}
