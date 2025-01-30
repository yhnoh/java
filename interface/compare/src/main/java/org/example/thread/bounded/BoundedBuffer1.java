package org.example.thread.bounded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class BoundedBuffer1 implements BoundedBuffer{

    private final Logger log = LoggerFactory.getLogger(BoundedBuffer1.class);

    private final int bufferSize;
    private final Queue<String> queue;
    public BoundedBuffer1(int bufferSize) {
        this.bufferSize = bufferSize;
        this.queue = new ArrayDeque<>(bufferSize);
    }

    @Override
    public synchronized void produce(String data) {
        while (bufferSize == queue.size()){
            log.info("[생산자] 버퍼가 가득차 대기");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        queue.offer(data);
        this.notifyAll();
        log.info("[생산자] notifyAll 호출");
    }

    @Override
    public synchronized String consume() {
        while (queue.isEmpty()){
            log.info("[소비자] 버퍼가 비어있어 대기");
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String poll = queue.poll();
        this.notifyAll();
        log.info("[소비자] notifyAll 호출");
        return poll;
    }
}
