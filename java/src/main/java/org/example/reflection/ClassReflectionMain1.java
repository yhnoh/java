package org.example.reflection;

/**
 * 클래스 조회를 위한 방법
 */
public class ClassReflectionMain1 {

    public static void main(String[] args) throws ClassNotFoundException {


        Class<? extends String> retrievingClass1 = "String".getClass();
        Class<?> retrievingClass2 = Class.forName("java.lang.String");
        // 
        Class<?> retrievingClass3 = Class.forName("org.example.reflection.another.DefaultClass");
        Class<String> retrievingClass4 = String.class;
        Class<Double> retrievingClass5 = Double.TYPE;

        System.out.println("retrievingClass1 = " + retrievingClass1);
        System.out.println("retrievingClass2 = " + retrievingClass2);
        System.out.println("retrievingClass3 = " + retrievingClass3);
        System.out.println("retrievingClass4 = " + retrievingClass4);
        System.out.println("retrievingClass5 = " + retrievingClass5);
    }
}
