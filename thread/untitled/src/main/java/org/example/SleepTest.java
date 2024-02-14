package org.example;

import static java.lang.Thread.*;
import static java.lang.Thread.sleep;

public class SleepTest {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {

            try {
                System.out.println("1초 후에 메시지 출력됩니다.");
                sleep(20000);
                System.out.println("잠에서 깨어났습니다.");
            } catch (InterruptedException e) {
                System.out.println("잠들어 있는동안 인터럽트가 발생하였습니다.");
            }
        });

        thread.start();
        sleep(1000);
        thread.interrupt();

    }

}
