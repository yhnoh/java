package org.example.concurrency.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

import static java.lang.Thread.sleep;

public class ExecutorMain1 {

    private final static Logger log = LoggerFactory.getLogger(ExecutorMain1.class);

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            try {
                log.info("작업 시작");
                sleep(1000);
                log.info("작업 종료");
            } catch (InterruptedException e) {

            }
        };
        Task task = new Task();
        task.execute(runnable);
        
        sleep(2000); // 메인 스레드가 종료되지 않도록 잠시 대기
    }

    private static class Task implements Executor {

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

}
