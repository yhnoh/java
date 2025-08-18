package org.example.concurrency.executor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ScheduledThreadPoolExecutorMain1 {
    private static final Logger log = LoggerFactory.getLogger(ScheduledThreadPoolExecutorMain1.class);

    @Test
    @DisplayName("schedule() 메서드를 통한 작업 예약 테스트")
    public void scheduleTest() {

        try (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1)) {
            log.info("schedule() 메서드를 통한 작업 예약");
            scheduledThreadPoolExecutor.schedule(() -> {
                log.info("작업 시작");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("작업 완료");
            }, 5, TimeUnit.SECONDS);

        }
    }

    /**
     * {@code initialDelay}, then {@code initialDelay + period}, then {@code initialDelay + 2 * period} <br/>
     * 00:00 5초 대기 <br/>
     * 00:05 작업 시작 <br/>
     * 00:06 작업 완료 <br/>
     * 00:10 작업 시작 <br/>
     * 00:11 작업 완료 <br/>
     */
    @Test
    @DisplayName("scheduleAtFixedRateTest() 메서드를 통한 작업 예약 테스트")
    public void scheduleAtFixedRateTest() throws InterruptedException {

        try (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1)) {
            log.info("scheduleAtFixedRate() 메서드를 통한 작업 예약");
            scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
                log.info("작업 시작");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("작업 완료");
            }, 5, 5, TimeUnit.SECONDS);
            sleep(20000);
        }
    }

    /**
     * 00:00 5초 대기 <br/>
     * 00:05 작업 시작 <br/>
     * 00:06 작업 완료 <br/>
     * 00:11 작업 시작 <br/>
     * 00:12 작업 완료 <br/>
     */
    @Test
    public void scheduleWithFixedDelayTest() throws InterruptedException {

        try (ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1)) {
            log.info("scheduleWithFixedDelay() 메서드를 통한 작업 예약");
            scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
                log.info("작업 시작");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("작업 완료");
            }, 5, 5, TimeUnit.SECONDS);
            sleep(20000);
        }
    }
}
