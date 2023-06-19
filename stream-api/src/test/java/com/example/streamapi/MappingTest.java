package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.List;

public class MappingTest {

    @Test
    void mapToIntTest(){

        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        members.stream().mapToInt(Member::getAge).forEach(System.out::println);

    }
}
