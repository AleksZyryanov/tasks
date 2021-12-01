package com.telran.classwork.logic;

public class BrokerImpl implements Broker {
    @Override
    public void send(String message) {
        System.out.println("Sending with broker: " + message);
    }
}
