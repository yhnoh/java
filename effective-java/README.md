
- 가장 핵심적은 원칙은 명료성과 단순성
- 컴포넌트는 정해진 동작을 수행해야하며, 가능한한 작되 너무 작아서는 안된다.
- 컴포넌트는 복사가 아니라 재사용이 되어야하고, 컴포넌트 사이의 의존성은 최소로 유지해야한다.


### 생성자 대신 정적 팩터리 메서드를 고려하라
---

```java
public class FactoryMethod {

    private FactoryMethod(){}
    public static FactoryMethod create(){
        return new FactoryMethod();
    }
}
```

#### 장점
1. 이름을 가질 수 있다.
   - 생성자에 넘기는 매개변수와 생성자 자체만으로는 반환할 객체의 특성을 제대로 설명할지 못한다.
   - ***이름만 잘 지으면 반환할 객체의 특성을 잘 설명***할 수 있다.
2. 호출할 때마다 인스턴스를 새로 생성하지 않아도 된다.
   - 인스턴스를 단 하나만 생성하도록 만들 수 있고, 특정 인스턴스에 대해서 캐싱역할을 할 수 도 있다.
   - 위와 같은 방식으로 인스턴스를 통제할 경우 성능을 끓어 올릴 수 있다.
   - 플라이웨이트 패턴의 근간이 된다. 한번 알아보자. 예전에 한번 본거같은데 기억이 안난다.
3. 반환 타입의 하위 타입 객체를 반환할 수 있다.
   - 하위 타입을 반환할 수 있기 때문에 ***엄청난 `유연성`을 제공***할 수 있다.
4. 입력 매개변수에 따라 다른 클래스의 객체를 반활할 수 있다.
   - 반환 타입의 하위 타입이기만 하다면 매개변수의 값에 따라서 얼마든지 다른 인스턴스를 제공할 수 있다.
5. 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체가 존재하지 않아도 된다.
- ??

#### 단점
1. 정적 팩토리 메서드만 제공하면 하위 클래스를 만들 수 없다.
   - public이나 protected 생성자가 없기 때문에 상속을 활용하기 힘들다.

2. 정적 팩토리 메서드는 프로그래머가 찾기 어렵다.
   - 정적 팩토리 메소드는 결국 메소드이기 때문에 어떠한 네이밍 규약이 없어 개발자가 마음대로 작성하면 해당 메소드가 정적 팩토리 메소드인지 알 수 없다.

#### 정적 팩토리 메서드 사용시 알아두면 좋은 네이밍 규칙  
|메서드 네이밍 규칙|설명|예시|
|---|---|---|
|from|배개변수를 하나 받아서 해당 타임의 인스턴스를 반환하는 형변환 메서드|`Date date = Date.from(instant)`|
|of|여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드|`List.of(a,b,c,d)`|
|valueOf|from과 of를 좀 더 자세히 설명|`BigInteger.valueOf(Integer.MAX_VALUE)`|
|instance, getInstance|매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지 않음||
|create, newInstance|매번 새로운 인스턴스를 생성해 반환함을 보장||
|getType, newType, Type|생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 사용, 'Type'은 팩터리 메서드가 반환할 객체의 타입|`FileStore fs = Files.getFileStore(path)`|


### 생성자에 매개변수가 많다면 빌더를 고려하라

---

- 정적 팩토리 메소드와 생성자에는 매개변수가 많아질 때 적절한 대응이 어렵다.
- ***매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기가 어렵다.***
- 빌더 패턴을 활요하면 위와 같은 문제를 해결할 수 있다.

```java

    Member member = member.builder()
                    .name("name")
                    .age(20).build();
```

#### 장점
1. 빌더의 세터 메서드들은 빌더 자신을 반환하기 때문에 ***연쇄적으로 호출***할 수 있다.
  - fluent API, method chaining 이라 한다.
2. 필수 매개변수는 builder에 매개변수를 추가하고, 나머지는 선택적 매개변수로 둘 수 있다.
  - 잘못된 인자일 경우 `IllegalArgumentException`를 활용하자.
3. 빌더 패턴은 ***계층적으로 설계된 클래스와 함께 쓰기에도 좋다.***
  - 상속을 활용하여 가변인수 매개변수를 새롭게 정의할 수 있다.
  - 클라이언트 코드에서 형변환을 신경쓰지 않고 빌더를 사용할 수 있다.   

