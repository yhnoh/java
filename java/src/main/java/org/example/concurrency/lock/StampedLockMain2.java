package org.example.concurrency.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

/**
 * <p>StampedLock 재진입성 불가 예제</p>
 * 재진입성 불가로 인한 데드락 발생 예제 <br/><br/>
 *
 * <p>확인 사항</p>
 * <ul>
 *     <li>write1() 메서드에서 락을 획득한 이후 해제하지 않고 write2() 메서드 호출</li>
 *     <li>재진입성 불가로 인한 데드락 발생</li>
 * </ul>
 */
public class StampedLockMain2 {

    private static final Logger log = LoggerFactory.getLogger(StampedLockMain2.class);

    public static void main(String[] args) throws InterruptedException {

        Post post = new Post();
        post.write1("Hello, World!!");
    }


    private static class Post {

        private String content;
        private final StampedLock lock = new StampedLock();

        public void write1(String content) {
            long stamp = lock.writeLock();
            log.info("첫번째 쓰기 잠금 획득");
            this.content += content;
            try {
                sleep(1000);
            } catch (InterruptedException e) {

            }

            this.write2(content);

            lock.unlockWrite(stamp);
        }

        public void write2(String content) {
            long stamp = lock.writeLock();
            log.info("두번째 쓰기 잠금 획득");
            this.content += content;
            try {
                sleep(1000);
            } catch (InterruptedException e) {

            }
            lock.unlockWrite(stamp);
        }

    }
}
