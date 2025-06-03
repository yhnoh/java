package org.example.concurrency.thread;

import static java.lang.Thread.sleep;

public class ThreadJoinMain1 {


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadSleepMain1.Task());
        thread.start();

        // 메인 스레드가 다른 스레드의 작업이 완료될때까지 대기
        thread.join();
        thread.wait();
    }

    public static class Task implements Runnable {

        @Override
        public void run() {
            // 스레드가 2초 동안 대기
            try {
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 시작");
                sleep(2000);
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 완료");
            } catch (InterruptedException e) {
            }
        }
    }
}
