package com.telran.classwork.core;

import org.reflections.Reflections;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> intf);
    Reflections getScanner();
}
