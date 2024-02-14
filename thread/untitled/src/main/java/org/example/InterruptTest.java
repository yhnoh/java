package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        InterruptTest interruptTest = new InterruptTest();
        interruptTest.isInterrupted();
    }

    public void isInterrupted() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true){
                System.out.println("thread was running");
                //interrupted 상태 확인을 한다.
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("thread state = " + Thread.currentThread().getState());
                    System.out.println("thread2 is interrupted");
                    break;
                }
            }

            System.out.println("isInterrupted = " + Thread.currentThread().isInterrupted());
        });

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

    }

    public void interrupted() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true){
                System.out.println("thread was running");
                //interrupted 상태 확인 뿐 아니라 상태를 false로 변경한다.
                if(Thread.interrupted()){
                    System.out.println("thread is interrupted");
                    break;
                }
            }

            System.out.println("isInterrupted = " + Thread.currentThread().isInterrupted());
        });

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }


    public void test() throws InterruptedException {
        Thread thread = new Thread(() -> {
            //Interrupted 되지 않은 상태이기 때문에 isInterrupted = false
            System.out.println("interrupted state1 = " + Thread.currentThread().isInterrupted());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                //InterruptedException 발생이후 상황이기 때문에 isInterrupted = false
                System.out.println("thread is interrupted");
                System.out.println("interrupted state2 = " + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
            }
        });

        thread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        thread.interrupt();
        thread.join();
        //InterruptedException 발생 interrupt 메서드 사용 이후 해당 스레드 interrupt 상태 획인
        System.out.println("interrupted state3 = " + thread.isInterrupted());
    }
}
