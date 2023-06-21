package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class CollectTest {

    @Test
    void collectorsTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        List<String> memberNames = members.stream()
                .map(member -> member.getName())
                .collect(Collectors.toList());

        memberNames.stream().forEach(System.out::println);
    }

    @Test
    void collectorsDefineTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        List<String> memberNames = members.stream()
                .map(member -> member.getName())
                .collect(Collectors.toCollection(LinkedList::new));

        memberNames.stream().forEach(System.out::println);
    }

    @Test
    public void joiningTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        String joiningMemberNames = members.stream()
                .map(member -> member.getName())
                .collect(Collectors.joining(", "));

        System.out.println(joiningMemberNames);
    }

    @Test
    public void summingIntTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        Integer sumAge = members.stream()
                .collect(Collectors.summingInt(Member::getAge));

        System.out.println(sumAge);
    }

    @Test
    public void groupingByTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name1", 2);
        Member member3 = new Member("id3", "name2", 3);
        List<Member> members = List.of(member1,member2, member3);

        Map<String, List<Member>> memberMapByName = members.stream()
                .collect(Collectors.groupingBy(Member::getName));

        System.out.println(memberMapByName);
    }

    @Test
    public void groupingByAndSumAgeTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name1", 2);
        Member member3 = new Member("id3", "name2", 3);
        List<Member> members = List.of(member1,member2, member3);

        Map<String, Integer> memberMapByName = members.stream()
                .collect(Collectors.groupingBy(Member::getName, Collectors.summingInt(Member::getAge)));

        System.out.println(memberMapByName);
    }

    @Test
    public void partionByAndSumAgeTest(){
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name1", 2);
        Member member3 = new Member("id3", "name3", 3);
        List<Member> members = List.of(member1,member2, member3);

        Map<Boolean, List<Member>> memberPartitionByAge = members.stream()
                .collect(Collectors.partitioningBy(member -> member.getAge() >= 2));

        System.out.println(memberPartitionByAge);
    }
}
