package org.example.oop.obj.equals;

import java.util.Objects;

public class EqualsMain {


    public static void main(String[] args) {

        int a = 10;
        int b = 10;
        System.out.println("a == b 결과: " + (a == b));

        User user1 = new User("Alice", 30);
        User user2 = new User("Alice", 30);

        System.out.println("user1 == user2 결과: " + (user1 == user2));
    }


    static class User {
        private final String name;
        private final int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

//        @Override
//        public boolean equals(Object o) {
//            if (o == null || getClass() != o.getClass()) return false;
//            User user = (User) o;
//            return age == user.age && Objects.equals(name, user.name);
//        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }
}
