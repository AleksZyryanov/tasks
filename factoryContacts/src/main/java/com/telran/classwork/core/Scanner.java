package com.telran.classwork.core;

import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Scanner {
    private List<Class<?>> list;

    public Scanner(String packageName){
        list = getClasses(packageName);
    }

    public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type){
        Set<Class<? extends T>> result = new HashSet<>();
        for(Class<?> clazz : list){
            if(type.isInterface()){
                Class<?>[] interfaces = clazz.getInterfaces();
                for (Class<?> intr : interfaces) {
                    if(intr == type){
                        result.add((Class<? extends T>) clazz);
                        break;
                    }
                }
            }else{
                if(clazz.getSuperclass() == type){
                    result.add((Class<? extends T>) clazz);
                }
            }
        }
        return result;
    }

    @SneakyThrows
    private ArrayList<Class<?>> getClasses(String packageName){
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        String path = packageName.replaceAll("\\.","/");
        Enumeration<URL> resources = loader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while(resources.hasMoreElements()){
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File dir : dirs) {
            classes.addAll(findClasses(dir,packageName));
        }
        return classes;
    }

    @SneakyThrows
    private List<Class<?>> findClasses(File directory, String packageName){
        List<Class<?>> classes = new ArrayList<>();
        if(!directory.exists()){
            return classes;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                classes.addAll(findClasses(file,packageName));
            }else if(file.getName().endsWith(".class")){
                classes.add(
                        Class.forName(
                                packageName + "."
                                + file.getName().substring(0, file.getName().length() - 6)
                        )
                );
            }
        }
        return classes;
    }
}
