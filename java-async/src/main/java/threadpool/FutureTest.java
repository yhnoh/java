package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        delayFirstTaskInFutures();
        useFuture();
    }

    private static void useFuture() throws InterruptedException, ExecutionException {
        final int maxCore = Runtime.getRuntime().availableProcessors();
        final ExecutorService executor = Executors.newFixedThreadPool(maxCore);
        final List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            final int index = i;
            futures.add(executor.submit(() -> {
                return "[" + Thread.currentThread().getName() + "] " + "job" + index;
            }));
        }


        for (Future<String> future : futures) {
            String result = future.get();
            System.out.println(result);
        }

        executor.shutdownNow();
    }

    private static void delayFirstTaskInFutures() throws InterruptedException, ExecutionException {
        final int maxCore = Runtime.getRuntime().availableProcessors();
        final ExecutorService executor = Executors.newFixedThreadPool(maxCore);
        final List<Future<String>> futures = new ArrayList<>();

        for (int i = 4; i >= 0; i--) {
            final int index = i;
            futures.add(executor.submit(() -> {
                Thread.sleep(index * 1000);
                return "[" + Thread.currentThread().getName() + "] " + "job" + index;
            }));
        }


        for (Future<String> future : futures) {
            String result = future.get();
            System.out.println(result);
        }

        executor.shutdownNow();
    }

}