#### 단점
1. 빌더 패턴을 적용하는 클래스를 만드려면 일단 빌더 부터 제작해야한다.
2. 빌더 패턴을 적용함으로 코드가 장황해질 수 있다.
   - 해당 클래스에 추가적으로 필요한 메소드들을 작성하기 부담스러워 질 수 있다.


> 매개변수가 많다면(4개 이상) 빌더 패턴을 선택하는 것이 좀 더 좋은 선택일 수 있다.
> 또한 생성자나 정적 팩토리 메서드로 처리해 두었더라도 나중에 매개변수가 많아질 수 있으니 애초에 빌더로 시작하는 편이 나을 때도 많다.

### 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

---

#### 필요성

- 의존 객체 주입을 활용하여 유연성을 확보할 수 있다.
  - ***자원을 내부에서 직접 생성하고 다른 곳에서 조립할 수 없는 형태로 제작된다면, 유연하지 않으며 테스트를 하기도 어렵다.***

```java
//내부에서 직접 생성하고 다른 곳에서 
public class SpellChecker {
    private static final Dictionary dictionary = new Dictionary();

    private SpellChecker() {} //객체 생성 방지

    public static boolean isValid(String word) { ... }
    public static List<String> suggestions(String typo) { ... }
}

```
- 위 코드에서 `SpellChecker` 객체 내에서 `Dictionary`객체를 직접생성한다.
- `SpellChecker` 클래스를 사용하는 개발자는 `Dictionary`객체를 변경할 수 없다.
- 실전에서는 `Dictionary`가 언어별로 또는 어휘별로 두기도 하며 테스트용으로도 필요할 수 있다.

#### 장점
1. `SpellChecker`클래스는 ***사용하는 자원에 따라 동작이 달라지는 클래스***가 될 수 있기 때문에 직접적으로 `Dictionary`를 명시하기 보다는 ***해당 자원을 외부에서 주입받을 수 있도록 하면 유연하게 대처할 수 있다.***
    ```java
    public class SpellChecker {
        private final Dictionary dictionary;

        private SpellChecker(Dictionary dictionary) {
            this.dictionary = dictionary;
        } //객체 생성 방지

        public boolean isValid(String word) { ... }
        public List<String> suggestions(String typo) { ... }
    }   
    ```
2. 의존 객체 주입은 클래스의 유연성, 재사용성, 테스트 용이성을 개선해준다.

#### 단점
1. 의존 객체 주입이 유연성과 테스트 용이성을 제공해주지만, 의존성이 많아지면 코드가 어지러워진다.
  - 스프링과 같은 프레임워크를 활용하면 DI를 통해서 이러한 문제를 조금이나마 해소가 가능하다.

### 불필요한 객체 생성을 피하라

- 불필요한 객체를 계속해서 생성하지말고 생성되어 있는 객체를 재사용하는 것이 좋다.
  - 성능상의 이점을 가져올 수 있다.

#### 객체의 재사용의 단적인 예시
```java
public final class Boolean implements java.io.Serializable, Comparable<Boolean>{
    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);
    
    //필드에 생성된 Boolean 객체를 재활용 한다.
    public static Boolean valueOf(boolean b) {
        return (b ? TRUE : FALSE);
    }

    public static Boolean valueOf(String s) {
        return parseBoolean(s) ? TRUE : FALSE;
    }

}
```
- Boolean 클래스의 valueOf 정적 메소드 덕분에 불필요한 Boolean 객체를 생성하지 않으며, 재사용 가능하다.
  - `mew Boolean(...)`하여 직접 생성하는 것은 지속적으로 불필요한 객체를 생성하는 행위이다.

#### 값비싼 객체 재사용하기
```java
public class Mobile(){
    public static boolean isMobile(String mobile){
        return mobile.matches("정규식");
    }
}
```
- String.matches는 정규표현식으로 문자열 혈태를 확인하는 가장 좋은 방법이지만, 성능이 중요한 상황에서는 적합하지 않다.

```java
public class Mobile(){
    private static final Pattern MOBILE = Pattern.compile("정규식");
    public static boolean isMobile(String mobile){
        return MOBILE.matcher(mobile).matches();
    }
}
```
- 위와 같이 Pattern클래스를 ***한번만 생성하여 재사용을 하게되면 성능의 이점을 상당히 끌어올릴 수 있다.***
- 생성이 비싼 객체의 경우 캐싱해서 재사용할 수 있는 것과 비슷한 원리이다.

