package org.example.concurrency.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;


/**
 * <p>Future 예제</p>
 * Callable을 사용하여 Future를 생성한 이후 작업의 결과물을 가져오는 예제 <br/>
 */
public class FutureMain1 {

    private static final Logger log = LoggerFactory.getLogger(FutureMain1.class);

    public static void main(String[] args) {

        Callable<String> callable = () -> {
            log.info("Callable Task is running");
            sleep(1000);
            return "Callable Task Completed";
        };

        FutureTask<String> future = new FutureTask<>(callable);
        Thread thread = new Thread(future);

        thread.start();
        String result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("result = {}", result);
    }

}
