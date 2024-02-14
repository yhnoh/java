package org.example;

import static java.lang.Thread.sleep;

public class JoinTest {

    public static void main(String[] args) {

        JoinTest joinTest = new JoinTest();
        joinTest.interruptJoinThread();
        joinTest.joinThread();
    }

    public void joinThread(){
        Thread thread = new Thread(() -> {
            System.out.println("thread is working");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("thread is done");
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("main thread is done");
    }

    public void interruptJoinThread() {
        Thread longRunningThread = new Thread(() -> {

            try {
                for (int i = 0; i < 10; i++) {
                    //1. longRunningThread 작업 진행
                    System.out.println("longRunningThread is working");
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                //3. longRunningThread InterruptedException 발생
                System.out.println("longRunningThread is interrupted...");
            }
        });

        longRunningThread.start();

        Thread interruptThread = new Thread(() -> {
            try {
                //2. longRunningThread interrupt 호출
                System.out.println("longRunningThread will interrupt after 2 seconds");
                sleep(2000);
                longRunningThread.interrupt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        interruptThread.start();

        try {
            //4. mainThread longRunningThread 작입이 완료될때까지 대기
            longRunningThread.join();
            System.out.println("main thread is done");
        } catch (InterruptedException e) {
            System.out.println("main thread is Interrupted");
        }

    }
}
