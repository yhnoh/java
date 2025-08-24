package org.example.reflection;

import java.lang.reflect.Field;

public class FieldReflectionMain1 {

    public static void main(String[] args) {
        Member member = new Member();
        System.out.println("member = " + member);
        Class<? extends Member> clazz = member.getClass();

        try {
            Field name = clazz.getDeclaredField("name");
            Field age = clazz.getDeclaredField("age");

            name.set(member, "steve");
            age.set(member, 18);
            
            System.out.println("member = " + member);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
