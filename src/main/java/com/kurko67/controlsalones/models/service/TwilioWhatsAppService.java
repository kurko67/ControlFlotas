package com.kurko67.controlsalones.models.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;


@Service
public class TwilioWhatsAppService {

    public static final String ACCOUNT_SID = "AC3f65d6c8f951f7c052fe1f0677f0da83";
    public static final String AUTH_TOKEN = "27baac81aa85808ba98b666accf0dc61";

    public static void sendMessage(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(
                        new PhoneNumber("whatsapp:+5492613438409"),
                        new PhoneNumber("whatsapp:+14155238886"),
                        "Your appointment is coming up on July 21 at 3PM"
                )
                .create();

        System.out.println(message.getSid());
    }

}
