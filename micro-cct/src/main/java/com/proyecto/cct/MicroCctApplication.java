package com.proyecto.cct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroCctApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroCctApplication.class, args);
	}

}
