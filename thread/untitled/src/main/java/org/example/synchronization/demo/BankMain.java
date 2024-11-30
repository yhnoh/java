package org.example.synchronization.demo;

import static java.lang.Thread.sleep;
import static org.example.synchronization.demo.MyLogger.log;

public class BankMain {


    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccountV3(1000);

        Thread t1 = new Thread(new WithdrawTask(account, 800));
        Thread t2 = new Thread(new WithdrawTask(account, 800));
        t1.start();
        t2.start();

        sleep(500);
        log("t1 state : " + t1.getState());
        log("t2 state : " + t2.getState());

        t1.join();
        t2.join();

        log("최종 잔액: " + account.getBalance());
    }
}
