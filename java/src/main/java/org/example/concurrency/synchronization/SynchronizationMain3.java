package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * <p>인스턴스 레벨 synchronized 키워드 잘못된 사용 예제</p>
 */
public class SynchronizationMain3 {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        List<Thread> incrementThreads = new ArrayList<>();
        List<Thread> decrementThreads = new ArrayList<>();

        int threadCount = 50;
        for (int i = 0; i < threadCount; i++) {
            incrementThreads.add(new Thread(() -> {
                try {
                    sleep(10); // 10ms 대기
                    counter.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));

            decrementThreads.add(new Thread(() -> {
                try {
                    sleep(10); // 10ms 대기
                    counter.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        for (int i = 0; i < threadCount; i++) {
            incrementThreads.get(i).start();
            decrementThreads.get(i).start();
        }

        for (int i = 0; i < threadCount; i++) {
            incrementThreads.get(i).join();
            decrementThreads.get(i).join();
        }

        System.out.println("value = " + counter.getValue());

    }

    public static class Counter {
        private final Object incrementLock = new Object();
        private final Object decrementLock = new Object();
        private int value;

        // increment 메서드는 incrementLock 인스턴스의 모니터 락을 사용하여 동기화
        public void increment() {
            synchronized (incrementLock) {
                value++;
            }
        }

        // decrement 메서드는 decrementLock 인스턴스의 모니터 락을 사용하여 동기화
        public void decrement() {
            synchronized (decrementLock) {
                value--;
            }
        }

        public int getValue() {
            return value;
        }
    }

}
