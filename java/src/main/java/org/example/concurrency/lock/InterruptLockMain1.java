package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;


/**
 * <p>Lock 인터럽트 예제</p>
 * <p>락 획득을 시도하려 할때 인터럽트가 발생하면 InterruptedException을 발생 시키고 작업 실패</p>
 * <p>해당 예제에서는 락 획득을 대기하는 스레드에 대해 인터럽트를 발생시킨다.</p>
 */
public class InterruptLockMain1 {

    private static final Logger log = LoggerFactory.getLogger(InterruptLockMain1.class);

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threads.add(new Thread(() -> {
                try {
                    task.execute(5000);
                } catch (InterruptedException e) {
                    System.out.println("인터럽트 발생으로 인한 작업 실패");
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        sleep(500);

        Thread waitingInterrupterThread = new Thread(new WaitingInterrupter(threads));
        waitingInterrupterThread.start();

        for (Thread thread : threads) {
            thread.join();
        }
        waitingInterrupterThread.join();

    }

    /**
     * 대기 중인 스레드를 찾아서 인터럽트하는 클래스
     */
    public static class WaitingInterrupter implements Runnable {

        private final List<Thread> threads;

        public WaitingInterrupter(List<Thread> threads) {
            this.threads = threads;
        }

        @Override
        public void run() {

            threads.forEach(thread -> {
                log.info("state = " + thread.getState());
            });

            threads.stream()
                    .filter(thread -> thread.getState().equals(Thread.State.WAITING))
                    .forEach(Thread::interrupt);
        }
    }


    private static class Task {

        private final Lock lock = new ReentrantLock();
        private final long startTimeMills = System.currentTimeMillis();

        public void execute(int executionMillis) throws InterruptedException {
            // 락 획득 시도 진행, 락 획득 도중 인터럽트 발생시 InterruptedException 예외 발생
            lock.lockInterruptibly();
            try {
                log.info("작업 처리중... 총 소요 시간 {} ms", executionMillis);
                while (true) {
                    long endTimeMills = System.currentTimeMillis();
                    if (endTimeMills - startTimeMills > executionMillis) {
                        break;
                    }
                }
            } finally {
                lock.unlock();
            }
            log.info("작업 완료");
        }
    }
}
