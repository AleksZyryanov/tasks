package com.telran.factory;

public class FileWriter implements Writer {
    @Override
    public void write(Object object) {
        System.out.println("Writing to file");
    }
}
