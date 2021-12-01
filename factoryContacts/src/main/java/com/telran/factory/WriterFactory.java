package com.telran.factory;

import java.util.Objects;

public class WriterFactory {
    public Writer createWriter(Class type){
        Objects.requireNonNull(type);
        if(type == JsonWriter.class){
            return new JsonWriter();
        }else if(type == FileWriter.class){
            return new FileWriter();
        }
        return new FileWriter();
    }
}
