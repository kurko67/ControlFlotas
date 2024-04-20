package com.kurko67.controlflotas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ControlflotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlflotasApplication.class, args);
	}

}
