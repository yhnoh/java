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


### CompletableFuture
- `CompletableFuture`는 자바 1.8이후부터 지원하는 구현체로써 `Future`를 확장하여 비동기 작업을 좀 더 유연하게 처리할 수 있는 기능을 제공한다. 
- `Futrue`의 경우 비동기 작업의 반환된 결과를 후 처리하거나 예외를 처리하기 위해서는 `get()`을 호출하여 스레드를 블록킹한 상태에서만 처리가 가능하지만, `CompletableFuture`를 활용하면 이러한 단점을 해결할 수 있다.
  - 작업의 결과를 반환받아 추가 로직 수행
  - 여러 작업을 조합하여 처리
  - 작업의 완료 또는 예외에 따른 후처리 가능

#### 작업의 결과를 반환받아 추가 로직 수행
- `Future`의 경우에는 `get()` 메서드를 호출하여 작업이 완료될때까지 호출 스레드를 블록킹한 이후 추가 로직을 수행해야하지만, `CompletableFuture`는 호출 스레드를 블록킹하지 않고 메서드 체이닝을 통해 추가 로직을 수행할 수 있는 메서드를 제공한다.
  - `thenApply(Function<? super T,? extends U> fn)`: 작업의 결과를 받아서 추가 로직을 수행, 새로운 결과를 반환
  - `thenAccept(Consumer<? super T> action)`: 작업의 결과를 받아서 추가 로직을 수행, 새로운 결과를 반환하지 않음
  - `thenRun(Runnable action)`: 작업의 결과를 무시하고 추가 로직을 수행, 새로운 결과를 반환하지 않음
- `thenApply(Function<? super T,? extends U> fn)` 예제
  - 해당 예제를 통해서 비동기 작업의 결과를 반환한 이후 추가 로직을 수행, 이후 새로운 결과를 반환하는 과정을 확인할 수 있다.
  ```java
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업 시작");
        return 1;
    }).thenApply(i -> {
        log.info("추가 로직 수행");
        return i + 1;
    });
  ```
- `thenAccept(Consumer<? super T> action)` 예제
  - 해당 예제를 통해서 비동기 작업의 결과를 반환한 이후 추가 로직을 수행, 이후 새로운 결과를 반환하지 않는 과정을 확인할 수 있다.
  ```java
    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업 시작");
        return 1;
    }).thenAccept(i -> {
        log.info("추가 로직 수행 i = {} ", i + 1);
    });
  ``` 
- `thenRun(Runnable action)` 예제
  - 해당 예제를 통해서 비동기 작업의 결과를 무시하고 추가 로직을 수행, 이후 새로운 결과를 반환하지 않는 과정을 확인할 수 있다.
  ```java
    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업 시작");
        return 1;
    }).thenRun(() -> {
        log.info("추가 로직 수행");
    });
  ```

> [작업의 결과를 반환받아 추가 로직 수행 예제](../../../../../../test/java/org/example/concurrency/future/CompletableFutureAfterLogicMain1Test.java)

#### 여러 작업을 조합하여 처리
- `CompletableFuture`는 여러 비동기 작업을 조합하여 처리할 수 있는 메서드를 제공하며 작업의 결과를 조합하여 새로운 값을 반환하거나, 여러 작업 중 일부 또는 모든 작업이 완료된 이후에 후처리를 수행할 수 있다.
  - `thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)`: 두개의 작업의 반환값을 조합하여 새로운 결과를 반환
  - `thenCompose(Function<? super T,? extends CompletionStage<U>> fn)`: 이전 작업의 결과를 사용하여 새로운 작업을 생성하고, 그 결과를 반환
  - `acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)`: 두개의 작업 중 먼저 완료된 작업의 결과를 사용하여 후처리 수행, 새로운 결과를 반환하지 않음
  - `applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn)`: 두개의 작업 중 먼저 완료된 작업의 결과를 사용하여 후처리 수행, 새로운 결과를 반환
  - `anyOf(CompletableFuture<?>... cfs)`: 여러 작업 중 먼저 완료된 작업의 결과를 반환
  - `allOf(CompletableFuture<?>... cfs)`: 여러 작업이 모두 완료될 때까지 기다린 후, 새로운 결과를 반환하지 않음
