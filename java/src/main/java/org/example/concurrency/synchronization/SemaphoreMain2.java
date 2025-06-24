package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SemaphoreMain2 {


    public static void main(String[] args) throws InterruptedException {
        CustomSemaphore semaphore = new CustomSemaphore(5);
        Buffer buffer = new Buffer();
        for (int i = 0; i < 100; i++) {
            buffer.add(i);
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new Consumer(semaphore, buffer)));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

    }

    public static class Consumer implements Runnable {

        private final CustomSemaphore semaphore;
        private final Buffer buffer;

        public Consumer(CustomSemaphore semaphore, Buffer buffer) {
            this.semaphore = semaphore;
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                Thread.sleep(1000);
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 소비자 작업 시작: " + buffer.poll() + ", acquire permit = " + semaphore.getPermits());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 소비자 작업 완료, release permit = " + semaphore.getPermits());
            }
        }
    }


    public static class Buffer {
        private final Queue<Integer> queue = new LinkedList<>();

        public void add(Integer item) {
            queue.add(item);
        }

        public synchronized Integer poll() {
            return queue.poll();
        }
    }

    public static class CustomSemaphore {

        private int permits;
        private final int maxPermits;

        public CustomSemaphore(int permits) {
            this.permits = permits;
            this.maxPermits = permits;
        }

        public synchronized void acquire() throws InterruptedException {
            while (permits <= 0) {
                wait();
            }
            permits--;
        }

        public synchronized void release() {
            // 최대 허용량 초과 확인
            if (permits < maxPermits) {
                permits++;
                notifyAll();
            }
        }

        public synchronized int getPermits() {
            return permits;
        }
    }

}
