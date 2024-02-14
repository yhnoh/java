package org.example.synchronization_java;

public class SynchronizedMethodTest {

    static class SynchronizedStaticMethod {
        private static int count = 0;

        public static synchronized void increment(){
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 현재 값은 : " + count);
        }

        public static synchronized void decrement(){
            count--;
            System.out.println(Thread.currentThread().getName() + " 가 감소했습니다.. 현재 값은 : " + count);
        }

        public static int getCount() {
            return count;
        }
    }
    static class SynchronizedInstanceMethod {
        private int count = 0;

        public synchronized void increment(){
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 현재 값은 : " + count);
        }

        public synchronized void decrement(){
            count--;
            System.out.println(Thread.currentThread().getName() + " 가 감소했습니다.. 현재 값은 : " + count);
        }

        public int getCount() {
            return count;
        }
    }

    static class SynchronizedStaticInstanceMethod {
        private static int staticCount = 0;
        public synchronized void incrementInstanceCount(){
            staticCount++;
            System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 현재 값은 : " + staticCount);
        }

        public synchronized void decrementInstanceCount(){
            staticCount--;
            System.out.println(Thread.currentThread().getName() + " 가 감소했습니다.. 현재 값은 : " + staticCount);
        }

        public static synchronized void incrementStaticCount(){
            staticCount++;
            System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 현재 값은 : " + staticCount);
        }

        public static synchronized void decrementStaticCount(){
            staticCount--;
            System.out.println(Thread.currentThread().getName() + " 가 감소했습니다.. 현재 값은 : " + staticCount);
        }
        public static int getCount() {
            return staticCount;
        }
    }
    public static void invokeSynchronizedInstanceMethod() throws InterruptedException {
        SynchronizedInstanceMethod logic = new SynchronizedInstanceMethod();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                logic.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                logic.decrement();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("결과 값 : " + logic.getCount());
    }

    public static void staticSynchronizedInstanceMethod() throws InterruptedException {


        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStaticMethod.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStaticMethod.decrement();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("결과 값 : " + SynchronizedStaticMethod.getCount());
    }

    /**
     * 스태틱 메서드와 인스턴스 메서드는 서로 다른 모니터를 활용하고 있기 때문에 동시성 문제가 발생한다.
     *
     */
    public static void invokeSynchronizedStaticInstanceMethod() throws InterruptedException {

        SynchronizedStaticInstanceMethod login = new SynchronizedStaticInstanceMethod();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                login.incrementInstanceCount();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                login.decrementInstanceCount();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStaticInstanceMethod.incrementStaticCount();
            }
        });
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                SynchronizedStaticInstanceMethod.decrementStaticCount();
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

        System.out.println("결과 값 : " + SynchronizedStaticInstanceMethod.getCount());
    }


    public static void main(String[] args) throws InterruptedException {
        invokeSynchronizedStaticInstanceMethod();
    }
}
