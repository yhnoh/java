package org.example.jvm.classloader.dynamic;

public class UserApplication {

    public static void main(String[] args) {
        System.out.println("UserApplication 실행");

        if (args.length != 0) {
            User user = new User();
        }
        System.out.println("UserApplication 종료");
    }
}
