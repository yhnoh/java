package org.example.concurrency.thread;

import static java.lang.Thread.sleep;

/**
 * <p>스레드 상태 WAITING, TIMED_WAITING 예제<p/>
 * 메인 스레드가 다른 스레드의 작업이 완료될때까지 대기하고 메인 스레드의 상태를 주기적으로 출력하는 예제
 */
public class ThreadState2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();

        Thread mainThreadState = new Thread(new ThreadStatePrinter(Thread.currentThread()));
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

    public static class ThreadStatePrinter implements Runnable {
        private final Thread thread;

        public ThreadStatePrinter(Thread thread) {
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
