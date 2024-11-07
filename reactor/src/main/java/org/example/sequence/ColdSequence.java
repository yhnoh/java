package org.example.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


public class ColdSequence {

    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.just("A", "B", "C").map(String::toLowerCase);

        coldFlux.subscribe(System.out::println);
        System.out.println("-------------------");
        coldFlux.subscribe(System.out::println);
    }
}
