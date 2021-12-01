package com.telran.factory;

public class JsonWriter implements Writer {
    @Override
    public void write(Object object) {
        System.out.println("Writing to json...");
    }
}
