package org.example.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

class FieldReflectionMain1Test {

    @Test
    @DisplayName("Reflection을 이용한 Field 정보 조회")
    public void getField() throws NoSuchFieldException, IllegalAccessException {

        Member member = new Member();
        Class<? extends Member> clazz = member.getClass();

        Field[] fields = clazz.getDeclaredFields();
        System.out.println("fields = " + Arrays.toString(fields));
        for (Field field : fields) {
            String name = field.getName();
            try {
                printField(member, clazz.getField(name));
            } catch (NoSuchFieldException e) {
                throw e;
            }
        }

        try {
            Field name = clazz.getDeclaredField("age");
        } catch (NoSuchFieldException e) {
            throw e;
        }
    }

    private void printField(Member member, Field field) throws IllegalAccessException {
        System.out.println("field.getName() = " + field.getName());
        System.out.println("field.getModifiers() = " + Modifier.toString(field.getModifiers()));
        System.out.println("field.getType() = " + field.getType());
        System.out.println("field.getGenericType() = " + field.getGenericType());
        System.out.println("field.getDeclaredAnnotations() = " + Arrays.toString(field.getDeclaredAnnotations()));
        System.out.println("field.getDeclaringClass() = " + field.getDeclaringClass());
        try {
            System.out.println("field.get(obj) = " + field.get(member));
        } catch (IllegalAccessException e) {
            throw e;
        }
        System.out.println();
    }

    @Test
    @DisplayName("Reflection을 이용한 Field 정보 조작")
    public void setField() throws NoSuchFieldException, IllegalAccessException {

        Member member = new Member();
        System.out.println("member = " + member);
        Class<? extends Member> clazz = member.getClass();

        Field name = null;
        Field age = null;

        try {
            name = clazz.getDeclaredField("name");
            age = clazz.getDeclaredField("age");
        } catch (NoSuchFieldException e) {
            throw e;
        }

        try {
            name.set(member, "steve");
            age.set(member, 18);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println("member = " + member);

    }


    static class Member {
        private String name;
        private int age;

        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


}