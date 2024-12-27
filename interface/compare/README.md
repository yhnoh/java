
### Comparable, Comparator 의 필요성

- 자바에서 primitive 타입의 경우 부등호를 통해서 비교문을 작성할 수 있지만 객체를 비교하고 싶을 수 있다.
  - 예를 들어 IP 주소를 정렬하거나 비교문을 작성하고자하는 요구사항이 들어오는 경우
- 위와 같은 경우 비교문을 작성하기 위해서는 `Comparable`나 `Comparator` 인터페이스를 구현해야 한다.
- 클래스를 인스턴스화한 객

> https://st-lab.tistory.com/243
> https://mangkyu.tistory.com/101
> https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html#method.summary
> https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#method.summary