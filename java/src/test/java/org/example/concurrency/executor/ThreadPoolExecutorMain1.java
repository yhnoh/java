package org.example.concurrency.executor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ThreadPoolExecutorMain1 {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolExecutorMain1.class);

    @Test
    @DisplayName("ThreadPoolExecutor corePoolSize, maximumPoolSize, workQueue 동작 방식 테스트")
    public void poolSizeTest() throws InterruptedException {

        // corePoolSize: 1, maximumPoolSize: 2, workQueue: 1
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                2,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1));
        Printer printer = new Printer(threadPoolExecutor);
        printer.start();

        sleep(1000);
        threadPoolExecutor.submit(() -> {
            // 작업1 시작...
            log.info("작업1 시작");
            try {
                sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업1 완료");
        });

        sleep(2000);
        threadPoolExecutor.submit(() -> {
            // 작업2 시작...
            log.info("작업2 시작");
            try {
                sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업2 완료");
        });

        sleep(3000);
        threadPoolExecutor.submit(() -> {
            // 작업3 시작...
            log.info("작업3 시작");
            try {
                sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업3 완료");
        });

        boolean isTerminated = threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS);
        if (!isTerminated) {
            threadPoolExecutor.shutdownNow();
        }


        printer.interrupt();
    }

    @Test
    @DisplayName("corePoolSize 이상의 스레드 유휴 상태 확인")
    public void idleTest() throws InterruptedException {
        //given
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));

        Printer printer = new Printer(threadPoolExecutor);
        printer.start();

        sleep(1000);
        threadPoolExecutor.submit(() -> {
            log.info("작업1 시작");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업1 완료");
        });

        sleep(1000);
        threadPoolExecutor.submit(() -> {
            log.info("작업2 시작");
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업2 완료");
        });

        sleep(1000);
        threadPoolExecutor.submit(() -> {
            log.info("작업3 시작");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업3 완료");
        });

        sleep(10000);
        threadPoolExecutor.shutdown();
        printer.interrupt();
        //when

        //then
    }

    public void rejectionTest() throws InterruptedException {
        //given
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        Printer printer = new Printer(threadPoolExecutor);
        printer.start();

        sleep(1000);
        threadPoolExecutor.submit(() -> {
            log.info("작업1 시작");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업1 완료");
        });

        sleep(1000);
        threadPoolExecutor.submit(() -> {
            log.info("작업2 시작");
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업2 완료");
        });

        sleep(1000);
        threadPoolExecutor.submit(() -> {
            log.info("작업3 시작");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업3 완료");
        });

        sleep(10000);
        threadPoolExecutor.shutdown();
        printer.interrupt();
    }

    static class Printer extends Thread {
        private final ThreadPoolExecutor threadPoolExecutor;

        public Printer(ThreadPoolExecutor threadPoolExecutor) {
            this.threadPoolExecutor = threadPoolExecutor;
        }

        @Override
        public void run() {
            while (true) {
                log.info("threadPoolExecutor = {}", threadPoolExecutor);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }

        }
    }
}
