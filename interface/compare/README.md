### Comparable, Comparator 의 필요성

- Java에서 제공하는 기본형 타입(Primitive type)의 경우에는 부등호를 이용하여 비교문을 작성할 수 있지만 객체의 경우에는 부등호를 이용하여 비교를 할 수 없다.
- 때문에 객체를 비교하기 위해서는 Object 클래스의 `equals()` 메서드를 구현하거나, `Comparable, Comparator` 인터페이스를 구현하여 객체를 비교할 수 있다.
- Comparable, Comparator 인터페이스의 경우에는 객체를 비교하기 위해서 사용하기 위한 인터페이스이면서 정렬에 주로 사용되는 인터페이스다.
- Comparable, Comparator을 구현하지 않고 객체를 비교할 수 있겠지만, Java에서 제공하는 여러 API들의 정렬이나 비교문 기능을 포기해야하기 때문에 객체를 비교하기 위함이라면
  `Comparable, Comparator`을 구현하는 것이 좋다.
    - Java Collections
    - Java Stream
    - Java Util
    - ....
- Comparable, Comparator 인터페이스 둘다 객체를 비교하기 위한 인터페이스이지만 서로간의 용도가 다르다.
    - Comparable: 객체를 비교하기 위한 인터페이스이며, 주로 ***this(자기 자신)을 기준으로 매개변수(타입이 일치하는 다른 객체)를 비교***할 때 사용된다.
    - Comparator: 객체를 비교하기 위한 인터페이스이며, ***두 매개변수(동일한 타입을 가진 객체)를 비교***할 때 사용된다.

### Comparable

- Comparable 인터페이스는 객체를 비교하기 위하여 사용되며, 주로 ***this(자기 자신)을 기준으로 매개변수(타입이 일치하는 다른 객체)를 비교***할 때 사용된다.
    - Comparable 인터페이스는 compareTo() 메서드를 구현하여 객체의 대소관계를 작성하고 int 타입을 반환해주면 된다. 기본적인 사용 방법은 주로 아래와 같이 사용한다.
        - `this`를 기준으로 매개변수의 값이 작을 경우 1을 반환
        - `this`를 기준으로 매개변수의 값이 클 경우 -1을 반환
        - `this`와 매개변수의 값이 같을 경우 0 반환
      ```java
      // Integer 클래스의 Comparable 인터페이스 구현 예시
      public final class Integer extends Number
            implements Comparable<Integer>, Constable, ConstantDesc {
        public int compareTo(Integer anotherInteger) {
          return compare(this.value, anotherInteger.value);
        }
        
        private static int compare(int x, int y) {
            return (x < y) ? -1 : ((x == y) ? 0 : 1);
        }
      }
      ```

### Comparator

- Comparator 인터페이스는 객체를 비교하기 위하여 사용되며, ***두 매개변수를 비교***할 때 사용된다.
- Comparable 인터페이스와 비슷하게 작동을 하지만, Comparable은 객체 내부에서 정렬 방법을 구현하는데 반해 ***Comparator는 객체의 정렬 방법을 외부에서 구현하는 것이 큰 특징***이다.
- ***주로 기본 비교 기능을 사용할때 Comparable을 사용하고, 기본 비교 기능을 이외의 다른 비교 기능을 사용하고 싶을 때 Comparator를 사용***할 수 있다.
    - Integer 클래스에는 이미 Comparable 인터페이스를 구현하여 객체의 기본 비교 기능을 제공하고 있다.
    - Integer의 기본 비교 기능과 다른 비교 기능을 사용하고 싶을 때, Comparator 인터페이스를 구현할수 있다.
  ```java
  public class IntegerReservedComparator implements Comparator<Integer> {
      @Override
      public int compare(Integer o1, Integer o2) {
          int compare = Integer.compare(o1, o2);
          return compare * -1;
      }
  }
  ```
    - 해당 Comparator를 사용하여 Integer 객체를 내림차순으로 정렬할 수 있다.
  ```java
  Integer[] integers1 = {5, 4, 3, 2, 1};
  Arrays.sort(integers1);
  //[1, 2, 3, 4, 5]
  System.out.println(Arrays.toString(integers1));

  IntegerReservedComparator integerReservedComparator = new IntegerReservedComparator();
  Integer[] integers2 = {1, 2, 3, 4, 5};
  Arrays.sort(integers2, integerReservedComparator);
  //[5, 4, 3, 2, 1]
  System.out.println(Arrays.toString(integers2)); 
  ```

### 실사용 예제

- [Compare를 이용한 IP 주소 비교 예제](src/main/java/org/example/api/compare/CompareMain.java)
- [Comparator을 이용한 IP 주소 비교 예제](src/main/java/org/example/api/comparator/ComparatorMain.java)

<br/>

> [자바 [JAVA] - Comparable 과 Comparator의 이해](https://st-lab.tistory.com/243) <br/>
> [[Java] equals와 hashCode 함수](https://mangkyu.tistory.com/101) <br/>
> [Java Docs > Comparable](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html#method.summary) <br/>
> [Java Docs > Comparator](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#method.summary) <br/>