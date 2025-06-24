package org.example.concurrency.synchronization;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * <p>Lock을 통한 Mutex 구현</p>
 * 스레드 생성 이후 각 스레드는 값을 1씩 증가 시키는 작업을 수행 <br/>
 */
public class MutexMain2 {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        List<Thread> threads = new ArrayList<>();
        // 100개의 스레드 생성 및
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    sleep(10); // 10ms 대기
                    counter.increase();
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
        private final Lock lock = new ReentrantLock();
        private int value;


        public void increase() {
            lock.lock();
            try {
                value++;
            } finally {
                lock.unlock();
            }
        }

        public int getValue() {
            return value;
        }
    }
}
