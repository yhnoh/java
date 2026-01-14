## 절차지향 프로그래밍 (Procedural Programming)

---

- 절차 지향 프로그래밍은 ***함수(기능)을 중심으로 순서도와 같이 프로그램을 작성하는 방식***이다. 프로그램이 수행해야할 각 작업들을 나열하고, 이를 함수로 구현하여 순차적으로 실행한다.
- 초기 프로그램의 경우 ***입력받은 값을 기반으로 기능을 수행하는 것에 초점***이 맞춰져 있었고, ***"어떤 데이터를 취급하는지? 해당 데이터의 상태를 어떻게 관리할지?" 등에 대한 것은 관심이 크지
  않았다.***
    - 기본적으로 전역 변수와 함수를 사용하여 데이터를 처리하고 있었고, 이후 구조체의 등을 도입하여 타입이 다른 데이터를 묶어서 관리하는 방식을 도입하기도 했다.

```c
// account.h 파일
struct Account {
    int balance;
    int accountNumber; 
}

// account_operation.c 파일: Account 행위를 위한 파일
void deposit(struct Account* account, int amount)
void withDraw(struct Account* account, int amount)

// account_getter.c 파일: Account 값 확인을 위한 파일
void printAccount(struct Account* account)
int getBalance(struct Account* account)
```

### 절차지향 프로그램의 한계

- 절차지향 프로그램은 ***데이터와 함수를 별도로 관리***하기 때문에 ***응집도가 낮고, 재사용성이 떨어져 유지보수가 어렵다는 단점***이 있다.
    - Account 구조체와 관련된 함수들이 여러 파일에 분산되어 있어서, Account와 관련된 ***기능을 수정하거나 확장할 때 여러 파일을 찾아서 수정***해야 한다.
    - 데이터에 직접 접근하여 수정이 가능하기 때문에 ***어디서 데이터가 변경되는지 추적하기 어렵다.***
    - 하나의 요청에 다양한 기능을 수행해야할때, ***if-else 문이나 switch 문을 사용하여 처리해야 하므로 코드가 복잡해지고 가독성이 떨어진다.***
- 위 단점은 GUI 프로그램이 등장하면서 점점 확대되기 시작했다.
    - GUI 프로그램의 경우 사용자와 지속적인 상호작용을 하는 프로그램으로써, 다양한 행위에 따라서 데이터에 대한 지속적인 상태관리를 요구하게 되었지만, 절차지향 프로그램의 경우 데이터의 직접 접근 및 응집도가
      떨어져서 유지보수가
      어렵다.
    - 또한 하나의 이벤트에도 요청자에 따라서 다양한 기능을 수행해야하는 경우가 많아지면서, if-else 문이나 switch 문이 난무하게 되어 코드의 복잡도가 증가하였다.

> 현재 실무에서 스프링을 통한 웹 개발을 진행하고 있는데,
> 실제로 서비스가 커졌을때 도메인 로직이 서비스 레이어 여기저기 흩어져있을 경우나 if-else문이 난무하는경우 상태 변경에 대한 추적이 어렵고
> 코드의 가독성이나 기능 추가나 수정과 같은 유지보수가 어렵다고 생각한다.

### 절차지향 프로그래밍은 단점만 있는 걸까?

- 절차지향 프로그래밍이 무조건 단점만 있다고 할 수 없다. 왜냐하면 인간의 사고방식이 절차지향적이기 때문에 객체지향 프로그래밍보다 이해하기 쉽고, 작성하기도 간편한다.
- 때문에 프로그램을 개발할때 객제지향적 설계의 어려움이 있다면 일단은 절차지향적으로 코드를 작성한 이후 리팩토링을 통해 객체지향적으로 개선해나가는 방법도 고려해볼 수 있다.
    - 설계의 어려움이 있는데 무작정 객체지향적으로 작성하다보면 일정을 못맞추거나 오히려 코드가 너무 복잡해져 유지보수가 어려워질 수 있다.
    - 때문에 일단은 절차지향적으로 작성한 이후 팀원간의 코드리뷰나 여유시간이 있을때 리팩토링을 통해 객체지향적으로 개선해나가는 방법도 고려해볼 수 있다.

