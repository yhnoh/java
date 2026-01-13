package org.example.jvm.classloader;

import java.util.Set;

public class ClassLoaderMain {


    public static void main(String[] args) {
        Set<String> packages = ClassLoader.getPlatformClassLoader().getUnnamedModule().getPackages();
        for (String pack : packages) {
            System.out.println(pack);
        }
//        ClassLoader.getPlatformClassLoader();
//        ClassLoader.getSystemClassLoader();
    }
}
