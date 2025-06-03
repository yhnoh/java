package org.example.concurrency.thread;

import static java.lang.Thread.sleep;

public class ThreadSleepMain1 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();
        
        sleep(3000);
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            try {
                // 스레드가 2초 동안 대기
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 시작");
                sleep(2000);
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 완료");
            } catch (InterruptedException e) {
            }
        }
    }
}
