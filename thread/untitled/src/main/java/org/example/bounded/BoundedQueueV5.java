package org.example.bounded;

import jdk.jshell.spi.SPIResolutionException;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.synchronization.demo.MyLogger.log;

public class BoundedQueueV5 implements BoundedQueue {

    private final Lock lock = new ReentrantLock();
    private final Condition producerCondition = lock.newCondition();
    private final Condition consumerCondition = lock.newCondition();

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV5(int max) {
        this.max = max;
    }

    @Override
    public void put(String data) {

        lock.lock();
        try {
            while(queue.size() == max) {
                log("[put] 큐가 가득 참, 생산자 대기");
                try {
                    producerCondition.await();
                    log("[put] 생산자 깨어남 : producerCondition.await");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            queue.offer(data);
            log("[put] 생산자 데이타 저장, consumerCondition.signal 호출");
            consumerCondition.signal();

        }finally {
            lock.unlock();
        }
    }

    @Override
    public String take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                log("[take] 큐에 데이터 없음, consumerCondition.await");
                try {
                    consumerCondition.await();
                    log("[take] 소비자 깨어님");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            String data = queue.poll();
            log("[take] 소비자 데이터 획득, producerCondition.signal 호출");
            producerCondition.signal();
            return data;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
