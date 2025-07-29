package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public class FutureStateMain1 {

    private static final Logger log = LoggerFactory.getLogger(FutureStateMain1.class);

    public static void main(String[] args) {
//        showDoneState();
//        showFailState();
        showCancelState();
//        showCancelStateAndInterrupt();
    }


    /**
     * DONE 상태 확인
     */
    public static void showDoneState() {

        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {

            Future<?> submit = executor.submit(() -> {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            log.info("showDoneState() state = " + submit.state());
            while (submit.state() == Future.State.RUNNING) {

            }
            log.info("showDoneState() state = " + submit.state());
            log.info("showDoneState() isDone = " + submit.isDone());
            log.info("showDoneState() isCancelled = " + submit.isCancelled());
        }
    }

    /**
     * FAIL 상태 확인
     */
    public static void showFailState() {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<?> submit = executor.submit(() -> {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                throw new IllegalArgumentException();
            });

            log.info("showFailState() state = " + submit.state());
            while (submit.state() == Future.State.RUNNING) {
            }

            log.info("showFailState() state = " + submit.state());
            log.info("showFailState() isDone = " + submit.isDone());
            log.info("showFailState() isCancelled = " + submit.isCancelled());
        }
    }

    /**
     * CANCEL 상태 확인
     */
    public static void showCancelState() {

        FutureTask<?> future = new FutureTask<>(() -> {
            try {
                log.info("작업 시작");
                sleep(1000);
                log.info("작업 완료");
            } catch (InterruptedException e) {
            }
        }, null);


        log.info("showCancelState() state = " + future.state());
        while (future.state() == Future.State.RUNNING) {
            future.cancel(false);
        }
        log.info("showCancelState() state = " + future.state());
        log.info("showCancelState() isDone = " + future.isDone());
        log.info("showCancelState() isCancelled = " + future.isCancelled());
    }

    /**
     * CANCEL 상태 확인 및 인터럽트 발생 확인
     */
    public static void showCancelStateAndInterrupt() {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Future<?> submit = executor.submit(() -> {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    log.info("인터럽트 발생");
                }
            });

            log.info("showCancelStateAndInterrupt() state = " + submit.state());
            while (submit.state() == Future.State.RUNNING) {
                submit.cancel(true);
            }
            log.info("showCancelStateAndInterrupt() state = " + submit.state());
            log.info("showCancelStateAndInterrupt() isDone = " + submit.isDone());
            log.info("showCancelStateAndInterrupt() isCancelled = " + submit.isCancelled());
        }
    }

}
