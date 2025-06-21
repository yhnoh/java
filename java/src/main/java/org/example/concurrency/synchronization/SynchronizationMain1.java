package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * <p>멀티 스레드 환경에서 동시성 이슈가 발생하는 예제</p>
 * 스레드 생성 이후 각 스레드는 값을 1씩 증가 시키는 작업을 수행 <br/>
 * 최종적으로 스레드가 생성된 수만큼 값이 증가되기를 기대하지만 동시성 이슈로 인하여 원하는 값이 반환되지 않음
 */
public class SynchronizationMain1 {

    public static void main(String[] args) throws InterruptedException {

        Increment increment = new Increment();

        List<Thread> threads = new ArrayList<>();
        // 100개의 스레드 생성 및
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    sleep(10); // 10ms 대기
                    increment.increase();
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


        System.out.println("value = " + increment.getValue());
    }

    public static class Increment {
        private int value;

        public void increase() {
            value++;
        }

        public int getValue() {
            return value;
        }
    }


}
