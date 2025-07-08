## java.util.concurrent.locks
- `java.util.concurrent.locks` 패키지는 `synchronized` 키워드를 이용한 암묵적 동기화와 달리 ***명시적인 동기화 방법***이다.
  - 명시적이라는 의미는 개발자가 직접 ***락을 획득하고 해제하는 과정을 작성하고 제어***한다.
- `synchronized` 키워드와 매우 유사하게 작동되지만, `synchronized`의 한계점을 보완하고 좀 더 유연하게 사용할 수 있다.
  - ***락 획득에 대한 시도 및 대기 시간 설정 가능***
  - ***락 획득에 대한 인터럽트 가능***
  - 스레드의 공정성을 보장할 수 있는 방법 제공
  - 모니터락의 매커니즘 활용 가능 (Mutex + Condition Variable)하며, 하나의 Lock 객체에 여러 개의 Condition 객체 생성 가능
  - 

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

### java.util.concurrent.locks.Condition
> [Java Docs Tutorial > Lock](https://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html)