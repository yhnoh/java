package org.example.synchronization;

public class MultiThreadTest {

    public static void main(String[] args) throws InterruptedException {

        MultiThreadTest multiThreadTest = new MultiThreadTest();
        multiThreadTest.runSumMultiThreadNotSynchronized();
    }

    public void runSumMultiThread() throws InterruptedException {
        SumMultiThread sumMultiThread = new SumMultiThread();
        sumMultiThread.run();
    }

    public void runSumMultiThreadNotSynchronized() throws InterruptedException {
        SumMultiThreadNotSynchronized sumMultiThreadNotSynchronized = new SumMultiThreadNotSynchronized();
        sumMultiThreadNotSynchronized.run();
    }

    static class SumMultiThread {
        int sum = 0;
        private Object lock = new Object();
        public void run() throws InterruptedException {

            long startTime = System.currentTimeMillis();
            Thread thread1 = new Thread(() -> {
                for (int i = 1; i <= 500; i++) {
                    synchronized (lock) {
                        sum += i;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 501; i <= 1000; i++) {
                    synchronized (lock) {
                        sum += i;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("합계 : " + sum);
            System.out.println("처리 시간 : " + (System.currentTimeMillis() - startTime) + "ms");
        }

    }

    static class SumMultiThreadNotSynchronized {
        int sum = 0;
        private Object lock = new Object();
        public void run() throws InterruptedException {

            long startTime = System.currentTimeMillis();
            Thread thread1 = new Thread(() -> {
                for (int i = 1; i <= 500; i++) {
                        sum += i;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 501; i <= 1000; i++) {
                    synchronized (lock) {
                        sum += i;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("합계 : " + sum);
            System.out.println("처리 시간 : " + (System.currentTimeMillis() - startTime) + "ms");
        }

    }

    public void runSingleThread() throws InterruptedException {

        long startTime = System.currentTimeMillis();

        int sum = 0;
        for (int i = 1; i <= 1000; i++) {
            sum += i;
            Thread.sleep(1);
        }

        System.out.println("합계 : " + sum);
        System.out.println("처리 시간 : " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
