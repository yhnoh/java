package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

/**
 * <p>StampedLock 낙관적 락 모드 예제</p>
 *
 * <p>확인 사항</p>
 * <ul>
 *     <li>낙관적 읽기 모드시에 쓰기 락 획득 가능</li>
 *     <li>낙관적 읽기 모드 도중 쓰기 락 획득시 실패</li>
 *     <li>낙관적 읽기 모드 도중 쓰기 락 획득 하지 않으면 성공</li>
 * </ul>
 */
public class StampedLockMain1 {

    private static final Logger log = LoggerFactory.getLogger(StampedLockMain1.class);

    public static void main(String[] args) throws InterruptedException {

        Post post = new Post();
        // 읽기 작업 수행, 낙관적 읽기 모드 실패
        Thread readThread1 = new Thread(() -> post.read());
        readThread1.start();
        sleep(1000);

        // 읽기 작업 수행 중에도 쓰기 락 획득 가능
        Thread writeThread1 = new Thread(() -> post.write("Hello, World!!"));
        writeThread1.start();
        sleep(1000);

        readThread1.join();
        writeThread1.join();

        // 읽기 작업 수행, 낙관적 읽기 모드 성공
        Thread readThread2 = new Thread(() -> post.read());
        readThread2.start();
        readThread2.join();
    }


    private static class Post {

        private String content;
        private final StampedLock lock = new StampedLock();

        public String read() {
            long stamp = lock.tryOptimisticRead();
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String content = this.content;
            if (lock.validate(stamp)) {
                log.info("optimistic read 성공");
            } else {
                log.info("optimistic read 실패");
            }

            return content;
        }

        public void write(String content) {
            long stamp = lock.writeLock();
            log.info("쓰기 잠금 획득");
            this.content += content;
            try {
                sleep(5000);
            } catch (InterruptedException e) {

            }
            lock.unlockWrite(stamp);
        }

    }
}
