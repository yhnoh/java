package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class CompletableFutureMain1 {

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureMain1.class);

    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                log.info("작업 시작");
                sleep(1000);
            } catch (InterruptedException e) {

            }
            return "작업 종료";
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
