package org.example.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.example.bounded.ThreadUtils.sleep;
import static org.example.synchronization.demo.MyLogger.log;

public class BoundedQueueV3 implements BoundedQueue {

    private final Object producerLock = new Object();
    private final Object consumerLock = new Object();
    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public void put(String data) {
        synchronized (producerLock){
            while(queue.size() == max) {
                log("[put] 큐가 가득 참, 생산자 대기");
                sleep(1000);
            }
            queue.offer(data);
        }
    }

    @Override
    public String take() {
        synchronized (consumerLock){
            while (queue.isEmpty()) {
                log("[put] 큐에 데이터 없음, 생산자 대기");
                sleep(1000);
            }

            return queue.poll();
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
