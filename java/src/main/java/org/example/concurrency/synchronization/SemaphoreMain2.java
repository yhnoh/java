package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * <p>세마포어를 사용한 예제</p>
 * <p>설명</p>
 * <ul>
 *     <li>버퍼만큼의 스레드를 생성하여 소비</li>
 *     <li>각 스레드는 버퍼에서 값을 하나씩 꺼내어 소비</li>
 * </ul>
 * <p>가정</p>
 * <ul>
 *     <li>Consumer는 DB에 접근하여 I/O 작업을 수행한다고 가정</li>
 *     <li>DB가 최대로 요청 받을 수 있는 클라이언트 수는 5</li>
 * </ul>
 * 세마 포어를 사용하여 DB 서버 과부화 발생 또는 연결 제한 초과로 인한 오류를 방지
 */
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

        public synchronized int availablePermits() {
            return permits;
        }
    }

}
