package org.example.concurrency.executor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

class ExecutorServiceSubmitMain1Test {

    private static final Logger log = LoggerFactory.getLogger(ExecutorServiceSubmitMain1Test.class);

    @Test
    @DisplayName("submit() 메서드 사용 예제")
    public void submitTest() throws ExecutionException, InterruptedException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            Future<Integer> future = executorService.submit(() -> {
                int result = 1;
                log.info("작업{} 시작", result);
                log.info("작업{} 종료", result);
                return result;
            });
            System.out.println("result = " + future.get());
        }
    }

    @Test
    @DisplayName("invokeAny() 메서드 사용 예제")
    public void invokeAny() throws ExecutionException, InterruptedException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {

            List<Callable<Integer>> callables = new ArrayList<>();
            callables.add(() -> {
                int result = 1;
                log.info("작업{} 시작", result);
                sleep(1000);
                log.info("작업{} 종료", result);
                return result;
            });
            callables.add(() -> {
                int result = 2;
                log.info("작업{} 시작", result);
                sleep(2000);
                log.info("작업{} 종료", result);
                return result;
            });
            callables.add(() -> {
                int result = 3;
                log.info("작업{} 시작", result);
                sleep(3000);
                log.info("작업{} 종료", result);
                return result;
            });

            Integer result = executorService.invokeAny(callables);

            System.out.println("result = " + result);
        }
    }

    @Test
    @DisplayName("invokeAll() 메서드 사용 예제")
    public void invokeAll() throws ExecutionException, InterruptedException {
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {

            List<Callable<Integer>> callables = new ArrayList<>();
            callables.add(() -> {
                int result = 1;
                log.info("작업{} 시작", result);
                sleep(1000);
                log.info("작업{} 종료", result);
                return result;
            });
            callables.add(() -> {
                int result = 2;
                log.info("작업{} 시작", result);
                sleep(2000);
                log.info("작업{} 종료", result);
                return result;
            });
            callables.add(() -> {
                int result = 3;
                log.info("작업{} 시작", result);
                sleep(3000);
                log.info("작업{} 종료", result);
                return result;
            });

            
            List<Future<Integer>> futures = executorService.invokeAll(callables);
            for (Future<Integer> future : futures) {
                System.out.println("result = " + future.get());
            }

        }
    }

}