package org.example.sequence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class JustTest {


    @Test
    public void justTest() {

        Flux<Integer> sequence = Flux.just(1, 2)
                .doOnSubscribe(subscription -> log.info("doOnSubscribe Subscribe Start"))
                .doOnRequest(request -> log.info("doOnRequest Subscribe Request: " + request))
                .doOnNext(data -> log.info("doOnNext Subscribe Data: " + data))
                .doOnComplete(() -> log.info("doOnComplete Subscribe Complete"))
                .doOnError(throwable -> log.error("doOnError Subscribe Error: " + throwable));

        sequence.subscribe(data -> log.info("subscribe data: " + data));


//        sequence.subscribe()

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
