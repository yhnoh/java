package org.example.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

class ConstructorReflectionMain1Test {

    @Test
    @DisplayName("Reflection을 이용한 Constructor 정보 조회")
    void getConstructor() {
        Class<? extends Member> clazz = Member.class;
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            try {
                printConstructor(clazz.getDeclaredConstructor(constructor.getParameterTypes()));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void printConstructor(Constructor constructor) {
        System.out.println("constructor.getName() = " + constructor.getName());
        System.out.println("constructor.getModifiers() = " + Modifier.toString(constructor.getModifiers()));
        System.out.println("constructor.getParameterTypes() = " + Arrays.toString(constructor.getParameterTypes()));
        System.out.println("constructor.getGenericParameterTypes() = " + Arrays.toString(constructor.getGenericParameterTypes()));
        System.out.println("constructor.getDeclaredAnnotations() = " + Arrays.toString(constructor.getDeclaredAnnotations()));
        System.out.println("constructor.getDeclaringClass() = " + constructor.getDeclaringClass());
    }


    @Test
    @DisplayName("Reflection을 이용한 객체 생성 및 값 주입")
    void setConstructor() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> clazz = null;
        try {
            clazz = Class.forName("org.example.reflection.ConstructorReflectionMain1Test$Member");
        } catch (ClassNotFoundException e) {
            throw e;
        }

        Constructor<?> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(String.class, int.class);
        } catch (NoSuchMethodException e) {
            throw e;
        }
        ConstructorReflectionMain1Test.Member member = null;
        try {
            member = (ConstructorReflectionMain1Test.Member) constructor.newInstance("steve", 18);
            System.out.println("member = " + member);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw e;
        }

    }

    static class Member {
        private final String name;
        private final int age;


        public Member(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}