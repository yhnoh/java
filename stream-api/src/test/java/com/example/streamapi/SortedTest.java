package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

public class SortedTest {



    @Test
    void sortedWhenExceptionTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        members.stream().sorted().forEach(System.out::println);


    }

    @Test
    void comparatorComparingTest(){

        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        //오름차순
        members.stream()
                .sorted(Comparator.comparing(Member::getAge))
                .forEach(System.out::println);
        //내림차순
        members.stream()
                .sorted(Comparator.comparing(Member::getAge, (o1, o2) -> Integer.compare(o2, o1)))
                .forEach(System.out::println);

    }
}