## 객체지향 프로그래밍 (OOP: Object-Oriented Programming)

---

- 객체지향 프로그래밍은 ***데이터와 데이터를 처리하는 행위를 하나의 단위(객체: Object)로 묶어서 서로 간에 상호작용하도록 설계***하는 프로그래밍 방식이다.
- 객체지향 프로그래밍에는 캡슐화, 다형성, 상속, 추상화와 같은 요소가 존재하며 객체지향 언어라고 하여도 이러한 요소들을 모두 지원하지 않는 경우도 있다.

```java
// Account.java 파일
class Account {
    private int balance;
    private int accountNumber;

    void deposit(int amount)

    void withDraw(int amount)

    void printAccount()

    int getBalance()
}
```

### 절차지향 프로그래밍의 한계를 극복하기 위한 객체지향 프로그래밍

#### 1. 캡슐화 (Encapsulation)를 통한 응집도 향상

- 절차지향 프로그래밍에서는 데이터와 함수가 별도로 분리되어 있어서 응집도가 낮아, 기능 추가나 수정에 대한 유지보수가 어렵다고 했다. 하지만 하지만 객체지향 프로그래밍에서는
  ***캡슐화를 통해 데이터와 함수를 단위(객체)로 묶여 있어서 응집도가 높아지고, 유지보수가 용이***해진다.
- `Account` 클래스의 경우 데이터와 행위를 하나의 단위로 묶어서 관리하기 때문에, `Account`와 관련된 기능을 수정하거나 확장할 때 해당 클래스만 수정하면 된다.

> 참고로 응집도(Cohesion)란 관련있는 기능들이 하나의 모듈에 얼마나 밀접하게 결합되어 있는지를 나타내는 개념이다.

#### 2. 정보 은닉 (Information Hiding)을 통한 데이터 보호 및 불필요한 노출 방지

- 절차지향 프로그래밍에서 데이터를 직접 접근하여 수정하는 경우, 어디서 데이터가 변경되는지 추적하기 어렵다고 했다. 때문에 객체지향 프로그래밍에서는
  ***정보 은닉을 통해 데이터에 대한 직접 접근을 제한하고, 불필요한 데이터나 함수의 노출을 방지***한다.
- 데이터의 직접 접근 제한의 경우 아마 많은 개발자들이 접근 제한자를 통해서 경험해 보았을 것이고, 왜 이러한 접근 제한자가 필요한지에대해서도 공감할 것이다.
  > 참고로 Reflection API를 통해서 private 접근제한자도 접근이 가능하다.
- 하지만 많은 개발자들이 정보 은닉의 또 다른 측면인 불필요한 노출 방지에 대해서는 간과하는 경우가 많다.
- 에를 들어 슈퍼회원과 일반회원에 따라서 할인 정책을 달리해야하는 경우가 있다고 가정하였을 때, 아래와 같이 코드를 작성할 수 있다.

```java
// 회원 할인 정책 인터페이스
public interface MemberDiscountPolicy {

    int getDiscountAmount(int amount);
}

// 일반 회원 할인 정책 클래스
public class NormalMemberDiscountPolicy implements MemberDiscountPolicy {

    @Override
    public int getDiscountAmount(int amount) {
        // 10% 할인 정책
        double discountRate = 0.1;
        return (int) (amount * discountRate);
    }
}

// 슈퍼 회원 할인 정책 클래스
public class SuperMemberDiscountPolicy implements MemberDiscountPolicy {

    @Override
    public int getDiscountAmount(int amount) {
        // 20% 할인 정책
        double discountRate = 0.2;
        return (int) (amount * discountRate);
    }
}

// 회원 할인 정책 팩토리 클래스
public class MemberDiscountPolicyFactory {

    private final MemberDiscountPolicy normalMemberDiscountPolicy;
    private final MemberDiscountPolicy superMemberDiscountPolicy;

    public MemberDiscountPolicyFactory() {
        normalMemberDiscountPolicy = new NormalMemberDiscountPolicy();
        superMemberDiscountPolicy = new SuperMemberDiscountPolicy();
    }

    public MemberDiscountPolicy getDiscountPolicy(String memberType) {
        if ("NORMAL".equals(memberType)) {
            return normalMemberDiscountPolicy;
        } else if ("SUPER".equals(memberType)) {
            return superMemberDiscountPolicy;
        } else {
            return null;
        }

    }
}
```

