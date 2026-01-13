package org.example.oop.obj.tostring;

public class ToStringMain {

    public static void main(String[] args) {
        User user = new User("user1", 1);
        System.out.println("name = " + user.name() + ", age = " + user.age());
        System.out.println(user);
    }
}
