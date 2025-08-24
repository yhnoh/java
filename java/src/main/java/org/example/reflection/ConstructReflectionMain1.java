package org.example.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructReflectionMain1 {

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("org.example.reflection.ConstructReflectionMain1$Member");
            Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
            Member member = (Member) constructor.newInstance("steve", 18);
            System.out.println("member = " + member);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
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
