package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * <p>ReentrantReadWriteLock ReadLock 예제</p>
 * 읽기 잠금 획득시 다른 스레드는 읽기 잠금을 획득할 수 있지만, 쓰기 잠금은 획득할 수 없는 예제 <br/><br/>
 * <p>가정</p>
 * <ul>
 *     <li>읽기 잠금 획득 이후 5초간 작업 수행</li>
 *     <li>쓰기 잠금 획득 이후 5초간 작업 수행</li>
 *     <li>읽기 스레드를 실행한 이후, 쓰기 스레드 실행</li>
 * </ul>
 */
public class ReentrantReadWriteLockMain1 {
    private static final Logger log = LoggerFactory.getLogger(ReentrantReadWriteLockMain1.class);

    public static void main(String[] args) throws InterruptedException {
        Post post = new Post();

        List<Thread> readThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread readThread = new Thread(() -> post.read());
            readThreads.add(readThread);
        }

        for (Thread readThread : readThreads) {
            readThread.start();
        }

        sleep(1000); // 읽기 스레드가 먼저 시작되도록 잠시 대기
        Thread writeThread = new Thread(() -> post.write("Hello, World!!"));
        writeThread.start();

        for (Thread readThread : readThreads) {
            readThread.join();
        }
    }

    private static class Post {

        private String content = "Hello, World!!";
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();

        public String read() {
            try {
                readLock.lock();
                log.info("읽기 잠금 획득");
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                }

                return content;
            } finally {
                readLock.unlock();
            }
        }

        public void write(String content) {
            writeLock.lock();
            log.info("쓰기 잠금 획득");
            this.content += content;
            try {
                sleep(5000);
            } catch (InterruptedException e) {

            }
            writeLock.unlock();
        }
    }
}
