package com.kurko67.controlflotas.service;

import com.kurko67.controlflotas.models.whatsapp.RequestMessage;
import com.kurko67.controlflotas.models.whatsapp.RequestMessageText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiWhatsappService {

    private final String baseUrl;
    private final String token;
    private final RestTemplate restTemplate;

    public ApiWhatsappService(
            @Value("${whatsapp.identificador}") String identificador,
            @Value("${whatsapp.token}") String token
    ) {
        this.baseUrl = "https://graph.facebook.com/v18.0/" + identificador + "/messages";
        this.token = token;
        this.restTemplate = new RestTemplate();
    }

    public void sendMessage(){
        RequestMessage request = new RequestMessage("whatsapp", "5492613438409", "hola");
    }

}
