package org.example.concurrency.executor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExecutorServiceShutdownMain1Test {

    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceShutdownMain1.class);

    @Test
    public void shutdownTest() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            int result = 1;
            log.info("작업{} 시작", result);
            sleep(1000);
            log.info("작업{} 종료", result);
            return result;
        });

        executorService.submit(() -> {
            int result = 2;
            log.info("작업{} 시작", result);
            sleep(1000);
            log.info("작업{} 종료", result);
            return result;
        });

        executorService.submit(() -> {
            int result = 3;
            log.info("작업{} 시작", result);
            sleep(1000);
            log.info("작업{} 종료", result);
            return result;
        });


        sleep(500);
        executorService.shutdown();
        // 대기 중인 작업을 수행하는지 확인을 위한 대기
        sleep(3000);

        // shutdown() 호출 후에 작업 제출 가능 여부 확인
        assertThrows(RejectedExecutionException.class, () -> {
            executorService.submit(() -> {
            });
        });
    }

    @Test
    public void shoutdownNotTest() throws InterruptedException {
        //given
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            int result = 1;
            log.info("작업{} 시작", result);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                log.info("shutdownNow() 호출로 인한 인터럽트 발생");
            }
            log.info("작업{} 종료", result);
            return result;
        });

        executorService.submit(() -> {
            int result = 2;
            log.info("작업{} 시작", result);
            sleep(1000);
            log.info("작업{} 종료", result);
            return result;
        });

        executorService.submit(() -> {
            int result = 3;
            log.info("작업{} 시작", result);
            sleep(1000);
            log.info("작업{} 종료", result);
            return result;
        });


        sleep(500);
        List<Runnable> runnables = executorService.shutdownNow();
        // 대기 중인 작업을 수행하는지 확인을 위한 대기
        sleep(3000);

        // shutdownNow() 호출 후에 대기 중인 작업이 반환되는지 확인
        assertEquals(2, runnables.size());
        // shutdownNow() 호출 후에 작업 제출 가능 여부 확인
        assertThrows(RejectedExecutionException.class, () -> {
            executorService.submit(() -> {
            });
        });
    }
}