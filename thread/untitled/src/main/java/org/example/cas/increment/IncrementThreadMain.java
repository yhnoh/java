package org.example.cas.increment;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class IncrementThreadMain {

    private static final int THREAD_COUNT = 1000;

    public static void main(String[] args) throws InterruptedException {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }


    private static void test(IncrementInteger incrementInteger) throws InterruptedException {

        Runnable runnable = () -> {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            incrementInteger.increment();
        };

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = threads.get(i);
            thread.join();
        }

        int result = incrementInteger.get();
        System.out.println(incrementInteger.getClass().getSimpleName() + " : " + result);
    }
}
