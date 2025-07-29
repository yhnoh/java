package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;


public class FutureExceptionMain1 {

    private static final Logger log = LoggerFactory.getLogger(FutureExceptionMain1.class);

    public static void main(String[] args) {

        Callable<String> callable = () -> {
            log.info("작업 시작");
            sleep(1000);
            throw new IllegalStateException("작업 진행중 예외 발생");
        };

        FutureTask<String> future = new FutureTask<>(callable);
        Thread thread = new Thread(future);

        thread.start();
        String result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("result = {}", result);
    }
}
