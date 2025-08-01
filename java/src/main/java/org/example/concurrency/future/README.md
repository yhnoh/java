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
  - 작업 상태 관리 가능


### 작업의 결과 반환 가능
- `Future`는 `Callable` 인터페이스를 사용하여 비동기 작업을 수행하고, `get()` 메서드를 통해 ***작업의 결과를 반환받을 수 있다.***
  - `Callable` 인터페이슨는 `Runnable`과 유사하지만, `call()` 메서드를 통해 작업의 결과를 반환한다.
  - 만약 `Thread`와 `Runnable`을 사용하여 동시에 많은 API 요청을 보내거나 동시에 많은 계산 작업을 한 이후에 해당 결과를 취합하여 클라이언트에게 전달해야하는경우 코드가 복잡해질 수 있기 때문에 `Future`를 사용하면 이러한 작업을 간단하게 처리할 수 있다.

> [작업의 결과 반환 가능 예제](./FutureResultMain1.java)

### 예외 처리 가능
- `Future`는 ***작업 중에 발생한 예외를 핸들링하여 후처리를 할 수 있다.***
  - `Thread`와 `Runnable`을 사용하여 비동기 작업을 수행할 때 예외가 발생할 경우, 해당 예외 처리를 하기위한 별도의 코드를 작성헤야하며 이는 코드의 가독성을 떨어뜨리고 유지보수를 어렵게 만든다. 
- `Future`를 사용하면 `get()` 메서드를 호출한 이후 작업 내부에서 발생한 예외를 `ExecutionException`으로 감싸서 반환한다. 이를 통해서 ***작업 실행과 관련된 코드와 에외 처리 코드를 분리할 수 있는 장점***이 있다.

> [예외 처리 가능 예제](./FutureExceptionMain1.java)

### 작업 상태 관리 가능
- `Future`는 비동기 ***작업의 상태를 관리하거나 확인할 수 있는 메서드를 제공하며, 내부적으로 작업의 상태를 관리***한다.
- `Future` 내부적으로 작업의 상태를 관리하기 때문에 ***스레드의 상태와 분리되어 작업의 상태를 관리할 수 있는 매커니즘을 제공***한다.
  - 예를 들어 작업의 생명 주기와 스레드의 생명 주기를 분리함으로써, 작업이 완료된 이후에도 스레드가 계속해서 사용 가능한 상태로 남아있을 수 있다. 이는 스레드를 지속적으로 생성하지 않고 재사용할 수 있는 장점을 제공한다. (스레드풀)

#### 작업의 상태
- `RUNNING`: 작업이 현재 실행 중인 상태
- `SUCCESS`: 작업이 성공적으로 완료된 상태
- `FAILED`: 예외 발생으로 인한 작업이 실패한 상태
- `CANCELLED`: 작업이 취소된 상태

#### 작업 상태 확인 메서드
- `boolean isDone()`: 작업이 완료되었는지 여부를 확인
  - 작업이 성공적으로 완료되어 결과 반환이 가능한 경우
  - 작업이 실패하거나 예외가 발생하여 작업이 실해한 경우
  - `cancel()` 메서드를 호출하여 작업이 취소된 경우
- `boolean isCancelled()`: 작업이 취소되었는지 여부를 확인
  - `cancel()` 메서드를 호출하여 작업이 취소된 경우

#### 작업 상태 변경 메서드
- `boolean cancel(boolean mayInterruptIfRunning)`: 실행중이지 않은 작업을 취소하거나, 실행중인 작업을 중단할 수 있다.
  - `mayInterruptIfRunning`이 `true`인 경우, 인터럽트를 발생시켜 실행 중인 작업을 중단할 수 있으며, 작업 상태는 `CANCELLED`로 변경된다.
  - `mayInterruptIfRunning`이 `false`인 경우, 실행 중인 작업은 중단되지 않지만, 작업 상태는 `CANCELLED`로 변경된다. 

> [작업 상태 확인 예제](./FutureExceptionMain1.java)

### ForkJoinTask

### CompletableFuture
- `CompletableFuture`는 자바 1.8이후부터 지원하는 구현체로써 `Future`를 확장하여 비동기 작업을 좀 더 유연하게 처리할 수 있는 기능을 제공한다. 
- `Futrue`의 경우 비동기 작업의 반환된 결과를 처리하거나 예외를 처리하기 위해서는 `get()`을 호출하여 스레드를 블록킹한 상태에서만 처리가 가능하지만, `CompletableFuture`를 활용하면 이러한 단점을 해결할 수 있다.
  - 작업의 결과 수행 이후 추가 로직 수행
  - 여러 작업을 조합하여 처리
  - 작업의 완료 또는 예외에 따른 후처리 가능




> [Baeldung > Guide to java.util.concurrent.Future](https://www.baeldung.com/java-future) <br/>
> [MangKyu's Diary:티스토리 > Callable, Future 및 Executors, Executor, ExecutorService, ScheduledExecutorService에 대한 이해 및 사용법](https://mangkyu.tistory.com/259) <br/>
> [MangKyu's Diary:티스토리 > CompletableFuture에 대한 이해 및 사용법](https://mangkyu.tistory.com/263) <br/>