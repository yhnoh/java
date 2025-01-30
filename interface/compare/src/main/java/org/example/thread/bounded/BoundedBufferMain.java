package org.example.thread.bounded;

import static java.lang.Thread.sleep;


/**
 * 소비자 생산자 문제, 한정된 버퍼 문제
 */
public class BoundedBufferMain {

    public static void main(String[] args) throws InterruptedException {

        int bufferSize = 3;
        BoundedBuffer boundedBuffer = new BoundedBuffer1(bufferSize);
        
        startProduce(boundedBuffer, 5);
        startConsume(boundedBuffer, 5);

    }



    private static void startProduce(BoundedBuffer boundedBuffer, int threadSize) throws InterruptedException {

        for (int i = 1; i <= threadSize; i++) {
            Thread thread = new Thread(new BoundedBufferProduceTask(boundedBuffer, "data" + i));
            thread.start();
        }
    }

    private static void startConsume(BoundedBuffer boundedBuffer, int threadSize) throws InterruptedException {

        for (int i = 1; i <= threadSize; i++) {
            Thread thread = new Thread(new BoundedBufferConsumeTask(boundedBuffer));
            thread.start();
        }
    }

}