- 위 코드의 경우 `MemberDiscountPolicyFactory`를 통해서만 `MemberDiscountPolicy`를 얻을 수 있도록 의도를 가지고 설계했다고 가정해보자.
    - 하지만 `NormalMemberDiscountPolicy`와 `SuperMemberDiscountPolicy` 클래스가 `public`으로 선언되어 있기 때문에, 외부에서 해당 클래스를 직접 생성하여
      사용할 수 있다.
- 이는 두가지 문제를 일으킨다.
    - 클라이언트에게 불필요한 클래스를 노출시켜 어떤 것을 사용해야하는지에 대한 혼동을 준다.
    - 만약 클라이언트가 `MemberDiscountPolicy`의 구현체들을 직접 의존받아서 사용했을 경우, 해당 구현체중 하나를 수정하거나 삭제하였을때 클라이언트 코드도 함께 수정해야하는 문제가 발생한다.
- 때문에 위와 같은 경우 `NormalMemberDiscountPolicy`와 `SuperMemberDiscountPolicy` 클래스를 `package-private`으로 선언하여 외부에 노출되지 않도록 하는
  것이 좋다.

> 현재 코드는 단순하여 크게 문제가 되지 않아보이지만, 실제 서비스가 커졌을때 불필요한 클래스들이 노출되어 혼란을 주거나 유지보수가 어려워질 수 있다.

#### 3. 추상화(Abstraction)와 다형성(Polymorphism)을 활용한 프로그램 설계

- 객체지향 프로그래밍에서 추상화와 다형성이란 단어를 자주 접할 수 있다.
    - 추상화란 ***복잡한 시스템에서 핵심적인 개념이나 기능을 추출하여 단순화하는 것***을 의미한다.
    - 다형성이란 ***동일한 인터페이스여도 다르게 동작할 수 있는 것을 의미***한다.
- 추상화와 다형성을 활용하면 ***코드의 가독성을 높이고 유지보수를 용이하게 할 수 있다는 장점***이 있지만, 객체지향 프로그래밍을 가장 어렵게 만드는 개념중에 하나이기도 하다.
    - 추상화와 다형성은 단순히 코드 작성법을 넘어서 프로그램 설계에 대한 개념이기 때문에, 해당 개념을 실제 코드로 옮기는 것이 쉽지 않다.
- 우리가 다양한 메시지 전송 기능을 제공하는 프로그램을 설계한다고 가정해보자. 이때 우리는 다짜고짜 코드부터 구현하지 않고, 먼저 추상화 과정을 거치며 어디에 다형성을 적용할지 고민한다.
    - 메시지는 카카오톡, 이메일, 문자메시지 등 다양한 방법으로 전송될 수 있다.
    - 각 메시지 전송 방법은 서로 다른 구현 방식을 가지고 있지만, 공통적인 인터페이스를 제공할 수 있다. (다형성 적용 가능)
    - 메시지 전송 기능을 추상화하여 기능을 단순화 할 수 있다. (추상화 적용 가능)

##### 1. 추상화 과정: 핵심 개념 도출

- 메시지 전송 시스템의 핵심은 "메시지를 전송 하는 것"이다. 어떤 방법으로 전송하든 결국 수신자에게 메시지를 전달하는 것이 목적이다.
- 때문에 아래와 같은 공통 인터페이스를 추출할 수 있다.

```java
public interface MessageSender {

    void sendMessage(String recipient, String message);
}
```

##### 2. 다형성 적용: 동일한 인터페이스를 가반으로 다르게 동작하는 구현체 작성

- 추상화된 인터페이스를 기반으로 각 전송 방법을 구체적으로 구현한다.
- 해당 구현체들은 동일한 `MessageSender` 인터페이스를 구현하지만, 내부 동작은 다르게 작성된다.

