package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class ReadWriteLockMain2 {
    private static final Logger log = LoggerFactory.getLogger(ReadWriteLockMain2.class);

    public static void main(String[] args) throws InterruptedException {
        Post post = new Post();

        // 글 작성 스레드
        Thread writerThread = new Thread(() -> {
            try {
                log.info("글 쓰기 요청");
                post.write(" After Hello, World!!");
                log.info("글 쓰기 완료");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 글 읽기 스레드
        List<Thread> readerThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread readerThread = new Thread(() -> {
                log.info("글 읽기 요청");
                String content = post.read();
                log.info("글 읽기 완료: {}", content);
            });
            readerThreads.add(readerThread);
        }

        for (Thread readerThread : readerThreads) {
            readerThread.start();
        }

        sleep(500);
        writerThread.start();

        for (Thread readerThread : readerThreads) {
            readerThread.join();
        }

        writerThread.join();

    }


    public static class Post {

        private String content = "Hello, World!!";
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();

        public String read() {
            try {
                readLock.lock();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                }

                return content;
            } finally {
                readLock.unlock();
            }
        }

        public void write(String content) throws InterruptedException {
            writeLock.lock();
            this.content += content;
            sleep(500);
            writeLock.unlock();
        }
    }
}

