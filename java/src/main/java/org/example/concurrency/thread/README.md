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
> [join 예제](./ThreadJoinMain1.java) <br/>


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
> [스레드 상태 WAITING, TIMED_WAITING 예제](./ThreadState2.java) <br/>
> [스레드 상태 BLOCKED 예제](./ThreadState3.java) <br/>


## 스프링에서의 멀티 스레드 실무

### 스프링의 스레드 관리

#### Tomcat 스레드 풀
- 스프링 부트는 내장 Tomcat을 사용하며, Tomcat은 **요청당 스레드 모델(Thread-per-Request)**을 사용한다.
- 각 HTTP 요청은 스레드 풀의 하나의 스레드에 할당되어 처리되며, 요청 처리가 완료되면 스레드는 풀로 반환된다.

**기본 설정 (application.yml)**
```yaml
server:
  tomcat:
    threads:
      max: 200          # 최대 스레드 수 (기본값: 200)
      min-spare: 10     # 최소 유휴 스레드 수 (기본값: 10)
    accept-count: 100   # 대기 큐 크기 (기본값: 100)
    max-connections: 8192  # 최대 동시 연결 수 (기본값: 8192)
```

**스레드 풀 튜닝 가이드**
- **최대 스레드 수**: CPU 집약적 작업은 CPU 코어 수 + 1, I/O 집약적 작업은 더 많은 스레드를 할당
- **모니터링**: 스레드 사용률이 지속적으로 80% 이상이면 스레드 풀 크기 조정 필요
- **주의사항**: 무작정 스레드 수를 늘리면 Context Switching 비용 증가로 오히려 성능 저하 발생 가능

#### 스레드 풀 고갈 문제와 해결 방법
- **문제 상황**: 모든 스레드가 작업 처리 중이면 새로운 요청은 대기 큐에 쌓이고, 큐가 가득 차면 요청이 거부됨
- **원인**:
  - 느린 외부 API 호출 (동기 블로킹 방식)
  - 데이터베이스 쿼리 지연
  - 비효율적인 비즈니스 로직
  - 동기화로 인한 스레드 대기

**해결 방법**
```java
// 1. 비동기 처리로 전환 (@Async 활용)
@Async
public CompletableFuture<String> callExternalApi() {
    // 외부 API 호출
    return CompletableFuture.completedFuture(result);
}

// 2. WebClient 사용 (논블로킹 방식)
@Service
public class ExternalApiService {
    private final WebClient webClient;

    public Mono<String> callApi() {
        return webClient.get()
            .uri("/api/endpoint")
            .retrieve()
            .bodyToMono(String.class);
    }
}

// 3. Timeout 설정
@Bean
public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory factory =
        new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(3000);  // 연결 타임아웃: 3초
    factory.setReadTimeout(5000);     // 읽기 타임아웃: 5초
    return new RestTemplate(factory);
}
```

### @Async와 비동기 처리

#### @Async 어노테이션 사용법
```java
// 1. 설정 활성화
@Configuration
@EnableAsync
public class AsyncConfig {
    // 비동기 처리를 위한 TaskExecutor 빈 등록
}

// 2. 비동기 메서드 정의
@Service
public class NotificationService {

    @Async
    public void sendEmail(String to, String message) {
        // 이메일 발송 로직 (별도 스레드에서 실행)
        log.info("Sending email on thread: {}",
            Thread.currentThread().getName());
    }

    @Async
    public CompletableFuture<String> processData(String data) {
        // 처리 로직
        String result = heavyProcess(data);
        return CompletableFuture.completedFuture(result);
    }
}

// 3. 호출
@RestController
public class OrderController {
    private final NotificationService notificationService;

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        // 주문 처리
        Order saved = orderService.save(order);

        // 비동기로 이메일 발송 (메인 스레드 블로킹 없음)
        notificationService.sendEmail(order.getEmail(), "주문 완료");

        return ResponseEntity.ok(saved);
    }
}
```