```java
public class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        // Email 보내는 로직 작성
        System.out.println("Sending Email to " + recipient + ": " + message);
    }
}

public class KakaoSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        // KakaoTalk 보내는 로직 작성
        System.out.println("Sending KakaoTalk message to " + recipient + ": " + message);
    }
}

public class SMSSender implements MessageSender {

    @Override
    public void sendMessage(String recipient, String message) {
        // SMS 보내는 로직 작성
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}
```

##### 3. 비지니스 로직 작성: 추상화된 인터페이스를 활용한 메시지 전송

- 비지니스 로직에서는 구체적인 구현체에 의존하지 않고, 추상화된 `MessageSender` 인터페이스에만 의존한다.
- 이로 인해서 절차지향 프로그래밍에서 있었던 if-else 문이나 switch 문이 사라지고, 코드의 가독성과 공통기능 추가와 같은 유지보수가 용이해진다.
- 만약 새로운 메시지 전송 방법이 추가되더라도, `MesssgeService`라는 비지니스 로직 코드는 수정할 필요가 없다.
- 또한 `MessageSender`라는 의존성을 주입받아 사용하기 때문에, 테스트 코드 작성도 용이하다.

```java
public class MessageService {

    private final MessageSender messageSender;

    public MessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendMessage(String recipient, String message) {
        messageSender.sendMessage(recipient, message);
    }
}
```

- 만약 추상화와 다형성을 활용하지 않고 절차지향 프로그래밍 방식으로 작성하였다면, 아래와 같이 if-else 문이나 switch 문이 난무하는 복잡한 코드가 된다.
- 또한 테스트 코드의 작성은 어려워 지며 `MessageService` 코드를 지속적으로 수정해야하는 문제가 발생한다.

```java
public class MessageService {
    public void sendMessage(String method, String recipient, String message) {
        if ("EMAIL".equals(method)) {
            // Email 보내는 로직 작성
            System.out.println("Sending Email to " + recipient + ": " + message);
        } else if ("KAKAO".equals(method)) {
            // KakaoTalk 보내는 로직 작성
            System.out.println("Sending KakaoTalk message to " + recipient + ": " + message);
        } else if ("SMS".equals(method)) {
            // SMS 보내는 로직 작성
            System.out.println("Sending SMS to " + recipient + ": " + message);
        } else {
            throw new IllegalArgumentException("Unknown message method: " + method);
        }
    }
}
```

##### 추상화와 다형성을 활용한 프로그램 설계의 결론

- 절차지향 프로그래밍에서 발생하는 많은 if-else 문이나 switch 문은 추상화와 다형성을 활용하여 해결할 수 있다.
    - 이로 인해서 코드의 가독성은 향상되고, 테스트 코드 작성 용이해진다.
    - 또한 메시지 전송 기능이 추가되거나 삭제, 변경 시, `MessageService` 코드를 수정할 필요가 없기 때문에 유지보수가 용이해진다.
- 하지만 추상화와 다형성은 단순히 코드 작성법을 넘어서 프로그램 설계에 대한 개념이기 때문에, 해당 개념을 실제 코드로 옮기는 것이 쉽지 않다.
- 또한 추상화의 과도한 사용은 오히려 코드의 복잡도를 증가시키고, 이해하기 어렵게 만들 수 있다.
    - 스프링 프레임워크는 추상화와 다형성을 적극적으로 활용하는 프레임워크이지만, 코드 분석이 쉽지는 않다.
- 때문에 처음부터 완벽한 추상화를 설계하기보다는, 요구사항을 구현하면서 추상화와 다형성을 적용할 수 있는 부분이 있는지 지속적으로 고민하고 작성하는 것이 중요하다.

#### 4. 상속(Inheritance)을 통한 코드 재사용 및 확장성 향상

