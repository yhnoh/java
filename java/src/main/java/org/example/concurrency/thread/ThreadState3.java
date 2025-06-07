package org.example.concurrency.thread;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * <p>스레드 상태 BLOCKED 예제</p>
 * 여러 스레드가 동시에 작업을 수행하고, 각 스레드의 상태를 주기적으로 출력하는 예제 <br/>
 * synchronized 메서드를 사용하여 스레드 간의 동기화를 구현
 */
public class ThreadState3 {

    public static void main(String[] args) throws InterruptedException {

        ThreadState3 threadState3 = new ThreadState3();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Task(threadState3));
            threads.add(thread);
        }

        // 모든 스레드 시작
        for (Thread thread : threads) {
            thread.start();
        }

        // 스레드 상태를 주기적으로 출력하는 스레드
        Thread printer = new Thread(new ThreadStatePrinter(threads));
        printer.start();

        // 모든 스레드가 작업을 완료할 때까지 대기
        for (Thread thread : threads) {
            thread.join();
        }
        printer.interrupt();
    }

    public static class ThreadStatePrinter implements Runnable {
        private final List<Thread> threads;

        public ThreadStatePrinter(List<Thread> threads) {
            this.threads = threads;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 현재 실행 중인 스레드들의 상태를 출력
                    for (Thread thread : threads) {
                        System.out.println("[" + thread.getName() + "]" + " 스레드 상태: " + thread.getState());
                    }
                    sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public static class Task implements Runnable {
        private final ThreadState3 threadState3;

        public Task(ThreadState3 threadState3) {
            this.threadState3 = threadState3;
        }

        @Override
        public void run() {
            threadState3.runTask();
        }
    }

    public synchronized void runTask() {
        System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 시작, 스레드 상태: " + Thread.currentThread().getState());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("[" + Thread.currentThread().getName() + "]" + " 스레드 작업 종료, 스레드 상태: " + Thread.currentThread().getState());
    }
}
