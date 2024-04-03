package com.kurko67.controlflotas.models.whatsapp;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class WhatsAppService {

    public void enviarMensaje() {
        // Configura las credenciales de autenticación
        String accessToken = "EAAGFRfXKgvYBO2KXkkTRkknIeCXYLelCQ3GoDShATZAZCl3RGlJ3KcRaTO1bAVmVmVSbBOAuriUIDA1Rngx6Kyqc9tmIbWtJXswZBqo5Bz92SzIoE4BkdR2m1oIE8YaUs3Lq7pVTyyMNFy8XZCwMEb3NtcHyH4mxp46RoqutxDgBY03DpuwpJDuUOYaKIDu4pfSrcQMMSwuPZBgMi";

        // URL de la API de WhatsApp
        String url = "https://graph.facebook.com/v18.0/260280147172037/messages";

        // Crea un objeto RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Crea el encabezado de la solicitud con el token de acceso
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        // Crea el cuerpo de la solicitud con los datos del mensaje
        String cuerpoSolicitud = "{ \"messaging_product\": \"whatsapp\", \"to\": \"5492613438409\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }";

        // Crea la entidad de la solicitud con el encabezado y el cuerpo
        HttpEntity<String> requestEntity = new HttpEntity<>(cuerpoSolicitud, headers);

        // Realiza la solicitud POST a la API de WhatsApp
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Verifica el código de estado de la respuesta
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Mensaje enviado exitosamente.");
        } else {
            System.err.println("Error al enviar el mensaje. Código de estado: " + responseEntity.getStatusCodeValue());
        }
    }
}
