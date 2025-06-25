package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * <p>java.util.concurrent.Semaphore를 활용한 세마포어 예제</p>
 */
public class SemaphoreMain3 {


    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(5);
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

        private final Semaphore semaphore;
        private final Buffer buffer;

        public Consumer(Semaphore semaphore, Buffer buffer) {
            this.semaphore = semaphore;
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                Thread.sleep(1000);
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 소비자 DB I/O 작업 수헹: " + buffer.poll() + ", acquire permit = " + semaphore.availablePermits());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 소비자 DB I/O 작업 완료, release permit = " + semaphore.availablePermits());
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
