package org.example.concurrency.executor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

class ExecutorServiceMain1Test {

    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceMain1Test.class);

    @Test
    void main1() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<String> submit1 = executor.submit(() -> {
            String result = "작업 결과";
            try {
                log.info("작업 시작");
                sleep(1000); // 작업 시뮬레이션
                log.info("작업 종료");
            } catch (InterruptedException e) {

            }
            return result;
        });

        Future<String> submit2 = executor.submit(() -> {
            String result = "작업 결과";
            try {
                log.info("작업 시작");
                sleep(1000); // 작업 시뮬레이션
                log.info("작업 종료");
            } catch (InterruptedException e) {

            }
            return result;
        });

        Future<String> submit3 = executor.submit(() -> {
            String result = "작업 결과";
            try {
                log.info("작업 시작");
                sleep(1000); // 작업 시뮬레이션
                log.info("작업 종료");
            } catch (InterruptedException e) {

            }
            return result;
        });

        executor.shutdown();
        boolean terminated = executor.isTerminated();

        if (!terminated) {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
    }
}