package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public class FutureStateMain1 {

    private static final Logger log = LoggerFactory.getLogger(FutureStateMain1.class);

    public static void main(String[] args) {
        showDoneState();
//        showFailState();
//        showCancelState();
//        showCancelStateAndInterrupt();
    }


    /**
     * DONE 상태 확인
     */
    public static void showDoneState() {

        FutureTask<?> future = new FutureTask<>(() -> {
            try {
                log.info("작업 시작");
                sleep(1000);
                log.info("작업 완료");
            } catch (InterruptedException e) {
            }
        }, null);

        Thread thread = new Thread(future);
        thread.start();

        log.info("showDoneState() state = " + future.state());
        while (future.state() == Future.State.RUNNING) {

        }
        log.info("showDoneState() state = " + future.state());
        log.info("showDoneState() isDone = " + future.isDone());
        log.info("showDoneState() isCancelled = " + future.isCancelled());

    }

    /**
     * FAIL 상태 확인
     */
    public static void showFailState() {

        FutureTask<?> future = new FutureTask<>(() -> {
            try {
                log.info("작업 시작");
                sleep(1000);
                throw new RuntimeException("작업 진행중 예외 발생");
            } catch (InterruptedException e) {
            }
        }, null);

        Thread thread = new Thread(future);
        thread.start();

        log.info("showFailState() state = " + future.state());
        while (future.state() == Future.State.RUNNING) {
        }

        log.info("showFailState() state = " + future.state());
        log.info("showFailState() isDone = " + future.isDone());
        log.info("showFailState() isCancelled = " + future.isCancelled());
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

        Thread thread = new Thread(future);
        thread.start();

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

        FutureTask<?> future = new FutureTask<>(() -> {
            try {
                log.info("작업 시작");
                sleep(1000);
                log.info("작업 완료");
            } catch (InterruptedException e) {
                log.info("작업 도중 인터럽트 발생");
            }
        }, null);

        Thread thread = new Thread(future);
        thread.start();

        log.info("showCancelStateAndInterrupt() state = " + future.state());
        while (future.state() == Future.State.RUNNING) {
            future.cancel(true);
        }
        log.info("showCancelStateAndInterrupt() state = " + future.state());
        log.info("showCancelStateAndInterrupt() isDone = " + future.isDone());
        log.info("showCancelStateAndInterrupt() isCancelled = " + future.isCancelled());

    }

}
