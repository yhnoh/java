package org.example.jvm.memory;

public class StackAreaMain {


    // Stack 영역 메서드 호출 테스트
    // methodA -> methodB -> methodC 호출 순서로 출력
    // Stack 영역에 methodA, methodB, methodC 스택에 쌓임
    // methodC 종료 -> methodB 종료 -> methodA 종료 순서로 스택에서 제거됨
    public static void main(String[] args) {

        StackAreaMain stackAreaMain = new StackAreaMain();
        stackAreaMain.methodA();
    }

    public void methodA() {
        System.out.println("StackAreaMain.methodA 호출");
        methodB();
    }

    public void methodB() {
        System.out.println("StackAreaMain.methodB 호출");
        methodC();
    }

    public void methodC() {
        System.out.println("StackAreaMain.methodC 호출");
    }

}
