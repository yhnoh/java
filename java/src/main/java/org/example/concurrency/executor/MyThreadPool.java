package org.example.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * <p>간단한 스레드 풀 구현</p>
 * <p>
 * 스레드를 미리 생성하고, 작업 큐에 작업이 추가되면 해당 작업을 수행할 수 있도록 한다.
 * 작업 큐에 작업이 없으면 스레드를 종료 시키지 않고 대기 상태로 유지한다.
 * 스레드 풀을 종료할 때는 작업 큐에 작업이 없더라도 대기하지 않고 종료한다.
 * 이미 실행 중인 작업의 경우는 작업이 완료될때까지 수행한다.
 */
public class MyThreadPool {


    private final BlockingQueue<Runnable> taskQueue;
    private final List<Worker> workers = new ArrayList<>();
    private boolean isShutdown = false;

    public MyThreadPool(int corePoolSize, int taskSize) {
        taskQueue = new LinkedBlockingQueue<>(taskSize);

        // 스레드를 미리 생성한 이후 실행하여, Thread를 RUNNABLE 상태로 만들어 작업을 수행할 수 있도록 한다.
        for (int i = 0; i < corePoolSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.thread.start();
        }

    }

    public void submit(Runnable task) {
        if (task == null) {
            throw new IllegalArgumentException("task cannot be null");
        }

        // BlockingQueue의 offer() 메서드는 큐가 가득 찼을 경우, 작업을 추가하지 않고 false를 반환
        if (!taskQueue.offer(task)) {
            throw new IllegalStateException("taskQueue is full");
        }
    }

    public synchronized boolean isShutdown() {
        return isShutdown;
    }

    // shutdown 메서드는 스레드 풀이 관리하고 있는 모든 스레드를 종료시키기 위한 메서드
    public synchronized void shutdown() {
        isShutdown = true;
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    class Worker implements Runnable {

        private boolean isShutdown = false;
        private final Thread thread = new Thread(this);

        @Override
        public void run() {
            while (!isShutdown) {
                Runnable task = null;
                try {
                    // BlockingQueue의 take() 메서드는 큐가 비어있을 경우, 작업이 추가될 때까지 대기한다.
                    // shutdown() 메서드가 호출 되면, 작업이 추가될 때까지 대기하지 않는다.
                    task = taskQueue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (task != null) {
                    task.run();
                }
            }
        }

        // shutdown 메서드는 Worker 스레드를 인터럽트 시켜 작업을 중지하기위한 메서드
        public synchronized void shutdown() {
            isShutdown = true;
            thread.interrupt();
        }
    }
}
