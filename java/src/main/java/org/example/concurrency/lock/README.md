## java.util.concurrent.locks
- `java.util.concurrent.locks` 패키지는 `synchronized` 키워드를 이용한 암묵적 동기화와 달리 명시적인 동기화 방법이다.
  - 명시적이라는 의미는 개발자가 직접 락을 획득하고 해제하는 과정을 작성하고 제어한다.
- `synchronized` 키워드와 매우 유사하게 작동되지만, `synchronized`의 한계점을 보완하고 좀 더 유연하게 사용할 수 있다.
  - 타임아웃 설정 가능
  - 인터럽트 가능
  - 모니터락의 매커니즘 활용 가능 (Mutex + Condition Variable)하며, 하나의 Lock 객체에 여러 개의 Condition 객체 생성 가능

### java.util.concurrent.locks.Condition
> [Java Docs Tutorial > Lock](https://docs.oracle.com/javase/tutorial/essential/concurrency/newlocks.html)