package org.example.synchronization_technique;

/**
 * 스레드가 공유할 수 있는 자원의 최대치를 한정해서 운영하는 방식이 가능하다.
 */
public class SemaphoreTest {


    public static void main(String[] args) throws InterruptedException {
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        semaphoreTest.synchronizeCountingSemaphore();
    }

    void synchronizeBinarySemaphore() throws InterruptedException {
        SharedData sharedData = new SharedData(new BinarySemaphore());

        Thread thread1 = new Thread(() -> {
            sharedData.sum();
        });

        Thread thread2 = new Thread(() -> {
            sharedData.sum();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("결과 출력: " + sharedData.getSum());
    }

    void synchronizeCountingSemaphore() throws InterruptedException {

        int permits = 10;
        CommonSemaphore semaphore = new CountingSemaphore(permits);
        SharedData sharedData = new SharedData(semaphore);

        int threadCount = 15;

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() ->{
                synchronized(SemaphoreTest.class){
                    sharedData.sum();
                }
            });
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        System.out.println("결과 출력: " + sharedData.getSum());
    }

    private static class SharedData {

        private int sharedValue = 0;
        private CommonSemaphore semaphore;
        public SharedData(CommonSemaphore semaphore) {
            this.semaphore = semaphore;
        }
        public void sum(){
            try{
                //해당 내용 주석 처리시 데이터 동기화가 되지 않음
                semaphore.acquired();
                for (int i = 0; i < 10000000; i++) {
                    sharedValue++;
                }
            } finally {
                //오류 발생시에도 mutex를 release를 하기 위해서 사용
                semaphore.release();
            }
        }
        public int getSum(){
            return sharedValue;
        }
    }


    public interface CommonSemaphore {
        void acquired();

        void release();

    }

    static class CountingSemaphore implements CommonSemaphore {
        private int signal;
        private int permits;

        public CountingSemaphore(int permits) {
            this.permits = permits;
            this.signal = permits;
        }

        @Override
        public synchronized void acquired() {
            while (this.signal == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            this.signal--;
            System.out.println(Thread.currentThread().getName() + " 락 획득, 현재 signal = " + signal);
        }

        @Override
        public synchronized void release() {
            if(this.signal < permits){
                this.signal++;
                this.notify();
                System.out.println(Thread.currentThread().getName() + " 락 해제, 현재 signal = " + signal);
            }
        }
    }

    static class BinarySemaphore implements CommonSemaphore{

        private int signal = 1;

        @Override
        public synchronized void acquired() {
            while (this.signal == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            this.signal = 0;

        }

        @Override
        public synchronized void release() {
            this.signal =  1;
            this.notify();
        }
    }

}
