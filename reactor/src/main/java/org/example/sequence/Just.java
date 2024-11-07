package org.example.sequence;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class Just {

    public static void main(String[] args) {
        Flux<Integer> sequence = Flux.just(1, 2)
                .doOnRequest(value -> log.info("doOnRequest Subscribe Request: " + value))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe Subscribe Start"))
                .doOnNext(integer -> log.info("doOnNext Subscribe Data: " + integer))
                .doOnComplete(() -> log.info("doOnComplete Subscribe Complete"));

        sequence.subscribe(new BaseSubscriber<Integer>() {
            @Override
            public void hookOnSubscribe(Subscription subscription) {
                log.info("hookOnSubscribe Subscribe Start");
                request(1);
            }

            @Override
            public void hookOnNext(Integer value) {
                log.info("hookOnNext Subscribe Data: " + value);
                request(1);
            }

            @Override
            public void hookOnComplete() {
                log.info("hookOnComplete Subscribe Complete");
            }
        });
    }
}
