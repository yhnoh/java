## 스레드와 프로세스

## 스레드 상태
![](./thread_state.png)

### Java 스레드

#### 스레드 생성 방법

- java는 `java.lang.Thread`클래스를 통해서 스레드를 생성하고 실행할 수 있다.
- 직접 Thread클래스를 상속받아서 스레드를 생성하거난 Runnable 인터페이스를 통해서 생성할 수 있으며 

- java에서 스레드를 사용하기 위해서는 `java.lang.Thread`클래스를 통해서 생성할 수 있다.
- java.lang.Runnable 인터페이스의 run()
- java.lang.Thread 클래스의 run()
- Runnable 인터페이스를 통해 구현한 구현체는 다른 클래스를 상속 받을 수 있다는 장점이 있으며, 이는 좀더 유연한 구조로 나아갈 수 있으며, Java에서 제공하는 동시성에 대한 고수준 API에도 적용할 수
  있다는 장점이 있다.

#### 스레드 대기/블록킹

#### 스레드 인터럽트(Thread Interrupt)

- 인터럽트는 스레드가 ***현재 진행중인 작업을 중지하고 개발자가 지정한 다른 작업을 수행해야한다는 신호***를 보내는 것을 의미한다.
    - 인터럽트를 활용하지 않고 스레드를 강제종료하게되면 스레드가 사용중이던 자원에 대한 정리가 제대로 이루어 지지 않을 수 있기 때문에, 강제종료보다는 인터럽트를 사용하여 작업을 진행중이던 스레드가 작업을
      정리하고 종료할 수 있도록 설계하는 것이 좋다.


- Thread.interrupted(): 인터럽트 상태가 true인 경우 인터럽트 상태를 초기화한다.
- InterruptedException이 발생하면 인터럽트 상태를 다시 초기화한다.
  > IllegalArgumentException – if the value of millis is negative
  InterruptedException – if any thread has interrupted the current thread. The interrupted status of the current thread
  is cleared when this exception is thrown.