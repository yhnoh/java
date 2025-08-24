package org.example.reflection;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassReflectionMain2 {


    public static void main(String[] args) {
        Class<?> classObject = null;
        try {
            classObject = Class.forName("java.util.HashMap");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Class

        printClassName(classObject);
        printModifier(classObject);
        printTypeParameters(classObject);
        printInterfaces(classObject);
        printSuperClasses(classObject);
        printAnnotations(classObject);
        printConstructors(classObject);
        printFields(classObject);
        printMethods(classObject);
    }

    private static void printClassName(Class<?> classObject) {
        System.out.println("Class Name: ");
        System.out.println("classObject.toString() = " + classObject);
        System.out.println("classObject.toGenericString() = " + classObject.toGenericString());
        System.out.println("classObject.getName() = " + classObject.getName());
        System.out.println("classObject.getPackage() = " + classObject.getPackage());
        System.out.println("classObject.getSimpleName() = " + classObject.getSimpleName());
        System.out.println();
    }

    private static void printModifier(Class<?> classObject) {
        System.out.println("Modifier: ");
        System.out.println("classObject.getModifiers() = " + Modifier.toString(classObject.getModifiers()));
        System.out.println();
    }

    private static void printTypeParameters(Class<?> classObject) {
        System.out.println("Generic Declaration: ");
        System.out.println("classObject.getTypeParameters() = " + Arrays.toString(classObject.getTypeParameters()));
        System.out.println();
    }

    private static void printInterfaces(Class<?> classObject) {
        System.out.println("Interfaces: ");
        System.out.println("classObject.getInterfaces() = " + Arrays.toString(classObject.getInterfaces()));
        System.out.println();
    }

    private static void printSuperClasses(Class<?> classObject) {
        System.out.println("Superclasses: ");

        List<Class<?>> superclasses = new ArrayList<>();
        Class<?> superclass = classObject.getSuperclass();
        if (superclass != null) {
            superclasses.add(superclass);
        }

        while (superclass != null) {
            superclass = superclass.getSuperclass();
            if (superclass != null) {
                superclasses.add(superclass);
            }
        }

        System.out.println("classObject.getSuperclass() = " + superclasses);
        System.out.println();
    }

    private static void printAnnotations(Class<?> classObject) {
        System.out.println("Annotations: ");
        System.out.println("classObject.getAnnotations() = " + Arrays.toString(classObject.getAnnotations()));

        System.out.println();
    }

    private static void printConstructors(Class<?> classObject) {
        System.out.println("Constructors: ");
        System.out.println("classObject.getDeclaredConstructors() = " + Arrays.toString(classObject.getDeclaredConstructors()));
        System.out.println("classObject.getConstructors() = " + Arrays.toString(classObject.getConstructors()));
        System.out.println();
    }

    private static void printFields(Class<?> classObject) {
        System.out.println("Fields: ");
        System.out.println("classObject.getDeclaredFields() = " + Arrays.toString(classObject.getDeclaredFields()));
        System.out.println("classObject.getFields() = " + Arrays.toString(classObject.getFields()));
        System.out.println();
    }

    private static void printMethods(Class<?> classObject) {
        System.out.println("Methods: ");
        System.out.println("classObject.getDeclaredMethods() = " + Arrays.toString(classObject.getDeclaredMethods()));
        System.out.println("classObject.getMethods() = " + Arrays.toString(classObject.getMethods()));
        System.out.println();
    }

}
