package org.example.sequence;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotSequence {

    public static void main(String[] args) throws InterruptedException {
        Flux<String> hotFlux = Flux.just("A", "B", "C", "D", "E")
                .delayElements(Duration.ofSeconds(1))
                .share();

        /**
         * 데이터를 subscribe 할 때마다 스레드가 다른것을 확인
         */
        hotFlux.subscribe(data -> {
            System.out.println("[" + Thread.currentThread().getName() + "] Subscriber 1 data: " + data);
        });
        Thread.sleep(2500);
        hotFlux.subscribe(data -> {
            System.out.println("[" + Thread.currentThread().getName() + "] Subscriber 2 data: " + data);
        });
        Thread.sleep(3000);
    }

}
