## java.util.concurrent 고수준 비동기 API 프로그래밍의 필요성
- 개발자가 애플리케이션을 개발하면서 `Thread`와 `Runnable`을 직접 사용하여 비동기 작업을 수행하는 경우 많은 것을 신경쓰면서 코드를 작성해야한다. 
  - 프로세스 내에 스레드의 생명 주기(생성/종료)를 직접 관리해야하는 어려움
  - 스레드 갯수 관리의 어려움 (프로세스의 시작 및 실행 시점)
  - 프로그램 종료시 현재 작업중인 스레드를 어떻게 처리할지 고민해야하는 어려움
  - 위의 문제를 고려해가며 비지니스 로직을 작성해야하는 어려움
- Java 1.5부터 제공되는 `java.util.concurrent` 패키지의 `Executor`는 이러한 문제를 해결하기 위한 고수준 비동기 API를 제공한다.

## 스레드 풀
- 현대의 웹 애플리케이션에서 서버의 역할은 동시에 많은 클라이언트의 요청을 처리하여 빠른 응답을 제공하는 것이다.
- 만약 사용자의 요청에 따라 매번 새로운 스레드를 생성한다면 아래와 같은 문제가 발생할 수 있다.
  - 스레드 생성 비용으로 인한 성능 저하 발생
  - 스레드 갯수 관리의 어려움으로 인한 서버 자원 고갈
- 이러한 문제를 해결하기 위해서는 애플리케이션의 실행시점 부터 종료 시점까지 스레드를 관리할 수 있는 스레드 풀(Thread Pool)이 필요하다.
  - 스레드 풀은 요청 시점 마다 스레드를 생성하는 것이 아닌, 미리 생성된 스레드를 재사용할 수 있다.
  - 스레드 풀은 스레드의 갯수를 관리하여 서버 자원의 고갈을 방지할 수 있다.

### 스레드 풀의 동작 방식

![](./image/threadpool.png)

1. 클라이언트는 작업을 제출한다.
2. 제출한 작업은 스레드 풀에 있는 작업 큐에 저장된다.
3. 스레드 풀에 있는 스레드는 작업 큐에 작업을 가져와 작업을 수행한다.
   - 이때 스레드 풀은 미리 생성된 스레드를 사용하여 작업을 수행한다.
   - 생성된 스레드 수만큼 작업을 동시에 수행할 수 있다.
4. 작업 큐에 작업이 없을 경우, 생성된 스레드를 종료시키지 않고 대기 상태로 만들어 새로운 작업이 들어올 때까지 기다린다.

### 간단한 스레드 풀 구현해보기
- 위 동작 방식을 기반으로 [간단한 스레드 풀 구현](./MyThreadPool.java)을 구현해보자.

```java
public class MyThreadPool {
    
    private final BlockingQueue<Runnable> taskQueue;
    private final List<Worker> workers = new ArrayList<>();
    private boolean isShutdown = false;

    public MyThreadPool(int corePoolSize, int taskSize) {
        taskQueue = new LinkedBlockingQueue<>(taskSize);

        // 스레드를 미리 생성한 이후 실행하여, Thread를 RUNNABLE 상태로 만들어 작업을 수행할 수 있도록 한다.
        for (int i = 0; i < corePoolSize; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.thread.start();
        }

    }

    public void submit(Runnable task) {
        if (task == null) {
            throw new IllegalArgumentException("task cannot be null");
        }

        // BlockingQueue의 offer() 메서드는 큐가 가득 찼을 경우, 작업을 추가하지 않고 false를 반환
        if (!taskQueue.offer(task)) {
            throw new IllegalStateException("taskQueue is full");
        }
    }

    public synchronized boolean isShutdown() {
        return isShutdown;
    }

    // shutdown 메서드는 스레드 풀이 관리하고 있는 모든 스레드를 종료시키기 위한 메서드
    public synchronized void shutdown() {
        isShutdown = true;
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    class Worker implements Runnable {

        private boolean isShutdown = false;
        private final Thread thread = new Thread(this);

        @Override
        public void run() {
            while (!isShutdown) {
                Runnable task = null;
                try {
                    // BlockingQueue의 take() 메서드는 큐가 비어있을 경우, 작업이 추가될 때까지 대기한다.
                    // shutdown() 메서드가 호출 되면, 작업이 추가될 때까지 대기하지 않는다.
                    task = taskQueue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (task != null) {
                    task.run();
                }
            }
        }

        // shutdown 메서드는 Worker 스레드를 인터럽트 시켜 작업을 중지하기위한 메서드
        public synchronized void shutdown() {
            isShutdown = true;
            thread.interrupt();
        }
    }
}
```
- 위의 코드는 간단한 스레드 풀을 구현한 예시로, `BlockingQueue`를 사용하여 작업 큐를 관리하고, `Worker` 클래스를 통해 작업 큐에서 작업을 가져와 실행하는 구조이다.
- `MyThreadPool` 클래스를 생성하게 되면, 지정된 개수의 스레드를 미리 생성하고 작업 큐를 초기화한다.
  - `Worker` 클래스에서 `isShutdown` 변수를 통해 `shutdown()` 메서드가 호출되기 전까지 스레드를 계속 실행하도록 한다.
  - `taskQueue.take()` 메서드를 사용하여 작업 큐에서 작업을 가져와 수행하고, 작업 큐가 비어있을 경우에는 작업이 추가될 때까지 대기한다.
- 클라이언트는 `submit(Runnable task)` 메서드를 통해 작업을 제출할 수 있으며, 해당 작업을 작업 큐에 추가한다. 만약 작업 큐가 가득 찼을 경우 예외가 발생한다.




## Java에서 제공하는 스레드풀
- `Executor`는 Java 1.5부터 제공되는 인터페이스로, 대부분의 구현체들이 쓰레드의 생성과 관리를 위한 스레드풀을 제공한다.
- 때문에 개발자가 직접 스레드풀을 구현할 필요 없이, `Executor` 인터페이스의 구현체를 사용하여 스레드풀을 쉽게 사용할 수 있다.

### Executor 인터페이스
- `Executor`는 작업을 실행하기 위한 단순한 인터페이스로, `Runnable` 객체를 실행하는 메서드인 `execute(Runnable command)`를 제공한다.
- 때문에 
### ExecutorService 인터페이스
  

### ExecutorService
- `ExecutorService`는 `Executor`의 하위 인터페이스로, 스레드 풀을 관리하고 작업을 제출할 수 있는 기능을 제공한다.


- `Executor`는 단순히 새로운 작업을 실행하기 위한 인터페이스로써, 
> [The Java Tutorials > Executors](https://docs.oracle.com/javase/tutorial/essential/concurrency/executors.html)
> [Baeldung > Introduction to Thread Pools in Java](https://www.baeldung.com/thread-pool-java-and-guava)
> [Baeldung > A Guide to the Java ExecutorService](https://www.baeldung.com/java-executor-service-tutorial)