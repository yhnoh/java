package org.example.reflection;

import org.example.reflection.another.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class SetAccessibleTest {

    @Test
    @DisplayName("setAccessible(false) 테스트")
    void setAccessibleWhenFalse() throws NoSuchFieldException, IllegalAccessException {
        
        Member member = new Member();
        Class<?> clazz = member.getClass();

        Field name = null;
        Field age = null;
        try {
            name = clazz.getDeclaredField("name");
            age = clazz.getDeclaredField("age");
        } catch (NoSuchFieldException e) {
            throw e;
        }

        try {
            System.out.println("name = " + name.get(member));
            System.out.println("age = " + age.get(member));
        } catch (IllegalAccessException e) {
            throw e;
        }
    }

    @Test
    @DisplayName("setAccessible(true) 테스트")
    void setAccessibleWhenTrue() throws NoSuchFieldException, IllegalAccessException {

        Member member = new Member();
        Class<?> clazz = member.getClass();

        Field name = null;
        Field age = null;
        try {
            name = clazz.getDeclaredField("name");
            age = clazz.getDeclaredField("age");
        } catch (NoSuchFieldException e) {
            throw e;
        }

        try {
            name.setAccessible(true);
            age.setAccessible(true);

            System.out.println("name = " + name.get(member));
            System.out.println("age = " + age.get(member));
        } catch (IllegalAccessException e) {
            throw e;
        }
    }
}
