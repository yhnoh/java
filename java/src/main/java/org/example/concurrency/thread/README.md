## 멀티 스레드 (Multi Thread)란?

![](img/single_thread_multi_thread.png)
- 멀티 스레드는 ***하나의 프로세스에서 여러 개의 스레드를 동시에 실행하는 것***을 의미한다.
- 웹 서버를 떠올려 보면 하나의 웹 서버(프로세스)가 여러 사용자의 요청(스레드)를 동시에 처리하는 것을 알 수 있다. 만약 싱글 스레드로 구현되어 있다면, 웹 서버는 한 번에 하나의 요청만 처리할 수 있어 매우 비효율적일 것이다.
- 멀티 스레드에 대한 자세한 내용을 알기 전에 먼저 운영체제, 프로세스, 스레드에 대한 개념을 이해하는 것이 중요하다.

### 운영체제와 프로세스와 스레드

#### 운영체제
- 운영체제는 프로세스를 관리하는 주체로써 ***자원(Resource)를 효율적으로 관리하는 역할을*** 한다.
  > 물론 자원 관리를 제외한 다른 역할도 한다. (사용자 인터페이스 제공, 보안, 네트워크 지원..)
- 대표적인 자원 관리 역할로써 CPU 관리와 메모리 관리가 존재한다.
  - CPU 관리: CPU 스케줄러를 통해서 여러 실행 가능한 작업들 중에서 어떤 ***작업(커널 수준 스레드: Kernel-level Thread, 프로세스)을 얼마동안 할당할지를 결정***하고, Context Switching으로 현재 ***작업중이던 작업을 저장하고 다른 작업을 수행***한다.
  - 메모리 관리: 프로세스별로 독립적인 메모리 공간을 할당하고, 프로세스간의 메모리 영역을 침범하지 못하도록 보호한다.

#### 프로세스
- 프로그램이 메모리에 로드되어 운영체제에 실행될때 이를 프로세스라고한다.
- 프로세스는 운영체제로 부터 ***독립적인 자원을 할당받아 실행되기 때문에 자신만의 가상 주소 공간과 메모리를 공유하지 않는 특징***이 있기 때문에 프로세스간의 메모리에 직접 접근할 수 없다.
- 프로세스가 생성될때 운영체제는 ***프로세스를 관리하기 위하여 프로세스에 대한 중요한 정보를 저장하고 관리하는데 이를 PCB(Process Control Block)*** 라고 한다.
  - 운영체제는 PCB를 바탕으로 현재 작업중이던 프로세스의 상태를 저장하고 다음에 작업해야할 프로세스의 상태를 복원하는 과정을 통해서 Context Switching을 하며 멀티태스킹이 가능해진다.

#### 스레드
- ***스레드는 프로세스내에서 실행되는 하나의 경량 프로세스(lightweight process), 실행 단위***로써 하나의 프로세스는 하나 이상의 스레드를 가질 수 있다.
- 스레드는 ***프로세스 내에서 실행되기 때문에 동일한 가상 주고 공간과 메모리를 공유할 수 있다는 특징***이 있으며 이로 인해서 스레드의 Context Switching이 프로세스의 Context Switching 보다 저렴하다.
  - 스레드의 Context Switching이 프로세스보다 저렴한 이유는 자원을 공유하기 때문에 진행중이던 작업의 상태 저장이나 다른 작업을 복원하는 과정이 프로세스 보다 간단하고 적기 때문이다.

### 멀티 스레드가 등장한 이유는?
- 초기 프로그램은 입력을 받아 처리하고 결과를 출력하는 단순한 구조였다. 이로 인해서 ***I/O 작업이 발생하면 CPU는 아무 일도 하지 못하고 대기한 이후에 결과를 출력하는 방식***이었다.
    - 즉 CPU가 I/O 작업이 끝날 때까지 놀고 있는 시간이 많았다. 이는 ***자원을 효율적으로 사용하지 못하는 문제를 야기***했다.

![](./img/multi_process_multi_thread.png)
- 이를 해결하기 위하여 멀티 프로세스와 멀티 스레드가 등장하게 되었다. 멀티 스레드와 멀티 프로세스 모두 병렬적으로 작업을 처리할 수 있도록 하여 ***CPU의 유휴 시간을 줄이고, 자원을 효율적으로 사용***할 수 있도록 한다.
    - 특히 공유 자원(ex: 데이터베이스, 파일)을 사용하는 프로그램의 경우, 멀티 프로세스 및 멀티 스레드를 통해 자원을 효율적으로 사용할 수 있다.
