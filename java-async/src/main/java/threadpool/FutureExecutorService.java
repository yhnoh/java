package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExecutorService {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //useFutureWhenDelayTask();
        useFuture();
    }

    private static void useFuture() throws InterruptedException, ExecutionException {
        final int maxCore = Runtime.getRuntime().availableProcessors();
        final ExecutorService executor = Executors.newFixedThreadPool(maxCore);
        final List<Future<String>> futures = new ArrayList<>();

        //다른 쓰레드의 결과값을 Future에 따로 저장
        for (int i = 0; i < 5; i++) {
            final int index = i;
            futures.add(executor.submit(() -> "[" + Thread.currentThread().getName() + "] " + "job" + index));
        }

        //Future를 이용해 결과 값 가져오기
        for (Future<String> future : futures) {
            String result = future.get();
            System.out.println(result);
        }

        executor.shutdownNow();
    }

    private static void useFutureWhenDelayTask() throws InterruptedException, ExecutionException {
        final int maxCore = Runtime.getRuntime().availableProcessors();
        final ExecutorService executor = Executors.newFixedThreadPool(maxCore);
        final List<Future<String>> futures = new ArrayList<>();

        for (int i = 4; i >= 0; i--) {
            final int index = i;
            futures.add(executor.submit(() -> {
                long startTime = System.currentTimeMillis();
                Thread.sleep(1000 * index);
                long endTime = System.currentTimeMillis();
                return "[" + Thread.currentThread().getName() + "] job" + index + " : " + (endTime - startTime) + "ms";
            }));
        }


        for (Future<String> future : futures) {
            String result = future.get();
            System.out.println(result);
        }

        executor.shutdownNow();
    }

}
