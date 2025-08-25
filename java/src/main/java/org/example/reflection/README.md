### Java Reflection
- 개발자가 자바 프로그래밍을 할 때 주로 `new` 키워드를 사용하여 객체를 생성하고, 필요한 값을 가져오거나 수정하는 코드를 작성하고 컴파일 이후 실행하게 된다. 이러한 방식은 ***컴파일 시점에 코드의 구조가 결정되기 때문에, 유연성이 떨어지고 런타임 시점에 동적으로 변경하기 어렵다는 단점***이 있다.
- Java Reflection은 이러한 문제를 해결하기 위한 도구로, ***런타임 시점에 클래스, 메서드, 필드 등의 정보를 동적으로 조회하고 조작***할 수 있게 해준다. 이를 통해 개발자는 컴파일 시점에 알 수 없는 클래스나 메서드를 동적으로 로드하고 실행할 수 있으며, 프레임워크나 라이브러리 개발에 매우 유용하게 활용된다.
  - 디자인 패턴의 프록시 패턴 처럼 런타임 시점에 ***동적으로 클래스를 생성하여 기능을 확장하거나 변경하는 경우***에도 Reflection이 활용된다.
  - 특히 자바에서 어노테이션을 활용한 프레임워크(예: Spring, Hibernate 등...)에서는 Reflection을 통해 어노테이션이 붙은 클래스나 메서드를 동적으로 처리하여 다양한 기능을 제공한다.

## java.lang.Class
- `java.lang.Class` 클래스는 자바의 모든 클래스나 인터페이스, Enum, 어노테이션, Primitive type, Void와 같은 모든 타입에 대한 클래스 메터 데이터를 관리하는 클래스이다.
- JVM은 모든 유형의 객체에 대하여 `java.lang.Class` 객체를 생성하고, `Object.getClass()` 메서드를 통해서 해당 객체의 Class 객체를 얻을 수 있다.
  - 때문에 개발자가 `new` 키워드를 통해서 `java.lang.Class` 객체를 직접 생성할 수 없다.
- `java.lang.Class` 클래스는 정적언어인 자바에서 동적 프로그래밍을 가능하게 해주는 초기 진입점 역할을 하며, 이를 통해서 Reflection API를 이용할 수 있다.

### Class 객체를 얻는 방법
- Class 객체를 얻는 방법으로는 4가지가 존재한다.
- `Object.getClass()`
  - Object 클래스에서 제공하는 메서드로 클래스가 인스턴스화 되어 있을때 Class 객체를 얻을 수 있다.
- `.class`
  - 클래스 이름 뒤에 `.class`를 붙여서 해당 클래스의 Class 객체를 얻을 수 있다.
  - 인스턴스화 되어 있지 않아도 Class 객체를 얻을 수 있는 장점이 있다.
- `Class.forName(String className)`
  - 클래스의 이름을 문자열로 받아서 해당 클래스의 Class 객체를 반환한다.
  - 문자열을 통해서 Class 객체를 얻을 수 있기 때문에, 런타임 시점에 Class 객체를 동적으로 로드할 수 있는 장점이 있다.
  - 만약 해당 클래스가 존재하지 않으면 ClassNotFoundException이 발생하게 된다.
  - `Class.forName` 메서드는 클래스 로더를 사용하여 Class 객체를 반환하기 때문에 클래스의 접근 제어를 무시하고 로드할 수 있다.
    - 예를 들어 다른 패키지에 존재하는 default 접근 제어자 Class 객체를 반환 받을 수 있다. 
- `WrapperClass.Type`
  - Wrapper 클래스에서 제공하는 Type 필드를 통해서 Primitice Type의 Class 객체를 얻을 수 있다.

### Class 객체를 통한 메타데이터 정보 확인
- Class 객체를 통해서 클래스의 다양한 메타 데이터 정보를 확인할 수 있다.
- Class 객체를 통해서 확인할 수 있는 메타데이터 정보는 다음과 같다
  - 선언한 클래스의 패키지, 이름, 접근 제어자
  - 선언한 제네릭 정보
  - 선언한 상위 클래스 및 인터페이스 정보
  - 선언한 생성자, 필드, 메서드 정보
  - 선언한 어노테이션 정보

### Class 객체가 생성될 수 있는 이유
- java.lang.Class 객체는 JVM이 자바 프로그램을 실행할 때, 클래스 로더가 클래스를 메모리에 로드하면서 자동으로 생성된다.

