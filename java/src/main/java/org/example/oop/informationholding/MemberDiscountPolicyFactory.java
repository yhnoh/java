package org.example.oop.informationholding;

// 회원 할인 정책 팩토리 클래스
public class MemberDiscountPolicyFactory {

    private final MemberDiscountPolicy normalMemberDiscountPolicy;
    private final MemberDiscountPolicy superMemberDiscountPolicy;

    public MemberDiscountPolicyFactory() {
        normalMemberDiscountPolicy = new NormalMemberDiscountPolicy();
        superMemberDiscountPolicy = new SuperMemberDiscountPolicy();
    }

    public MemberDiscountPolicy getDiscountPolicy(String memberType) {
        if ("NORMAL".equals(memberType)) {
            return normalMemberDiscountPolicy;
        } else if ("SUPER".equals(memberType)) {
            return superMemberDiscountPolicy;
        } else {
            return null;
        }

    }
}
