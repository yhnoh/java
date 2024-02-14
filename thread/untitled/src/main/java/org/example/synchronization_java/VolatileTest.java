package org.example.synchronization_java;

public class VolatileTest {

    private volatile boolean running = true;

    /**
     * volatile 키워드가 없으면 캐시 메모리에서 데이터를 읽어들임으로 인해서
     * Thread2가 데이터를 변경하더라도 Thread1은 종료되지 않는데
     *
     * 때문에 volatile 키워드를 사용하여 Thread1, Thread2가 캐시 메모리가 아닌 메인메모리에서 데이터를
     * 가져오고 쓰도록 해야지 Thread1이 종료된다.
     */
    public void volatileTest(){
        new Thread(() -> {
            int count = 0;
            while (running) {
                count++;
            }
            System.out.println("Thread 1 종료 Count: " + count);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread 2 종료");
            running = false;
        }).start();
    }

    class VolatileConcurrency {
        private volatile int counter;

        public void increment(){
            counter++;}

        public int getCounter() {
            return counter;
        }

        public void runMultiThread(){
            new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    this.increment();
                }
            });


        }
    }


    public static void main(String[] args) {
        VolatileTest volatileTest = new VolatileTest();
        volatileTest.volatileTest();
    }
}