## java.lang.reflect
- java.lang.reflect 패키지는 자바의 Reflection API를 제공하는 패키지로, 런타임 시점에 Class 객체를 통해서 ***클래스의 메타데이터를 조회하거나, 클래스나 객체에 존재하는 필드, 메서드, 생성자들을 동적으로 접근하고 조작할 수 있는 기능을 제공***한다.
- 일반적인 Java 프로그래밍시 클래스 내에 선언된 static, instance 필드, 메서드, 생성자는 개발자가 클래스 구조를 이해한 상태에서 코드를 작성하고 컴파일함으로써, 외부의 요청에 따라 정해진 동작을 수행하게 된다.
- 하지만 만약 ***클래스의 구조를 정확하게 알 수 없는 상황에서 클래스의 필드나 메서드에 접근하여 특정 작업을 수행***해야 한다면, ***Reflection API를 활용하여 런타임 시점에 클래스의 메타데이터를 조회하고 동적으로 접근하여 조작할 수 있다.***. 이러한 기능들은 주로 프레임워크나 라이브러리 개발에서 많이 활용된다. (예: Spring, Hibernate, Lombok 등...)
- Reflection API에서는 `java.lang.reflect.Member` 인터페이스를 상속하는 `Field`, `Method`, `Constructor` 클래스를 통해서 클래스의 필드, 메서드, 생성자에 접근할 수 있는 기능을 제공한다.

### java.lang.reflect.Field
- `java.lang.reflect.Field` 클래스는 자바 클래스의 필드를 나타내며, Reflection을 통해 필드에 접근하고 조작할 수 있다.
- `Field` 객체를 통해서 필드의 이름, 타입, 접근 제어자 등의 메타데이터를 조회할 수 있으며, `get()`, `set()` 메서드를 사용하여 필드 값을 읽거나 수정할 수 있다.
 
> [Field 객체를 통한 필드 정보 조회 및 수정](../../../../../../src/test/java/org/example/reflection/FieldReflectionMain1Test.java)

### java.lang.reflect.Method
- `java.lang.reflect.Method` 클래스는 자바 클래스의 메서드를 나타내며, Reflection을 통해 메서드에 접근하고 조작할 수 있다.
- `Method` 객체를 통해서 메서드의 이름, 반환 타입, 매개변수 타입 등의 메타데이터를 조회할 수 있으며, `invoke()` 메서드를 사용하여 메서드를 동적으로 호출할 수 있다.

> [Method 객체를 통한 메서드 정보 조회 및 수정](../../../../../../src/test/java/org/example/reflection/MethodReflectionMain1Test.java)

### java.lang.reflect.Constructor
- `java.lang.reflect.Constructor` 클래스는 자바 클래스의 생성자를 나타내며, Reflection을 통해 생성자에 접근하고 조작할 수 있다.
- `Constructor` 객체를 통해서 생성자의 이름, 매개변수 타입 등의 메타데이터를 조회할 수 있으며, `newInstance()` 메서드를 사용하여 생성자를 동적으로 호출하여 객체를 생성할 수 있다.

> [Constructor 객체를 통한 생성자 정보 조회 및 수정](../../../../../../src/test/java/org/example/reflection/ConstructorReflectionMain1Test.java)

### setAccessible
- 일반적으로 자바에서는 접근 제어자(private, protected, default, public)을 통해서 클래스나 필드, 메서드, 생성자에 대한 접근을 외부로 부터 제한할 수 있다.
- 하지만 Reflection API에서는 `setAccessible(true)` 메서드를 사용하여 접근 제어자를 무시하고 접근하여 값을 읽거나 수정할 수 있다.
- 해당 기능은 접근 제어자를 무시하고 객체 내부에 접근할 수 있어, 캡슐화가 깨질 수 있다. 때문에 의도치 않게 객체의 상태를 변경하거나, 예기치 않은 동작을 유발할 수 있기 때문에 주의해서 사용해야 한다.

> [setAccessible 테스트](../../../../../../src/test/java/org/example/reflection/SetAccessibleTest.java)

### Reflection API 사용시 주의 사항
- 성능 저하: Reflection은 컴파일 시점에 결정되는 일반적인 코드 실행보다 추가적인 오버헤드가 발생하기 때문에 성능 저하를 유발할 수 있다. 
- 캡슐화 위반: 접근 제어자를 무시하고 객체 내부에 접근할 수 있어, 캡슐화가 깨질 수 있다. 때문에 의도치 않게 객체의 상태를 변경하거나, 예기치 않은 동작을 유발할 수 있다.
- 유지보수 어려움: Reflection을 사용한 코드는 일반적인 코드보다 이해하기 어렵고 디버깅이 어렵기 때문에 유지보수가 어려울 수 있다.
- 컴파일 시점 오류 검사 불가: 런타임에만 동작하기 때문에 오타나 잘못된 메서드 호출 등의 오류를 컴파일 시점에 미리 발견하기 어렵다.

> [Java Tutorial > Reflection](https://docs.oracle.com/javase/tutorial/reflect/)
> [infa > 누구나 쉽게 배우는 Reflection API 사용법](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EB%88%84%EA%B5%AC%EB%82%98-%EC%89%BD%EA%B2%8C-%EB%B0%B0%EC%9A%B0%EB%8A%94-Reflection-API-%EC%82%AC%EC%9A%A9%EB%B2%95)
> [JVM 내부 구조 & 메모리 영역 💯 총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-JVM-%EB%82%B4%EB%B6%80-%EA%B5%AC%EC%A1%B0-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EC%98%81%EC%97%AD-%EC%8B%AC%ED%99%94%ED%8E%B8)