package org.example.synchronization;

public class DataSynchronizationTest {
    public static void main(String[] args) throws InterruptedException {
        Sync sync = new Sync();
        sync.run();
//        Non non = new Non();
//        non.run();
    }

    static class Non {
        private int count = 0;
        private int length = 100000;
        public void run() throws InterruptedException {

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < length; i++) {
                    count++;
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < length; i++) {
                    count++;
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("예상 결과 : " + length * 2);
            System.out.println("실제 결과 : " + count);
        }
    }

    static class Sync {
        private int count = 0;
        private int length = 100000;
        public void run() throws InterruptedException {

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < length; i++) {
                    synchronized (Sync.class){
                        count++;
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < length; i++) {
                    synchronized (Sync.class){
                        count++;
                    }
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("예상 결과 : " + length * 2);
            System.out.println("실제 결과 : " + count);
        }
    }
}
