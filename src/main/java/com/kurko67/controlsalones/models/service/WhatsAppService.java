package com.kurko67.controlsalones.models.service;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {

    private final RestTemplate restTemplate;

    public WhatsAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarMensajeWhatsApp(String numeroDestinatario, String mensaje) {
        String url = "https://graph.facebook.com/v18.0/293190173874038/messages";
        String accessToken = "EAAPyPEuWaZAgBO4KFy4bO0jQ1eR12QxY4aSMcJRwldFvZCsIOcTHRil9YPW1ZBOcgHV2Uq4vd5nTNqRhoqfMZAPyRN8EPz75GZAvA1vlGViWKhR9tiCTMqBkYUHfYYDw8BcuNLsH2ByTGdKMVbQDYdiYR1ivQ1C3lDZAP98Dm8ZCgsZAgnehXdTP6brTPvbi3ZAHg9Dy9fZCE3fUnyGiyK"; // Asegúrate de manejar de forma segura tus credenciales

        String requestBody = String.format("{ \"messaging_product\": \"whatsapp\", \"to\": \"%s\", \"type\": \"template\", \"template\": { \"name\": \"%s\", \"language\": { \"code\": \"en_US\" } } }", numeroDestinatario, mensaje);

        // Configura las cabeceras de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        // Configura la solicitud
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Realiza la solicitud POST
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Maneja la respuesta si es necesario
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            // Éxito
            System.out.println("Mensaje de WhatsApp enviado con éxito.");
        } else {
            // Error
            System.err.println("Error al enviar mensaje de WhatsApp. Código de estado: " + statusCode);
        }
    }
}