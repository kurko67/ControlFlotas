package com.kurko67.controlflotas;

import com.kurko67.controlflotas.util.paginator.WhatsAppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ControlflotasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlflotasApplication.class, args);
	}

}
