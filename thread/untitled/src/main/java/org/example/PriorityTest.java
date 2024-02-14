package org.example;


/**
 * 자바 런타임은 고정 우선순위 선점형 스케쥴링 (fixed-priority pre-emptive scheduling)으로 알려진 매우 단순한 스케쥴링 알고리즘을 지원한다.
 * 해당 알고리즘은 실행 대기 상태의 스레드 중에 상대적인 우선 순위에 따라 스레드를 예약한다.
 *
 * 하지만 스케쥴러가 반드시 우선순위가 높은 스레드를 실행한다고 보장할 수 없다.
 * 운영체제마다 다른 정책들이 있을 수 있으며 기아상태를 피하기 위해 스케줄러는 우선순위가 낮은 스레드를 선택할 수 있다.
 * 예를 들어서 높은 스레드들만 계속 할당된다면 우선순위가 낮은 스레드들은 실행시킬 수 없을 것이다.
 * 때문에 우선순위가 낮은 스레드가 기아상태에 빠지는 것을 방지하기 위하여 우선순위가 낮은 스레드를 선택한다.
 */
public class PriorityTest {

    /**
     * 우선순위를 확인하기 위한 예제를 만들었지만 운영체제의 CPU 스케쥴링의 영향을 받기 때문에 실제로 설정한 우선순위대로 작업을 완료하지 않는다.
     */
    public static void main(String[] args) throws InterruptedException {
        PriorityTest priorityTest = new PriorityTest();
        priorityTest.countThread();

    }

    public void hasPriorityThread(){
        Thread minPriorityThread = new Thread(() -> {
            System.out.println("Thread = " + Thread.currentThread());
        });
        minPriorityThread.setName("minPriorityThread");
        minPriorityThread.setPriority(Thread.MIN_PRIORITY);
        minPriorityThread.start();

        Thread maxPriorityThread = new Thread(() -> {
            System.out.println("Thread = " + Thread.currentThread());
        });
        maxPriorityThread.setName("maxPriorityThread");
        maxPriorityThread.setPriority(Thread.MAX_PRIORITY);
        maxPriorityThread.start();

        Thread defaultPriorityThread = new Thread(() -> {
            System.out.println("Thread = " + Thread.currentThread());
        });
        defaultPriorityThread.setName("defaultPriorityThread");
        defaultPriorityThread.start();
    }
    public void countThread() throws InterruptedException {

        CountingThread minPriorityThread = new CountingThread("우선순위가 낮은 스레드", Thread.MIN_PRIORITY);
        CountingThread normPriorityThread = new CountingThread("우선순위가 기본 스레드", Thread.NORM_PRIORITY);
        CountingThread maxPriorityThread = new CountingThread("우선순위가 높은 스레드", Thread.MAX_PRIORITY);

        minPriorityThread.start();
        normPriorityThread.start();
        maxPriorityThread.start();

        minPriorityThread.join();
        normPriorityThread.join();
        maxPriorityThread.join();

        System.out.println("작업 종료");
    }

    static class CountingThread extends Thread{

        private final String threadName;
        private int count = 0;

        public CountingThread(String threadName, int priority) {
            this.threadName = threadName;
            super.setPriority(priority);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000000; i++) {
                if(i % 1000000 == 0) {
                    System.out.println(threadName + "이 작업진행중....");
                }
                count++;
            }

            System.out.println(threadName + "이 작업을 완료하였습니다.");
        }
    }

}
