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

    @Test
    @DisplayName("thenCombine: 두개의 작업이 완료된 이후 결과 취합")
    void thenCombineTest() {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작1");
            return "작업 완료1";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작2");
            return "작업 완료2";
        }), (s1, s2) -> {
            log.info("{} > {} > 작업 취합", s1, s2);
            return s1 + " > " + s2 + " > 작업 취합";
        });


        try {
            log.info("result = {}", future1.get());
        } catch (InterruptedException | ExecutionException e) {

        }

    }

    @Test
    @DisplayName("thenCompose: 작업을 연결하여 취합하지만 결과는 하나의 작업 결과만 취합")
    void thenComposeTest() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작1");
            return 1;
        }).thenCompose(i -> CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작2");
            return String.valueOf(i);
        }));

        try {
            log.info("result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void acceptEitherTest() {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작1");
            return "작업 완료1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작2");
            return "작업 완료2";
        });

        CompletableFuture<Void> result = future1.acceptEither(future2, s -> {
            log.info("{} > 작업 취합", s);
        });

        try {
            log.info("result = {}", result.get());
        } catch (InterruptedException | ExecutionException e) {

        }
    }

    @Test
    void applyToEitherTest() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작1");
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작2");
            return 2;
        });

        CompletableFuture<Integer> result = future1.applyToEither(future2, i -> i * 2);
        try {
            log.info("result = {}", result.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}