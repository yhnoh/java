package org.example.thread.threadpool;

public class SimpleThreadPoolMain {
    public static void main(String[] args) {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool(2);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            simpleThreadPool.submit(() -> System.out.println("Task is running :" + finalI));
        }

        simpleThreadPool.shutdown();
    }
}
