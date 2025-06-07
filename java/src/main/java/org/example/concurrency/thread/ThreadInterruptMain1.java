package org.example.concurrency.thread;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/**
 * interrupt 활용 예제
 */
public class ThreadInterruptMain1 {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Task());
        thread.start();

        sleep(2000); // 2초 후에 인터럽트 시도

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
            System.out.println("[" + currentThread().getName() + "]" + " 작업 스레드 종료");
        }
    }


}
