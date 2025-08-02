package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureMain2 {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureMain2.class);

    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("작업 시작");
            return "작업 종료";
        }).thenApply(s -> {
            log.info("추가 작업 진행 시작");
            return s + " > 추가 작업 진행 완료";
        });

        String result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("result = {}", result);


    }
}
