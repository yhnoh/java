## java.util.concurrent.locks
- `java.util.concurrent.locks` 패키지는 `synchronized` 키워드를 이용한 암묵적 동기화와 달리 ***명시적인 동기화 방법***이다.
  - 명시적이라는 의미는 개발자가 직접 ***락을 획득하고 해제하는 과정을 작성하고 제어***한다.
- `synchronized` 키워드와 매우 유사하게 작동되지만, `synchronized`의 한계점을 보완하고 좀 더 유연하게 사용할 수 있다.
  - ***락 획득에 대한 시도 및 대기 시간 설정 가능***
  - ***락 획득에 대한 인터럽트 가능***
  - 모니터락의 매커니즘 활용 가능 (Mutex + Condition Variable)하며 `synchronized` 키워드와 달리 ***다중 조건 변수 사용 가능***

### java.util.concurrent.locks 사용
- `synchronized` 키워드와 달리 `Lock` 인터페이스의 구현체 사용하여 명시적으로 락을 획득하고 해제한다.
- 명시적으로 선언을 해야하기 때문에 ***락을 획득하기 위한 `lock()` 메서드와 해제를 위한 `unlock()` 메서드를 제공***한다.
  - 주의해야할점은 ***`unlock()` 메서드를 반드시 호출하여 락을 해제***해야한다. 
  - 락을 해제하지 않으면 다른 스레드가 해당 락을 획득할 수 없기 때문에 데드락이 발생할 수 있다.
```java
public static class Counter {
    private int value;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        // 락 획득
        lock.lock();
        try {
            value++;
        } finally {
            // 락 해제
            lock.unlock();
        }
    }


    public int getValue() {
        return value;
    }
}
```
> [Lock 사용 예제](./LockMain1.java)

## java.util.concurrent.locks 특징
- `java.util.concurrent.locks` 패키지는 `synchronized` 키워드와 유사하게 동작하지만, ***명시적인 락 획득과 해제 및 유연한 동기화 방법을 제공***한다.
- `java.util.concurrent.locks`의 특징을 알아보는 것과 동시에 `synchronized` 키워드와 비교하며 `synchronized`의 한계점에 대해서도 알아보자.

### 락 획득에 대한 시도 및 대기 시간 설정 가능
- `Lock` 인터페이스는 `tryLock()` 메서드를 제공하여 ***락을 획득할 수 있는지 시도할 수 있다.***
- 만약 락을 이미 획득하고 있어 스레드가 락을 획득하지 못하는 경우, `tryLock()` 메서드는 즉시 `false`를 반환한다. 또한 `tryLock(long timeout, TimeUnit unit)` 메서드를 사용하여 지정된 시간 동안 락을 획득할 수 있는지 시도할 수 있다.
  - `synchronized` 키워드의 경우 이미 선점하고 있는 락이 있는 경우 락이 해제되기 전까지 다른 스레드가 락을 획득하기 위하여 대기해야 한다.
  - 만약 락을 획득한 스레드가 데드락이 발생할거나 어떠한 문제로 인하여 락을 해제하지 못하는 경우, 다른 스레드는 락을 획득하지 못하여 다른 작업을 수행할 수 없다. 이는 시스템 전체의 성능을 저하시킬 수 있다.

#### tryLock() 사용 예제
```java
private static class Task {

    private final Lock lock = new ReentrantLock();

    public void execute(int executionMillis) throws InterruptedException {
        int lockTimeoutMillis = 1000;
        // 지정 시간 동안 락 획득 시도
        boolean isAcquiredLock = lock.tryLock(lockTimeoutMillis, TimeUnit.MILLISECONDS);
        try {
            if (isAcquiredLock) {
                log.info("작업 처리중... 총 소요 시간 {} ms", executionMillis);
                sleep(executionMillis);
            } else {
                log.info("락 획득 실패로 인한 작업 취소");
            }
        } finally {
            if (isAcquiredLock) {
                lock.unlock();
                log.info("작업 완료");
            }
        }
    }
}
```
- 위 예제에서는 락 획득에 대한 시도를 1초 동안 시도하고, 만약 락을 획득하지 못한다면 작업이 취소되는 예제이다.
```text
executionMillis: 2000 일 경우
Thread-1: 락 획득 성공 및 2000ms 동안 작업 수행
Thread-2: 락 획득 시도하지만 Thread-1이 락을 획득하고 있어 대기
Thread-2: 1000ms 동안 락 획득 시도하였지만 실패하여 작업 취소
Thread-1: 작업 완료 후 락 해제
```

