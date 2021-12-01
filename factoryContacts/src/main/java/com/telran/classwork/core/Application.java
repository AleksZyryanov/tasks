package com.telran.classwork.core;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class,Class> intrf2ImplClass){
        JavaConfig config = new JavaConfig(packageToScan,intrf2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }
}
