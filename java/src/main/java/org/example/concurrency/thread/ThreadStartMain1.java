package org.example.concurrency.thread;

public class ThreadStartMain1 {

    public static void main(String[] args) {

        printMessage();
        new Thread(new HelloRunnable()).start();
        new HelloThread().start();
    }

    public static void printMessage() {
        System.out.println("[" + Thread.currentThread().getName() + "] Hello World!");
    }

    // Runnable 인터페이스를 구현한 클래스
    public static class HelloRunnable implements Runnable {
        @Override
        public void run() {
            printMessage();
        }
    }

    // Thread 클래스를 상속받은 클래스
    public static class HelloThread extends Thread {
        @Override
        public void run() {
            printMessage();
        }
    }

}
