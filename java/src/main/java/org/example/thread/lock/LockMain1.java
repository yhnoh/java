package org.example.thread.lock;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

/**
 * LockSupport: 스레드를 대기상태로 변경하거나 대기중인 스레드를 실행상태로 변경하는 기능을 제공하는 클래스
 */

public class LockMain1 {


    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            LockSupport.park(); //LockSupport.park() 메서드를 이용하여 해당 스레드를 대기상태로 변경
            System.out.println("Thread State3 = " + Thread.currentThread().getState());
        });

        thread1.start();
        System.out.println("Thread State1 = " + thread1.getState());
        sleep(1000);
        System.out.println("Thread State2 = " + thread1.getState());
        LockSupport.unpark(thread1); //LockSupport.unpark() 메서드를 이용하여 대기중인 스레드를 Runnalbe 상태로 변경
    }
}
