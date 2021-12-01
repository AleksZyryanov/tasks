package com.telran.factory;

public class TestFactoryMethod {
    public static void main(String[] args) {
        WriterFactory factory = new WriterFactory();
        Writer writer = factory.createWriter(FileWriter.class);
        writer.write("Hello");
    }
}
