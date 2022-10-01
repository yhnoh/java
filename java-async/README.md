

### ExecutorService
---

- `ExecutorService`를 이용하여 간단하게 쓰레드 풀 생성 및 병렬 처리(멀티 스레드)가  가능하다.
    - `newFiexdThreadPool(int)` : 인자 개수 만큼 고정된 쓰레드풀을 만든다.
    - `newCachedThreadPool()` : 필요할 때 필요한 만큼 쓰레드풀을 생성한다. 이미 생성된 쓰레드를 재활용한다.
    - `newScheduledThreadPool(int)` : 일정 시간 뒤에 실행되는 작업이나, 주기적으로 수행되는 작업이 있는 경우 활용할 수 있다.
    - `newSingleThreadExecutor()` : 싱글 쓰레드에서 동작해야 하는 작업을 처리할 때 활용할 수 있다.


### Future
--- 


- `Future`를 이용하면 ExecutorService에서 작업한 결과값을 메인 쓰레드에서 얻을 수 있다.
    - 쓰레드 작업이 완료되기를 기다리며, 결과값을 확인할 수 있다.
    - 쓰레드 작업의 완료 여부, 취소 여부를 확인할 수 있다.

- 문제점
    - 첫 번째 작업이 늦어지면 다른 작업의 결과값을 가져오는 시간이 오래 걸리 수 있다.

> **Reference**
> - [Funture Reference](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html)
> - [Java - ExecutorService를 사용하는 방법
](https://codechacha.com/ko/java-executors/)
