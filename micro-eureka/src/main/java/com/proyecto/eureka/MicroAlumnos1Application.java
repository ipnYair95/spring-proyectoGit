package com.proyecto.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MicroAlumnos1Application {

	public static void main(String[] args) {
		SpringApplication.run(MicroAlumnos1Application.class, args);
	}

}
