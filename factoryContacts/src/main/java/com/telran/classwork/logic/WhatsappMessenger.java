package com.telran.classwork.logic;

import com.telran.classwork.core.InjectValue;
import com.telran.classwork.core.Singleton;

@Singleton
public class WhatsappMessenger implements Messenger {

    @InjectValue("messenger.server")
    private String serverName;

    public WhatsappMessenger() {
        System.out.println("******************");
        System.out.println("WhatsappMessenger constructor");
        System.out.println("******************");
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending message by WhatsApp: " + message + " server: " + serverName);
    }
}
