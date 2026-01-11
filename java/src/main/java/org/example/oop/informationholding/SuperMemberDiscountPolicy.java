package org.example.oop.informationholding;

// 슈퍼 회원 할인 정책 클래스
public class SuperMemberDiscountPolicy implements MemberDiscountPolicy {

    @Override
    public int getDiscountAmount(int amount) {
        // 20% 할인 정책
        double discountRate = 0.2;
        return (int) (amount * discountRate);
    }
}
