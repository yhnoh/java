package org.example.sequence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class JustTest {

    @Test
    public void helloWorldJustTest() {

        Flux<Integer> sequence = Flux.just(1, 2);
        sequence.subscribe(data -> log.info("emitted data = " + data),
                error -> log.info("emitted error signal = " + error.getMessage()),
                () -> log.info("emitted complete signal"));

//        Flux<Integer> sequence = Flux.just(1, 2)
//                .doOnRequest(value -> log.info("doOnRequest Subscribe Request: " + value))
//                .doOnSubscribe(subscription -> log.info("doOnSubscribe Subscribe Start"))
//                .doOnNext(integer -> log.info("doOnNext Subscribe Data: " + integer))
//                .doOnComplete(() -> log.info("doOnComplete Subscribe Complete"));
//
//        sequence.subscribe(new BaseSubscriber<Integer>() {
//            @Override
//            public void hookOnSubscribe(Subscription subscription) {
//                log.info("hookOnSubscribe Subscribe Start");
//                request(1);
//            }
//
//            @Override
//            public void hookOnNext(Integer value) {
//                log.info("hookOnNext Subscribe Data: " + value);
//                request(1);
//            }
//
//            @Override
//            public void hookOnComplete() {
//                log.info("hookOnComplete Subscribe Complete");
//            }
//        });

    }
}
