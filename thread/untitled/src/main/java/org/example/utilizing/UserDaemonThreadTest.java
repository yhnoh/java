package org.example.utilizing;

import static java.lang.Thread.sleep;

public class UserDaemonThreadTest {

    public static void main(String[] args) throws InterruptedException {

        UserDaemonThreadTest userDaemonThreadTest = new UserDaemonThreadTest();
        userDaemonThreadTest.test();
    }

    public void startUserThread(){
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("user thread is running");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("user thread is stopping");
        });

        thread.start();
        System.out.println("main thread is stopping");
    }


    public void startDaemonThread(){}{
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    sleep(3000);
                    System.out.println("daemon thread is running");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("main thread is stopping");
    }

    public void test() throws InterruptedException {
        Thread userThread = new Thread(() -> {
            new Thread(() -> {
                System.out.println("user child thread daemon state = " + Thread.currentThread().isDaemon());
            }).start();
            System.out.println("user thread daemon state = " + Thread.currentThread().isDaemon());
        });

        Thread daemonThread = new Thread(() -> {
            new Thread(() -> {
                System.out.println("daemon child thread daemon state = " + Thread.currentThread().isDaemon());
            });
            System.out.println("daemon thread state = " + Thread.currentThread().isDaemon());
        });
        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();

        sleep(1000);
        System.out.println("main thread daemon state = " + Thread.currentThread().isDaemon());
    }
}
