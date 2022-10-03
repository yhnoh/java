package threadpool;

import java.util.List;
import java.util.concurrent.*;

public class ParallelExecutorService {

    public static void main(String[] args) {
        runParallelExecutorServiceWhenDelayTask();
//        runParallelExecutorService();
    }


    public static void runParallelExecutorServiceWhenDelayTask(){

        int maxCore = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(maxCore);
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);


        for (int i = 0; i < 5; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    long startTime = System.currentTimeMillis();
                    Thread.sleep(1000 * index);
                    long endTime = System.currentTimeMillis();
                    queue.put("[" + Thread.currentThread().getName() + "] job" + index + " : " + (endTime - startTime) + "ms");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }

        List<Runnable> unfinishedWorks = executorService.shutdownNow();
        if(!unfinishedWorks.isEmpty()){
            System.out.println("Not all tasks finished before calling close: " + unfinishedWorks.size());
        }

    }

    public static void runParallelExecutorService(){
        int maxCore = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(maxCore);
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    long startTime = System.currentTimeMillis();
                    long endTime = System.currentTimeMillis();
                    queue.put("[" + Thread.currentThread().getName() + "] job" + index + " : " + (endTime - startTime) + "ms");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }

        List<Runnable> unfinishedWorks = executorService.shutdownNow();
        if(!unfinishedWorks.isEmpty()){
            System.out.println("Not all tasks finished before calling close: " + unfinishedWorks.size());
        }

    }


}
