package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Thread.sleep;

/**
 * <p>synchronized 키워드를 활용 생산자-소비자 문제를 해결하지만 불필요한 대기와 깨어남이 발생</p>
 * 버퍼가 가득찾을 경우 대기중이던 생산자 스레드가 락 획득 시도시 다시 대기 상태로 빠진다. <br/>
 * 버퍼가 비어있을 경우 대기중이던 소비자 스레드가 락 획득 시도시 다시 대기 상태로 빠진다.
 */
public class ConditionMain1 {
    private static final Logger log = LoggerFactory.getLogger(ConditionMain1.class);

    public static void main(String[] args) throws InterruptedException {
        Buffer<String> buffer = new Buffer<>(2);

        List<Thread> producerThreads = new ArrayList<>();
        int producerThreadCount = 30;
        for (int i = 0; i < producerThreadCount; i++) {
            String item = "item" + i;
            Producer<String> producer = new Producer<>(buffer, item);
            producerThreads.add(new Thread(producer));
        }

        List<Thread> consumerThreads = new ArrayList<>();
        int consumerThreadCount = 30;
        for (int i = 0; i < consumerThreadCount; i++) {
            Consumer<String> consumer = new Consumer<>(buffer);
            consumerThreads.add(new Thread(consumer));
        }

        for (Thread producerThread : producerThreads) {
            producerThread.start();
        }

        sleep(2000); // 2초간 대기하여 생산자 스레드가 먼저 시작하도록 함
        for (Thread consumerThread : consumerThreads) {
            consumerThread.start();
        }

        for (Thread producerThread : producerThreads) {
            producerThread.join();
        }
        for (Thread consumerThread : consumerThreads) {
            consumerThread.join();
        }

        System.out.println("buffer size = " + buffer.size());
    }

    public static class Producer<T> implements Runnable {
        private final Buffer<T> buffer;
        private final T item;

        public Producer(Buffer<T> buffer, T item) {
            this.buffer = buffer;
            this.item = item;
        }

        @Override
        public void run() {
            buffer.produce(item);
        }
    }

    public static class Consumer<T> implements Runnable {
        private final Buffer<T> buffer;

        public Consumer(Buffer<T> buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            buffer.consume();
        }
    }


    public static class Buffer<T> {
        private final Queue<T> queue;
        private final int capacity;

        public Buffer(int capacity) {
            this.queue = new LinkedList<>();
            this.capacity = capacity;
        }

        public synchronized void produce(T item) {
            log.info("[생산자] 작업 시작 item = {}", item);
            // 버퍼가 가득 찾을때 대기
            while (queue.size() == capacity) {
                try {
                    log.info("[생산자] 버퍼가 가득참으로 인하여 작업 대기 item = {}", item);
                    wait();
                } catch (InterruptedException e) {
                }
            }

            queue.add(item);
            notifyAll(); // 생산 이후 대기중인 스레드 깨움
            log.info("[생산자] 작업 완료 item = {}", item);
        }

        public synchronized T consume() {
            log.info("[소비자] 작업 시작");
            // 버퍼가 비어있을 경우 대기
            while (queue.isEmpty()) {
                try {
                    log.info("[소비자] 버퍼가 비어있음으로 인하여 작업 대기");
                    wait();
                } catch (InterruptedException e) {
                }
            }

            T item = queue.poll();
            notifyAll(); // 소비 이후 대기중인 스레드 깨움
            log.info("[소비자] 작업 완료 item = {}", item);
            return item;
        }

        public int size() {
            return queue.size();
        }
    }
}
