## 동기화(Synchronization)
- 동기화란 여러 프로세스나 스레드가 공유 자원에 접근할 때, ***공유 자원의 충돌을 방지하고 작업의 순서를 보장하여 데이터의 일관성을 유지하기 위한 과정***이다. 
- 동기화를 하기위해서는 어느 상황에 어떤 영역을 동기화해야하는지를 먼저 파악해야하며, 이후에는 동기화 기법을 통해서 데이터의 일관성을 유지할 수 있어야 한다.

- 동기화를 하기위한 경쟁상태와 임계영역을 파악한 이후에는 여러 동기화 기법을 통해서 데이터 일관성을 유지할 수 있다.
  - 뮤텍스(Mutex): 상호 배제를 위한 동기화 기법으로, 한 번에 하나의 프로세스나 스레드만 공유 자원에 접근할 수 있도록 한다.
  - 세마포어(Semaphore): 공유 자원에 접근할 수 있는 프로세스나 스레드의 수를 제한하는 동기화 기법
  - 모니터(Monitor): 공유 자원에 대한 접근을 제어하는 객체로, 내부적으로 뮤텍스와 조건 변수를 사용하여 동기화를 구현한다.
  - 조건 변수(Condition Variable): 특정 조건이 만족될 때까지 프로세스나 스레드를 대기시키는 동기화 기법

### 경쟁 상태(Race Condition)와 임계 영역(Critical Section)
- 동기화를 통해서 데이터의 일관성을 유지하기전에 먼저 ***어떤 상황이나 영역에 동기화가 필요한지를 먼저 파악***해야한다.
  - ***경쟁상태(Race Condtion) 여러 프로세스나 스레드가 동시에 공유 자원에 접근하여 데이터의 일관성이 깨지는 상황***
  - ***임계영역(Critical Section) 경쟁 상태가 발생하는 영역 또는 코드 블록***:

```java
public static class Counter {
    private int value;

    public void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }
}
```
> [동시성 문제 발생 예제](./SynchronizationMain1.java)
- 위의 `Counter` 클래스는 `increment()` 메서드를 통해서 값을 1씩 증가시키는 기능을 제공한다.
- 멀티 스레드 환경에서는 여러 스레드가 동시에 `increment()` 메서드를 호출할 경우 기대하는 결과값이 나오지 않을 수 있다.
- `increment()` 메서드를 통해서 `value` 값을 증가시키는 과정은 해석해보면 아래와 같다.
  - `value` 값을 읽어온다.
  - `value` 값을 1 증가시킨다.
  - 증가된 값을 다시 `value`에 저장한다.
- 멀티 스레드 환경에서의 동작 방식은 아래와 같이 동작할 수 있다.
```text
Thread-1: value 읽기 (value = 0)
Thread-2: value 읽기 (value = 0)
Thread-1: value 증가 (value = 1)
Thread-2: value 증가 (value = 1)
Thread-1: value 저장 (value = 1)
Thread-2: value 저장 (value = 1)
```
- `Counter` 클래스의 `increment()` 메서드를 멀티 스레드 환경에서 실행하게 되면, `value` 값이 2가 아닌 1로 저장되는 상황이 발생한다. 데이터의 일관성이 깨지는 경쟁 상태가 발생했으며 `increment()` 메서드가 임계 영역이라는 것을 알 수 있다.


> [Java Docs > Synchronization](https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html)