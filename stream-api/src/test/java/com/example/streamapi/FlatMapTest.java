package com.example.streamapi;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FlatMapTest {

    @Test
    void notFlatMapTest(){
        String[][] rawData = {
                {"a", "b"},{"c", "d"},{"e", "f"}
        };

        String flatData = Arrays.stream(rawData)
                .map(data -> Arrays.stream(data)
                        .collect(Collectors.joining()))
                .map(data -> data.replace("a", ""))
                .collect(Collectors.joining());

        System.out.println(flatData);
    }

    @Test
    void flatMapTest(){
        String[][] rawData = {
                {"a", "b"},{"c", "d"},{"e", "f"}
        };

        String flatData = Arrays.stream(rawData)
                .flatMap(Arrays::stream)
                .filter(data -> data.equals("a") ? false : true)
                .collect(Collectors.joining());

        System.out.println(flatData);
    }
}
