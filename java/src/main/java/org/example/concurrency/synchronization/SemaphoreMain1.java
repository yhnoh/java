package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SemaphoreMain1 {

    public static void main(String[] args) throws InterruptedException {

        Buffer buffer = new Buffer();
        for (int i = 0; i < 100; i++) {
            buffer.add(i);
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new Consumer(buffer)));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

    }

    public static class Consumer implements Runnable {

        private final Buffer buffer;

        public Consumer(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 소비자 작업 시작: " + buffer.poll());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
}
