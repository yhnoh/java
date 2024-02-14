package org.example.synchronization_java;

public class SynchronizedTest {

    private int instanceCount = 0;

    private static int staticCount = 0;

    public synchronized void instanceMethod(){
        instanceCount++;
        staticCount++;
        System.out.println("인스턴스 메서드 동기화: " + instanceCount);
    }

    public static synchronized void staticMethod(){
        staticCount++;
        System.out.println("정적 메서드 동기화: " + staticCount);
    }

    public void instanceBlock(){
        synchronized (this){
            staticCount++;
            instanceCount++;
            System.out.println("인스턴스 블록 동기화: " + instanceCount);
        }
    }

    public static void staticBlock(){
        synchronized (SynchronizedTest.class){
            staticCount++;
            System.out.println("정적 블록 동기화: " + staticCount);
        }

    }

    /**
     * 모니터가 다르면 동시성 문제가 발생할 수 있다.
     */
    public static void main(String[] args) throws InterruptedException {

        SynchronizedTest logic = new SynchronizedTest();

        new Thread(logic::instanceMethod).start();
        new Thread(() -> logic.staticMethod()).start();
        new Thread(logic::instanceBlock).start();
        new Thread(() -> logic.staticBlock()).start();

    }
}
