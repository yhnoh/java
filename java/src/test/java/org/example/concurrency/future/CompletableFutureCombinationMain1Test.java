package org.example.concurrency.future;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

class CompletableFutureCombinationMain1Test {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureCombinationMain1Test.class);

    @Test
    @DisplayName("thenCombine: 두개의 작업의 반환값을 조합하여 새로운 결과를 반환")
    void thenCombineTest() {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("작업1 시작");
            return 1;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info("작업2 시작");
            return 2;
        }), (i1, i2) -> {
            log.info("작업1 결과 = {}, 작업2 결과 = {}", i1, i2);
            return i1 + i2;
        });


        try {
            log.info("result = {}", future1.get());
        } catch (InterruptedException | ExecutionException e) {

        }

    }

    @Test
    @DisplayName("thenCompose: 이전 작업의 결과를 사용하여 새로운 작업을 생성하고, 그 결과를 반환")
    void thenComposeTest() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업1 시작");
            return 1;
        }).thenCompose(i -> CompletableFuture.supplyAsync(() -> {
            log.info("작업2 시작");
            return String.valueOf(i);
        }));

        try {
            String result = future.get();
            log.info("result type = {}, result = {}", result.getClass().getTypeName(), result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @DisplayName("acceptEither: 두개의 작업 중 먼저 완료된 작업의 결과를 사용하여 후처리 수행, 새로운 결과를 반환하지 않음")
    void acceptEitherTest() {

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업1 시작");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 1;
            log.info("작업1 완료, 결과 = {}", result);
            return result;
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            log.info("작업2 시작");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 2;
            log.info("작업2 완료, 결과 = {}", result);
            return result;
        }), i -> log.info("작업1 또는 작업2 결과 = {}", i));

        try {
            log.info("result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {

        }
    }

    @Test
    @DisplayName("acceptEither: 두개의 작업 중 먼저 완료된 작업의 결과를 사용하여 후처리 수행, 새로운 결과를 반환")
    void applyToEitherTest() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업1 시작");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 1;
            log.info("작업1 완료, 결과 = {}", result);
            return result;
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            log.info("작업2 시작");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 2;
            log.info("작업2 완료, 결과 = {}", result);
            return result;
        }), i -> {
            log.info("작업1 또는 작업2 결과 = {}", i);
            return i * 2;
        });

        try {
            log.info("result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("anyOf 예제: 여러 작업 중 먼저 완료된 작업의 결과를 반환")
    void anyOfTest() {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("작업1 시작");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 1;
            log.info("작업1 완료, 결과 = {}", result);
            return result;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("작업2 시작");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 2;
            log.info("작업2 완료, 결과 = {}", result);
            return result;
        });

        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(future1, future2);

        anyFuture.thenAccept(i -> {
            log.info("먼저 완료된 작업의 결과 = {}", i);
        });

        anyFuture.join();
    }

    @Test
    @DisplayName("allOf 예제: 여러 작업이 모두 완료될 때까지 기다리고, 결과를 반환하지 않음")
    void allOfTest() {

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("작업1 시작");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 1;
            log.info("작업1 완료, 결과 = {}", result);
            return result;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("작업2 시작");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 2;
            log.info("작업2 완료, 결과 = {}", result);
            return result;
        });

        CompletableFuture<Void> allFuture = CompletableFuture.allOf(future1, future2);

        allFuture.join();
    }
}