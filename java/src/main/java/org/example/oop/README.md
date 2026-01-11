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

> [나무위키 > 객체 지향 프로그래밍](https://namu.wiki/w/%EA%B0%9D%EC%B2%B4%20%EC%A7%80%ED%96%A5%20%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D)

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

#### 3. 다형성(Polymorphism)

#### 4. 추상화(Abstraction)와 상속(Inheritance)

> [Infa > OOP 캡슐화 & 정보 은닉 개념 완벽 이해하기](https://inpa.tistory.com/entry/OOP-%EC%BA%A1%EC%8A%90%ED%99%94Encapsulation-%EC%A0%95%EB%B3%B4-%EC%9D%80%EB%8B%89%EC%9D%98-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4?category=967430)
> [Infa > 객체 지향 개념과 추상화 완벽 이해하기](https://inpa.tistory.com/entry/OOP-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EA%B0%9C%EB%85%90%EA%B3%BC-%EC%B6%94%EC%83%81%ED%99%94-%EC%84%A4%EA%B3%84%EC%9D%98-%EC%9D%B4%ED%95%B4)
> [Infa > 자바의 다형성(Polymorphism) 완벽 이해하기](https://inpa.tistory.com/entry/OOP-JAVA%EC%9D%98-%EB%8B%A4%ED%98%95%EC%84%B1Polymorphism-%EC%99%84%EB%B2%BD-%EC%9D%B4%ED%95%B4?category=967430)