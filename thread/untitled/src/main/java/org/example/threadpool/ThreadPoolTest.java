package org.example.threadpool;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadPoolTest {

    static class MyThreadPool {
        private int numThreads;
        private Queue<Runnable> taskQueue;
        private Thread[] threads;
        private volatile boolean isShutdown;

        public MyThreadPool(int numThreads) {
            this.numThreads = numThreads;
            this.taskQueue = new LinkedList<>();
            this.threads = new Thread[numThreads];
            this.isShutdown = false;

            for (int i = 0; i < numThreads; i++) {
                this.threads[i] = new WorkerThread(this);
                this.threads[i].start();
            }
        }

        public void submit(Runnable task){
            if(!isShutdown){
                synchronized (taskQueue){
                    taskQueue.offer(task);
                    taskQueue.notifyAll();
                }
            }
        }

        public void shutdown(){
            isShutdown = true;
            synchronized (taskQueue){
                taskQueue.notifyAll();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    static class WorkerThread extends Thread{
        private MyThreadPool myThreadPool;
        public WorkerThread(MyThreadPool myThreadPool) {
            super();
            this.myThreadPool = myThreadPool;
        }

        @Override
        public void run() {
            while (!myThreadPool.isShutdown){
                Runnable task;
                synchronized (myThreadPool.taskQueue) {
                    while(myThreadPool.taskQueue.isEmpty() && !myThreadPool.isShutdown){
                        try {
                            myThreadPool.taskQueue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if(!myThreadPool.taskQueue.isEmpty()){
                    task = myThreadPool.taskQueue.poll();
                }else {
                    continue;
                }

                task.run();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        MyThreadPool myThreadPool = new MyThreadPool(30);

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            myThreadPool.submit(() -> {
                System.out.println("작업 " + taskId + " 수행중입니다.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("작업 " + taskId + " 완료되었습니다.");
            });
        }

        Thread.sleep(3000);
        myThreadPool.shutdown();

    }
}
