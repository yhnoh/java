package org.example.concurrency.future;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

class CompletableFutureHandleMain1Test {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureHandleMain1Test.class);

    
    @Test
    void handleTest() {
        boolean isThrowException = true;
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            if (isThrowException) {
                throw new RuntimeException("작업 도중 예외 발생");
            }
            return 1;
        }).handle((i, throwable) -> {
            if (throwable == null) {
                log.info("handle no exception : result = {}, throwable = {}", i, "No Exception");
                return i;
            }
            log.error("handle exception: result = {}, throwable = {}", i, throwable.getMessage());
            return 2;
        }).thenApply(i -> i * 2);


        log.info("result = {}", future.join());
    }

    @Test
    void exceptionallyTest() {

        boolean isThrowException = false;
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            if (isThrowException) {
                throw new RuntimeException("작업 도중 예외 발생");
            }
            return 1;
        }).exceptionally((throwable) -> {
            log.info("exceptionally : throwable = {}", throwable.getMessage());
            return 2;
        });


        log.info("result = {}", future.join());
    }

    @Test
    void whenCompleteTest() {
        boolean isThrowException = false;
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            if (isThrowException) {
                throw new RuntimeException("작업 도중 예외 발생");
            }
            return 1;
        }).whenComplete((i, throwable) -> {
            if (throwable == null) {
                log.info("handle no exception : result = {}, throwable = {}", i, "No Exception");
                return;
            }
            log.error("handle exception: result = {}, throwable = {}", i, throwable.getMessage());
        }).thenApply(i -> i * 2);


        log.info("result = {}", future.join());
    }


}