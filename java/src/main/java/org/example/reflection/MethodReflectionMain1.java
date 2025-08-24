package org.example.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodReflectionMain1 {

    public static void main(String[] args) {

        Member member = new Member();
        System.out.println("member = " + member);
        Class<? extends Member> clazz = member.getClass();

        try {
            Method setName = clazz.getMethod("setName", String.class);
            Method setAge = clazz.getMethod("setAge", int.class);
            
            setName.invoke(member, "steve");
            setAge.invoke(member, 18);

            System.out.println("member = " + member);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

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
