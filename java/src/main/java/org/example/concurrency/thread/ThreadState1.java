package org.example.concurrency.thread;

import static java.lang.Thread.sleep;

public class ThreadState1 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        //1. NEW
        System.out.println("[" + thread.getName() + "]" + " 스레드 생성, 스레드 상태: " + thread.getState());
        thread.start();

        // 대기 상태를 확인하기 위하여 잠시 대기
        sleep(1000);

        //3. TIMED_WAITING
        System.out.println("[" + thread.getName() + "]" + " 스레드 대기, 스레드 상태: " + thread.getState());

        // 메인 스레드가 다른 스레드의 작업이 완료될 때까지 대기
        thread.join();

        //5. TERMINATED
        System.out.println("[" + thread.getName() + "]" + " 스레드 종료, 스레드 상태: " + thread.getState());
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            try {
                //2. RUNNABLE
                System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 시작, 스레드 상태: " + Thread.currentThread().getState());
                sleep(2000);    // 2초 동안 대기
            } catch (InterruptedException e) {
            }
            //4. RUNNABLE
            System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 완료, 스레드 상태: " + Thread.currentThread().getState());
        }
    }
}
