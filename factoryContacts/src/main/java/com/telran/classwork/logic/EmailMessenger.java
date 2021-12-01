package com.telran.classwork.logic;

import com.telran.classwork.core.InjectByType;
import com.telran.classwork.core.ObjectFactory;

public class EmailMessenger implements Messenger {
    @InjectByType
    private Broker broker;
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending to email " + message);
        System.out.println("====== Broker ======");
        broker.send(message);
        System.out.println("====== Broker ======");
    }
}