#### TaskExecutor 설정
```java
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);           // 기본 스레드 수
        executor.setMaxPoolSize(10);           // 최대 스레드 수
        executor.setQueueCapacity(25);         // 큐 크기
        executor.setThreadNamePrefix("Async-"); // 스레드 이름 접두사
        executor.setKeepAliveSeconds(60);      // 유휴 스레드 유지 시간
        executor.setRejectedExecutionHandler(  // 거부 정책
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
```

#### 비동기 처리시 주의사항

**1. 예외 처리**
```java
// void 반환 메서드의 예외 처리
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Async method {} threw exception: {}",
            method.getName(), ex.getMessage());
        // 예외 처리 로직 (알림, 로깅 등)
    }
}

// CompletableFuture 반환 메서드의 예외 처리
@Async
public CompletableFuture<String> processWithErrorHandling(String data) {
    try {
        String result = process(data);
        return CompletableFuture.completedFuture(result);
    } catch (Exception e) {
        return CompletableFuture.failedFuture(e);
    }
}
```

**2. 트랜잭션 주의사항**
```java
@Service
public class OrderService {

    @Transactional
    public void createOrder(Order order) {
        orderRepository.save(order);

        // 주의: @Async 메서드는 별도 스레드에서 실행되므로
        // 현재 트랜잭션 컨텍스트를 공유하지 않음
        notificationService.sendNotification(order);  // 트랜잭션 외부
    }
}

// 올바른 방법
@Service
public class OrderService {

    @Transactional
    public void createOrder(Order order) {
        orderRepository.save(order);
        // 트랜잭션 커밋 후 이벤트 발행
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleOrderCreated(OrderCreatedEvent event) {
        // 트랜잭션 커밋 후 비동기 실행
        notificationService.sendNotification(event.getOrder());
    }
}
```

**3. @Async 동작하지 않는 경우**
- 같은 클래스 내에서 호출 (프록시 우회)
- private 메서드에 적용
- @EnableAsync 설정 누락

```java
// 잘못된 예
@Service
public class MyService {
    public void publicMethod() {
        asyncMethod();  // 같은 클래스 내 호출 - 비동기 동작 안 됨
    }

    @Async
    public void asyncMethod() {
        // ...
    }
}

// 올바른 예
@Service
public class MyService {
    private final AsyncService asyncService;

    public void publicMethod() {
        asyncService.asyncMethod();  // 다른 빈 호출 - 비동기 동작함
    }
}
```

### 스프링에서의 동시성 문제

#### 싱글톤 빈과 스레드 안전성
- 스프링의 기본 빈 스코프는 **싱글톤**으로, 모든 스레드가 동일한 인스턴스를 공유한다.
- **상태를 가진 필드**가 있으면 멀티 스레드 환경에서 동시성 문제가 발생할 수 있다.

```java
// 잘못된 예 - 스레드 안전하지 않음
@Service
public class UserService {
    private User currentUser;  // 상태를 가진 필드 - 위험!

    public void processUser(Long userId) {
        this.currentUser = userRepository.findById(userId);
        // 여러 스레드가 동시에 currentUser를 변경하면 문제 발생
        someProcess(currentUser);
    }
}

// 올바른 예 1 - 로컬 변수 사용
@Service
public class UserService {
    public void processUser(Long userId) {
        User user = userRepository.findById(userId);  // 로컬 변수
        someProcess(user);
    }
}

// 올바른 예 2 - 불변 객체 사용
@Service
public class ConfigService {
    private final Config config;  // final - 불변

    public ConfigService(Config config) {
        this.config = config;
    }
}

// 올바른 예 3 - 동기화 (필요시)
@Service
public class CounterService {
    private final AtomicInteger counter = new AtomicInteger(0);

    public int increment() {
        return counter.incrementAndGet();  // 원자적 연산
    }
}
```

#### ThreadLocal 활용
- **ThreadLocal**은 각 스레드마다 독립적인 변수를 저장할 수 있는 메커니즘이다.
- 스프링에서는 Security Context, Transaction 정보 등을 저장하는데 사용된다.

