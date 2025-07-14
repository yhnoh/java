package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FutureMain1 {

    private static final Logger log = LoggerFactory.getLogger(FutureMain1.class);

    public static void main(String[] args) {

        createFutureByRunnable();
        createFutureByCallable();
    }


    public static void createFutureByRunnable() {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<?> futrue = executor.submit(() -> {
                log.info("createFutureByRunnable() Hello World");
            });

            futrue.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFutureByCallable() {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<String> submit = executor.submit(() -> "Hello World");
            log.info("createFutureByCallable() " + submit.get());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
