package org.example.synchronization;

import static java.lang.Thread.sleep;

public class ThreadSafeTest {


    public static void main(String[] args) throws InterruptedException {
        stackThreadSafeTest2();
    }

    public static void stackThreadSafeTest() throws InterruptedException {

        ThreadSafeTest threadSafeTest = new ThreadSafeTest();

        Thread thread1 = new Thread(() -> {
            threadSafeTest.printNumbers(5);
        });

        Thread thread2 = new Thread(() -> {
            threadSafeTest.printNumbers(10);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    public static void stackThreadSafeTest2() throws InterruptedException {

        ThreadSafeTest threadSafeTest = new ThreadSafeTest();

        Thread thread1 = new Thread(() -> {
            threadSafeTest.increments();
        });

        Thread thread2 = new Thread(() -> {
            threadSafeTest.increments();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }


    public void printNumbers(int plus){

        int localSum = 0;

        for (int i = 0; i < 5; i++) {
            localSum += i;
            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        localSum += plus;
        System.out.println(Thread.currentThread().getName() + " 현재 합계: " + localSum);
    }

    static class LocalObject {
        int value = 0;

        public void increment(){
            value++;
        }

        @Override
        public String toString() {
            return "LocalObject{" +
                    "value=" + value +
                    '}';
        }
    }

    public void increments(){
        LocalObject localObject = new LocalObject();

        for (int i = 0; i < 5; i++) {
            localObject.increment();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Thread.currentThread().getName() + " 현재 합계: " + localObject);
    }

    static class ChangeNameRunnable implements Runnable {

        private ImmutableObject immutableObject;
        private String newName;
        public ChangeNameRunnable(ImmutableObject immutableObject, String newName) {
            this.immutableObject = immutableObject;
            this.newName = newName;
        }

        @Override
        public void run() {
            String name = immutableObject.getName();
            ImmutableObject immutableObject1 = immutableObject.changeName(newName);
            immutableObject1.getName();

            System.out.println("oldName = " + name );
        }
    }
    static final class ImmutableObject {
        private final String name;

        public ImmutableObject(String name) {
            this.name = name;
        }

        public ImmutableObject changeName(String newName){
            return new ImmutableObject(newName);
        }

        public String getName() {
            return name;
        }
    }


}
