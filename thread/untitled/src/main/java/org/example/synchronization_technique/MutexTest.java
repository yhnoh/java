package org.example.synchronization_technique;

import java.util.concurrent.atomic.AtomicBoolean;

public class MutexTest {

    public static void main(String[] args) throws InterruptedException {

        SharedDate sharedDate = new SharedDate(new Mutex());

        Thread thread1 = new Thread(() -> {
            sharedDate.sum();
        });

        Thread thread2 = new Thread(() -> {
            sharedDate.sum();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("결과 출력: " + sharedDate.getSum());
    }

    static class Mutex{
        private boolean lock;
        public synchronized void acquired(){
            //락이 있을 경우 스레드 대기
            while (lock){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.lock = true;
        }

        public synchronized void release(){
            //학 해제 이후 스레드 깨우기
            this.lock = false;
            this.notify();
        }

    }

    private static class SharedDate {

        private int sharedValue = 0;
        private Mutex mutex;
        public SharedDate(Mutex mutex) {
            this.mutex = mutex;
        }
        public void sum(){
            try{
                //해당 내용 주석 처리시 데이터 동기화가 되지 않음
                mutex.acquired();
                for (int i = 0; i < 10000000; i++) {
                    sharedValue++;
                }
            } finally {
                //오류 발생시에도 mutex를 release를 하기 위해서 사용
                mutex.release();
            }
        }
        public int getSum(){
            return sharedValue;
        }
    }
}
