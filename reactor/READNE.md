### Reactor 구성요소

#### Publisher

```java
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
```

#### Subscriber

```java
public interface Subscriber<T> {

    void onSubscribe(Subscription s);

    void onNext(T t);

    void onError(Throwable t);


```

#### Subscription

```java
public interface Subscription {

    void request(long n);

    void canc
```

#### Processor

```java
public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
}
```

### Reactor를 이용한

- Reactor는 Publisher와 Subscriber를 이용한 리액티브 프로그래밍을 지원하는 라이브러리이다.
- Subscriber로 부터 request(요청) 시그널을 받아서 Publisher로 부터 데이터를 전달받는다.
- Java Stream은 Publisher가 Subscriber에게 데이터를 제공하지만, Reactor는 Subscriber가 Publisher에게 데이터 요청 시그널을 통해서 데이터를 전닯받는다.

### 리액터

- 구독자의 요청으로 인하여 생성자가 데이터를 전달한다.
    - 이전 프로그래밍의 경우에는 생성자가 데이터 생성을 완료하면 구독을 할 수 있다.
    - 마치 메시지 큐와 같은 형식이다. 구독자가 데이터를 전달받을 수 있는 상황에 데이터를 전달받게 된다.
    -

> https://projectreactor.io/docs/core/release/reference/#intro-reactive
> https://projectreactor.io/docs/core/release/reference/
> https://javacan.tistory.com/category/Reactive
> https://devocean.sk.com/blog/techBoardDetail.do?ID=165099&boardType=techBlog#none