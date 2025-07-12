package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;


/**
 * <p>Condition 인터페이스를 활용한 생산자-소비자 문제 해결</p>
 * <p>
 * 생산자는 작업 완료 이후 대기중인 소비자 스레드를 깨운다. <br/>
 * 소비자는 작업 완료 이후 대기중인 생산자 스레드를 깨운다.
 */

public class ConditionMain2 {

    private static final Logger log = LoggerFactory.getLogger(ConditionMain2.class);

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

        private final Lock lock = new ReentrantLock();
        private final Condition queueEmptyCondition = lock.newCondition();
        private final Condition queueFullCondition = lock.newCondition();

        public Buffer(int capacity) {
            this.queue = new LinkedList<>();
            this.capacity = capacity;
        }

        public void produce(T item) {

            try {
                lock.lock();

                log.info("[생산자] 작업 시작 item = {}", item);

                // 버퍼가 가득 찾을때 대기
                while (queue.size() == capacity) {
                    try {
                        log.info("[생산자] 버퍼가 가득참으로 인하여 작업 대기 item = {}", item);
                        queueFullCondition.await(); // 버퍼가 가득 찼을 때 대기, 생산자 대기 집합에서 대기
                    } catch (InterruptedException e) {
                    }
                }

                queue.add(item);
                queueEmptyCondition.signalAll(); // 생산자 작업 완료 이후 소비자 대기 집합 깨움
                log.info("[생산자] 작업 완료 item = {}", item);


            } finally {
                lock.unlock();
            }
        }

        public T consume() {
            try {
                lock.lock();

                log.info("[소비자] 작업 시작");

                // 버퍼가 비어있을 경우 대기
                while (queue.isEmpty()) {
                    try {
                        log.info("[소비자] 버퍼가 비어있음으로 인하여 작업 대기");
                        queueEmptyCondition.await(); // 버퍼가 비어있을 때 대기, 소비자 대기 집합에서 대기
                    } catch (InterruptedException e) {
                    }
                }

                T item = queue.poll();
                queueFullCondition.signalAll(); // 소비자 작업 완료 이후 생산자 대기 집합 깨움
                log.info("[소비자] 작업 완료 item = {}", item);
                return item;
            } finally {
                lock.unlock();
            }

        }

        public int size() {
            return queue.size();
        }
    }
}
