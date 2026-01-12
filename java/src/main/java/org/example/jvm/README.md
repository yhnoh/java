## JVM(Java Virtual Machine)란 무엇일까?

![](./img/jvm_execution_flow.png)

- JVM은 ***자바 프로그램을 실행하기위한 가상 머신***이며, 자바 애플리케이션이 운영체제와 하드웨어에 독립적으로 실행될 수 있도록 한다.
    - 개발자가 .java 파일에 코드를 작성한 이후 .class 파일로 컴파일을 하면, JVM이 해당 바이트코드를 해석하여 운영체제에서 실행할 수 있는 형태로 변환한다.
    - 즉, JVM은 ***자바 프로그램이 다양한 환경에서 동일하게 동작할 수 있도록 중간 매개체 역할***을 한다.
- JVM은 단순히 자바 프로그램을 실행하는 것 외에도 다양한 기능을 제공한다.
    - 자바 바이트코드를 실행
    - 메모리 관리 (메모리 할당 및 해제, 사용 용도에 따른 메모리 구분 등)
    - 운영체제와의 상호 작용 (CPU, 파일 시스템, 네트워크 등)

### JVM은 어떻게 자바를 실행할까?

1. 소스 코드 작성: 개발자가 .java 파일에 자바 코드를 작성한다.
    ```java
    public class HelloWorld {
    
        public static void main(String[] args) {
            System.out.println("Hello, World!");
        }
    }
    ```

2. 컴파일: 자바 컴파일러(javac)가 .java 파일을 바이트코드(.class 파일)로 컴파일한다.
    - 컴파일을 진행하면서 문법적 오류가 있는지 검사를하고, 오류가 없다면 바이트코드로 변환한다.
    ```sh
    javac HelloWorld.java
    ```

3. JVM 실행: OS로부터 자원을 할당받아, JVM이 .class 파일을 로드하고 프로그램 실행 준비를 한다.
    - OS로 부터 메모리를 할당받은 이후 JVM은 필요한 `.class` 파일을 찾아 JVM 메모리 위에 로드한다.
    ```sh
    java HelloWorld
    ```

4. 실행: JVM이 변환된 코드를 실행하며, 운영체제와 상호작용하여 프로그램이 동작하도록 한다.
    - JVM은 바이트코드를 해석하여 기계어로 변환하여 자바 프로그램이 실행되도록 한다.

#### 자바 프로그램은 컴파일 언어인가?

- 자바는 ***컴파일 언어이면서 동시에 인터프리터 언어의 특성***을 가지고 있다.
    - 위의 자바 실행 과정에서 볼 수 있듯이, 자바 코드는 먼저 바이트코드로 컴파일된 후, JVM에 의해 실행된다.
    - 이후 JVM은 ***바이트코드를 해석하여 기계어로 변환하여 실행***한다.
- 이러한 특성 덕분에 컴파일 언어와 인터프리터 언어의 장점을 모두 누릴 수 있다.
    - 컴파일 언어의 장점: 빠른 실행 속도, 문법 오류 확인
    - 인터프리터 언어의 장점: 바이트코드라는 중간 단계 덕분에 다양한 플랫폼에서 실행 가능

#### 인터프리터의 한계를 극복한 JIT(Just-In-Time) 컴파일러

- 자바는 인터프리터 언어의 특성을 가지기 때문에, 바이트코드를 실행할 때마다 기계어로 해석하는 과정이 필요하여 실행 속도가 느릴 수 있다.
- 이를 해결하기 위해 JVM은 JIT(Just-In-Time) 컴파일러를 사용한다.
    - JIT 컴파일러는 자주 사용되는 바이트코드 부분을 기계어로 미리 컴파일하여 메모리에 저장해둔다. (캐싱)
    - 이후 동일한 코드가 실행될 때는 JIT 컴파일러가 미리 컴파일된 기계어를 사용하여 실행 속도를 향상시킨다.
- JIT 컴파일러 덕분에 자바 프로그램은 인터프리터 언어의 유연성을 유지하면서도, 컴파일 언어에 가까운 실행 속도를 얻을 수 있다.
  > 자주사용되는 코드를 메모리에 캐싱하는 과정이 추가되기 때문에 단순 실행 및 종료되는 프로그램에서는 오히려 성능이 저하될 수 있다.
- JIT 코드캐시 Size 조정 방법
    - 기계어를 미리 캐싱한다는 의미는 결국 메모리를 추가로 사용한다는 의미이기도 하다. 만약 대규모 애플리케이션이 실행되는 경우, JIT 컴파일러가 생성한 기계어 코드가 메모리를 과도하게 점유할 수 있다.
      때문에 JIT 코드 캐시의 크기를 조정하는 것이 필요할 수 있다.
    - `-XX:InitialCodeCacheSize=<size>` : JVM 시작 시 코드 캐시의 초기 크기를 설정
    - `-XX:ReservedCodeCacheSize=<size>` : JVM이 사용할 수 있는 코드 캐시의 최대 크기를 설정

> https://docs.oracle.com/javase/8/embedded/develop-apps-platforms/codecache.htm

> https://catsbi.oopy.io/df0df290-9188-45c1-b056-b8fe032d88ca
> https://www.geeksforgeeks.org/java/how-jvm-works-jvm-architecture/
> https://www.freecodecamp.org/news/jvm-tutorial-java-virtual-machine-architecture-explained-for-beginners/