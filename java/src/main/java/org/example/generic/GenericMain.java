package org.example.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericMain {

    public static void main(String[] args) {
        NormalClass normalClass = new NormalClass();
        normalClass.setData("Normal String Data");
        String normalData = normalClass.getData();

        GenericClass<String> genericClass = new GenericClass<>();
        genericClass.setData("Generic String Data");
        String data = genericClass.getData();


        List<String> stringList = new ArrayList<>();
        List rawList = stringList;
        rawList.add(1);

//        String s = stringList.get(0);

        List<Integer> integerList = new ArrayList<>();

        if(stringList instanceof List) {
            System.out.println("integerList = " + integerList);
        }

    }

    static class NormalClass {
        private String data;

        public void setData(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }


    static class GenericClass<T> {
        private T data;

        public void setData(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }
}
