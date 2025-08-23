package org.example.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class ClassReflectionMain2 {


    public static void main(String[] args) {
        Class<? extends String> classObject = "String".getClass();

        // Class
        System.out.println("Class");
        System.out.println("classObject.toString() = " + classObject);
        System.out.println("classObject.toGenericString() = " + classObject.toGenericString());
        System.out.println("classObject.getName() = " + classObject.getName());
        System.out.println("classObject.getSimpleName() = " + classObject.getSimpleName());
        System.out.println("classObject.getPackage() = " + classObject.getPackage());

        // Modifier
        System.out.println("classObject.getModifiers() = " + Modifier.toString(classObject.getModifiers()));

        // Type Parameters
        TypeVariable<?>[] typeParameters = classObject.getTypeParameters();
        for (TypeVariable<?> typeParameter : typeParameters) {
            System.out.println("typeParameter.getName() = " + typeParameter.getName());
        }

        // Generic Interfaces
        Type[] genericInterfaces = classObject.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            System.out.println("genericInterface.getTypeName() = " + genericInterface.toString());
        }

        Class<?> superclass = classObject.getSuperclass();
        System.out.println("superclass = " + superclass);

        Annotation[] annotations = classObject.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("annotation = " + annotation);
        }

    }


    public static class Member {
        private String name;
        private int age;

        public Member() {
        }

        public Member(String name) {
            this.name = name;
        }

        public Member(int age, String name) {
            this.age = age;
            this.name = name;
        }


        public void setAttributes(String name) {
            this.name = name;
        }

        public void setAttributes(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

}
