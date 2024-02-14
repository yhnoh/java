package org.example.utilizing;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadStopTest {

    public static void main(String[] args) {

        InterruptThread3 threadStop = new InterruptThread3();
        threadStop.stop();
    }

    static class ThreadStop {
        private volatile boolean running = true;
        public void stop(){
            //volatile 키워드를 활용하면 캐시 메모리가 아닌 공유 메모리에 running의 값이 담겨지기 때문에 변경된 running의 값을 사용할 수 있다.
            new Thread(() -> {
                int count = 0;
                while(running){
                    count++;
                }
                System.out.println("thread1 stop, count = " + count);
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread2 stop");
                running = false;
            }).start();

        }

    }

    static class ThreadStop2 {
        private boolean running = true;
        public void stop(){
            //sleep 메서드를 이용하여 대기 상태에 빠졌다가 다시 스레드를 Running 하기 때문에 지속적으로 캐시메모리르 비워줘서 변경된 running의 값을 가질 수 잇다.
            new Thread(() -> {
                int count = 0;
                while(running){

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    count++;
                }
                System.out.println("thread1 stop, count = " + count);
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread2 stop");
                running = false;
            }).start();

        }

    }

    static class ThreadStop3 {

        private boolean running = true;
        public void stop(){
            //캐시 메모리 때문에 thread1은 running의 값을 true로 계속 가지고 있다.
            new Thread(() -> {
                int count = 0;
                while(running){
                    count++;
                }
                System.out.println("thread1 stop, count = " + count);
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread2 stop");
                running = false;
            }).start();

        }
    }

    static class ThreadStop4 {

        private AtomicBoolean running = new AtomicBoolean(true);
        public void stop(){
            //캐시 메모리 때문에 thread1은 running의 값을 true로 계속 가지고 있다.
            new Thread(() -> {
                int count = 0;
                while(running.get()){
                    count++;
                }
                System.out.println("thread1 stop, count = " + count);
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread2 stop");
                running.set(false);
            }).start();

        }
    }


    static class InterruptThread {
        public void stop(){
            Thread worker = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()){
                    System.out.println("worker thread is running");
                }

                System.out.println("worker thread is stopped interrupted, interrupt state = " + Thread.currentThread().isInterrupted());
            });

            Thread stopper = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                worker.interrupt();
                System.out.println("stopper thread is stopping worker thread");
            });

            worker.start();
            stopper.start();

        }
    }

    static class InterruptThread2 {
        public void stop(){
            Thread worker = new Thread(() -> {
                while (!Thread.interrupted()){
                    System.out.println("worker thread is running");
                }

                System.out.println("worker thread is stopped interrupted, interrupt state = " + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("worker thread is stopped interrupted, interrupt state = " + Thread.currentThread().isInterrupted());
            });

            Thread stopper = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                worker.interrupt();
                System.out.println("stopper thread is stopping worker thread");
            });

            worker.start();
            stopper.start();

        }
    }

    static class InterruptThread3 {
        public void stop(){
            Thread worker = new Thread(() -> {
                try {
                    while (true){
                        System.out.println("worker thread is running");
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    System.out.println("worker thread is stopped interrupted, interrupt state = " + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                }

                System.out.println("worker thread is stopped interrupted, interrupt state = " + Thread.currentThread().isInterrupted());
            });

            Thread stopper = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                worker.interrupt();
                System.out.println("stopper thread is stopping worker thread");
            });

            worker.start();
            stopper.start();

        }
    }


}
