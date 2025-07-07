package org.example.concurrency.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>java.util.concurrent.locks 패키지를 활요한 동기화</p>
 */
public class LockMain1 {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        List<Thread> incrementThreads = new ArrayList<>();
        List<Thread> decrementThreads = new ArrayList<>();
        int threadCount = 100;
        for (int i = 0; i < threadCount; i++) {
            Thread increamentThread = new Thread(() -> {
                try {
                    Thread.sleep(10); // 10ms 대기
                    counter.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            incrementThreads.add(increamentThread);

            Thread decrementThread = new Thread(() -> {
                try {
                    Thread.sleep(10);
                    counter.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            decrementThreads.add(decrementThread);
        }

        for (int i = 0; i < 100; i++) {
            incrementThreads.get(i).start();
            decrementThreads.get(i).start();
        }


        for (int i = 0; i < 100; i++) {
            incrementThreads.get(i).join();
            decrementThreads.get(i).join();
        }

        System.out.println("value = " + counter.getValue());
    }


    public static class Counter {
        private int value;
        private final Lock lock = new ReentrantLock();

        public void increment() throws InterruptedException {
            lock.lock();
            try {
                value++;
            } finally {
                lock.unlock();
            }
        }

        public void decrement() {

            lock.lock();
            try {
                value--;
            } finally {
                lock.unlock();
            }
        }

        public int getValue() {
            return value;
        }
    }
}
