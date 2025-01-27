package org.example.thread;

import static java.lang.Thread.*;


/**
 * 스레드는 총 6가지 상태를 가진다.
 * 1. NEW: 스레드가 생성 되고 아직 실행되지 않은 상태
 * 2. RUNNABLE: 스레드가 실행중인 상태
 * 3. BLOCKED: 스레드가 동기화 블록에 의해 대기하는 상태
 * 4. WAITING: 스레드가 다른 스레드가 통지할 때까지 대기하는 상태
 * 5. TIMED_WAITING: 스레드가 일정 시간 동안 대기하는 상태
 * 6. TERMINATED: 스레드가 실행을 마친 상태
 */
public class ThreadStateMain1 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                //RUNNABLE: 스레드가 실행중인 상태
                System.out.println("Thread State2 = " + currentThread().getState());;
                sleep(2000);
                //RUNNABLE: 스레드가 실행중인 상태
                System.out.println("Thread State4 = " + currentThread().getState());;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //NEW: 스레드가 생성 되고 아직 실행되지 않은 상태
        System.out.println("Thread State1 = " + thread.getState());
        thread.start();
        //2초동안 작업하는 스레드의 상탱를 확인하기 위하여, 1초 대기하여 스레드가 현재 어떤 상태인지 확인한다.
        sleep(1000);
        //TIMED_WAITING: 스레드가 일정 시간 동안 대기하는 상태
        System.out.println("Thread State3 = " + thread.getState());
        sleep(2000);
        //TERMINATED: 스레드가 실행을 마친 상태
        System.out.println("Thread State5 = " + thread.getState());
    }
}
