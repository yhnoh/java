package org.example.thread.interrupt;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


/**
 * 인터럽트는 대기중인 스레드를 즉시 깨워 Runnable 상태로 만든다.
 * Runnable 상태를 만들어 작업을 진행하거나 중지할 수 있다. 대기 상태를 Runnable로 변경할 수 있는 이유는 스레드 내부에서 인터럽트 상태를 확인하기 때문이다.
 *
 * InterruptException이
 * 인터럽트 예외가 발생한 경우 인터럽트 상태를 true -> false 변경
 * 예외가 밠갱한 이후 다시 false로 변경하지 않으면, 해당 스레드가 계속해서 인터럽트 상태를 유지하게 된다.
 *
 * interrupt(): interrupted 상태 false -> ture 변경,
 * Thread.currentThread().isInterrupted(); : 스레드의 인터럽트 상태 확인
 * Thread.interrupted(); : interrupted 상태가 true인 경우, interrupted 상태를 false로 변경한 이후 결과값을 true로 반환
 *
 * 생성한 스레드에서 InterruptException을 가진 메서드를 호출한 상태에서, 인터럽트가 발생하면 interrupted 상태를 false로 변경한 이후에 InterruptException을 발생시킨다.
 */
public class ThreadStateMain2 {

    public static void main(String[] args) throws InterruptedException {
//        Thread thread = new Thread(() -> {
//            try {
//
//                //RUNNABLE: 스레드가 실행중인 상태
//                System.out.println("Thread State1 = " + currentThread().getState());;
//                System.out.println("Thread isInterrupted1 = " + currentThread().isInterrupted());;
//                sleep(2000);
//            } catch (InterruptedException e) {
//                //인터럽트 발생 이후 WAITING -> RUNNABLE 상태로 변경
//                System.out.println("Thread isInterrupted3 = " + currentThread().isInterrupted());;
//                System.out.println("Thread State3 = " + currentThread().getState());;
//                System.out.println("InterruptedException.getMessage() = " + e.getMessage());
//            }
//
//        });
//
//        thread.start();
//        //스레드 1초 대기 이후 인터럽트 발생
//        sleep(1000);
//        System.out.println("Thread State2 = " + thread.getState());;
//        thread.interrupt();
//        System.out.println("Thread isInterrupted2 = " + thread.isInterrupted());

//        interrupt();
        interrupt3();
    }

    public static void interrupt() throws InterruptedException {

        Thread thread = new Thread(() -> {

            while (!currentThread().isInterrupted()){

            }
            System.out.println("Thread isInterrupted3 = " + currentThread().isInterrupted());

            try{
                sleep(1000);
            }catch (InterruptedException e) {
                System.out.println("InterruptedException 예외 발생 = " + e.getMessage());
                System.out.println("Thread isInterrupted4 = " + currentThread().isInterrupted());
            }

        }, "Thead1");


        thread.start();
        System.out.println("Thread isInterrupted1 = " + thread.isInterrupted());
        sleep(1000);
        thread.interrupt();
        System.out.println("Thread isInterrupted2 = " + thread.isInterrupted());
    }

    public static void interrupt3() throws InterruptedException {

        Thread thread = new Thread(() -> {
            //스레드가 인터럽트 상태이면 true를 반환하고 인터럽트 상태를 false로 변경
            while (!Thread.interrupted()){

            }
            System.out.println("Thread isInterrupted3 = " + currentThread().isInterrupted());

            try{
                sleep(1000);
            }catch (InterruptedException e) {
                System.out.println("InterruptedException 예외 발생 = " + e.getMessage());
                System.out.println("Thread isInterrupted4 = " + currentThread().isInterrupted());
            }

        }, "Thead1");


        thread.start();
        System.out.println("Thread isInterrupted1 = " + thread.isInterrupted());
        sleep(1000);
        thread.interrupt();
        System.out.println("Thread isInterrupted2 = " + thread.isInterrupted());
    }
}