> [tryLock() 사용 예제](./TryLockMain1.java)

### 락 획득에 대한 인터럽트 가능
- `Lock` 인터페이스는 락을 획득을 시도하는 스레드를 대상으로 인터럽트를 발생시킬 수 있는 `lockInterruptibly(), tryLock(long timeout, TimeUnit unit)` 메서드를 제공한다.
- 만약 락을 이미 획득하고 있어 스레드가 락을 획득하지 못하고 대기 중인 경우, 해당 스레드를 인터럽트하면 `InterruptedException` 예외가 발생하며 개발자가 의도한 동작을 수행할 수 있다.
  - 락 획득 대시 시간 설정과 비슷한 이유로 락 획득 시도시 무한정 대기하는 것을 방지하여 스레드가 다른 작업을 수행할 수 있도록 한다.

#### lockInterruptibly() 사용 예제 

```java
public static class WaitingInterrupter implements Runnable {

    private final List<Thread> threads;

    public WaitingInterrupter(List<Thread> threads) {
        this.threads = threads;
    }

    @Override
    public void run() {

        threads.forEach(thread -> {
            log.info("state = " + thread.getState());
        });

        threads.stream()
                .filter(thread -> thread.getState().equals(Thread.State.WAITING))
                .forEach(Thread::interrupt);
    }
}


private static class Task {

    private final Lock lock = new ReentrantLock();
    private final long startTimeMills = System.currentTimeMillis();

    public void execute(int executionMillis) throws InterruptedException {
        // 락 획득 시도 진행, 락 획득 도중 인터럽트 발생시 InterruptedException 예외 발생
        lock.lockInterruptibly();
        try {
            log.info("작업 처리중... 총 소요 시간 {} ms", executionMillis);
            while (true) {
                long endTimeMills = System.currentTimeMillis();
                if (endTimeMills - startTimeMills > executionMillis) {
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
        log.info("작업 완료");
    }
}
```
- 위 예제는 하나의 스레드가 락을 획득하고 작업을 수행하는 동안, 락 획득을 시도하며 대기 중엔 스레드를 인터럽트 시키는 예제이다.
```text
Thread-1: 락 획득 성공 및 작업 수행
Thread-2: 락 획득 시도 및 대기 중
WaitingInterrupter Thread: 대기 중인 스레드 인터럽트
Thread-2: 인터럽트 발생으로 InterruptedException 예외 발생 및 작업 취소
Thread-1: 작업 완료 후 락 해제
```

> [lockInterruptibly() 사용 예제](./InterruptLockMain1.java.java)


### 다중 조건 변수 사용 가능
- `synchronized` 키워드를 사용하여 단일 조건 변수(하나의 대기 집합)만을 지원하는 반면, `java.util.concurrent.locks.Condition` 인터페이스를 사용하면 ***다중 조건 변수(여러개의 대기 집합)를 사용할 수 있다.***
- `java.util.concurrent.locks.Condition`는 `await(), signal(), signalAll()` 메서드를 제공하여 스레드 간의 통신을 지원한다.
  - Object 클래스의 `wait()`, `notify()`, `notifyAll()` 메서드와 유사한 역할을 한다.

#### 단인 조건 변수의 한계
- `synchronized` 키워드를 활용할 경우 `wait(), notify(), notifyAll()` 메서드를 통해서 스레드간의 통신을 할 수 있다.
  - `wait()` 메서드를 통해 락을 해제하고 대기시키고, `notify()` 또는 `notifyAll()` 메서드를 통해 대기 중인 스레드를 깨워 다시 락을 획득할 수 있도록 한다.
