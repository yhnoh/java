
### 스트림
---

- 주로 컬렉션 프레임워크나 이와 유사한 형테의 데이터를 처리할 때 도움을 줄 수 있는, 자바 8에서 새롭게 제안한 API이다.
- 스트림을 이용한 컬렉션 프레임 워크의 가장 큰 특징은 외부 코드로 for 루프를 실행하는 것이 아니라, 스트림 내부에서 개발자가 정의한 코드가 반복적으로 실행된다는 것을 정의한다.
  - 인덱스 변수를 처리하거나 Iterator 객체를 생성하거나 하는 반복적이며 에러 지향적인 수고를 하지 않아도 된다.

- Stream 인터페이스에 정의된 상당수가 리턴 타입이 Stream이고 나머지는 void형이다.
  - 리턴 타입이 Stream
    - 리턴 터입이 Stream인 메서드들은 리턴 결과를 이용해서 데이터를 ***중간에 변형 혹은 필터링한 후 다시 Stream 객체를 만들어서 결과를 리턴***한다.
    - 작업을 반복적으로 수행할 수 있어 중간 연산 메서드라고 한다.
  - 리턴 타입이 void
    - 리턴 타입이 void형 메서드들은 주로 ***Stream을 이용해서 데이터를 최종적으로 소비***한다.
    - 이러한 메서드들을 최종 연산 메서드라고 한다.
- Stream 객체는 불변성이라는 특징이 있다.
  - 원천 데이터를 가공한 것이 아닌 완전히 새롭게 생성된 데이터를 제공한다.
  - 병렬 처리가 가능하기 때문에 데이터의 정합성을 확보하기 위하여 불변 데이터를 제공한다.

### 기본형 스트림 인터페이스

- Strema 인터페이스를 확인해보면 제네릭 타입을 명시적으로 지정해야한다.
- 때문에 int, long, double과 같은 원시 데이터 처리시 성능면에서 취약해진다.
  - 해당 데이터는 객체가 아니기 때문에 Stream 내부에서 오토 박싱과 언박싱이 빈번하게 발생하고, 이는 처리 시간을 급격하게 증가시킨다.
- 그래서 스트림 API는 기본형으로 많이 사용하는 int, long, double을 위해 별도의 인터페이스르 제공한다.
  - DoubleStream, IntStream, LongStream
  - 해당 Stream 인터페이스는 참조한 객체를 데이터로 사용하지 않고 기본형 데이터를 사용한다.
  - 데이터가 자동으로 박싱/언박싱 되지 않기 때문에 처리 속도가 빨라진다.
- 또한 기본형 스트림 인터페이스는 기본형 데이터를 처리하기 위한 특별한 메서드를 제공한다.
  - sum, max, min

### 스트림의 기본 특징
- 스트림은 한번 사용하고 나면 다시 사용할 수 없다.
- 최종 연산 메서드를 사용한 스트림은 다시 사용할 수 없다.

### 스트림 빌더
- 스트림은 데이터를 소모하는 역할만 하고 데이터를 생성하는 역할을 수행하지 않는다.
- 하지만 스트림에서는 데이터를 처리하는 것에서 끝나지 않고 데이터를 직접 생성하기 위한 기능도 제공하는데 그것이 바로 스트림 빌더이다.

### 주요 스트림 연산 기능

#### 데이터 필터링

#### 조건에 맞는 데이터만 필터링
- 스트림에서 데이터 필터링은 불필요한 데이터를 없애고 본인이 원하는 데이터만을 가져오기 위해 사용된다.
  - `마치 데이터베이시의 where절과 비슷하다.`
- 스트림 API에서는 데이터 필터링을 위해서 `Stream<T> filter(Predicate<? super T> predicate);` 메서드를 제공한다.
  - Predicate와 일치하는 항목으로 구성된 스트림을 리턴한다.
  - 스트림을 리턴하기 때문에 중간 연산자 메서드에 해당한다.
  - 함수형 인터페이스인 Predicate는 test 메서드를 실행시켜, 인자로 전달 받은 내용이 true이면 스트림이 포함시키고, false이면 스트림에서 제외시킨다.
    ```java
    List<Member> members = List.of(new Member("id1", "username1", 1),
            new Member("id2", "username2", 2));

    members.stream()
            .filter(member -> member.getName().equals("username1"))
            .forEach(System.out::println);
    ```
    - id1인 객체만 출려된다.

