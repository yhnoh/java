package org.example.synchronization_java;


import java.util.LinkedList;
import java.util.Queue;

class SharedQueue {

    private int capacity = 5;
    private Queue<Integer> queue = new LinkedList<>();

    private final Object lock = new Object();

    public void consume() throws InterruptedException {
        synchronized (lock){
            while (queue.isEmpty()){
                System.out.println("큐가 비었습니다.");
                lock.wait();
            }

            System.out.println(Thread.currentThread().getName() + ": " + queue.poll());
            lock.notifyAll();
        }
    }

    public void produce(int item) throws InterruptedException {
        synchronized (lock){
            while (queue.size() == capacity){
                System.out.println("큐가 가득 찼습니다.");
                lock.wait();
            }

            queue.add(item);
            System.out.println(Thread.currentThread().getName() + ": " + item);
            lock.notifyAll();
        }
    }
}

public class WaitNotifyTest {


    public static void main(String[] args) {
        SharedQueue sharedQueue = new SharedQueue();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    sharedQueue.produce(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "생산자").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    sharedQueue.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "소비자").start();
    }

}
