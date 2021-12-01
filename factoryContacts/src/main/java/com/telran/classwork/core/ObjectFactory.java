package com.telran.classwork.core;

import com.telran.classwork.logic.EmailMessenger;
import com.telran.classwork.logic.Messenger;
import com.telran.classwork.logic.WhatsappMessenger;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ObjectFactory {
//    private static ObjectFactory instance = new ObjectFactory();
//    private Reflections scanner;
//    private Config config;
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private final ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
//        scanner = new Reflections("com.telran");
//        config = new JavaConfig(
//                "com.telran",
//                new HashMap<>(
//                        Map.of(Messenger.class, WhatsappMessenger.class)
//                )
//        );

        for (Class<? extends ObjectConfigurator> configurator :
                context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(configurator.getDeclaredConstructor().newInstance());
        }
    }

//    public static ObjectFactory getInstance() {
//        return instance;
//    }


    @SneakyThrows
    public <T> T createObject(Class<T> type){
//        Class<? extends T> impl = config.getImplClass(type);
        T t = create(type);

        configure(t);

        return t;
    }

    private <T> void configure(T t){
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t,context));
    }

    @SneakyThrows
    private <T> T create(Class<T> impl) {
        return impl.getDeclaredConstructor().newInstance();
    }
}
