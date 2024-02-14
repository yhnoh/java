package org.example.synchronization_java;

public class SynchronizedBlockTest {


    static class SynchronizedInstanceBlock {
        private int count = 0;
        private Object lockObject = new Object();
        public void incrementBlockThis(){
            synchronized (this) {
                count++;
                System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. this를 통한 동기화, 현재 값은 : " + count);
            }
        }

        public void incrementBlockLockObject(){
            synchronized (lockObject) {
                count++;
                System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. lockObject를 통한 동기화 현재 값은 : " + count);
            }
        }

        public int getCount() {
            return count;
        }
    }

    static class OtherSynchronizedStackBlock {

    }
    static class SynchronizedStackBlock {
        private static int count = 0;

        public static void incrementBlockThisClass(){
            synchronized (SynchronizedStackBlock.class) {
                count++;
                System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 현재 클래스를 통한 동기화, 현재 값은 : " + count);
            }
        }

        public static void incrementBlockOtherClass(){
            synchronized (OtherSynchronizedStackBlock.class) {
                count++;
                System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 다른 클래스를 통한 동기화 현재 값은 : " + count);
            }
        }

        public static int getCount() {
            return count;
        }
    }

    /**
     * 블록 동기화시 this와 lockObject를 이용하여 동기화를 진행하기 때문에 별도의 모니터 사용
     * 때문에 동시성 문제가 발생한다.
     */
    static void invokeSynchronizedInstanceBlock() throws InterruptedException {
        SynchronizedInstanceBlock logic = new SynchronizedInstanceBlock();
        //this 모니터 사용
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                logic.incrementBlockThis();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                logic.incrementBlockThis();
            }
        });

        //lockObject 모니터 사용
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                logic.incrementBlockLockObject();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                logic.incrementBlockLockObject();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        //동시성 문제 발생
        System.out.println("결과 값 : " + logic.getCount());
    }

    /**
     * 스태틱 블록 동기화시 클래스 타입을 다르게하여 동기화를 진행하기 때문에 별도의 모니터 사용
     * 때문에 동시성 문제가 발생한다.
     */
    static void invokeSynchronizedStackBlock() throws InterruptedException {
        //this 모니터 사용
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStackBlock.incrementBlockThisClass();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStackBlock.incrementBlockThisClass();
            }
        });

        //lockObject 모니터 사용
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStackBlock.incrementBlockOtherClass();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStackBlock.incrementBlockOtherClass();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        //동시성 문제 발생
        System.out.println("결과 값 : " + SynchronizedStackBlock.getCount());
    }



    public static void main(String[] args) throws InterruptedException {
        invokeSynchronizedStackBlock();
    }
}
