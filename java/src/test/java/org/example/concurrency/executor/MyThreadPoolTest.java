package org.example.concurrency.executor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

class MyThreadPoolTest {


    private static final Logger log = LoggerFactory.getLogger(MyThreadPoolTest.class);

    @Test
    @DisplayName("스레드 풀 테스트: 총 작업수 4, 스레드 수 2, 작업 큐 크기 4")
    public void threadPoolTest() throws InterruptedException {

        MyThreadPool threadPool = new MyThreadPool(2, 4);

        threadPool.submit(() -> {
            try {
                log.info("작업1 시작");
                sleep(1000);
                log.info("작업1 완료");
            } catch (InterruptedException e) {

            }
        });

        threadPool.submit(() -> {
            try {
                log.info("작업2 시작");
                sleep(1000);
                log.info("작업2 완료");
            } catch (InterruptedException e) {

            }
        });

        threadPool.submit(() -> {
            try {
                log.info("작업3 시작");
                sleep(1000);
                log.info("작업3 완료");
            } catch (InterruptedException e) {

            }
        });

        threadPool.submit(() -> {
            try {
                log.info("작업4 시작");
                sleep(1000);
                log.info("작업4 완료");
            } catch (InterruptedException e) {

            }
        });

        sleep(3000);
        threadPool.shutdown();
        sleep(1000);
    }
}