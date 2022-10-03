package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {


        runFixedThreadPool();
        //runSingleThreadExecutor();
    }

    private static void runFixedThreadPool() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(() -> {
            printThreadName("job1");
        });

        executor.submit(() -> {
            printThreadName("job2");
        });

        executor.submit(() -> {
            printThreadName("job3");
        });

        executor.submit(() -> {
            printThreadName("job4");
        });

        printThreadName("main job");

        //더 이상 쓰레드풀에 작업을 추가하지 못하며, 처리 중인 Task가 모두 완료되면 쓰레드풀을 종료시킨다.
        executor.shutdown();

        //수행 중인 Task가 지정된 시간동안 끝나기를 기다린다. 만약 Task가 지정된 시간 내에에 끝나지 않으면 false를리턴
        if(!executor.awaitTermination(20, TimeUnit.SECONDS)){
            executor.shutdownNow();
        }
    }

    public static void runSingleThreadExecutor() throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            printThreadName("job1");
        });

        executor.submit(() -> {
            printThreadName("job2");
        });

        executor.submit(() -> {
            printThreadName("job3");
        });

        executor.submit(() -> {
            printThreadName("job4");
        });

        printThreadName("main job");
        executor.shutdown();

        if(!executor.awaitTermination(20, TimeUnit.SECONDS)){
            executor.shutdownNow();
        }

    }

    public static void printThreadName(String workName){
        System.out.println("[" + Thread.currentThread().getName() + "] " + workName);
    }
}
