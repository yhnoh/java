package org.example.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.example.bounded.ThreadUtils.sleep;
import static org.example.synchronization.demo.MyLogger.log;

public class BoundedQueueV2 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV2(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while(queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            sleep(1000);
        }
        queue.offer(data);
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[put] 큐에 데이터 없음, 생산자 대기");
            sleep(1000);
        }

        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
