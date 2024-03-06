package com.example.gestionconference.Controllers.Sponsoring;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class WhatsappApi {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACdb6efcf7354cd10040111e1ad97c01bc";
    public static final String AUTH_TOKEN = "0dbd884ed35f4bfb702900842dafcf54";

    public void whatsapp() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String msg = "Agreement is done";

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+21693819654"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                msg).create();

        System.out.println(message.getSid());
    }
}