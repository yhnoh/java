## java.util.concurrent.locks
- `java.util.concurrent.locks` 패키지는 `synchronized` 키워드를 이용한 암묵적 동기화와 달리 ***명시적인 동기화 방법***이다.
  - 명시적이라는 의미는 개발자가 직접 ***락을 획득하고 해제하는 과정을 작성하고 제어***한다.
- `synchronized` 키워드와 매우 유사하게 작동되지만, `synchronized`의 한계점을 보완하고 좀 더 유연하게 사용할 수 있다.
  - ***타임아웃 설정 가능***
  - ***인터럽트 가능***  
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


### java.util.concurrent.locks.Condition
> [Java Docs Tutorial > Lock](https://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html)