- `synchronized` 키워들의 경우 하나의 조건 변수만을 지원하기 때문에 하나의 대기 집합만을 사용할 수 있다.
- 하나의 조건 변수만을 활용하기 때문에 ***여러 스레드가 서로 다른 조건에 따라서 대기하고 깨어나는 것에 제한이 있으며, 불필요한 대기와 깨어남이 발생하는 비효율을 야기할 수 있다.***
  - 불필요한 대기와 깨어남이 발생한다는 것은 ***불필요한 컨텍스틑 스위칭 비용이 발생할 수 있다는 것을 의미하고 이는 성능저하를 야기***시킬 수 있다.

```java
public static class Buffer<T> {
    private final Queue<T> queue;
    private final int capacity;

    public Buffer(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void produce(T item) {
        log.info("[생산자] 작업 시작 item = {}", item);
        // 버퍼가 가득 찾을때 대기
        while (queue.size() == capacity) {
            try {
                log.info("[생산자] 버퍼가 가득참으로 인하여 작업 대기 item = {}", item);
                wait();
            } catch (InterruptedException e) {
            }
        }

        queue.add(item);
        notifyAll(); // 생산 이후 대기중인 스레드 깨움
        log.info("[생산자] 작업 완료 item = {}", item);
    }

    public synchronized T consume() {
        log.info("[소비자] 작업 시작");
        // 버퍼가 비어있을 경우 대기
        while (queue.isEmpty()) {
            try {
                log.info("[소비자] 버퍼가 비어있음으로 인하여 작업 대기");
                wait();
            } catch (InterruptedException e) {
            }
        }

        T item = queue.poll();
        notifyAll(); // 소비 이후 대기중인 스레드 깨움
        log.info("[소비자] 작업 완료 item = {}", item);
        return item;
    }
}
```
> [단일 조건 변수의 한계 예제](./ConditionMain1.java)
- 해당 예제는 생산자-소비자 문제를 `synchronized` 키워드를 사용하여 구현한 예제이다.
- `produce()` 메서드는 버퍼가 가득 찾을 경우 `wait()` 메서드를 호출하여 대기하고, 아닐 경우 `notifyAll()` 메서드를 호출하여 대기 중인 스레드를 깨운다.
- `consume()` 메서드는 버퍼가 비어있을 경우 `wait()` 메서드를 호출하여 대기하고, 아닐 경우 `notifyAll()` 메서드를 호출하여 대기 중인 스레드를 깨운다.
- 하나의 대기 집합만을 사용하기 때문에 생산자와 소비자가 `notifyAll()`를 호출할 때마다 ***생산자와 소비자 둘중 어떤 스레드가 깨워질지 예측할 수 없으며, 불필요한 대기와 깨어남이 발생할 수 있다.***
  - 버퍼가 가득찾을 경우 대기중이던 생산자 스레드가 락 획득 시도시 다시 대기 상태로 빠진다. 
  - 버퍼가 비어있을 경우 대기중이던 소비자 스레드가 락 획득 시도시 다시 대기 상태로 빠진다.
```text
대기중인 모든 스레드가 깨어남
락 획득을 위한 경쟁이 발생
락 획득 이후 조건에 의해 다시 대기 상태로 빠짐
락 획득을 위한 경쟁이 발생
락 획득 이후 조건에 의해 다시 대기 상태로 빠짐
....
....
```

- 이러한 비효율성을 해결하기 위해서는 모든 스레드를 깨우는 것이 아닌, ***생산자 스레드는 대기중인 소비자 스레드를 깨우고 소비자 스레드는 대기중인 생산자 스레드를 깨우는 방식으로 구현***해야한다.

#### 다중 조건 변수 활용
- `java.util.concurrent.locks.Condition` 인터페이스를 사용하면 ***다중 조건 변수(여러개의 대기 집합)를 사용할 수 있다.***
- 다중 조건 변수를 사용하면 ***생산자와 소비자가 각각의 대기 집합을 가지고 서로 다른 조건에 따라서 대기하고 깨어날 수 있다.***
  - 생산자는 버퍼가 가득 찾을 경우 대기하고, 소비자는 버퍼가 비어있을 경우 대기한다.
  - 생산자는 작업 완료 이후 대기중인 소비자 스레드를 깨운다.
  - 소비자는 작업 완료 이후 대기중인 생산자 스레드를 깨운다.
