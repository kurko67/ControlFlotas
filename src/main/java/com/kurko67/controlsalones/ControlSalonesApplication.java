package com.kurko67.controlsalones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ControlSalonesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlSalonesApplication.class, args);
	}

}
