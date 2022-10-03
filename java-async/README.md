

### ExecutorService
---

- `ExecutorService`를 이용하여 간단하게 쓰레드 풀 생성 및 병렬 처리(멀티 스레드)가  가능하다.
    - `newFiexdThreadPool(int)` : 인자 개수 만큼 고정된 쓰레드풀을 만든다.
    - `newCachedThreadPool()` : 필요할 때 필요한 만큼 쓰레드풀을 생성한다. 이미 생성된 쓰레드를 재활용한다.
    - `newScheduledThreadPool(int)` : 일정 시간 뒤에 실행되는 작업이나, 주기적으로 수행되는 작업이 있는 경우 활용할 수 있다.
    - `newSingleThreadExecutor()` : 싱글 쓰레드에서 동작해야 하는 작업을 처리할 때 활용할 수 있다.

- 4개의 고정된 쓰레드풀을 생성하여 작업
    ```java
        private static void runFixedThreadPool() throws InterruptedException {
            ExecutorService executor = Executors.newFixedThreadPool(4);

            executor.submit(() -> {
                printThreadName("job1");
            });

            executor.submit(() -> {
                printThreadName("job2");
            });

            executor.submit(() -> {
                printThreadName("job3");
            });

            executor.submit(() -> {
                printThreadName("job4");
            });

            printThreadName("main job");

            //더 이상 쓰레드풀에 작업을 추가하지 못하며, 처리 중인 Task가 모두 완료되면 쓰레드풀을 종료시킨다.
            executor.shutdown();

            //수행 중인 Task가 지정된 시간동안 끝나기를 기다린다. 만약 Task가 지정된 시간 내에에 끝나지 않으면 false를리턴
            if(!executor.awaitTermination(20, TimeUnit.SECONDS)){
                executor.shutdownNow();
            }
        }

        public static void printThreadName(String workName){
            System.out.println("[" + Thread.currentThread().getName() + "] " + workName);
        }
    ```

### Future
--- 


- `Future`를 이용하면 ExecutorService에서 작업한 결과값을 메인 쓰레드에서 얻을 수 있다.
    - 쓰레드 작업이 완료되기를 기다리며, 결과값을 확인할 수 있다.
    - 쓰레드 작업의 완료 여부, 취소 여부를 확인할 수 있다.

- 문제점
    - 첫 번째 작업이 늦어지면 다른 작업의 결과값을 가져오는 시간이 오래 걸리 수 있다.

> **Reference**
> - [Funture Reference](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html)
> - [Java - ExecutorService를 사용하는 방법
](https://codechacha.com/ko/java-executors/)