- 특히 멀티 스레드가 웹 서버와 같은 I/O 작업이 빈번하며, 자원의 공유가 잦고 요청이 잦은 경우에 더욱 유리하다.
    - 메모리 공유로 인한 효율적 자원 사용: 멀티 스레드는 같은 프로세스 내에서 메모리를 공유하기 때문에 적은 메모리를 사용하고, 스레드 간의 자원 공유가 쉽고 빠르다. 반면 멀티 프로세스는 각 프로세스가 독립적인 메모리 공간을 가지기 때문에 많은 메모리를 사용하고. 자원 공유를 위해서는 별도의 통신 메커니즘이 필요하다.
    - Context Switching 비용이 낮음: 멀티 스레드는 프로세스 내에서 스레드 간의 전환이 이루어지기 때문에, 컨텍스트 스위칭 오버헤드가 적다. 반면 멀티 프로세스는 각 프로세스 간의 전환이 이루어지기 때문에, 컨텍스트 스위칭 오버헤드가 크다.
    - 요청 처리 속도 빠름: 멀티 스레드는 같은 프로세스 내에서 스레드 간의 통신이 빠르기 때문에, 요청 처리 속도가 빠르다. 반면 멀티 프로세스는 각 프로세스 간의 통신이 느리기 때문에, 요청 처리 속도가 느리다.



## Java 스레드
- Java에서 동시성 애플리케이션을 만들기 위하여 `java.lang.Thread` 클래스를 제공한다. Java에서 제공하는 스레드는 운영체제에서 제공하는 커널 스레드와 1:1로 매핑되며 운영체제의 스케쥴러에 의해서 스레드를 관리하게 된다.
    > Thread supports the creation of platform threads that are typically mapped 1:1 to kernel threads scheduled by the operating system
    - 참고로 커널 수준 스레드와 1:1 매핑이되는데 Java에서 많은 수의 스레드를 생성하고 작업이 가능한 이유는 자바 서버 프로그래밍시 I/O 작업이 대부분인 I/O Bound 프로세스이기 때문이다. I/O 작업으로 인한 대기 발생시 다른 스레드가 작업을 수행가능하기 때문에 커널 수준 스레드의 개수보다 더 많은 스레드를 생성할 수 있다. 
