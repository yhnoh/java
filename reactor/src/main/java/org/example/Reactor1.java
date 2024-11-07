package org.example;

import reactor.core.publisher.Mono;
import reactor.util.Logger;


import java.util.Locale;

public class Reactor1 {

    public static void main(String[] args) {
        Mono.just("Hello, World!")
                .subscribe(data -> System.out.println("emitted data = " + data),
                        error -> System.out.println("emitted error signal = " + error.getMessage()),
                        () -> System.out.println("emitted complete signal"));
    }
}
