package org.example;

import java.text.MessageFormat;

import static java.lang.Thread.onSpinWait;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        Thread thread = new Thread(() -> {
            //RUNNABLE 상태
            printThread(Thread.currentThread());
            long startTimeMills = System.currentTimeMillis();
            synchronized (lock){
                while (true) {
                    long endTimeMills = System.currentTimeMillis();
                    if (endTimeMills - startTimeMills > 3000){
                        break;
                    }
                }
            }

//            onSpinWait();
//
//            try {
//                //WATTING: 대기 상태를 위해서 sleep 주기
//                sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        });

        //NEW 상태 : Thread 생성 단계이고 실행 단계가 아니다.
        printThread(thread);
        thread.start();
        while (true){

            Thread.State state = thread.getState();
            if(state == Thread.State.WAITING){
                printThread(thread);
                break;
            }

            if(state == Thread.State.TIMED_WAITING){
                printThread(thread);
                break;
            }

        }


        printThread(Thread.currentThread());
    }

    private static void printThread(Thread thread) {
        String message = MessageFormat.format("스레드 이름 = {0}, 스레드 상태 = {1}", thread.getName(), thread.getState());
        System.out.println(message);
    }
}

