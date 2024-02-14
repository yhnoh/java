package org.example.async_framwork;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class CallableTest {

    interface Callback {
        void onComplete(int result);
    }
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        System.out.println("비동기 작업 시작");

        executorService.execute(() -> {
            System.out.println("비동기 작업 진행중...");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 42;
            Callback myCallback = new MyCallback();
            myCallback.onComplete(result);
        });

        executorService.shutdown();
    }

    static class MyCallback implements Callback {

        @Override
        public void onComplete(int result) {
            System.out.println("비동기 작업 결과 : " + result);
        }
    }
}
