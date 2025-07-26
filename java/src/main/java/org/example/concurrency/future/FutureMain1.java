package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;


/**
 * <p>Future 예제</p>
 * Runnable과 Callable을 사용하여 Future를 생성하는 예제 <br/>
 * Future를 통해서 작업의 결과물을 가져올 수 있는 방법 확인
 */
public class FutureMain1 {

    private static final Logger log = LoggerFactory.getLogger(FutureMain1.class);

    public static void main(String[] args) {

        createFutureByRunnable();
        createFutureByCallable();
    }


    public static void createFutureByRunnable() {

        Runnable runnable = () -> {
            log.info("Runnable Task is running");
        };
        RunnableFuture<String> future = new FutureTask<>(runnable, "Runnable Task Completed");
        Thread thread = new Thread(future);

        try {
            thread.start();
            log.info("future result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFutureByCallable() {
        Callable<String> callable = () -> {
            log.info("Callable Task is running");
            return "Callable Task Completed";
        };

        RunnableFuture<String> future = new FutureTask<>(callable);
        Thread thread = new Thread(future);

        try {
            thread.start();
            log.info("future result = {}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
