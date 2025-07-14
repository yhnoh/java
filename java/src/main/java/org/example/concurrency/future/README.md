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
