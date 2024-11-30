package org.example.thread_pool_executor;

import java.util.concurrent.*;

public class Test {


    /**
     * ThreadPoolExecutor는 새 작업이 제출될 때, corePoolSize 미만의 스레드가 실행중이면  corePoolSize가 될때까지 새 스레드를 생성한다.
     * - 기본적으로 스레드를 미리 생성하지 않는다. 새로운 Task가 존재할 경우 스레드를 생성한다. 이는 corePoolSize도 마찬가지이다.
     * corePoolSize -> Queue -> maximumPoolSize
     *
     * prestartCoreThread(), prestartAllCoreThreads()를 통해서 코어스레드르 미리 생성할지에 대한 설정을 할 수 있다.
     *
     * KeepAliveTime: 스레드가 더이상 작업이 없을 경우 즉, 유휴상태일 경우 KeepAliveTime만큼 유지한 이후 제거한다.
     */


    public static void main(String[] args) throws InterruptedException {
        keepAlive();
    }

    private static void corePoolSizeAndMaxPooSize() {
        int corePoolSize = 2;
        int maxPoolSize = 4;
        long keepAliveTime = 0L;
        BlockingQueue<Runnable> workerQueue = new ArrayBlockingQueue<>(8);
        int taskCount = 9;


        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workerQueue);
        executor.prestartAllCoreThreads();

        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            executor.execute(() -> System.out.println(Thread.currentThread().getName() + " 태스크 진행"));
        }

        executor.shutdown();
    }

    /**
     * KeepAlive 정책은 corePoolSize 보다 많은 스레드가 생성되었을 경우 적용되지만 allowCoreThreadTimeOut을 설정하면 core 스레드에도 적용할 수 잇다.
     */

    private static void keepAlive() throws InterruptedException {
        int corePoolSize = 2;
        int maxPoolSize = 4;
        long keepAliveTime = 2L;
        BlockingQueue<Runnable> workerQueue = new ArrayBlockingQueue<>(2);
        int taskCount = 4;


        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workerQueue);
        executor.prestartAllCoreThreads();

        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " 태스크 진행");
            });
        }

        executor.allowCoreThreadTimeOut(true);
        Thread.sleep(4000);
        System.out.println("activeCount = " + executor.getActiveCount());

        executor.shutdown();

    }
}
