package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReduceTest {

    @Test
    void etcTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        Member member3 = new Member("id3", "name3", 3);
        Member member4 = new Member("id4", "name4", 4);

        List<Member> members = List.of(member1, member2, member3, member4);

        // 스트림 이전 전동적인 방식
        int ageSum = 0;
        for (Member member : members) {
            ageSum += member.getAge();
        }

        // IntStream 활용
        int intStreamAgeSum = members.stream().mapToInt(Member::getAge).sum();

        // Stream 활용
        int streamAgeSum = members.stream().collect(Collectors.summingInt(Member::getAge));


        System.out.println(ageSum);
        System.out.println(intStreamAgeSum);
        System.out.println(streamAgeSum);

    }

    @Test
    void reduceTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        Member member3 = new Member("id3", "name3", 3);
        Member member4 = new Member("id4", "name4", 4);

        List<Member> members = List.of(member1, member2, member3, member4);


        Integer sum = members.stream().map(Member::getAge).reduce(0, (x, y) -> {
            System.out.println("x = " + x + ", y = " + y);
            return Integer.sum(x, y);
        });

        System.out.println("sum = " + sum);

        /**
         * 결과값
         * x = 0, y = 1
         * x = 1, y = 2
         * x = 3, y = 3
         * x = 6, y = 4
         * sum = 10
         */
    }

    @Test
    void reduceMaxMinTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        Member member3 = new Member("id3", "name3", 3);
        Member member4 = new Member("id4", "name4", 4);

        List<Member> members = List.of(member1, member2, member3, member4);

        Integer max = members.stream().map(Member::getAge).reduce(0, Integer::max);
        Integer min = members.stream().map(Member::getAge).reduce(0, Integer::min);

        System.out.println("max = " + max);
        System.out.println("min = " + min);
    }


    @Test
    void parallelReduceTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        Member member3 = new Member("id3", "name3", 3);
        Member member4 = new Member("id4", "name4", 4);

        List<Member> members = List.of(member1, member2, member3, member4);
        Integer sum = members.parallelStream().map(Member::getAge).reduce(0, Integer::sum);
        Integer max = members.parallelStream().map(Member::getAge).reduce(0, Integer::max);
        Integer min = members.parallelStream().map(Member::getAge).reduce(1, Integer::min);

        System.out.println("sum = " + sum);
        System.out.println("max = " + max);
        System.out.println("min = " + min);

    }
}
