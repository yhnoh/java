package org.example.concurrency.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * <p>java.util.concurrent.locks 패키지를 활용한 동기화</p>
 */
public class LockMain1 {

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
        private final Lock lock = new ReentrantLock();

        public void increment() {
            // 락 획득
            lock.lock();
            try {
                value++;
            } finally {
                // 락 해제
                lock.unlock();
            }
        }


        public int getValue() {
            return value;
        }
    }
}
