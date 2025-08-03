package org.example.concurrency.future;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

class CompletableFutureHandleMain1Test {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureHandleMain1Test.class);


    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void handleTest(boolean isThrowException) {
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
            log.error("throw exception", throwable);
            return 2;
        }).thenApply(i -> i * 2);


        log.info("result = {}", future.join());
        System.out.println();
        System.out.println();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void exceptionallyTest(boolean isThrowException) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            if (isThrowException) {
                throw new RuntimeException("작업 도중 예외 발생");
            }
            return 1;
        }).exceptionally((throwable) -> {
            log.error("throw exception", throwable);
            return 2;
        }).thenApply(i -> i * 2);


        log.info("result = {}", future.join());
        System.out.println();
        System.out.println();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void whenCompleteTest(boolean isThrowException) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            if (isThrowException) {
                throw new RuntimeException("작업 도중 예외 발생");
            }
            return 1;
        }).whenComplete((i, throwable) -> {
            if (throwable == null) {
                log.info("whenComplete no exception : result = {}, throwable = {}", i, "No Exception");
                return;
            }
            
            log.error("throw exception", throwable);
        }).thenApply(i -> i * 2);


        try {
            log.info("result = {}", future.join());
        } catch (CompletionException e) {
            log.error("throw CompletionException", e);
        }

        System.out.println();
        System.out.println();


    }


}