```java
// Spring Security의 SecurityContext 저장
public class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> contextHolder =
        new ThreadLocal<>();

    public static SecurityContext getContext() {
        return contextHolder.get();
    }

    public static void setContext(SecurityContext context) {
        contextHolder.set(context);
    }
}

// 사용자 정의 ThreadLocal 예제
public class UserContextHolder {
    private static final ThreadLocal<UserContext> context = new ThreadLocal<>();

    public static void setUser(UserContext user) {
        context.set(user);
    }

    public static UserContext getUser() {
        return context.get();
    }

    public static void clear() {
        context.remove();  // 메모리 누수 방지를 위해 반드시 제거
    }
}

// Filter에서 설정
@Component
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            // 요청마다 사용자 컨텍스트 설정
            UserContext context = extractUserContext(request);
            UserContextHolder.setUser(context);
            chain.doFilter(request, response);
        } finally {
            // 반드시 제거 (메모리 누수 방지)
            UserContextHolder.clear();
        }
    }
}
```

**ThreadLocal 주의사항**
- **메모리 누수**: 사용 후 반드시 `remove()` 호출
- **스레드 풀 환경**: 스레드가 재사용되므로 이전 요청의 데이터가 남아있을 수 있음
- **@Async와 함께 사용 불가**: 별도 스레드에서 실행되므로 ThreadLocal 값이 전달되지 않음

#### @Transactional과 멀티 스레드
```java
@Service
public class OrderService {

    @Transactional
    public void processOrder(Order order) {
        orderRepository.save(order);

        // 잘못된 예: @Async 메서드는 별도 스레드에서 실행
        // 트랜잭션 컨텍스트가 공유되지 않음
        asyncService.updateInventory(order);

        // 만약 updateInventory에서 예외 발생해도
        // 이미 별도 스레드이므로 롤백 안 됨
    }
}

// 올바른 방법 1: 트랜잭션 분리
@Service
public class OrderService {

    @Transactional
    public void processOrder(Order order) {
        orderRepository.save(order);
    }

    public void processOrderWithNotification(Order order) {
        processOrder(order);  // 트랜잭션 커밋됨
        asyncService.updateInventory(order);  // 별도 실행
    }
}

// 올바른 방법 2: 트랜잭션 이벤트 리스너 활용
@Service
public class OrderService {

    @Transactional
    public void processOrder(Order order) {
        orderRepository.save(order);
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(order));
    }
}

@Component
public class OrderEventListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreatedEvent event) {
        // 트랜잭션 커밋 후 비동기 실행
        inventoryService.updateInventory(event.getOrder());
    }
}
```

### 실무 팁

#### 스레드 덤프 분석 방법

**스레드 덤프 생성**
```bash
# 1. jstack 사용
jstack <PID> > thread_dump.txt

# 2. jcmd 사용 (권장)
jcmd <PID> Thread.print > thread_dump.txt

# 3. kill 시그널 사용 (Unix/Linux)
kill -3 <PID>  # 표준 출력 또는 로그 파일에 덤프 생성

# 4. JVisualVM, JConsole 등의 GUI 도구 사용
```

**스레드 덤프 분석 포인트**
```
// 1. 데드락 확인
Found one Java-level deadlock:
=============================
"Thread-1":
  waiting to lock monitor 0x00007f8c8c004e00 (object 0x00000007d5f7e0a0)
  which is held by "Thread-2"

// 2. BLOCKED 스레드 확인
"http-nio-8080-exec-10" #34 daemon prio=5 os_prio=0 tid=0x00007f8c8c123456
   java.lang.Thread.State: BLOCKED (on object monitor)
   - waiting to lock <0x00000007d5f7e0a0> (a java.lang.Object)

// 3. WAITING/TIMED_WAITING 스레드 확인
"http-nio-8080-exec-5" #29 daemon prio=5 os_prio=0
   java.lang.Thread.State: TIMED_WAITING (sleeping)
   at java.lang.Thread.sleep(Native Method)
```

**주요 분석 시나리오**
- **높은 CPU 사용률**: RUNNABLE 상태의 스레드가 무한 루프나 무거운 연산 수행
- **응답 지연**: BLOCKED, WAITING 상태의 스레드가 많으면 락 경합이나 대기 문제
- **스레드 풀 고갈**: "http-nio-" 접두사가 있는 모든 스레드가 RUNNABLE이면 스레드 부족

#### 스레드 풀 모니터링

