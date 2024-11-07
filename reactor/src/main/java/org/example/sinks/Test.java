package org.example.sinks;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.Random;
import java.util.function.Consumer;

@Slf4j
public class Test {

    public static void main(String[] args) {

        Consumer<SynchronousSink<Integer>> generator = new Consumer<>() {
            private int emitCount = 0;
            private Random rand = new Random();

            @Override
            public void accept(SynchronousSink<Integer> sink) {
                //2. 구독자 요청으로 부터 데이터 생성
                emitCount++;
                int data = rand.nextInt(100) + 1; // 1~100 사이 임의 정수
                log.info("Generator sink next " + data);
                sink.next(data); // 임의 정수 데이터 발생
                if (emitCount == 10) { // 10개 데이터를 발생했으면
                    log.info("Generator sink complete");
                    sink.complete(); // 완료 신호 발생
                }
            }
        };

        Flux<Integer> seq = Flux.generate(generator);

        seq.subscribe(new BaseSubscriber<>() {
            private int receiveCount = 0;
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                //1. 구독자가 데이터 요청
                log.info("Subscriber#onSubscribe");
                log.info("Subscriber request first 3 items");
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                //3. 데이터 수신
                log.info("Subscriber#onNext: " + value);
                receiveCount++;
                if (receiveCount % 3 == 0) {
                    log.info("Subscriber request next 3 items");
                    request(3);
                }
            }

            @Override
            protected void hookOnComplete() {
                log.info("Subscriber#onComplete");
            }
        });

    }

}
