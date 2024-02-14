package org.example.utilizing;

import java.text.MessageFormat;

public class UncaughtExceptionHandlerTest {

    public static void main(String[] args) {
        UncaughtExceptionHandlerTest uncaughtExceptionHandlerTest = new UncaughtExceptionHandlerTest();
        uncaughtExceptionHandlerTest.invokeUncaughtExceptionHandler();
    }

    public void invokeDefaultUncaughtExceptionHandler(){
        //스레드 예외 발생서 전역 처리 한다.
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            String message = MessageFormat.format("thread = {0}, e = {1}", t, e);
            System.out.println(message);
        });

        Thread thread1 = new Thread(() -> {
            throw new RuntimeException("RuntimeException 예외 발생");
        });
        Thread thread2 = new Thread(() -> {
            throw new IllegalArgumentException("IllegalArgumentException 예외 발생");
        });
        thread1.start();
        thread2.start();
    }

    public void invokeUncaughtExceptionHandler(){

        Thread thread1 = new Thread(() -> {
            throw new RuntimeException("RuntimeException 예외 발생");
        });
        Thread thread2 = new Thread(() -> {
            throw new IllegalArgumentException("IllegalArgumentException 예외 발생");
        });

        thread1.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("thread1 handle exception");
            String message = MessageFormat.format("thread1 = {0}, e = {1}", t, e);
            System.out.println(message);
        });

        thread2.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("thread2 handle exception");
            String message = MessageFormat.format("thread2 = {0}, e = {1}", t, e);
            System.out.println(message);
        });

        thread1.start();
        thread2.start();

    }

}
