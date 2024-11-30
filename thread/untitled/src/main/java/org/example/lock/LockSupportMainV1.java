package org.example.lock;

import java.util.concurrent.locks.LockSupport;

import static org.example.synchronization.demo.MyLogger.log;

public class LockSupportMainV1 {

    public static void main(String[] args) {

    }

    static class ParKTest implements Runnable{
        @Override
        public void run() {
            log("park 시작");
            LockSupport.park();
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: " + Thread.currentThread().getState());
        }
    }
}
