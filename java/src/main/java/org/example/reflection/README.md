### Java Reflection
- 현재 우리가 자바 프로그래밍을 할 때, `new` 키워드를 사용하여 필요한 값을 가져오거나 조작하기 위한 코드를 작성하고 컴파일 이후 실행하게 된다. 이러한 방식은 컴파일 시점에 코드의 구조가 결정되기 때문에, 유연성이 떨어지고 런타임 시점에 동적으로 변경하기 어렵다는 단점이 있다.
- Java Reflection은 이러한 문제를 해결하기 위한 강력한 도구로, 런타임 시점에 클래스, 메서드, 필드 등의 정보를 동적으로 조회하고 조작할 수 있게 해준다. 이를 통해 개발자는 컴파일 시점에 알 수 없는 클래스나 메서드를 동적으로 로드하고 실행할 수 있으며, 프레임워크나 라이브러리 개발에 매우 유용하게 활용된다.
  - 특히 자바에서 어노테이션을 활용한 프레임워크(예: Spring, Hibernate 등)에서는 Reflection을 통해 어노테이션이 붙은 클래스나 메서드를 동적으로 처리하여 다양한 기능을 제공한다. 디자인 패턴의 프록시


## Class
- Java의 모든 타입은 reference type이거나 primitive type이다. primitive type은 boolean, byte, short, int, long, char, float, 과 double과 같은 기본 데이터 타입을 의미하며, reference type은 class, interface, array, enum 등을 포함한다.

- java.lang.Class 클래스는 자바 클래스의 메터 데이터를 관리하는 

### Class 객제
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
  - Wrapper 클래스에서 제공하는 Type 필드를 통해서 primitice type의 Class 객체를 얻을 수 있다.


### Class 객체 정보

#### 패키지
#### 생성자

#### 필드

#### 메서드

> [Java Tutorial > Reflection](https://docs.oracle.com/javase/tutorial/reflect/)