package org.example.concurrency.synchronization;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>세마포어를 사용하지 않은 예제</p>
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
 * DB 서버 과부화 발생 또는 연결 제한 초과로 인한 오류 발생
 */
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

        System.out.println("buffer.size() = " + buffer.size());
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
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 소비자 DB I/O 작업 수헹: " + buffer.poll());
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

        public synchronized int size() {
            return queue.size();
        }
    }
}