- `java.lang.Thread` 클래스를 이용하여 개발자가 직접 스레드를 생성하고 관리를 하거나, [고수준 동시성 API](https://docs.oracle.com/javase/tutorial/essential/concurrency/highlevel.html)를 통해서 동시성 애플리케이션을 제작하는 방법을 제공한다.

### Java 스레드 생성 및 실행 방법
- Java에서 스레드를 생성하고 실행하기 위해서는 `Thread` 클래스를 상속받아서 사용하거나 `Runnable` 인터페이스를 통해서 작업 내역을 정의하여 사용하는 두가지 방식이 존재한다.
- 두가지 방식 모두 스레드를 생성하고 실행시킬 수 있지만, ***Runnable 인터페이스를 이용한 방식***은 다른 클래스를 상속 받을 수 있기 때문에 ***좀 더 유연한 구조***를 가질 수 있으며, Java에서 제공하는 ***동시성 고수준 API에서도 사용***할 수 있다는 장점이 있다.
    > 참고로 run() 메서드를 직접 실행하게 되면 신규 스레드를 생성해서 실행시키는 것이 아니기 때문에 스레드를 사용하기 위해서는 start()를 활용하자.

> [예제](./ThreadStartMain1.java)

### Java 스레드 대기/블록킹
- Java는 실행중인 스레드를 대기/블록킹할 수 있도록 만드는 `sleep(), join()`메서드를 제공한다.
- `sleep(), join()` 메서드 둘다 작업에 대한 대기/블록킹을 할 수 있도록 제공하지만 목적이 조금 다르다.
- `sleep()`은 Thread 정적 메서드로써 ***현재 실행 중인 스레드의 실행을 특정 시간 동안 대기*** 시킨다. 즉 자기 자신을 대기하기 위하여 사용하는 메서드이다.
- `join()`은 Thread 인스턴스 메서드로써 ***다른 스레드의 작업이 완료될때까지 대기***할 수 있다. 예를 들어 메인 스레드 내에서 생성된 스레드의 작업이 완료될때 까지 메인 스레드는 대기할 수 있다.

> [sleep 예제](./ThreadSleepMain1.java) <br/>
> [join 예제](./ThreadSleepMain1.java) <br/>


### Java 스레드 인터럽트
- 인터럽트는 스레드가 ***현재 진행중인 작업을 중지하고 개발자가 지정한 다른 작업을 수행해야한다는 신호***를 보내는 것을 의미한다.
  - 인터럽트를 활용하지 않고 스레드를 강제종료하게되면 스레드가 사용중이던 자원에 대한 정리가 제대로 이루어 지지 않을 수 있기 때문에, 강제종료보다는 인터럽트를 사용하여 작업을 진행중이던 스레드가 작업을 정리하고 종료할 수 있도록 설계하는 것이 좋다.
- Java에서는 `java.lang.Thread` 클래스 내부의 `interrupted` 플래그 변수와 스레드를 ***인터럽트 상태로 만들 수 있는 `interrupt()` 인스턴스 메서드, 인터럽트 상태에 있는 스레드를 다시 원상태로 복구해주는 `interrupted()` 정적 메서드***를 제공한다. 또한 인터럽트의 상태를 확인할 수 있는 `isInterrupted()` 정적 메서드를 제공한다.
- 스레드를 인터럽트 상태로 만들어도 인터럽트 상태가 해제되는 경우가 있다. `InterruptedException`을 발생시키는 모든 메서드는 외부에서 해당 스레드를 인터럽트 상태로 만들어도 해제된다. 
    > By convention, any method that exits by throwing an InterruptedException clears interrupt status when it does so. However, it's always possible that interrupt status will immediately be set again, by another thread invoking interrupt.

> [interrupt 예제](./ThreadInterruptMain1.java) <br/>
> [interrupted 예제](./ThreadInterruptMain2.java) <br/>
> [InterruptedException 예제](./ThreadInterruptMain3.java) <br/>

## 스레드 상태
![](./thread_state.png)
- 스레드는 생성/실행/대기/종료의 생명 주기를 가지고 있으며 각 상황에 따른 상태를 6가지로 표현할 수 있다.
  - 일반적인 스레드의 생명주기에 따른 상태 변화: NEW -> RUNNABLE -> (BLOCKED / WAITING / TIMED_WAITING) -> RUNNABLE -> TERMINATED
- 스레드의 단순 상태를 아는것도 중요하지만 ***왜 그런 상태가 되었는지를 이해하는것이 더 중요***하다. 만약 그렇지 못한다면 아래와같은 문제가 발생할 수 있다.
  - 스레드가 왜 대기 상태에 빠졌는지 이해하지 못하고 불필요한 스레드를 지속적으로 생성으로 인한 성능 악화
  - 데드락이나 스레드 기아(Starvation)와 같은 상황 발생시 문제에 대한 원인 파악의 어려움
  - 위와 같은 이유로 멀티 스레드 프로그램 개발시 안정적인 애플리케이션 개발의 어려움

### Java의 스레드 상태
- NEW: 스레드 객체가 생성되었지만 `start()`메서드가 호출되지 않아 아직 실행되지 않은 상태
- RUNNABLE: `start()` 메서드가 호출되어 실행 중인 상태
- BLOCKED: syncronized 블록이나 메서드에 진입하기 위해서 모니터 락(monitor lock)이 해제되기를 기다리는 상태, `syncronized`
- WAITING: 다른 스레드가 작업이 종료될때까지 무기한으로 대기하는 상태, `join()`
- TIMED_WAITING: 지정된 시간동안 대기하는 상태, `sleep()`
- TERMINATED: `run()`메서드가 실행을 완료했거나 예외 발생으로 스레드가 종료된 상태

> [스레드 상태 및 생명 주기에 대한 예제](./ThreadState1.java) <br/>
> [스레드 상태 WAITING, TIMED_WAITING 예제](./ThreadState1.java) <br/>
> [스레드 상태 BLOCKED 예제](./ThreadState1.java) <br/>



## Reference
> [완전희 정복하는 프로세스 vs 스레드 개념, inpa](https://inpa.tistory.com/entry/%F0%9F%91%A9%E2%80%8D%F0%9F%92%BB-%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4-%E2%9A%94%EF%B8%8F-%EC%93%B0%EB%A0%88%EB%93%9C-%EC%B0%A8%EC%9D%B4) <br/>
> [How Java thread maps to OS thread?](https://medium.com/@unmeshvjoshi/how-java-thread-maps-to-os-thread-e280a9fb2e06)<br/>
> [Java Docs > Thread](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html) <br/>
> [Java Docs > Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/threads.html)