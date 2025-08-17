package org.example.concurrency.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ExecutorServiceMain1 {

    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceMain1.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            log.info("작업 시작");
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업 종료");
        });
        executorService.submit(() -> {
            log.info("작업 시작");
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업 종료");
        });
        executorService.submit(() -> {
            log.info("작업 시작");
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업 종료");
        });

        executorService.shutdown();

//        executorService.submit()
    }
}
