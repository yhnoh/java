package org.example.threadpool;

import java.util.concurrent.*;

public class TaskHandleSize {


    public static void main(String[] args) {
        int corePoolSize = 1;
        int maximumPoolSize = 2;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
        //최대 받을 수 있는 작업 개수 = 5;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, Long.MAX_VALUE, TimeUnit.SECONDS, workQueue);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                System.out.println("worQueue size = " + executor.getQueue().size());
                System.out.println("activeCount = " + executor.getActiveCount());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(Thread.currentThread().getName() + " 스레드 작업 완료");

            });
        }

        executor.shutdown();

    }
}
