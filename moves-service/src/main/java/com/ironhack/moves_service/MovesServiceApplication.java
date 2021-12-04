package com.ironhack.moves_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovesServiceApplication.class, args);
	}

}
