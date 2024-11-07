package org.example.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.function.Consumer;

public class Backpressure {

    public static void main(String[] args) {

        /**
         * Subscriber를 통해서 처리 가능한 만큼의 request 갯수를 조절하는 Backpressure
         */
/*
        Flux.range(1, 5)
                .doOnNext(data -> System.out.println("[" + Thread.currentThread().getName() + "] doOnNext: " + data))
                .doOnRequest(data -> System.out.println("[" + Thread.currentThread().getName() + "] doOnRequest: " + data))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        request(1);
                    }

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }
                });
*/
//        int count = 0;
//        Flux.range(1, 5)
//                .doOnNext(data -> System.out.println("[" + Thread.currentThread().getName() + "] doOnNext: " + data))
//                .doOnRequest(data -> System.out.println("[" + Thread.currentThread().getName() + "] doOnRequest: " + data))
//                .subscribe(new BaseSubscriber<>() {
//                    @Override
//                    protected void hookOnNext(Integer value) {
//                        request(2);
//                    }
//
//                    @Override
//                    protected void hookOnSubscribe(Subscription subscription) {
//
//
//                    }
//                });

    }
}
