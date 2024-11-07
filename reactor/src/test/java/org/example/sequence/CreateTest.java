package org.example.sequence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CreateTest {

    @Test
    public void creatTest() {

//        Flux<Integer> flux = Flux.create((FluxSink<Integer> sink) -> {
//            sink.onRequest(request -> { // request는 Subscriber가 요청한 데이터 개수
//                for (int i = 1; i <= request; i++) {
//                    log.info("emitted data = " + i);
//                    sink.next(i); // Flux.generate()의 경우와 달리 한 번에 한 개 이상의 next() 신호 발생 가능
//                }
//
//            });
//        }).doOnSubscribe(subscription -> {
//            log.info("doOnSubscribe Subscribe Start");
//        }).doOnNext(data -> {
//            log.info("doOnNext Subscribe Data: " + data);
//        }).doOnComplete(() -> {
//            log.info("doOnComplete Subscribe Complete");
//        });

    }
}
