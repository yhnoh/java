package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;


/**
 * <p>tryLock 예제<p/>
 * <p>지정 시간 동안 락 획득 시도하고, 락을 획득하지 못한다면 작업 취소</p>
 */
public class TryLockMain1 {

    private static final Logger log = LoggerFactory.getLogger(TryLockMain1.class);

    public static void main(String[] args) throws InterruptedException {

        Task task = new Task();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(() -> {
                try {
                    task.execute(2000);
                } catch (InterruptedException e) {
                    System.out.println("작업 실패");
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static class Task {

        private final Lock lock = new ReentrantLock();

        public void execute(int executionMillis) throws InterruptedException {
            int lockTimeoutMillis = 1000;
            // 지정 시간 동안 락 획득 시도
            boolean isAcquiredLock = lock.tryLock(lockTimeoutMillis, TimeUnit.MILLISECONDS);
            try {
                if (isAcquiredLock) {
                    log.info("작업 처리중... 총 소요 시간 {} ms", executionMillis);
                    sleep(executionMillis);
                } else {
                    log.info("락 획득 실패로 인한 작업 취소");
                }
            } finally {
                if (isAcquiredLock) {
                    lock.unlock();
                    log.info("작업 완료");
                }
            }
        }
    }

}
