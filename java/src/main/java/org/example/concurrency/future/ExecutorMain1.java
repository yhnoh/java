package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorMain1 {

    private static final Logger log = LoggerFactory.getLogger(ExecutorMain1.class);

    public static void main(String[] args) throws InterruptedException {


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        threadPoolExecutor.execute(() -> {
            try {
                log.info("작업1 시작");
                Thread.sleep(3000);

            } catch (InterruptedException e) {
            }
            log.info("작업1 완료");
        });
        threadPoolExecutor.execute(() -> {
            try {
                log.info("작업2 시작");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업2 완료");

        });
        threadPoolExecutor.execute(() -> {
            try {
                log.info("작업3 시작");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("작업3 완료");
        });


        log.info("스레드풀 종료 요청");
        threadPoolExecutor.shutdown();

        log.info("스레드풀 내에 실행 중인 작업 완료 대기");
        boolean isTerminated = threadPoolExecutor.awaitTermination(4, TimeUnit.SECONDS);
        if (isTerminated) {
            log.info("모든 작업이 완료되었습니다. isTerminated = {}", isTerminated);
        } else {
            log.info("모든 작업이 완료되지 않았습니다. 작업을 강제종료합니다. isTerminated = {}", isTerminated);
            threadPoolExecutor.shutdownNow();
        }
    }

}
