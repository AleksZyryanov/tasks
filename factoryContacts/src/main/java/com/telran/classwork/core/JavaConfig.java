package com.telran.classwork.core;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    private Map<Class,Class> intf2ImplClass;
    private Reflections scanner;

    public JavaConfig(String packageName, Map<Class,Class> intf2ImplCLass) {
        if(intf2ImplCLass == null){
            this.intf2ImplClass = new HashMap<>();
        }else {
            this.intf2ImplClass = intf2ImplCLass;
        }
        scanner = new Reflections(packageName);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> intf) {
        return intf2ImplClass.computeIfAbsent(intf, aClass -> {
            Set<Class<? extends T>> set = scanner.getSubTypesOf(intf);
            if (set.size() > 1) {
                throw new RuntimeException(
                        "More then one impl of type " + intf.getSimpleName() + " please configure your config"
                );
            }
            return set.iterator().next();
        });
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}
