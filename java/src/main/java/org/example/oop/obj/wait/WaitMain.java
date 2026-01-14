package org.example.oop.obj.wait;

import static java.lang.Thread.sleep;

public class WaitMain {


    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();

        Thread thread1 = new Thread(() -> {
            try {
                sleep(1000);

                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " 락 획득 이후, wait() 호출");
                    // Thread-1 Waiting 상태로 전환
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + " 작업 완료");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, "Thread-1");

        Thread thread2 = new Thread(() -> {

            try {
                // Thread-2 10초 대기: Thread-1이 wait() 상태로 들어간 후에 notify() 호출하도록 보장
                sleep(10000);

                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " 락 획득 이후, notify() 호출을 통한 Thread-1 깨우기");
                    // Thread-1 Runnable 상태로 전환 시도
                    lock.notifyAll();
                    System.out.println(Thread.currentThread().getName() + " 작업 완료");
                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Thread-2");


        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }


}