**Spring Boot Actuator 활성화**
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: metrics, health, threaddump
  metrics:
    export:
      prometheus:
        enabled: true
```

**주요 메트릭 확인**
```java
// 1. Micrometer를 통한 커스텀 메트릭
@Configuration
public class ThreadPoolMetricsConfig {

    @Bean
    public MeterBinder threadPoolMetrics(
            @Qualifier("taskExecutor") ThreadPoolTaskExecutor executor) {
        return (registry) -> {
            Gauge.builder("thread.pool.active", executor,
                ThreadPoolTaskExecutor::getActiveCount)
                .description("Active thread count")
                .register(registry);

            Gauge.builder("thread.pool.size", executor,
                ThreadPoolTaskExecutor::getPoolSize)
                .description("Current pool size")
                .register(registry);

            Gauge.builder("thread.pool.queue.size", executor,
                e -> e.getThreadPoolExecutor().getQueue().size())
                .description("Queue size")
                .register(registry);
        };
    }
}

// 2. 프로그래밍 방식 모니터링
@RestController
public class MonitoringController {
    private final ThreadPoolTaskExecutor taskExecutor;

    @GetMapping("/monitoring/thread-pool")
    public Map<String, Object> getThreadPoolStatus() {
        ThreadPoolExecutor executor = taskExecutor.getThreadPoolExecutor();

        Map<String, Object> status = new HashMap<>();
        status.put("activeCount", executor.getActiveCount());
        status.put("corePoolSize", executor.getCorePoolSize());
        status.put("maximumPoolSize", executor.getMaximumPoolSize());
        status.put("poolSize", executor.getPoolSize());
        status.put("queueSize", executor.getQueue().size());
        status.put("taskCount", executor.getTaskCount());
        status.put("completedTaskCount", executor.getCompletedTaskCount());

        return status;
    }
}
```

**Prometheus + Grafana 대시보드 설정**
```yaml
# Prometheus 쿼리 예제
# 스레드 풀 사용률
(thread_pool_active / thread_pool_size) * 100

# 큐 적재율
(thread_pool_queue_size / thread_pool_queue_capacity) * 100

# 스레드 풀 효율성 (완료된 작업 비율)
rate(thread_pool_completed_tasks_total[5m])
```

**알림 설정 예시**
- 스레드 풀 사용률 > 80%: 경고
- 스레드 풀 사용률 > 95%: 긴급
- 큐 적재율 > 80%: 경고
- BLOCKED 상태 스레드 > 10개: 경고

#### Virtual Thread (Java 21+) 도입시 고려사항

**Virtual Thread란?**
- Java 21에서 정식 도입된 경량 스레드 (Project Loom)
- OS 스레드와 1:1 매핑이 아닌 M:N 매핑 (다수의 Virtual Thread : 소수의 OS Thread)
- I/O 작업시 자동으로 다른 작업으로 전환되어 효율성 극대화

**스프링 부트에서 Virtual Thread 활성화**
```yaml
# application.yml (Spring Boot 3.2+)
spring:
  threads:
    virtual:
      enabled: true
```

```java
// 프로그래밍 방식
@Configuration
public class VirtualThreadConfig {

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public AsyncTaskExecutor asyncTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
}
```

**Virtual Thread 사용 예제**
```java
// 기존 플랫폼 스레드
Thread platformThread = new Thread(() -> {
    System.out.println("Platform Thread");
});

// Virtual Thread
Thread virtualThread = Thread.ofVirtual().start(() -> {
    System.out.println("Virtual Thread");
});

// ExecutorService with Virtual Threads
try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        executor.submit(() -> {
            try {
                // 수만 개의 Virtual Thread도 부담 없이 생성 가능
                Thread.sleep(Duration.ofSeconds(1));
                return "Done";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Interrupted";
            }
        });
    }
}
```

**도입시 주의사항 및 고려사항**

1. **synchronized 블록 사용 주의**
```java
// 주의: synchronized 블록에서 I/O 작업 수행시 Virtual Thread의 이점 상실
synchronized (lock) {
    // I/O 작업 - Pinning 발생 (OS 스레드에 고정됨)
    callExternalApi();
}

