package org.example.thread;

import static java.lang.Thread.sleep;

public class CounterMain {

    static class Counter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // 공유 자원
        Counter counter = new Counter();

        // 100개의 스레드 생성
        int threadCount = 100;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {

            threads[i] = new Thread(() -> {
                // 각 스레드는 카운트를 1 증가
                try {
                    sleep(10);
                    counter.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // 모든 스레드 시작
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }

        // 모든 스레드가 종료될 때까지 대기
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        // 최종 카운트 출력
        System.out.println("count = " + counter.getCount());

    }


}