- `thenCombine(CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)` 예제
  - 해당 예제를 통해서 두개의 비동기 작업의 결과를 조합하여 새로운 결과를 반환하는 과정을 확인할 수 있다.
  ```java
    CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
        log.info("작업1 시작");
        return 1;
    }).thenCombine(CompletableFuture.supplyAsync(() -> {
        log.info("작업2 시작");
        return 2;
    }), (i1, i2) -> {
        log.info("작업1 결과 = {}, 작업2 결과 = {}", i1, i2);
        return i1 + i2;
    });
  ```
- `thenCompose(Function<? super T,? extends CompletionStage<U>> fn)` 예제
  - 해당 예제를 통해서 이전 작업의 결과를 사용하여 새로운 작업을 생성하고, 그 결과를 반환하는 과정을 확인할 수 있다.
  ```java
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업1 시작");
        return 1;
    }).thenCompose(i -> CompletableFuture.supplyAsync(() -> {
        log.info("작업2 시작");
        return String.valueOf(i);
    }));
  ```
- `acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action)` 예제
  - 해당 예제를 통해서 두개의 작업 중 먼저 완료된 작업의 결과를 사용하여 후처리 수행한 이후 새로운 결과를 반환하지 않은 것을 확인할 수 있다.
  ```java
    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업1 시작");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int result = 1;
        log.info("작업1 완료, 결과 = {}", result);
        return result;
    }).acceptEither(CompletableFuture.supplyAsync(() -> {
        log.info("작업2 시작");
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int result = 2;
        log.info("작업2 완료, 결과 = {}", result);
        return result;
    }), i -> log.info("작업1 또는 작업2 결과 = {}", i));

  ```
- `applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn)`
  - 해당 예제를 통해서 두개의 작업 중 먼저 완료된 작업의 결과를 사용하여 후처리를 수행한 이후 새로운 결과를 반환하는 것을 확인할 수 있다.
  ```java
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
      log.info("작업1 시작");
      try {
          sleep(1000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
      int result = 1;
      log.info("작업1 완료, 결과 = {}", result);
      return result;
    }).applyToEither(CompletableFuture.supplyAsync(() -> {
      log.info("작업2 시작");
      try {
          sleep(2000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
      int result = 2;
      log.info("작업2 완료, 결과 = {}", result);
      return result;
    }), i -> {
        log.info("작업1 또는 작업2 결과 = {}", i);
        return i * 2;
    });
  ```
- `anyOf(CompletableFuture<?>... cfs)` 예제
  - 해당 예제를 통해서 여러 작업 중 먼저 완료된 작업의 결과를 반환하는 것을 확인할 수 있다.
  ```java
    CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
        log.info("작업1 시작");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int result = 1;
        log.info("작업1 완료, 결과 = {}", result);
        return result;
    });

    CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
        log.info("작업2 시작");
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int result = 2;
        log.info("작업2 완료, 결과 = {}", result);
        return result;
    });

    CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(future1, future2);

    anyFuture.thenAccept(i -> {
        log.info("먼저 완료된 작업의 결과 = {}", i);
    });

    anyFuture.join();
  ```
- `allOf(CompletableFuture<?>... cfs)` 예제
  - 해당 예제를 통해서 모든 작업이 완료될 때까지 기다린 후 새로운 결과를 반환하지 않는 것을 확인할 수 있다.
  ```java
    CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
        log.info("작업1 시작");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int result = 1;
        log.info("작업1 완료, 결과 = {}", result);
        return result;
    });

    CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
        log.info("작업2 시작");
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int result = 2;
        log.info("작업2 완료, 결과 = {}", result);
        return result;
    });

    CompletableFuture<Void> allFuture = CompletableFuture.allOf(future1, future2);

    allFuture.join();
  ```
> [여러 작업을 조합하여 처리 예제](../../../../../../test/java/org/example/concurrency/future/CompletableFutureAfterLogicMain1Test.java)


