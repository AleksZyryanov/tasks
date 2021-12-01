package com.telran.factory.absfactory;

public class HondaFactory implements CarFactory {
    @Override
    public Sedan createSedan() {
        return new HondaSedan();
    }

    @Override
    public Coupe createCoupe() {
        return new HondaCoupe();
    }
}
