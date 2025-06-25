## 동기화(Synchronization)

- 동기화란 여러 프로세스나 스레드가 공유 자원에 접근할 때, ***공유 자원의 충돌을 방지하고 작업의 순서를 보장하여 데이터의 일관성을 유지하기 위한 과정***이다.
- 동기화를 하기위해서는 어느 상황에 어떤 영역을 동기화해야하는지를 먼저 파악해야하며, 이후에는 동기화 기법을 통해서 데이터의 일관성을 유지할 수 있어야 한다.

## 동기화가 필요한 상황과 영역 파악

- 동기화를 통해서 데이터의 일관성을 유지하기전에 먼저 ***어떤 상황이나 영역에 동기화가 필요한지를 먼저 파악***해야한다.
    - ***경쟁상태(Race Condtion): 여러 프로세스나 스레드가 동시에 공유 자원에 접근하여 데이터의 일관성이 깨지는 상황***
    - ***임계영역(Critical Section): 경쟁 상태가 발생하는 영역 또는 코드 블록***

### 경쟁상태(Race Condtion)와 임계영역(Critical Section)

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
> [동시성 문제 발생 예제](./SynchronizationMain1.java) <br/>

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

- `Counter` 클래스의 `increment()` 메서드를 멀티 스레드 환경에서 실행하게 되면, `value` 값이 2가 아닌 1로 저장되는 상황이 발생한다. 데이터의 일관성이 깨지는 경쟁 상태가 발생했으며
  `increment()` 메서드가 임계 영역이라는 것을 알 수 있다.

## Java Synchronized 키워드
- Java에서는 동기화를 위해 `synchronized` 키워드를 제공한다. ***`synchronized` 키워드는 메서드나 코드 블록에 적용할 수 있으며, 해당 영역에 대한 접근을 동기화하여 경쟁 상태를 방지***한다.
  - `synchronized` 키워드를 사용하면 해당 ***메서드나 블록에 접근하는 스레드는 하나만 허용되며, 다른 스레드는 해당 영역이 해제될 때까지 대기***하게 된다.
- `synchronized` 키워드를 통하여 ***동기화가 가능한 이유는 객체마다 내장된 모니터 락(Monitor Lock)을 가지고 있기 때문이다.***
  > 자바는 객체 지향 언어이며 객체는 공유 자원으로써 멀티 스레드 환경에서 데이터의 일관성이 깨질 수 있기 때문에, 자연스럽게 자바에서는 객체 단위의 동기화 기법을 제공한다는 것을 유추해볼 수 있다.

### Synchronized 키워드 사용 방법

#### Synchronized 키워드 선언
- `synchronized` 키워드는 메서드에 선언하거나, 코드 블록에 선언하여 사용할 수 있다.
```java
public static class Counter {
    private int value;
    private final Object lock = new Object();

    /**
     * synchronized 메서드로 동기화된 increment 메서드
     */
    public synchronized void increment() {
        value++;
    }

    /**
     * synchronized 블록을 사용하여 동기화된 increment 메서드
     */
    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    /**
     * synchronized 블록을 사용하여 동기화된 increment 메서드
     */
    public void increment() {
        synchronized (lock) {
            value++;
        }
    }
    
    public int getValue() {
        return value;
    }
}
```
> [synchronized 키워드 사용 예제](./SynchronizationMain2.java)  <br/>

- `synchronized` 키워드를 활용하여 멀티 스레드 환경에서도 동시성 이슈가 발생하지 않은 이유는 아래와 같다
```text
Thread-1: value 읽기 (value = 0, lock 획득) 
Thread-2: (lock 획득 대기)
Thread-1: value 증가 (value = 1)
Thread-1: value 저장 (value = 1, lock 해제)
Thread-2: value 읽기 (value = 1, lock 획득)
Thread-2: value 증가 (value = 2)
Thread-2: value 저장 (value = 2, lock 해제)
```

