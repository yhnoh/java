package org.example.concurrency.future;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class CompletableFutureAfterLogicMain1Test {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureAfterLogicMain1Test.class);


    @Test
    @DisplayName("thenApply 예제: 작업의 결과를 받아서 추가 로직을 수행, 새로운 결과를 반환")
    void thenApplyTest() {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            return 1;
        }).thenApply(i -> {
            log.info("추가 로직 수행");
            return i + 1;
        });

        try {
            log.info("result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {

        }
    }

    @Test
    @DisplayName("thenAccept 예제: 작업의 결과를 받아서 추가 로직을 수행, 새로운 결과를 반환하지 않음")
    void thenAcceptTest() {

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            return 1;
        }).thenAccept(i -> {
            log.info("추가 로직 수행 i = {} ", i + 1);
        });

        try {
            log.info("result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {

        }
    }

    @Test
    @DisplayName("thenRun: 작업의 결과를 무시하고 추가 로직을 수행, 새로운 결과를 반환하지 않음")
    void thenRunTest() {

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            return 1;
        }).thenRun(() -> {
            log.info("추가 로직 수행");
        });

        try {
            log.info("result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {

        }
    }


}