#### 오토박싱을 조심하자
> 여기서 이야기하는 오토 박싱은 기본 타입을 Wrapper클래스로 박싱해 주는 것을 이야기한다.
```java
private static long sum() {
    Long sum = 0L;
    for (long i = 0; i <= Interger.MAX_VALUE; i++){
        sum += i;
    }
    return sum;
}
```
- 
- 위의 코드는 개발자가 원하는 답을 정확하게 주겠지만 Long으로 선언해서 불필요한 인스턴스가 지속적으로 생성이 된다.
- ***Wrapper클래스를 사용하기 보다 기본 타입을 사용하고, 의도치 않은 오토박싱이 있는지 주의해서 코드를 작성하자.***

> 객체 생성을 통해서 성능적 이점을 극대화하자 라는 것이 본질이 아니다.
> ***작은 습관하나가 품질 좋은 프로그램을 생산해내기 때문에 해당 객체를 재사용할 수 있을지 없을지 고민해보는 프로그래머가 되자.***

### 다 쓴 개체 참조를 해제하라

---

- C, C++ 처럼 메모리리를 직접 관리하는 언어를 쓰다가 가비지 컬렉터를 갖춘 언어를 사용하면 프로그래머는 훨씬 더 개발하기 수월해진다.
  - 하지만 메모리 관리를 아예 신경쓰지 않아도 된다는 말이 아닌다.
  - 메모리 누수는 겉으로 잘 드러나지 않으며 디버깅 도구를 동원해야만 발견되는 경우가 많기 때문에 예방법을 미리 익혀두는 것이 매우 중요하다.
   
#### 메모리 관리를 하지 않는 스택
```java
public class Stack {
    //...

    public Object pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        return elements[--size];
    }
    // 배열의 크기를 늘려야할 때 마다 두 배씩 사이즈를 늘린다.
    private void ensureCapacity(){
        if(elements.length == size){
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
```
- 위 Stack을 사용하는 프로그램은 오래 사용하다 보면 가비지 컬렉션 활동과 메모리 사용량이 늘어나 결국 성능이 저하될 것이다.
- 왜냐하면 `pop()`메서드를 실행하더라도 가비지 컬렉터가 회수를 하지 않기 때문이다.
  - 다 사용한 객체를 `elements`컬렉션이 계속해서 정보를 가지고 있기 때문에 메모리 해제가 안된다.

#### 메모리 해제가 가능한 스택
```java
public class Stack {
    //...

    public Object pop(){
        if(size == 0){
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        elements[size] == null;
        return result;
    }

    //...
}
```
- 다 사용한 객체의 경우 null로 처리한다.
  - `elements`내에서 비활성 영역이 생긴다 하더라도, 가비지 컬렉터는 해당 객체를 사용하는지 아닌지를 알 길이 없다.
  - 때문에 ***비활성 영역이 되는 순간 null 처리해서 해당 객체를 더는 쓰지않는 것임을 가비지 컬렉터에게 알려야 한다.***

#### 언제 메모리 관리를 하는 것이 좋을까?
> 모든 객체를 다 쓰자마자 일일이 null처리를 하는 것도 힘들고, 한다고 해도 프로그램을 지저분하게 만든다.
1. 위의 Stack 클래스 처럼 자기 메모리를 직접 관리하는 클래스라면 항상 메모리 누수에 조심하는 것이 좋다.
2. 캐시를 사용할 때 언제 메모리를 해제할지에 대한 고민을 하는 것이 좋다.
   - 캐시의 유효기간을 언제까지 둘 것이가?
     - 백그라운드 스레드에서 캐시를 주기적으로 삭제하낟.
   - 외부에서 키를 참조하는 동안만 엔트리가 살아있는 캐시가 필요한 상황이라면 `WeakHashMap`과 같은 약한 참조(Waek reference)를 고려하자.
3. 리스너(listener), 콜백(Callback)을 사용하는 경우 메모리 관리를 신경쓰자.
   - 클라이언트가 콜백을 등록하고 해지하지 않는다면 후처리를 진행하거나 약한 참조(Weak reference)로 저장하면 가비지 컬렉터가 즉시 수거해간다.
   - ex) WeakHashMap

### finalizer와 cleaner 사용을 피하라

---

- 아직 모름

### try-finally 보다는 try-with-resources를 사용하라

---
