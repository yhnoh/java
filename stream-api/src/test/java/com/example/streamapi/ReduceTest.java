package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReduceTest {

    @Test
    void reduceTest(){
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
}