#### 작업의 완료 또는 예외에 따른 후처리 가능
- `Future`의 경우 작업이 정상적으로 완료되거나 예외가 발생하였을 경우 후처리를 위해서는 `get()` 메서드를 사용하여 호출 스레드를 블록킹한 이후 반환된 결과값(성공/예외)을 통해서 후처리를 수행해야하지만, `CompletableFuture`는 작업이 완료되거나 예외가 발생한 이후에 후처리를 수행할 수 있는 메서드를 제공한다.
  - `handle(BiFunction<? super T, ? super Throwable, ? extends U> fn)`: 작업이 완료되거나 예외가 발생한 이후에 후처리를 수행하고, 새로운 결과를 반환
  - `exceptionally(Function<Throwable, ? extends T> fn)`: 작업 중에 예외가 발생한 경우에만 후처리를 수행하고, 새로운 결과를 반환
  - `whenComplete(BiConsumer<? super T, ? super Throwable> action)`: 작업이 완료되거나 예외가 발생한 이후에 후처리를 수행, 새로운 결과를 반환하지 않음

- `handle(BiFunction<? super T, ? super Throwable, ? extends U> fn)` 
  - handle 메서드를 사용하면 작업이 성공적으로 완료되었을 때와 예외가 발생하였을 때 모두 핸들링하여 새로운 결과를 반환할 수 있다.
  - 이후 메서드 체이닝을 통해서 추가 로직을 수행할 수 있다.
  ```java
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업 시작");
        if (isThrowException) {
            throw new RuntimeException("작업 도중 예외 발생");
        }
        return 1;
    }).handle((i, throwable) -> {
        if (throwable == null) {
            log.info("handle no exception : result = {}, throwable = {}", i, "No Exception");
            return i;
        }
        log.error("throw exception", throwable);
        return 2;
    }).thenApply(i -> i * 2);
  ```
- `exceptionally(Function<Throwable, ? extends T> fn)`
  - exceptionally 메서드를 사용하면 작업 중에 예외가 발생한 경우에만 핸들링하여 새로운 결과를 반환할 수 있다.
  - 이후 메서드 체이닝을 통해서 추가 로직을 수행할 수 있다.
  ```java
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업 시작");
        if (isThrowException) {
            throw new RuntimeException("작업 도중 예외 발생");
        }
        return 1;
    }).exceptionally((throwable) -> {
        log.error("throw exception", throwable);
        return 2;
    }).thenApply(i -> i * 2);
  ```
- `whenComplete(BiConsumer<? super T, ? super Throwable> action)`
  - `whenComplete` 메서드를 사용하면 작업이 완료되거나 예외가 발생한 이후에 대한 상황을 핸들링할 수 있지만, 새로운 결과를 반환하지 않는다.
  - 때문에 이후 메서드 체이닝에 영향을 주지 않으며, 만약 예외가 발생할 경우에는 `CompletionException`이 발생하며 이후 작업을 수행할 수 없게 된다.
  - `handle`과 `exceptionally`와는 다르게 예외가 발생하였을 경우 `whenComplete`는 `Future`의 상태를 `SUCCESS`로 변경하지 않기 때문에, 이후 작업을 수행할 수 없게 된다.
  ```java
    CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
        log.info("작업 시작");
        if (isThrowException) {
            throw new RuntimeException("작업 도중 예외 발생");
        }
        return 1;
    }).whenComplete((i, throwable) -> {
        if (throwable == null) {
            log.info("whenComplete no exception : result = {}, throwable = {}", i, "No Exception");
            return;
        }
        
        log.error("throw exception", throwable);
    }).thenApply(i -> i * 2);
  ```


## References
> [Java Docs > Future](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html) <br/>
> [Java Docs > CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) <br/>
> [Baeldung > Guide to java.util.concurrent.Future](https://www.baeldung.com/java-future) <br/>
> [MangKyu's Diary:티스토리 > Callable, Future 및 Executors, Executor, ExecutorService, ScheduledExecutorService에 대한 이해 및 사용법](https://mangkyu.tistory.com/259) <br/>
> [MangKyu's Diary:티스토리 > CompletableFuture에 대한 이해 및 사용법](https://mangkyu.tistory.com/263) <br/>