#### 중복 제거
- Stream API에서는 중복을 제거해주는 기능도 존재한다.
  - List 객체는 중복을 허용하기 때문에 Set으로 타입을 변경하지 않는 이상 중복 제거를 위해 특정 로직을 작성해야한다.
  - 하지만 Stream에서는 중복 제거를 위하여 `Stream<T> distinct()` 메서드를 제공해주며 직접 구현이 아닌 간편하게 중복 제거를 할 수 있다.
- distinct 메서드는 중복 여부를 확인하기 위하여 equals 메서드가 내부적으로 호출된다.
  - 때문에 특정 상황에서는 정확한 비교를 위해서 equals 메서드를 오버라이드 하는 것을 고려해야한다.
    ```java
    @Getter
    @ToString
    @EqualsAndHashCode(of = "id")
    public class Member {
        private String id;
        private String name;
        private int age;

        public Member(String id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
    ```
    - lombok을 이용하여 두 member의 id가 같으면 equals가 true로 메서드를 오버라이딩 하였다.
    ```java
    List<Member> members = List.of(new Member("id1", "username1", 1),
        new Member("id2", "username2", 2),
        new Member("id2", "username3", 3));

    members.stream()
            .distinct()
            .forEach(System.out::println);

    ```
    - `distinct()` 메서드 실행시, username3는 출력되지 않는다.
    - 여기서 주의해야할 것이, 중복 제거시 가장 앞에 있는 요소는 출력되지만 뒤에 있는 요소는 출력되지 않는다.
    - 즉, 중복 제거를 할 때 정렬 기준이 중요하다.

#### 데이터 제한하기

- Collection에서 상위 몇개의 데이터만 가져오고 싶을 수 있다.
  - Stream API 에서는 `Stream<T> limit(long maxSize);` 함수를 제공한다.
- limit 메서드를 이용하면 스트림에서 포함하고 있는 데이터중 앞에서 부터 maxSize만큼의 데이터만 가져올 수 있다.
    ```java
    List<Member> members = List.of(new Member("id1", "username1", 1),
            new Member("id2", "username2", 2),
            new Member("id3", "username3", 3));

    members.stream()
            .limit(1)
            .forEach(System.out::println);
    ```
    - limit 메서드 실행시, id1인 Member만 출력된다.
    - 데이터를 제한하는 limit 함수는 정렬 기준이 중요하다.


#### 데이터 뛰어넘기

- Collection에서 상위 몇개의 데이터를 넘어가고 싶을 수 있다.
    - Stream API 에서는 `Stream<T> skip(long n);` 함수를 제공한다.
- skip 메서드를 이용하면 스트림에서 포함하고 있는 데이터중 앞에서 부터 n만큼의 데이터를 필터링하고 나머지 데이터를 가져올 수 있다..
    ```java
    List<Member> members = List.of(new Member("id1", "username1", 1),
        new Member("id2", "username2", 2),
        new Member("id3", "username3", 3));

    members.stream()
            .skip(1)
            .forEach(System.out::println);
    ```
    - skip 메서드 실행시, id2와 id3인 Member만 출력된다.
    - 데이터를 뛰어넘는 skip 함수는 정렬 기준이 중요하다.

> Practical 모던 자바,p132-142

### 데이터 정렬

- 연속된 데이터를 처리할때 정렬은 자주 사용된다.
  - 데이터베이스의 경우 개발자가 SQL을 작성하여 정렬 할 수 있기 때문에 순서가 보장되지만, 네트워크나 파일은 데이터를 관리하는 것이 아니라 주어진 대로 수신하는 것이기 때문에 정렬이 보장되지 않는다.
- 스트림 API에서는 정렬 기능을 제공하고 있으며 다음과 같은 메서드를 제공한다.
    ```java
    Stream<T> sorted();

    Stream<T> sorted(Comparator<? super T> comparator);
    ```
    - 둘다 Stream을 리턴해주기 때문에 중간 연산 메서드라는 것을 알 수 있다.
- `sorted()` 메서드를 사용시, 개발자가 만든 클래스의 경우 Comparable 인터페이스를 구현하여 어떤 상태값을 이용하여 정렬할지를 정의해주어야 한다.
    - 안그러면 `cast to class java.lang.Comparable .... java.lang.ClassCastException`이라는 에러가 발생한다.