#### Synchronized 키워드 선언 레벨에 따른 락의 범위
- `synchronized` 키워드는 ***클래스 레벨과 인스턴스 레벨에서 선언이 가능하며, 해당 레벨에 따라서 락의 범위가 달라진다.***
- 때문에 `synchronized` 키워드를 선언할 때는 해당 ***메서드나 블록이 어떤 범위의 락을 사용하는지를 이해하고 사용해야만 데이터의 일관성을 유지***할 수 있다.
  - 인스턴스 레벨 `synchronized` 키워드가 인스턴스 레벨로 선언된 경우, ***해당 인스턴스의 모니터 락을 사용하여 동기화***한다. 즉, 해당 인스턴스에 대한 접근이 동기화된다.
  - 클래스 레벨: `synchronized` 키워드가 정적 메서드에 선언된 경우, ***클래스 자체의 모니터 락을 사용하여 동기화***한다. 즉, 해당 클래스의 모든 인스턴스에 대한 접근이 동기화된다.
- 인스턴스 레벨의 `synchronized` 키워드는 동일 인스턴스에 대해서만 동기화를 적용하며, 다른 인스턴스의 모니터락을 사용하게 되면 동기화가 되지 않는다.
    ```java
    public static class Counter {
        private final Object incrementLock = new Object();
        private final Object decrementLock = new Object();
        private int value;

        // increment 메서드는 incrementLock 인스턴스의 모니터 락을 사용하여 동기화
        public void increment() {
            synchronized (incrementLock) {
                value++;
            }
        }

        // decrement 메서드는 decrementLock 인스턴스의 모니터 락을 사용하여 동기화
        public void decrement() {
            synchronized (decrementLock) {
                value--;
            }
        }


        public int getValue() {
            return value;
        }
    }
    ```
    > [인스턴스 레벨 synchronized 키워드 잘못된 사용 예제](./SynchronizationMain3.java) <br/>
    - 멀티 스레드 환경에서 `increment()`와 `decrement()` 메서드를 동시에 호출하더라도, 서로 다른 인스턴스 모니터 락을 사용하기 때문에 동기화가 되지 않는다.
    ```text
    Thread-1: value 읽기 (value = 0, increment() 호출 incrementLock 획득)
    Thread-2: value 읽기 (value = 0, decrement() 호출 decrementLock 획득)
    Thread-1: value 증가 (value = 1)
    Thread-2: value 감소 (value = -1)
    Thread-1: value 저장 (value = 1, incrementLock 해제)
    Thread-2: value 저장 (value = -1, decrementLock 해제)
    ```
- 클래스 레벨의 `synchronized` 키워드는 동일 클래스에 대해서만 동기화를 적용하며, 다른 클래스의 모니터 락을 사용하게 되면 동기화가 되지 않는다.
    ```java
    public static class Counter {
        public static class IncrementLock {
        }

        public static class DecrementLock {
        }

        private int value;

        // increment 메서드는 IncrementLock 클래스의 모니터 락을 사용하여 동기화
        public void increment() {
            synchronized (IncrementLock.class) {
                value++;
            }
        }

        // decrement 메서드는 DecrementLock 클래스의 모니터 락을 사용하여 동기화
        public void decrement() {
            synchronized (DecrementLock.class) {
                value--;
            }
        }

        public int getValue() {
            return value;
        }
    }    
    ```
    > [클래스 레벨 synchronized 키워드 잘못된 사용 예제](./SynchronizationMain4.java) <br/>
    - 멀티 스레드 환경에서 `increment()`와 `decrement()` 메서드를 동시에 호출하더라도, 서로 다른 클래스 모니터 락을 사용하기 때문에 동기화가 되지 않는다.
    ```text
    Thread-1: value 읽기 (value = 0, increment() 호출 IncrementLock 획득)
    Thread-2: value 읽기 (value = 0, decrement() 호출 DecrementLock 획득)
    Thread-1: value 증가 (value = 1)
    Thread-2: value 감소 (value = -1)
    Thread-1: value 저장 (value = 1, IncrementLock 해제)
    Thread-2: value 저장 (value = -1, DecrementLock 해제)
    ```

