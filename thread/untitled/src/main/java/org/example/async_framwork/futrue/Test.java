package org.example.async_framwork.futrue;

public class Test {

    public static void main(String[] args) {
//        int i = 2;
        Test2 test = new Test2();
        changeValue(test);
        System.out.println(test.value);
    }

    public static class Test2{
        int value = 2;
    }

    public static void changeValue(Test2 test2){
        test2.value = 3;
    }

    public static void changeValue(int i){
        i = 3;
    }

    public static void changeValue(Integer i){
        i = 3;
    }
}
