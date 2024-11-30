package org.example.synchronization.demo;

import static org.example.synchronization.demo.MyLogger.log;

public class BankAccountV1 implements BankAccount{

    private int balance = 0;

    public BankAccountV1(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean withDraw(int amount) {

        log("거래 시작: " + getClass().getSimpleName());

        log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
        if (balance < amount) {
            log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
            return false;
        }

        // 잔고가 출금액 보다 많으면, 진행
        log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);

        balance = balance - amount;
        log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);

        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}