// 대안: ReentrantLock 사용
private final ReentrantLock lock = new ReentrantLock();

lock.lock();
try {
    // I/O 작업 - Pinning 없음
    callExternalApi();
} finally {
    lock.unlock();
}
```

2. **ThreadLocal 사용 주의**
```java
// Virtual Thread는 생성 비용이 낮아 매우 많이 생성됨
// ThreadLocal 사용시 메모리 사용량 급증 가능
private static final ThreadLocal<ExpensiveObject> threadLocal = new ThreadLocal<>();

// 대안: Scoped Values (JEP 446, Preview)
// 또는 메서드 파라미터로 전달
```

3. **CPU 집약적 작업**
```java
// Virtual Thread는 I/O 중심 작업에 최적화
// CPU 집약적 작업은 플랫폼 스레드가 더 효율적

// I/O 작업 - Virtual Thread 적합
executor.submit(() -> {
    String data = httpClient.get("https://api.example.com");
    database.save(data);
});

// CPU 집약적 작업 - 플랫폼 스레드 사용
ExecutorService cpuBoundExecutor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors()
);
cpuBoundExecutor.submit(() -> {
    // 복잡한 계산
    heavyComputation();
});
```

4. **모니터링 및 디버깅**
```java
// Virtual Thread 정보 확인
Thread thread = Thread.currentThread();
boolean isVirtual = thread.isVirtual();

// JFR(Java Flight Recorder)로 Virtual Thread 모니터링
// jcmd <PID> JFR.start duration=60s filename=recording.jfr

// Thread Dump
// Virtual Thread는 매우 많을 수 있으므로 덤프 크기 주의
```

5. **점진적 도입 전략**
```java
@Configuration
public class HybridThreadConfig {

    // I/O 작업용 Virtual Thread
    @Bean("ioTaskExecutor")
    public AsyncTaskExecutor ioTaskExecutor() {
        return new TaskExecutorAdapter(
            Executors.newVirtualThreadPerTaskExecutor()
        );
    }

    // CPU 작업용 플랫폼 스레드
    @Bean("cpuTaskExecutor")
    public AsyncTaskExecutor cpuTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.initialize();
        return executor;
    }
}

@Service
public class HybridService {

    @Async("ioTaskExecutor")
    public CompletableFuture<String> ioTask() {
        // I/O 작업
        return CompletableFuture.completedFuture(callApi());
    }

    @Async("cpuTaskExecutor")
    public CompletableFuture<Integer> cpuTask() {
        // CPU 집약적 작업
        return CompletableFuture.completedFuture(calculate());
    }
}
```

**Virtual Thread 도입 효과 측정**
- **처리량(Throughput)**: 동일 시간 내 처리 가능한 요청 수 증가
- **응답 시간(Latency)**: I/O 대기 시간 감소로 평균 응답 시간 개선
- **자원 효율성**: 동일 하드웨어에서 더 많은 동시 요청 처리 가능
- **메모리 사용량**: Virtual Thread는 플랫폼 스레드보다 메모리 사용량 적음

**마이그레이션 체크리스트**
- [ ] Java 21 이상 사용 가능한지 확인
- [ ] Spring Boot 3.2 이상으로 업그레이드
- [ ] synchronized 블록 검토 및 ReentrantLock으로 전환
- [ ] ThreadLocal 사용 현황 파악 및 대안 검토
- [ ] 부하 테스트 및 성능 측정
- [ ] 모니터링 대시보드 업데이트
- [ ] 점진적 롤아웃 계획 수립


## Reference
> [완전희 정복하는 프로세스 vs 스레드 개념, inpa](https://inpa.tistory.com/entry/%F0%9F%91%A9%E2%80%8D%F0%9F%92%BB-%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4-%E2%9A%94%EF%B8%8F-%EC%93%B0%EB%A0%88%EB%93%9C-%EC%B0%A8%EC%9D%B4) <br/>
> [How Java thread maps to OS thread?](https://medium.com/@unmeshvjoshi/how-java-thread-maps-to-os-thread-e280a9fb2e06)<br/>
> [Java Docs > Thread](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html) <br/>
> [Java Docs > Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/threads.html)