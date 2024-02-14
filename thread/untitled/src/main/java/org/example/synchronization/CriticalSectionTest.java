package org.example.synchronization;

public class CriticalSectionTest {


    public static void main(String[] args) throws InterruptedException {
        CriticalSectionTest criticalSectionTest = new CriticalSectionTest();
        criticalSectionTest.raceCondition();
    }

    void synchronizedSharedResource() throws InterruptedException {
        SharedResource sharedResource = new SharedResource();
        Thread thread1 = new Thread(sharedResource::increment);
        Thread thread2 = new Thread(sharedResource::increment);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

    void raceCondition() throws InterruptedException {
        RaceCondition raceCondition = new RaceCondition();
        Thread thread1 = new Thread(raceCondition::increment);
        Thread thread2 = new Thread(raceCondition::increment);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

    static class SharedResource {
        public int counter = 0;

        public void increment(){
            for (int i = 0; i < 1000000; i++) {
                synchronized (this){    //Entry Section
                    //Critical Section
                    counter++;
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
                } //Exit Section
            }
        
            // Remained Section : 클리티컬 섹션 영역 자체가 없는 영역

        }
    }

    static class RaceCondition {
        public int counter = 0;

        public void increment(){
            for (int i = 0; i < 1000000; i++) {
                    counter++;
                    System.out.println(Thread.currentThread().getName() + ": " + counter);
            }
        }
    }
}


