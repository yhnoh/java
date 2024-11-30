package org.example.deadlock;

public class DeadlockTest {

    static class Resource {
        private String name;
        public Resource(String name) {
            this.name = name;
        }

        public void work(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        public String getName() {
            return name;
        }
    }

    static class Worker implements Runnable {
        private Resource resource1;
        private Resource resource2;

        public Worker(Resource resource1, Resource resource2) {
            this.resource1 = resource1;
            this.resource2 = resource2;
        }
        @Override
        public void run() {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() +"이 " + resource1.getName() + "에 대한 작업을 위하여 락을 획득했습니다.");
                resource1.work();

                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() +" 이 " + resource2.getName() + "에 대한 작업을 위하여 락을 획득했습니다.");
                    resource2.work();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Resource resource1 = new Resource("Resource1");
        Resource resource2 = new Resource("Resource2");

        Thread process1 = new Thread(new Worker(resource2, resource1), "Process1");
        Thread process2 = new Thread(new Worker(resource1, resource2), "Process2");

        process1.start();
        process2.start();
    }


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



}