- `sorted()`는 `Comparable` 인터페이스를 파라미터로 받으 수 있는 또 다른 `Stream<T> sorted(Comparator<? super T> comparator)` 매서드를 제공하고 있다.
  - `sorted()`와 달리 `Comparator` 객체를 인자로 받을 수 있으며, 제네릭 타입으로 선언된 데이터를 어떤 기준으로 정렬할지를 지정할 수 있다.
  - 해당 메서드는 다음과 같은 상황에서 유용하다.
    - Comparable 인터페이스를 구현하지 않은 객체를 정렬할 때
    - 역순으로 정렬하고 싶을 때
    - 정렬하고자 하는 객체의 키 값을 다르게 하고 싶을 때
  - 주로 Comparator 내부에 정의해 놓은 메서드를 활용하는 방법들을 많이 사용한다.
    ```java
    //...
    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
            Function<? super T, ? extends U> keyExtractor)    

    //...
    public static <T, U> Comparator<T> comparing(
        Function<? super T, ? extends U> keyExtractor,
        Comparator<? super U> keyComparator)
    //...
    public static <T extends Comparable<? super T>> Comparator<T> reverseOrder()
    ```
    - reserverOrder의 경우 정렬할 객체에 받드시 Comparable 인터페이스가 구현되어 있어야 한다.
      - Comparable내에 compareTo 메서드를 반대로 해석하기 때문에 구현되어 있어야 한다.
    - 만약 구현하지 않고 본인이 원하는 정렬 방식으로 정렬하고자 한다면 아래와 같이 정렬이 가능하다.
        ```java
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        //오름차순
        members.stream()
                .sorted(Comparator.comparing(Member::getAge))
                .forEach(System.out::println);
        //내림차순
        members.stream()
                .sorted(Comparator.comparing(Member::getAge, (o1, o2) -> Integer.compare(o2, o1)))
                .forEach(System.out::println);
        ```
      - 오름차순의 경우 Comparator 내부 메서드를 활용하였고, 내림차순의 경우 Comparator에 compare 메서드를 직접 구현하였다.
  - 스트림 API 정렬 기능은 정의된 메서드를 활용할 뿐아니라 정의되지 않는 내용들도 직접 구현하여 정렬이 가능하도록 설계되어 있다.
  - 스트림 API가 나오면서 람다 표현식이나 메서드 참조를 이용해서 최소화된 코드로 정렬기능을 수행할 수 있다는 장점이 있다. 

> Practical 모던 자바,p142-148

### 데이터 매핑

- 스트림에서 데이터 매핑은 ***스트림에 포함되어 있는 데이터 항목을 다른 값으로 변환하는 것을 의미***한다.
   - 아래는 스트림 인터페이스에서 제공하는 매핑 관련 메서드이다.
    ```java
    <R> Stream<R> map(Function<? super T, ? extends R> mapper);

    IntStream mapToInt(ToIntFunction<? super T> mapper);

    LongStream mapToLong(ToLongFunction<? super T> mapper);

    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
    ```
  - Function 함수형 인터페이스를 통해서 내가 원하는 값을 변환하여 Stream으로 전달해준다.
    - Stream을 리턴형으로 넘겨주기 때문에 중간 연산 메서드라는 것을 알 수 있다.
  - 데이터를 변환해주기 때문에 원래 스트림 객체와 다른 제네릭 타입의 스트림을 리턴받을 수 있다.
- 만약 처리해야하는 데이터 타입이 기본형이라면 기본형에 대응하는 map메서드를 사용하여 성능과 가독성이 좋은 코드를 작성할 수 있다.
    ```java
    Member member1 = new Member("id1", "name1", 1);
    Member member2 = new Member("id2", "name2", 2);
    List<Member> members = List.of(member1, member2);

    members.stream().mapToInt(Member::getAge).forEach(System.out::println);
    ```
> Practical 모던 자바,p148-151

### 데이터 반복 처리
- 데이터를 필터링하고 정렬하고 매핑하는 것도 결국 스트림 데이터를 처리하기 위한 중간 절차일 뿐이다.
  - 마지막으로 데이터를 처리하기 위해서는 최종적으로 원하는 목적으로 저장하거나 전달하거나 소모하는 작업을 수행해야 한다.
- 스트림 API는 데이터를 소모하여 반복 처리를 지원해준다.
  ```java
  void forEach(Consumer<? super T> action);

  void forEachOrdered(Consumer<? super T> action);
  ```
- 스트림 반복 처리의 장점
  - 개발자가 루프 처리를 위한 각종 조건 및 사전 작업을 하지 않아된다.
  - for문을 돌리다가 해당 컬렉션에 값을 넣을 수 없다.
    - 컬렉션은 기본적으로 가변 객체이기 때문에 for문을 돌리면서 값을 추가하거나 삭제할 수 있다.
    - 이는 내가 원하지 않는 결과를 일으킬 수 있으며, 실제 코드상으로 어떤걸 의미하는지 정확히 파악하기 힘든 코드가 된다.

> Practical 모던 자바,p151-154

 

> https://futurecreator.github.io/2018/08/26/java-8-streams/