package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * <p>synchronized 키원드 사용 예제</p>
 */
public class SynchronizationMain2 {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        List<Thread> threads = new ArrayList<>();
        // 100개의 스레드 생성 및
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    sleep(10); // 10ms 대기
                    counter.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }


        System.out.println("value = " + counter.getValue());
    }

    public static class Counter {
        private int value;
        private final Object lock = new Object();


        // synchronized 메서드로 동기화된 increment 메서드
        public synchronized void increment() {
            value++;
        }


        // synchronized 블록을 사용하여 동기화된 increment 메서드
/*
        public void increment() {
            synchronized (this) {
                value++;
            }
        }
*/

        // synchronized 블록을 사용하여 동기화된 increment 메서드
/*
        public void increment() {
            synchronized (lock) {
                value++;
            }
        }
*/

        public int getValue() {
            return value;
        }
    }


}
