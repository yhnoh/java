package org.example.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

class MethodReflectionMain1Test {

    @Test
    @DisplayName("Reflection을 이용한 Method 정보 조회")
    void getMethod() throws NoSuchMethodException {

        Member member = new Member();
        Class<? extends Member> clazz = member.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            try {
                clazz.getMethod(method.getName(), method.getParameterTypes());
                printMethod(method);
            } catch (NoSuchMethodException e) {
                throw e;
            }
        }
    }

    @Test
    @DisplayName("Reflection을 이용한 Method 호출")
    void setMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Member member = new Member();
        System.out.println("member = " + member);
        Class<? extends Member> clazz = member.getClass();

        Method setName = null;
        Method setAge = null;
        try {
            setName = clazz.getMethod("setName", String.class);
            setAge = clazz.getMethod("setAge", int.class);

        } catch (NoSuchMethodException e) {
            throw e;
        }

        try {
            setName.invoke(member, "steve");
            setAge.invoke(member, 18);

            System.out.println("member = " + member);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw e;
        }
    }

    void printMethod(Method method) {
        System.out.println("method.getName() = " + method.getName());
        System.out.println("field.getModifiers() = " + Modifier.toString(method.getModifiers()));
        System.out.println("method.getReturnType() = " + method.getReturnType());
        System.out.println("method.getParameterTypes() = " + Arrays.toString(method.getParameterTypes()));
        System.out.println("method.getGenericParameterTypes() = " + Arrays.toString(method.getGenericParameterTypes()));
        System.out.println("method.getDeclaredAnnotations() = " + Arrays.toString(method.getDeclaredAnnotations()));
        System.out.println("method.getDeclaringClass() = " + method.getDeclaringClass());
        System.out.println();
    }

    static class Member {
        private String name;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
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