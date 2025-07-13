package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class ReentrantReadWriteLockMain2 {
    private static final Logger log = LoggerFactory.getLogger(ReentrantReadWriteLockMain2.class);

    public static void main(String[] args) throws InterruptedException {
        Post post = new Post();

        Thread writerThread = new Thread(new Writer(post));
        Thread readerThread = new Thread(new Reader(post));

        writerThread.start();
        readerThread.start();

        writerThread.join();
        readerThread.join();

    }

    public static class Writer implements Runnable {

        private final Post post;

        public Writer(Post post) {
            this.post = post;
        }

        @Override
        public void run() {
            while (true) {
                // 글 작성 스레드
                Thread writerThread = new Thread(() -> {
                    try {
                        log.info("글 쓰기 요청");
                        post.write("Hello, World!!");
                        log.info("글 쓰기 완료");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                writerThread.start();
                try {
                    writerThread.join();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static class Reader implements Runnable {

        private final Post post;

        public Reader(Post post) {
            this.post = post;
        }

        @Override
        public void run() {

            while (true) {
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

                for (Thread readerThread : readerThreads) {
                    try {
                        readerThread.join();
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
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
            sleep(2000);
            writeLock.unlock();
        }
    }
}

