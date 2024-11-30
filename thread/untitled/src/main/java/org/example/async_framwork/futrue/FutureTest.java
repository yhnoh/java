package org.example.async_framwork.futrue;

import java.util.concurrent.*;

import static java.lang.Thread.*;

public class FutureTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<Integer> callableTask = () -> {
            System.out.println("비동기 작업 시작...");
            sleep(2000);
            System.out.println("비동기 작업 완료...");
            return 42;
        };

        Future<Integer> future = executorService.submit(callableTask);

        try{
            Integer result = future.get();
            System.out.println("result = " + result);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();

    }


}