- 객체지향 프로그래밍에서 상속은 ***기존 클래스(부모/슈퍼/하위 클래스)의 속성과 행위를 새로운 클래스(자식/서브/자식 클래스)가 물려받아 재사용하고 확장하는 것***을 의미한다.
- 절차지향 프로그래밍에서는 함수를 이용하여 코드를 재사용했지만, 상속을 통해 코드 재사용성과 확장성을 더욱 향상시킬 수 있다.
    - 또한 상속의 경우 객체간의 관계를 명확히 표현할 수 있기 때문에 ***재사용성의 측면 뿐만 아니라 응집도 및 가독성 향상에도 도움***이 된다.
- 예를 들어 동물(Animal)이라는 슈퍼 클래스가 있고, 이를 상속받는 개(Dog)와 고양이(Cat)라는 서브 클래스가 있다고 가정해보자.
    - 이는 동물이라는 공통된 속성과 행위를 개와 고양이가 물려받아 재사용하고, 각 클래스에서 고유한 속성과 행위를 추가할 수 있다.
- 하지만 상속을 남용하게 아래와 같은 문제가 발생할 수 있다.
    - ***강합 결합도를 초래***하여, 부모 클래스의 변경사항이 자식 클래스에 영향을 미쳐 유지보수가 어려워진다.
    - ***잘못된 상속 구조로 인하여 상속 관련 전체 코드를 수정***해야 하는 상황이 발생할 수 있다.
    - ***불필요한 데이터나 함수를 자식 클래스에 노출***시켜야 하는 상황이 발생할 수 있다.
- 때문에 상속보다는 ***구성(Composition)을 우선적으로 고려***하는 것이 좋다.
    - 구성은 객체가 다른 객체를 포함하여 기능을 확장하는 방식으로, 상속보다 유연하고 재사용성이 높다.

### 객체지향 프로그래밍은 만능일까?

- 객체지향 프로그래밍은 절차지향 프로그밍의 한계를 극복하기 위해 나온 프로그래밍 패러다임이지만, 가장 큰 문제는 객체지향적으로 코드를 작성하는 것이 쉽지 않다는 것이다.
    - 비지니스에 대한 이해도가 낮거나, 설계가 미숙한데 객체지향적으로 작성하려고 하면 오히려 코드가 더 복잡해지고 유지보수가 어려워질 수 있다.
- 그럼에도 불구하고 객체지향 프로그래밍이 널리 사용되는 이유는, 응집도를 높이고 유지보수가 용이하며 재사용성이 높은 코드를 작성할 수 있기 때문이다.
- 때문에 작은 프로그램이여도 어떻게 설계할지에 대해 고민해보고, 아래와같이 객체지향적으로 작성해보는 연습을 지속적으로 하는 것이 중요하다.
    - 설계 단계에서 추상화와 다형성을 적용할 수 있는 부분이 있는지 고민해보자. (비지니스에 필요한 행위에 대한 고민)
    - 이후 상속과 캡슐화를 통해서 필요한 데이터와 행위를 하나의 단위로 묶어서 응집도를 높여보자. (엔티티나 도메인 추출을 위한 고민)
    - 마지막으로 정보 은닉을 통해서 불필요한 노출을 방지하여 컨텍스트를 분리해보자. (모듈화 및 패키지 설계에 대한 고민)
- 이러한 객체지향적 사고 방식은 단순 코드뿐만아니라 이후 아키텍처 설계에도 큰 도움이 될 것이다.

