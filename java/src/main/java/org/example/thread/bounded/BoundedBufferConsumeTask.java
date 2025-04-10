package org.example.thread.bounded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

import static java.lang.Thread.sleep;

public class BoundedBufferConsumeTask implements Runnable{

    private final Logger log = LoggerFactory.getLogger(BoundedBufferConsumeTask.class);
    private final BoundedBuffer boundedBuffer;

    public BoundedBufferConsumeTask(BoundedBuffer boundedBuffer) {
        this.boundedBuffer = boundedBuffer;
    }

    @Override
    public void run() {
        String data = boundedBuffer.consume();
        log.info("[소비자] 데이터 소비 완료: {}", data);
    }
}
