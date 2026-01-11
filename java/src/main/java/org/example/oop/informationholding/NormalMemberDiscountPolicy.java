package org.example.oop.informationholding;

// 일반 회원 할인 정책 클래스
public class NormalMemberDiscountPolicy implements MemberDiscountPolicy {

    @Override
    public int getDiscountAmount(int amount) {
        // 10% 할인 정책
        double discountRate = 0.1;
        return (int) (amount * discountRate);
    }
}