### 모니터(Monitor)
- Java에서 `synchronized` 키워드를 통하여 ***동기화가 가능한 이유는 객체마다 내장된 모니터 락(Monitor Lock)을 가지고 있기 때문이다.***
  > 참고로 클래스 레벨의 `synchronized` 키워드는 클래스 객체(Class Object)의 모니터 락을 사용한다. <br/>
  > ***Instances of the class Class represent classes and interfaces in a running Java application. An enum class and a record class are kinds of class; an annotation interface is a kind of interface.***  <br/>
  > [Java Docs > Class](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Class.html) <br/>

#### 모니터 구성 요소
![](./monitor.png)
- 모니터는 객체의 ***공유 자원을 동기화하기 위하여 뮤텍스(Mutex: Mutual Exclusion)와 조건 변수(Condition Variable)를 사용하여 동기화를 구현***한다.
  - 뮤텍스(Mutex: Mutual Exclusion): 하나의 프로세스나 스레드만 공유 자원에 접근할 수 있도록 하는 동기화 기법
  - 조건 변수(Condition Variable): 특정 조건이 만족될 때 스레드를 일시적으로 대기시키거나 깨우는 역할 수행
    - `wait()`, `notify()`, `notifyAll()` 메서드를 통해서 스레드를 대기시키거나 깨울 수 있다.
- 뮤텍스와 조건 변수를 사용하기 위해서는 스레드가 대기할 수 있는 ***Entry Set과 Wait Set이 필요***하다.
  - Entry Set: 스레드가 공유 자원에 접근하기 전에 대기하는 영역
  - Wait Set: 특정 조건이 만족되어 스레드가 대기하는 영역, 특정 조건이 만족되면 Wait Set에 있는 스레드를 깨워서 작업을 수행할 수 있다.

