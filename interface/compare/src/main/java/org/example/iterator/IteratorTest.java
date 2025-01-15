package org.example.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorTest {

    public static void main(String[] args) {

        System.out.println("list for loop get result");
        List<String> list1 = createList();
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i));
        }
        System.out.println();

        System.out.println("iterator cursor get result");
        List<String> list2 = createList();
        Iterator<String> iterator = list2.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println();

        System.out.println("list for loop remove result");
        List<String> list3 = createList();
        for (int i = 0; i < list3.size(); i++) {
            list3.remove(i);
            System.out.println(list3);
        }
        System.out.println();

        System.out.println("iterator cursor get result");
        List<String> list4 = createList();
        Iterator<String> iterator2 = list4.iterator();
        while (iterator2.hasNext()){
            iterator2.next();
            iterator2.remove();
            System.out.println(list4);

        }
        System.out.println();
    }

    private static List<String> createList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        return list;
    }
}
