package com.kurko67.controlflotas.util.paginator;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@RestController
public class WhatsAppService {


    private RestTemplate restTemplate = null;

    public WhatsAppService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("/enviar-mensaje")
    public ResponseEntity<String> enviarMensaje(@RequestBody String body) {
        String url = "https://graph.facebook.com/v18.0/223471454193555/messages";
        String accessToken = "EAAGJ3VuF6pYBO3BZAlnnPGuk0Wc009IVYl9hnGELO1tGbL0ykYLvsvXHZCTF69T1YwSJ19Jiy3SWcsXQ8GKgFFUdwB2tm6olZBnRFitZAlcU4kZCjeS2Mgejz3PSDIonz4lIydkY18Bl8hajZC4ZAu7p1W1L5ZCZCBx2TQA8boijDP8ahf6KsMBDnqflqHcYRDrZCflA4GH39QbvxUSQNZAUmwZD";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Mensaje enviado con éxito");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Hubo un error al enviar el mensaje. Código de estado: " + response.getStatusCodeValue());
        }

    }

}
