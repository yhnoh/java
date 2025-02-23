package org.example.thread.threadpool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;


public class SimpleThreadPool {

    private final Object lock = new Object();
    private final Queue<Runnable> taskQueue;
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    public SimpleThreadPool(int poolSize) {
        taskQueue = new LinkedList<>();
        Thread[] threads = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            threads[i] = new Thread(new Worker());
            //생성한 Thread를 Runnable 상태로 만들어 작업을 수행할 수 있는 상태로 만든다.
            threads[i].start();
        }

    }

    public void submit(Runnable task) {
        if (!isShutdown.get()) {
            synchronized (lock) {
                taskQueue.offer(task);
                //WAIT 상태인 스레드를 깨워 RUNNABLE 상태로 만들어 작업을 수행할 있도록 한다.
                lock.notifyAll();
            }
        }
    }

    public void shutdown() {
        isShutdown.compareAndSet(false, true);
        synchronized (lock) {
            //만약 WAIT 상태인 남은 스레드가 존재한다면, 스레드를 전부 깨워 RUNNABLE 상태로 만들어 작업을 수행할 있도록 한다.
            lock.notifyAll();
        }
    }

    private class Worker implements Runnable {

        @Override
        public void run() {
            //while 문을 통하여 스레드를 재활용할 수 있도록 셋팅한다.
            while (!isShutdown.get()) {
                synchronized (lock) {
                    if (!isShutdown.get() && taskQueue.isEmpty()) {
                        try {
                            //해당 스레드를 WAIT 상태로 만든 이후, 작업이 추가되면 해당 스레드를 깨워 작업을 수행한다.
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    Runnable task = taskQueue.poll();
                    if (task != null) {
                        task.run();
                    }
                }
            }
        }
    }


}
