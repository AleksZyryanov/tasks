package com.telran.factory.absfactory;

public class TestAbstractFactory {
    public static void main(String[] args) {
        CarFactory factory = new ToyotaFactory();
        Sedan sedan = factory.createSedan();
    }
}
