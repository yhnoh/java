package org.example.thread.bounded;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

public class BoundedBufferProduceTask implements Runnable{

    private final Logger log = LoggerFactory.getLogger(BoundedBufferProduceTask.class);
    private final BoundedBuffer boundedBuffer;
    private final String data;

    public BoundedBufferProduceTask(BoundedBuffer boundedBuffer, String data) {
        this.boundedBuffer = boundedBuffer;
        this.data = data;
    }

    @Override
    public void run() {
        boundedBuffer.produce(data);
        log.info("[생산자] 데이터 생성 완료: {}", data);
    }
}
