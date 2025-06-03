package org.example.concurrency.thread;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class ThreadInterruptMain3 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();

        sleep(2000);
        //외부에서 인터럽트 진행
        thread.interrupt();
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("[" + currentThread().getName() + "]" + " 작업 스레드 시작");
            boolean isInterrupted = false;
            while (!isInterrupted) {
                // 작업 진행 도중 인터럽트 여부 확인
                isInterrupted = currentThread().isInterrupted();
            }

            System.out.println("[" + currentThread().getName() + "]" + " 인터럽트 여부 확인: " + currentThread().isInterrupted());
            Thread.interrupted();
            System.out.println("[" + currentThread().getName() + "]" + " Thread.interrupted() 호출 이후 인터럽트 여부 확인: " + currentThread().isInterrupted());
            System.out.println("[" + currentThread().getName() + "]" + " 작업 스레드 종료");
        }
    }
}
