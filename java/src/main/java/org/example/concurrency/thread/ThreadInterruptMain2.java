package org.example.concurrency.thread;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class ThreadInterruptMain2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();

        sleep(2000); // 2초 후에 인터럽트 시도

        //외부에서 인터럽트 진행
        thread.interrupt();

        System.out.println("[" + thread.getName() + "]" + " 인터럽트 여부 확인: " + thread.isInterrupted());

    }

    public static class Task implements Runnable {
        @Override
        public void run() {

            try {
                // 작업 수행 중
                System.out.println("[" + currentThread().getName() + "]" + " 작업 스레드 시작");
                sleep(5000); // 5초 동안 대기
            } catch (InterruptedException e) {
                // InterruptedException이 발생하면 인터럽트 상태가 다시 false로 설정
                // 스레드가 인터럽트되었을 때의 처리
                System.out.println("[" + currentThread().getName() + "]" + " 인터럽트 여부 확인: " + currentThread().isInterrupted());
            }

            System.out.println("[" + currentThread().getName() + "]" + " 작업 스레드 종료");

        }
    }
}
