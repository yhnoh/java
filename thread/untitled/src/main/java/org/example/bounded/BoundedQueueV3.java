package org.example.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.example.bounded.ThreadUtils.sleep;
import static org.example.synchronization.demo.MyLogger.log;

public class BoundedQueueV3 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {

        while(queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                wait(); // 락 반납하고, 대기 상태 RUNNABLE -> WAITING, 락 반납
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(data);
        log("[put] 생산자 데이타 저장, notify() 호출");
        notify(); // WAITING -> RUNNABLE
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터 없음, 생산자 대기");
            try {
                wait();
                log("[take] 소비자 깨어님");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String data = queue.poll();
        log("[take] 소비자 데이터 획득, notify() 호출");
        notify(); // 대기 스레드, WAIT -> BLOCKED
        return data;
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