- 이를 통해 ***불필요한 대기와 깨어남을 줄일 수 있으며, 컨텍스트 스위칭 비용을 줄여 성능을 향상시킬 수 있다.***


```java
public static class Buffer<T> {
    private final Queue<T> queue;
    private final int capacity;

    private final Lock lock = new ReentrantLock();
    private final Condition queueEmptyCondition = lock.newCondition();
    private final Condition queueFullCondition = lock.newCondition();

    public Buffer(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public void produce(T item) {

        try {
            lock.lock();

            log.info("[생산자] 작업 시작 item = {}", item);

            // 버퍼가 가득 찾을때 대기
            while (queue.size() == capacity) {
                try {
                    log.info("[생산자] 버퍼가 가득참으로 인하여 작업 대기 item = {}", item);
                    queueFullCondition.await(); // 버퍼가 가득 찼을 때 대기, 생산자 대기 집합에서 대기
                } catch (InterruptedException e) {
                }
            }

            queue.add(item);
            queueEmptyCondition.signalAll(); // 생산자 작업 완료 이후 소비자 대기 집합 깨움
            log.info("[생산자] 작업 완료 item = {}", item);


        } finally {
            lock.unlock();
        }
    }

    public T consume() {
        try {
            lock.lock();

            log.info("[소비자] 작업 시작");

            // 버퍼가 비어있을 경우 대기
            while (queue.isEmpty()) {
                try {
                    log.info("[소비자] 버퍼가 비어있음으로 인하여 작업 대기");
                    queueEmptyCondition.await(); // 버퍼가 비어있을 때 대기, 소비자 대기 집합에서 대기
                } catch (InterruptedException e) {
                }
            }

            T item = queue.poll();
            queueFullCondition.signalAll(); // 소비자 작업 완료 이후 생산자 대기 집합 깨움
            log.info("[소비자] 작업 완료 item = {}", item);
            return item;
        } finally {
            lock.unlock();
        }

    }

    public int size() {
        return queue.size();
    }
}
```
[다중 조건 변수 활용 예제](./ConditionMain2.java)




## Lock 구현체

### ReentrantLock
- `ReentrantLock`은 `synchronized` 키워드와 유사하게 동작하는 락 구현체로써 `java.util.concurrent.locks`의 특징을 가지고 있으며 추가적인 기능을 제공한다.
  - 동일한 스레드가 여러 번 락을 획득할 수 있는 재진입(reentrant) 기능을 제공한다. `synchronized` 키워드도 재진입 기능을 제공한다.
    - 만약 재진입성을 제공하지 않는다면 동일한 스레드가 락 해제를 하지 않은 상태에서 다시 락을 획득하려 할때 데드락이 발생할 수 있다.
  - 공정성(fairness) 옵션을 제공하여, 대기 중인 스레드가 공정하게 락을 획득할 수 있도록 한다. 이를 통해서 스레드 기아를 방지할 수 있다.

### ReentrantReadWriteLock
- `ReentrantReadWriteLock`은 `ReadLock`과 `WriteLock`을 제공하여 읽기 작업과 쓰기 작업을 분리하여 동기화할 수 있는 락 구현체이다.
  - `WriteLock`: 하나의 스레드가 `WriteLock`을 획득하면 다른 스레드는 `ReadLock`과 `WriteLock`을 획득할 수 없다.
    - 즉, `WriteLock`을 획득한 스레드가 작업을 수행하는 동안 다른 스레드는 해당 리소스에 접근할 수 없다. 
  - `ReadLock`: 여러 스레드가 `ReadLock`을 획득할 수 있으며, 하나의 스레드가 `WriteLock`을 획득한 경우에는 `ReadLock`을 획득할 수 없다.
    - 즉, `ReadLock`을 획득한 스레드가 작업을 수행하는 동안 다른 스레드는 해당 리소스에 접근할 수 있다.
- 읽기 작업이 빈번하고 쓰기 작업이 드물지만 데이터 동기화가 필요한 경우에 유용하게 사용될 수 있다.
  - 시스템 설정 정보
  - 데이터 캐싱

### StampedLock


> [Java Docs Tutorial > Lock](https://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html) <br/>
> [Baeldung > Guide to java.util.concurrent.Locks](https://www.baeldung.com/java-concurrent-locks)