> [나무위키 > 객체 지향 프로그래밍](https://namu.wiki/w/%EA%B0%9D%EC%B2%B4%20%EC%A7%80%ED%96%A5%20%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D)
> [Infa > OOP 캡슐화 & 정보 은닉 개념 완벽 이해하기](https://inpa.tistory.com/entry/OOP-%EC%BA%A1%EC%8A%90%ED%99%94Encapsulation-%EC%A0%95%EB%B3%B4-%EC%9D%80%EB%8B%89%EC%9D%98-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4?category=967430) <br/>
> [Infa > 객체 지향 개념과 추상화 완벽 이해하기](https://inpa.tistory.com/entry/OOP-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EA%B0%9C%EB%85%90%EA%B3%BC-%EC%B6%94%EC%83%81%ED%99%94-%EC%84%A4%EA%B3%84%EC%9D%98-%EC%9D%B4%ED%95%B4) <br/>
> [Infa > 자바의 다형성(Polymorphism) 완벽 이해하기](https://inpa.tistory.com/entry/OOP-JAVA%EC%9D%98-%EB%8B%A4%ED%98%95%EC%84%B1Polymorphism-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4?category=967430) <br/>

## Java의 Object 클래스

- Java는 객체지향 프로그래밍 언어로써 Object 클래스를 최상위 클래스로 사용한다. 즉, 모든 클래스는 Object 클래스를 암묵적으로 상속받는다.
- Java의 Object 클래스를 이해한다는 것은 곧, Java가 어떠한 문제를 해결하기 위해 설계되었는지 이해하는 것과 같다.

### getClass(): 클래스 정보를 반환하는 메서드

- `getClass()` 메서드는 ***객체의 클래스 정보를 반환하는 메서드***이다. ClassLoader가 로드한 클래스의 메타데이터를 Class 객체로 반환하여 ***런타임 환경에서 확인***할 수 있다.
- 주로 리플렉션 (Reflection) API와 함께 사용되어, 객체의 클래스 타입을 동적으로 확인하거나, 메서드 호출, 필드 접근 등을 수행할 때 활용된다.
    - 우리가 사용하는 프레임워크나 라이브러리들이 리플렉션 API를 활용하여 부가적인 기능을 제공하는 경우가 많다.

### toString(): 객체를 문자열로 표현하는 메서드

- `toString()` 메서드는 객체를 문자열로 표현하는 메서드로, ***사람이 읽기 쉬운 형태로 객체의 상태를 나타내기 위해 사용***된다.
- 만약 직접 클래스를 제작한 이후, `toString()` 메서드를 오버라이딩하지 않는다면, 기본적으로 클래스 이름과 해시코드가 포함된 문자열이 반환된다.
    - ` getClass().getName() + '@' + Integer.toHexString(hashCode())`
- 만약 객체에 대한 상태를 로그로 남겨야 한다고 가정해볼때, `toString()` 메서드를 활용하여 쉽게 객체의 상태를 문자열로 변환할 수 있다.
    - toString() 오버라이딩 하지 않고 구현은 가능하지만 굉장히 번거러운 작업이 된다.

```java
public static void main(String[] args) {
    User user = new User("user1", 1);
    // toString 적용하지 않을 경우 출력 결과 name = user1, age = 1
    System.out.println("name = " + user.getName() + ", age = " + user.getAge());
    // toString 적용할 경우 출력 결과: User{name='user1', age=1}
    System.out.println(user);
}
```

### equals(), hashCode(): 객체간의 비교를 위한 메서드

- Java에서는 객체간의 동등성(equality)을 비교하기 위해 `equals()`와 `hashCode()` 메서드를 제공한다.
    - `equals()`: ***두 객체가 논리적으로 동일한지를 비교하는 메서드***.
    - `hashCode()`: ***객체의 해시 코드를 반환하는 메서드***
- 원시 타입의 경우 `==` 연산자를 사용하여 값을 비교하지만, 객체 타입의 경우 `==` 연산자는 참조(메모리 주소)를 비교하기 때문에 논리적인 동등성을 비교하기 위해서는 `equals()` 메서드를 사용해야 한다.
- "`equals()` 메서드를 사용할때 반드시 `hashCode()` 메서드도 함께 오버라이딩 해라"라는 말을 많이 들어봤을 것이다.

#### equals()와 hashCode()를 함께 오버라이딩 해야하는 이유가 뭘까?

- hashCode()는 객체의 해시 코드를 반환하는 메서드로, 주로 ***해시 기반 컬렉션(예: HashMap, HashSet)에서 객체를 효율적으로 저장하고 검색하기 위해 사용***된다.
- 우리가 Map에 대해서 조회를 할때, ***O(1)의 시간복잡도로 조회가 가능한 이유는 내부적으로 해시코드를 활용***하기 때문이다.
  - 기본적으로 자료구조의 구현의 경우 데이터 저장의 용도로 배열이 많이 활용이 되는데, 배열의 인덱스를 해시코드를 기반으로 계산하여 해당 인덱스에 바로 접근할 수 있기 때문에 빠른 조회가 가능하다.
- 하지만 hashCode() 메서드가 ***동일한 값을 반환하는 서로 다른 객체들이 존재할 수 있다.*** (해시 충돌)
    - 때문에 hashCode() 메서드가 동일한 값을 반환하는 객체들에 대해서는 equals() 메서드를 사용하여 논리적인 동등성을 비교한다.
- 아래는 Java HashMap의 put()과 get()이 어떻게 해시코드를 활용하는지 표현하기 위한 의사코드이다.
```text
public class HashMap<K, V> {
    
    transient Node<K,V>[] table; // 데이터 저장을 위한 배열
    
    static class Node<K,V> {} // 링크드 리스트 구조
    
    public V put(K key, V value) {
        // 1. key의 hashCode()를 기반으로 table 배열의 요소 찾기
        // 2. table 배열의 요소가 존재 여부에 따라서 분기 처리
        //  2.1. 존재하지 않는다면, 새로운 Node 생성하여 table 배열에 저장
        //  2.2. 존재한다면, 링크드 리스트를 순회하면서 key의 equals() 메서드를 사용하여 동일한 key가 존재하는지 확인
        //      2.2.1. 동일한 key가 존재한다면, 해당 Node의 value를 업데이트
        //      2.2.2. 동일한 key가 존재하지 않는다면, 새로운 Node를 링크드 리스트에 추가
    }
    
    public V get(Object key) {
        // 1. key의 hashCode()를 기반으로 table 배열의 요소 찾기
        // 2. table 배열의 요소가 존재 여부에 따라서 분기 처리
        //  2.1. 존재하지 않는다면, null 반환
        //  2.2. 존재한다면, 링크드 리스트를 순회하면서 key의 equals() 메서드를 사용하여 동일한 key가 존재하는지 확인
        //      2.2.1. 동일한 key가 존재한다면, 해당 Node의 value 반환
        //      2.2.2. 동일한 key가 존재하지 않는다면, null 반환
    }
      
}
```
- put()과 get() 메서드 둘다 key의 존재 여부를 확인할때 hashCode()를 통해서 배열의 요소를 가져오고 이후 equals() 메서드를 통해서 논리적인 동등성을 비교하는 것을 볼 수 있다.
  - 만약 equals()만 사용한다면, 배열의 모든 요소를 순회하면서 비교해야하기 때문에 O(n)의 시간복잡도가 된다.
  - 하지만 hashCode()를 통해서 배열의 요소를 바로 가져올 수 있기 때문에 O(1)의 시간복잡도로 조회가 가능하다. (물론 조금 더 걸릴 수 있다.)
- 이전에 hashCode()가 동일한 값을 반환하여도 서로 다른 객체들이 존재할 수 있어 해시 충돌이 발생할 수 있다고 했다.
  - HashMap의 경우 해시 충돌이 발생할 경우, 링크드 리스트를 활용하여 충돌된 객체들을 저장한다.
  - 때문에 hashCode()가 동일한 값을 반환하는 객체들에 대해서는 equals() 메서드를 사용하여 논리적인 동등성을 비교하는 것을 볼 수 있다.
- 위와 같은 이유로 equals() 메서드를 오버라이딩 할때 반드시 hashCode() 메서드도 함께 오버라이딩 해야한다.
    - 만약 equals() 메서드만 오버라이딩 하고 hashCode() 메서드를 오버라이딩 하지 않는다면, 논리적으로 동일한 객체들이라도 해시코드가 다를 수 있기 때문에
      HashMap과 같은 해시 기반 컬렉션에서 올바르게 동작하지 않을 수 있다.

### wait(), notify(): 스레드 간의 통신을 위한 메서드
- `wait()`와 `notify()` 메서드는 ***멀티스레드 환경에서 스레드 간의 통신을 위해 사용***되는 메서드이다.
  - `wait()`: 현재 스레드를 일시적으로 대기 상태로 전환시키는 메서드
  - `notify()`: 대기 상태에 있는 스레드를 깨워 실행 대기 상태로 전환시키는 메서드
- 참고로 해당 장에서는 너무 상세히 다루지 않겠다.
- 멀티스레드 환경에서 동시성 문제를 해결하기 위해서는 Lock 이라는 개념이 필요하고, Lock을 구성하기 아래와 같은 기본적인 매커니즘이 필요하다.
  - 현재 작업중인 스레드가 존재할 경우 다른 스레드가 접근하지 못하도록 막아야 한다. (wait)
  - 작업이 완료된 이후 다른 스레드가 접근할 수 있도록 허용해야 한다. (notify)
- 아래는 `wait()`와 `notify()` 메서드를 활용하여 Lock의 기본적인 매커니즘을 구현한 코드이다.

```java
public static void main(String[] args) throws InterruptedException {
    Object lock = new Object();

    Thread thread1 = new Thread(() -> {
        try {
            sleep(1000);

            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 락 획득 이후, wait() 호출");
                // Thread-1 Waiting 상태로 전환
                lock.wait();
                System.out.println(Thread.currentThread().getName() + " 작업 완료");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }, "Thread-1");

    Thread thread2 = new Thread(() -> {

        try {
            // Thread-2 10초 대기: Thread-1이 wait() 상태로 들어간 후에 notify() 호출하도록 보장
            sleep(10000);

            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 락 획득 이후, notify() 호출을 통한 Thread-1 깨우기");
                // Thread-1 Runnable 상태로 전환 시도
                lock.notifyAll();
                System.out.println(Thread.currentThread().getName() + " 작업 완료");
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }, "Thread-2");


    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();
}
```
- 위와 같이 서로 다른 스레드가 동일한 객체를 가지고 `wait()`와 `notify()` 메서드를 호출하여 스레드 간의 통신을 할 수 있다.

### Java Object 클래스를 통해서 어떤 문제를 해결하려고 했을까?
- Java의 Object 클래스는 단순히 객체의 표현을 넘어 아래와 같은 문제를 해결하기 위해서 나온 언어지 않을까 생각해본다.
- Class 정보의 제공을 통한 런타임 환경에서의 유연한 프로그래밍을 지원한다.
  - `getClass()` 메서드를 통해 런타임 환경에서 객체의 클래스 정보를 확인할 수 있도록 하여, 리플렉션 API와 같은 동적 프로그래밍을 지원한다.
- 객체를 표현하기 위하여 필요한 표준화된 인터페이스를 제공한다.
    - `toString()`, `equals()`, `hashCode()` 메서드를 통해 객체 자체를 하나의 데이터 타입으로 표현하고 비교할 수 있도록 한다.
- 멀티 스레드 환경을 고려하여 설계되었다.
  - `wait()`, `notify()` 메서드를 통해 멀티스레드 환경에서 스레드 간의 통신을 지원하여 동시성 문제를 해결할 수 있도록 한다.

> [Java Docs > java.lang.Object](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) <br/>
> [Infa > 자바 Object 클래스와 메서드 오버라이딩](https://inpa.tistory.com/entry/JAVA-%E2%98%95-Object-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%99%80-%EC%83%81%EC%9C%84-%EB%A9%94%EC%84%9C%EB%93%9C-%EC%9E%AC%EC%A0%95%EC%9D%98-%ED%99%9C%EC%9A%A9-%EC%B4%9D%EC%A0%95%EB%A6%AC#tostring_%EB%A9%94%EC%86%8C%EB%93%9C) <br/>
> [Infa > 자바 객체의 hashCode는 고유하지 않다](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%9D%EC%B2%B4%EC%9D%98-hashCode%EB%8A%94-%EA%B3%A0%EC%9C%A0%ED%95%98%EC%A7%80-%EC%95%8A%EB%8B%A4-%E2%9D%8C#%EA%B0%9D%EC%B2%B4%EC%9D%98_hashcode%EB%8A%94_%EA%B3%A0%EC%9C%A0%ED%95%98%EC%A7%80_%EC%95%8A%EB%8B%A4)