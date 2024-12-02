package org.example.synchronization.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.synchronization.demo.MyLogger.log;

public class BankAccountV6 implements BankAccount{

    private int balance = 0;

    private final Lock lock = new ReentrantLock();
    public BankAccountV6(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean withDraw(int amount){

        log("거래 시작: " + getClass().getSimpleName());

        try {
            if(!lock.tryLock(500, TimeUnit.MILLISECONDS)){
                log("[진인 실패] 이미 처리중인 작업이 있습니다.");
                return false;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        try{
            log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }

            // 잔고가 출금액 보다 많으면, 진행
            log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            balance = balance - amount;
            log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
        } finally {
            lock.unlock();
        }

        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
