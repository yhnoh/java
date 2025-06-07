package org.example.concurrency.thread;

import static java.lang.Thread.sleep;

public class ThreadState2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();

        Thread mainThreadState = new Thread(new MainThreadStateTask(Thread.currentThread()));
        mainThreadState.start();


        thread.join();  //WAITING
//        thread.join(3000);    //TIMED_WAITING
        mainThreadState.interrupt();
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static class MainThreadStateTask implements Runnable {
        private final Thread thread;

        public MainThreadStateTask(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("[" + thread.getName() + "]" + " 메인 스레드 상태: " + thread.getState());
                try {
                    sleep(1000); // 1초마다 상태 출력
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    }


}
