package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FilterTest {

    @Test
    void memberFilterTest(){

        List<Member> members = List.of(new Member("id1", "username1", 1),
                new Member("id2", "username2", 2));

        members.stream()
                .filter(member -> member.getName().equals("username1"))
                .forEach(System.out::println);
    }

    @Test
    void memberDistinctTest(){

        List<Member> members = List.of(new Member("id1", "username1", 1),
                new Member("id2", "username2", 2),
                new Member("id2", "username3", 3));

        members.stream()
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    void memberLimitTest(){

        List<Member> members = List.of(new Member("id1", "username1", 1),
                new Member("id2", "username2", 2),
                new Member("id3", "username3", 3));

        members.stream()
                .limit(1)
                .forEach(System.out::println);
    }

    @Test
    void memberSkipTest(){
        List<Member> members = List.of(new Member("id1", "username1", 1),
                new Member("id2", "username2", 2),
                new Member("id3", "username3", 3));

        members.stream()
                .skip(1)
                .forEach(System.out::println);

    }

}