> [Java Specifications > Synchronization](https://docs.oracle.com/javase/specs/jls/se21/html/jls-17.html#jls-17.1) <br/>
> [](https://ionutbalosin.com/2018/06/contended-locks-explained-a-performance-approach/) <br/>

### Synchronized의 장단점

- `synchronized` 키워드를 사용하여 동기화를 구현하는 것은 자바에서 가장 기본적이며 간단한 동기화 방법이다.
  - 직관적이며 간단한 구현
  - JVM에서 락을 자동으로 관리하기 때문에 락에 대한 복잡한 관리 불필요
- 하지만 `synchronized` 키워드에 대해 몇가지 생각해볼 필요가 있다.
  - 타임아웃 설정 불가: `synchronized` 키워드는 타임아웃을 설정할 수 없기 때문에, 스레드가 무한정 대기할 수 있다.   
  - 인터럽트 불가: `synchronized` 키워드는 인터럽트를 지원하지 않기 때문에, 스레드가 대기 중인 상태에서 인터럽트를 발생시켜도 대기 상태를 해제할 수 없다.
  - 읽기 작업에 대한 비효율성: 읽기 작업에 `synchronized` 키워드를 사용할 경우 읽기 작업도 하나의 스레드만 접근가능하기 때문에 다른 스레드가 대기하면서 성능 저하가 발생할 수 있다.
- 때문에 `synchronized` 키워드를 사용하여 동기화를 구현할 때는 ***어떤 상황에서 사용해야 하는지를 잘 파악하고 사용해야 한다.***
- Java에서는 `synchronized` 키워드 외에도 유연하고 효율적으로 동시성을 제어할 수 있는 `java.util.concurrent.locks` 패키지를 제공한다.

## 동기화 기법
- 동기화 기법은 ***경쟁 상태를 방지하고 임계 영역에 대한 접근을 제어하여 작업의 순서 및 데이터의 일관성을 유지하기 위한 방법***이다.
- 주요 동기화 기법은 크게 뮤텍스(Mutex), 세마포어(Semaphore), 모니터(Monitor)가 있다.

### 뮤텍스(Mutex: Mutual Exclusion)
- 뮤텍스는 ***한 번에 하나의 프로세스나 스레드만 공유 자원에 접근할 수 있도록 하는 동기화 기법**이다.
    - 뮤텍스는 공유 자원에 대한 접근을 제어하기 위한 가장 기본적인 동기화 기법이다.
- 락을 획득한 프로세스나 스레드는 공유 자원에 접근할 수 있으며 해당 락을 소유한 프로세스나 스레드만이 락을 해제할 수 있다. (락에 대한 소유권 O)
- Java에서는 `synchronized` 키워드를 활용하거나 `java.util.concurrent.locks` 패키지의 락을 활용하여 뮤텍스를 구현할 수 있다.

> [syncronized를 통한 Mutex 구현](./MutexMain1.java) <br/>
> [java.util.concurrent.locks을 통한 Mutex 구현](./MutexMain2.java)

### 세마포어(Semaphore)
- 세마포어는 ***공유 자원에 접근할 수 있는 프로세스나 스레드의 수를 제한하는 동기화 기법***이다.
- N개의 프로세스나 스레드가 동시에 공유 자원에 접근할 수 있도록 허용하며, 카운터가 0이 되면 더 이상 접근할 수 없고 대기하게 된다.
- 세마포어는 뮤텍스와 달리 락을 소유한 프로세스나 스레드가 아닌 다른 프로세스나 스레드가 락을 해제할 수 있다. (락에 대한 소유권 X)
- 세마포어를 통해서 해결할 수 있는 문제
    > [생산자-소비자 문제(producer-consumer problem)](https://ko.wikipedia.org/wiki/%EC%83%9D%EC%82%B0%EC%9E%90-%EC%86%8C%EB%B9%84%EC%9E%90_%EB%AC%B8%EC%A0%9C) <br/>
    > [독자-저자 문제(readers-writers problem)](https://ko.wikipedia.org/wiki/%EC%83%9D%EC%82%B0%EC%9E%90-%EC%86%8C%EB%B9%84%EC%9E%90_%EB%AC%B8%EC%A0%9C)

#### 세마포어 구성 요소
- permit: 세마포어의 카운터를 나타내며, 공유 자원에 접근할 수 있는 프로세스나 스레드 수를 나타낸다.
- P연산 (acquire): 세마포어의 카운터를 감소시키고, 카운터가 0이 되면 대기
- V연산 (release): 세마포어의 카운터를 증가시키고, 대기 중인 스레드가 있다면 하나를 깨워서 작업을 수행

#### 세마포어 예제
```java
public static class CustomSemaphore {

    private int permits;
    private final int maxPermits;

    public CustomSemaphore(int permits) {
        this.permits = permits;
        this.maxPermits = permits;
    }
    
    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        // 최대 허용량 초과 확인
        if (permits < maxPermits) {
            permits++;
            notifyAll();
        }
    }

    public synchronized int availablePermits() {
        return permits;
    }
}
```

> [세마포어를 사용하지 않은 예제](./SemaphoreMain1.java) <br/>
> [세마포어를 사용한 예제](./SemaphoreMain2.java) <br/>
> [java.util.concurrent.Semaphore를 활용한 세마포어 예제](./SemaphoreMain1.java) <br/>

- 위의 `CustomSemaphore` 클래스는 세마포어를 구현한 예제이다. `acquire()` 메서드는 P연산을 수행하며, `release()` 메서드는 V연산을 수행한다.
- 멀티 스레드 환경에 세마포어를 이용하게 될경우 아래와 같이 동작할 수 있다.
```text
Semaphore permit = 2 할당
Thread-1: acquire() 호출 및 작업 시작 (permit = 1)
Thread-2: acquire() 호출 및 작업 시작 (permit = 0)
Thread-3: acquire() 호출 및 대기 (permit = 0)
Thread-1: 작업 완료 후 release() 호출 (permit = 1)
Thread-3: 대기 해제 및 작업 시작 (permit = 0)
Thread-2: 작업 완료 후 release() 호출 (permit = 1)
Thread-3: 작업 완료 후 release() 호출 (permit = 2)
```
- 위의 예제에서 `CustomSemaphore` 클래스를 사용하여 세마포어를 구현하였으며, `java.util.concurrent.Semaphore` 클래스를 사용하여 세마포어를 구현할 수도 있다.



> [Java Tutorial Docs > Synchronization](https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html)