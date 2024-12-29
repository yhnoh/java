
### Comparable, Comparator 의 필요성

- Comparable, Comparator을 구현하지않고 객체를 비교하려 할때 직접 구현할 수도 있겠지만, Java에서 제공하는 여러 API들의 정렬이나 비교문 기능을 포기해야한다.
  - Java Collections Framework이나 Arrays 클래스등 많은 
    - 객체를 정렬하기 위해서는 `Arrays.sort()`나 `Collections.sort()`를 사용한다.
    - 이때 객체의 클래스가 `Comparable` 인터페이스를 구현하고 있지 않다면 `ClassCastException`이 발생한다.
    - `ClassCastException`은 객체를 비교할 수 없다는 의미로, 객체를 비교하기 위해서는 `Comparable` 인터페이스를 구현해야 한다.
    - `Comparable` 인터페이스를 구현하지 않고 객체를 정렬하고 싶다면 `Comparator` 인터페이스를 사용하면 된다.

- 자바에서 primitive 타입의 경우 부등호를 통해서 비교문을 작성할 수 있지만 객체를 비교하고 싶을 수 있다.
  - 예를 들어 IP 주소를 정렬하거나 비교에 대한 요구사항이 들어오는 경우
- 위와 같은 경우 비교문을 작성하기 위해서는 `Comparable`나 `Comparator` 인터페이스를 구현해야 한다.
- 클래스를 인스턴스화한 객
- Comparable, Comparator 인터페이스를 이용하여 Java 내부에서 제공하는  정령이나 비교문

- Java Collections Framework에서 사용되는 인터페이스며, 해당 인터페이스를 구현한 클래스는 정렬이 가능하다.
- 왼쪽을 기준으로 오른쪽보다 클 때 양수를 반환하면 오름차순이 되고, 왼쪽을 기준으로 오른쪽보다 작을 때 음수를 반환하면 내림차순이 된다.


### Comparable
- Comparable 인터페이스는 객체를 비교하기 위하여 사용되며, 주로 ***this(자기 자신)을 기준으로 매개변수(타입이 일치하는 다른 객체)를 비교***할 때 사용된다.
- 양수,0,음수를 반환할 수 있으며 주로 아래와 같이 사용된다.
  - `this`를 기준으로 매개변수의 값이 클경우 양수 반환
  - `this`와 매개변수의 값이 같을 경우 0 반환
  - `this`를 기준으로 매개변수의 값이 작을 경우 음수 반환
- 위 방식을 제외한 다른 방식으로도 구현은 가능하지만, 특수한 경우가 아니라면 위와 같은 방식으로 구현하는 것이 일관성이 있고 코드 가독성에 좋다.
- 

### Comparator
- Comparator 인터페이스는 객체를 비교하기 위하여 사용되며, ***두 매개변수를 비교***할 때 사용된다.
- Comparable 인터페이스와 비슷하게 작동을 하지만, Comparable은 객체 내부에서 정렬 방법을 구현하는데 반해 Comparator는 객체의 정렬 방법을 외부에서 구현하는 것이 큰 특징이다.
- 주로 기본 정렬 기능을 사용할때 Comparable을 사용하고, 기본 정렬 기능을 무시하고 다른 정렬 기능을 사용하고 싶을 때 Comparator를 사용할 수 있다.


> https://st-lab.tistory.com/243
> https://mangkyu.tistory.com/101
> https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html#method.summary
> https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#method.summary