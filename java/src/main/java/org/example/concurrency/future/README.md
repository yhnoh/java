## java.util.concurrent 고수준 비동기 API 프로그래밍의 필요성
- `Thread`와 `Runnable`을 직접 사용하여 비동기 작업을 수행할 수 있지만 몇가지 단점이 존재한다.
  - 결과 반환의 어려움: `Runnable` 인터페이스의 `void run()` 메서드로는 수행한 작업의 결과를 직접적으로 반환받기가 어려움
  - 예외 처리의 복잡성: 스레드 내에서 발생하는 예외를 처리하기 어려움
  - 스레드 자원 관리의 어려움: 스레드 생성 및 종료을 직접 관리, 스레드 갯수 제한의 어려움, 작업중인 스레드 취소의 어려움
- Java 5부터 제공되는 `java.util.concurrent` 패키지의 `ExecutorService`와 `Future`를 사용하면 이러한 단점을 해결할 수 있다.

## Future
- `Future`는 비동기 작업의 결과를 가져올수 있는 인터페이스로써, `Thread`와 `Runnable`을 사용하여 비동기 작업을 수행할 때 존재하는 몇가지 단점을 해소해준다.
  - 작업의 결과 반환 가능
  - 예외 처리 가능
  - 작업의 상태 확인 가능


### 작업의 결과 반환 가능
- `Future`는 `Callable` 인터페이스를 사용하여 비동기 작업을 수행하고, `get()` 메서드를 통해 ***작업의 결과를 반환받을 수 있다.***
  - `Callable` 인터페이슨는 `Runnable`과 유사하지만, `call()` 메서드를 통해 작업의 결과를 반환한다.
  - 만약 `Thread`와 `Runnable`을 사용하여 동시에 많은 API 요청을 보내거나 동시에 많은 계산 작업을 한 이후에 해당 결과를 취합하여 클라이언트에게 전달해야하는경우 코드가 복잡해질 수 있기 때문에 `Future`를 사용하면 이러한 작업을 간단하게 처리할 수 있다.

[](./FutureMain1.java)

#### 작업의 결과 반환 예시
```java

```



## ExecutorService와 Future
- `Thread`와 `Runnable`을 직접 사용하여 비동기 작업을 수행할 수 있지만 몇가지 단점이 존재한다.
  - 스레드 자원 관리의 어려움: 스레드 생성 및 종료을 직접 관리, 스레드 갯수 제한의 어려움, 작업중인 스레드 취소의 어려움
  - 예외 처리의 복잡성: 스레드 내에서 발생하는 예외를 처리하기 어려움
  - 결과 반환의 어려움: `Runnable` 인터페이스의 `void run()` 메서드로는 수행한 작업의 결과를 직접적으로 반환받기가 어려움

- `java.util.concurrent.Future`는 비동기 작업의 결과를 가져올 수 있다.
  - Futrue

- Executor
- ExecutorService
- Runnable vs Callable

### Future 실행해보기


> https://mangkyu.tistory.com/259
> https://www.baeldung.com/java-future