package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class CreateStreamTest {

    @Test
    void streamOfTest(){

        Stream.of(new Member("id1", "name1", 1),
                new Member("id2", "name2", 2),
                new Member("id3", "name3", 3))
                .forEach(System.out::println);
    }

    @Test
    void streamBuilderTest(){
        Stream<Member> streamBuilder = Stream.<Member>builder()
                .add(new Member("id1", "name1", 1))
                .add(new Member("id2", "name2", 2))
                .add(new Member("id3", "name3", 3)).build();

        streamBuilder.forEach(System.out::println);
    }

    @Test
    void arraysStreamTest(){

        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        Member member3 = new Member("id3", "name3", 3);
        Member[] members = {member1, member2, member3};

        Arrays.stream(members)
                .forEach(System.out::println);
    }

    @Test
    void arraysParallelSortTest(){

        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        Member member3 = new Member("id3", "name3", 3);
        Member[] members = {member1, member2, member3};

        Arrays.parallelSort(members, Comparator.comparing(Member::getAge).reversed());

        Arrays.stream(members)
                .forEach(System.out::println);
    }

    @Test
    void arraysParallelPrefixTest(){

        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        Member member3 = new Member("id3", "name3", 3);
        Member[] members = {member1, member2, member3};

        Arrays.parallelPrefix(members, (member, member21) ->
                new Member(member.getId(), member.getName(), member.getAge() + member21.getAge()));

        Arrays.stream(members)
                .forEach(System.out::println);
    }

}
