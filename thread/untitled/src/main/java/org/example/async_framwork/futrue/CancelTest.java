package org.example.async_framwork.futrue;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class CancelTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<Integer> callableTask = () -> {
            System.out.println("비동기 작업 시작...");
            sleep(2000);
            System.out.println("비동기 작업 완료...");
            return 42;
        };

        Future<Integer> future = executorService.submit(callableTask);
        sleep(1000);

        //취소 요청 시 중간에 작업을 중단할 수 있다.
        future.cancel(true);

        //취소 요청 시 중간에 작업을 중단하지 않는다.
//        future.cancel(false);

        try{
            Integer result = future.get();
            System.out.println("result = " + result);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();

    }

}
