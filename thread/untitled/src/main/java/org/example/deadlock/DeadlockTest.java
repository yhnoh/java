package org.example.deadlock;

public class DeadlockTest {

    /**
     * 락의 순서를 조정하여 락 획득 및 해제의 순환을 막으면 된다.
     */
    static class Deadlock1 {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();
        private void process1 (){
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "이 lock1를 획득했습니다.");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "이 lock2를 획득했습니다.");
                }
            }

        }

        private void process2 (){
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "이 lock2를 획득했습니다.");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "이 lock1를 획득했습니다.");
                }
            }
        }

        public void runMultiThread() throws InterruptedException {
            // lock을 순환 참조 하게끔 설정
            Thread thread1 = new Thread(() -> {
                this.process1();
            });

            Thread thread2 = new Thread(() -> {
                this.process2();
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();
        }
    }
    /**
     * 락의 순서를 조정하여 락 획득 및 해제의 순환을 막으면 된다.
     */

    static class Deadlock2 {
        private final TemporaryLock lock1 = new TemporaryLock("lock1");
        private final TemporaryLock lock2 = new TemporaryLock("lock2");
        static class TemporaryLock {
            private final String name;

            public TemporaryLock(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return name;
            }
        }
        public void methodWithLocks(TemporaryLock lock1, TemporaryLock lock2){
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "이 " + lock1 + "를 획득했습니다.");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "이 "  + lock2 + "를 획득했습니다.");
                }
            }
        }

        public void runMultiThread() throws InterruptedException {
            // lock을 순환 참조 하게끔 설정
            Thread thread1 = new Thread(() -> {
                this.methodWithLocks(lock1, lock2);
            });

            Thread thread2 = new Thread(() -> {
                this.methodWithLocks(lock2, lock1);
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();
        }
    }

    static class Deadlock3 {

    }


    public static void main(String[] args) throws InterruptedException {
        Deadlock2 deadlock = new Deadlock2();
        deadlock.runMultiThread();
    }

}
