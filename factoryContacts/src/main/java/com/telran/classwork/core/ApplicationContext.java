package com.telran.classwork.core;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private ObjectFactory factory;
    private Map<Class,Object> cache = new HashMap<>();
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    public Config getConfig() {
        return config;
    }

    public <T> T getObject(Class<T> type){
        if(cache.containsKey(type)){
            return (T) cache.get(type);
        }

        Class<? extends T> implClass = type;
        if(type.isInterface()){
            implClass = config.getImplClass(type);
        }

        T t = factory.createObject(implClass);

        if(implClass.isAnnotationPresent(Singleton.class)){
            cache.put(type,t);
        }

        return t;
    }
}
