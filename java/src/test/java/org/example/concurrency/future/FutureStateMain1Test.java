package org.example.concurrency.future;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

class FutureStateMain1Test {

    private final Logger log = LoggerFactory.getLogger(FutureStateMain1Test.class);

    @Test
    public void test() {

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


        System.out.println(future.cancel(false));
    }
}