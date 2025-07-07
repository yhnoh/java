package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class ReadWriteLockMain2 {
    private static final Logger log = LoggerFactory.getLogger(ReadWriteLockMain2.class);

    public static void main(String[] args) throws InterruptedException {
        Post post = new Post();

        // 글 작성 스레드
        Thread writerThread = new Thread(() -> {
            try {
                log.info("[" + Thread.currentThread().getName() + "]" + " 글 쓰기 요청");
                post.write("Hello, World!");
                log.info("[" + Thread.currentThread().getName() + "]" + " 글 쓰기 완료");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 글 읽기 스레드
        List<Thread> readerThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread readerThread = new Thread(() -> {
                log.info("[" + Thread.currentThread().getName() + "]" + "  글 읽기 요청");
                String content = post.read();
                log.info("[" + Thread.currentThread().getName() + "]" + " 글 읽기 완료 = " + content);

            });
            readerThreads.add(readerThread);
        }

        writerThread.start();
        for (Thread readerThread : readerThreads) {
            readerThread.start();
        }

        writerThread.join();
        for (Thread readerThread : readerThreads) {
            readerThread.join();
        }

    }

    public static class Post {

        private String content = "";
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        public String read() {
            readLock.lock();
            try {
                return content;
            } finally {
                readLock.unlock();
            }
        }

        public void write(String content) throws InterruptedException {
            // 글 작성 이후 5초 동안 대기
            writeLock.tryLock();
            try {
                this.content += content;
                sleep(5000); // 글 작성 후 5초 대기
            } finally {
                writeLock.unlock();
            }
        }
    }
}

