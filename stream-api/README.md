
### 1. 스트림

---

#### 1.1. 스트림이란?
- 주로 컬렉션 프레임워크나 이와 유사한 형테의 데이터를 처리할 때 도움을 줄 수 있는, 자바 8에서 새롭게 제안한 API이다.
- 스트림의 주된 목적은 매우 복잡하고 어려운 ***데이터 처리 작업을 쉽게 조회하고 필터링하고 변환하고 처리할 수 있도록 하는 것***이다.
- 스트림을 이용한 컬렉션 프레임 워크의 가장 큰 특징은 외부 코드로 for 루프를 실행하는 것이 아니라, 스트림 내부에서 개발자가 정의한 코드가 반복적으로 실행된다는 것을 정의한다.
  - ***인덱스 변수를 처리하거나 Iterator 객체를 생성하거나 하는 반복적이며 에러 지향적인 수고를 하지 않아도 된다.***.
    - for 루프 처리시 변수 i를 처리하는 경험이 다들 있을것이다. 어떤점이 불편했는지 한번 떠올려 보기 바란다.
      - `NullPointerException`
  - 스트림 내부에서 개발자가 정의한 코드를 작성하기 때문에 스트림 메서드에 대한 내용만 이해한다면 어떤 일을 하는지 쉽게 알아볼 수 있다.


#### 1.2. 스트림 연산자 종류
- Stream 인터페이스에 정의된 상당수가 리턴 타입이 Stream이고 나머지는 void형이다.
  - 리턴 타입이 Stream
    - 리턴 터입이 Stream인 메서드들은 리턴 결과를 이용해서 데이터를 ***중간에 변형 혹은 필터링한 후 다시 Stream 객체를 만들어서 결과를 리턴***한다.
    - 작업을 반복적으로 수행할 수 있어 중간 연산 메서드라고 한다.
  - 리턴 타입이 void
    - 리턴 타입이 void형 메서드들은 주로 ***Stream을 이용해서 데이터를 최종적으로 소비***한다.
    - 이러한 메서드들을 최종 연산 메서드라고 한다.
        - 물론 최종 연산 메서드 타입이 void만 있는 것은 아니다.

#### 1.3. 스트림의 기본 특징
- 스트림은 한번 사용하고 나면 다시 사용할 수 없다.
  - 스트림은 데이터를 소모시켜 새로운 상태를 만들어내기 때문에 재활용을 할 수 없다.
- 최종 연산 메서드를 사용한 스트림은 다시 사용할 수 없다.
- Stream 객체는 불변성이라는 특징이 있다.
  - 원천 데이터를 가공한 것이 아닌 완전히 새롭게 생성된 데이터를 제공한다.
  - 병렬 처리가 가능하기 때문에 데이터의 정합성을 확보하기 위하여 불변 데이터를 제공한다.


### 2. 기본형 스트림 인터페이스

---

- Strema 인터페이스를 확인해보면 ***제네릭 타입을 명시적으로 지정***해야한다.
- 때문에 ***int, long, double과 같은 원시 데이터 처리시 성능면에서 취약***해진다.
  - 해당 데이터는 객체가 아니기 때문에 Stream 내부에서 ***오토 박싱과 언박싱이 빈번하게 발생***하고, 이는 처리 시간을 급격하게 증가시킨다.
- 그래서 ***Stream API는 기본형으로 많이 사용하는 int, long, double을 위해 별도의 인터페이스르 제공***한다.
  - DoubleStream, IntStream, LongStream
  - 해당 Stream 인터페이스는 참조한 객체를 데이터로 사용하지 않고 기본형 데이터를 사용한다.
  - 데이터가 자동으로 박싱/언박싱 되지 않기 때문에 처리 속도가 빨라진다.
- 또한 기본형 스트림 인터페이스는 기본형 데이터를 처리하기 위한 특별한 메서드를 제공한다.
  - sum, max, min

### 3. 데이터 필터링

---

#### 3.1. 조건에 맞는 데이터만 필터링
- 스트림에서 데이터 필터링은 ***불필요한 데이터를 없애고 본인이 원하는 데이터만을 가져오기 위해 사용***된다.
  - `마치 데이터베이시의 where절과 비슷하다.`
- 스트림 API에서는 데이터 필터링을 위해서 `Stream<T> filter(Predicate<? super T> predicate);` 메서드를 제공한다.
  - Predicate와 일치하는 항목으로 구성된 스트림을 리턴한다.
  - 스트림을 리턴하기 때문에 중간 연산자 메서드에 해당한다.
  - 함수형 인터페이스인 Predicate는 test 메서드를 실행시켜, 인자로 전달 받은 내용이 true이면 스트림에 포함시키고, false이면 스트림에서 제외시킨다.
    ```java
    List<Member> members = List.of(new Member("id1", "username1", 1),
            new Member("id2", "username2", 2));

    members.stream()
            .filter(member -> member.getName().equals("username1"))
            .forEach(System.out::println);
    ```
    - id1인 객체만 출력된다.

#### 3.2. 중복 제거
- Stream API에서는 중복을 제거해주는 기능도 존재한다.
  - List 객체는 중복을 허용하기 때문에 Set으로 타입을 변경하지 않는 이상 중복 제거를 위해 특정 로직을 작성해야한다.
  - 하지만 Stream에서는 중복 제거를 위하여 `Stream<T> distinct()` 메서드를 제공해주며 직접 구현이 아닌 간편하게 중복 제거를 할 수 있다.
- ***distinct 메서드는 중복 여부를 확인하기 위하여 equals 메서드가 내부적으로 호출***된다.
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
    - 즉, ***중복 제거를 할 때 정렬 기준이 중요***하다.

#### 3.3. 데이터 제한하기

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
    - ***데이터를 제한하는 limit 함수는 정렬 기준이 중요***하다.


#### 3.4. 데이터 뛰어넘기

- Collection에서 상위 몇개의 데이터를 넘어가고 싶을 수 있다.
    - Stream API 에서는 `Stream<T> skip(long n);` 함수를 제공한다.
- skip 메서드를 이용하면 스트림에서 포함하고 있는 데이터중 앞에서 부터 n만큼의 데이터를 필터링하고 나머지 데이터를 가져올 수 있다.
    ```java
    List<Member> members = List.of(new Member("id1", "username1", 1),
        new Member("id2", "username2", 2),
        new Member("id3", "username3", 3));

    members.stream()
            .skip(1)
            .forEach(System.out::println);
    ```
    - skip 메서드 실행시, id2와 id3인 Member만 출력된다.
    - ***데이터를 뛰어넘는 skip 함수는 정렬 기준이 중요***하다.


### 4. 데이터 정렬

---

- 연속된 데이터를 처리할때 정렬은 자주 사용된다.
  - 데이터베이스의 경우 개발자가 SQL을 작성하여 정렬 할 수 있기 때문에 순서가 보장되지만, 네트워크나 파일은 데이터를 관리하는 것이 아니라 주어진 대로 수신하는 것이기 때문에 정렬이 보장되지 않는다.
- 스트림 API에서는 정렬 기능을 제공하고 있으며 다음과 같은 메서드를 제공한다.
    ```java
    Stream<T> sorted();

    Stream<T> sorted(Comparator<? super T> comparator);
    ```
    - 둘다 Stream을 리턴해주기 때문에 중간 연산 메서드라는 것을 알 수 있다.

#### 4.1. Comparable 인터페이스 구현이 필요한 스트림 정렬
- `sorted()` 메서드를 사용시, 개발자가 만든 클래스의 경우 ***Comparable 인터페이스를 구현하여 어떤 상태값을 이용하여 정렬할지를 정의***해주어야 한다.
    - 안그러면 `cast to class java.lang.Comparable .... java.lang.ClassCastException`이라는 에러가 발생한다.

#### 4.1. Comparator 인터페이스 구현이 필요한 스트림 정렬
- `sorted()`는 `Comparable` 인터페이스를 파라미터로 받을 수 있는 또 다른 `Stream<T> sorted(Comparator<? super T> comparator)` 매서드를 제공하고 있다.
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

        //내림차순
        members.stream()
                .sorted(Comparator.comparing(Member::getAge).reversed())
                .forEach(System.out::println);
        ```
      - 오름차순의 경우 Comparator 내부 메서드를 활용하였고, 내림차순의 경우 Comparator에 compare 메서드를 직접 구현하였다.
  - 스트림 API 정렬 기능은 ***정의된 메서드를 활용할 뿐아니라 정의되지 않는 내용들도 직접 구현하여 정렬이 가능하도록 설계***되어 있다.
  - 스트림 API가 나오면서 람다 표현식이나 메서드 참조를 이용해서 최소화된 코드로 정렬기능을 수행할 수 있다는 장점이 있다. 

### 5. 데이터 매핑

---

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

### 6. 데이터 반복 처리

---

- 데이터를 필터링하고 정렬하고 매핑하는 것도 결국 스트림 데이터를 처리하기 위한 중간 절차일 뿐이다.
  - 마지막으로 데이터를 처리하기 위해서는 최종적으로 원하는 목적으로 저장하거나 전달하거나 소모하는 작업을 수행해야 한다.
- 스트림 API는 데이터를 소모하여 반복 처리를 지원해준다.
  ```java
  void forEach(Consumer<? super T> action);

  void forEachOrdered(Consumer<? super T> action);
  ```
- 스트림 반복 처리의 장점
  - ***개발자가 루프 처리를 위한 각종 조건 및 사전 작업을 하지 않아된다.***
  - for문을 돌리다가 해당 컬렉션에 값을 넣을 수 없다.
    - 컬렉션은 기본적으로 가변 객체이기 때문에 for문을 돌리면서 값을 추가하거나 삭제할 수 있다.
    - 이는 내가 원하지 않는 결과를 일으킬 수 있으며, 실제 코드상으로 어떤걸 의미하는지 정확히 파악하기 힘든 코드가 된다.

### 7. 컬렉션으로 변환

- 스트림은 데이터를 처리하고 나면 다시 꺼내쓸수 없고 다시 스트림을 만들어야 한다.
- 반면에 컬렉션은 객체 내에 데이터를 읽어들인 후 다시 읽어들 수 있다.
  - 때문에 최종연산지인 collect 메서드를 이용해서 컬렉션 객체로 변환하는 경우가 많다.
    ```java
    <R> R collect(Supplier<R> supplier,
                    BiConsumer<R, ? super T> accumulator,
                    BiConsumer<R, R> combiner);

    <R, A> R collect(Collector<? super T, A, R> collector);
    ```
  - T: 리듀스 연산의 입력 항목으로 사용하는 데이터 타입
  - A: 리듀스 연산의 변경 가능한 누적값으로 사용하는 타입
  - R: 리듀스 연산의 최종 결과 데이터 타입
  > 입력, 누적, 결과가 있는 연산을 리듀스 연산이라고 부른다.

#### 7.1. Collector 유틸리티 클래스 Collectors
- collect 활용하기 위하여 상당히 많은 메서드들을 제공함으로 자바에서는 Collector 인터페이스를 구현한 유틸리티 클래스로 Collectors를 제공한다.
  - Collectors를 사용하면 간단하게 원하는 값으로 변환이 가능하다.
    - Collectors 내부에 있는 컬렉션 사용
        ```java
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);

        List<String> memberNames = members.stream()
                .map(member -> member.getName())
                .collect(Collectors.toList());
        ```
    - Collectors에서 개발자가 정의한 컬렉션 사용
        ```java
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);
        
        List<String> memberNames = members.stream()
                .map(member -> member.getName())
                .collect(Collectors.toCollection(LinkedList::new));        
        ```
        - Collectors.toCollection 메서드를 이용하여 컬렉션 데이터 유형과 객체를 생성하는 메서드나 생성자를 정의할 수 있다.
      - 하나의 데이터로 변환하여 사용
        ```java
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name2", 2);
        List<Member> members = List.of(member1, member2);
        
        //데이터를 쉼표로 구분하여 하나의 문자열로 리턴
        String joiningMemberNames = members.stream()
                .map(member -> member.getName())
                .collect(Collectors.joining(", "));

        //특정 값을 합한 값을 리턴        
        Integer sumAge = members.stream()
        .collect(Collectors.summingInt(Member::getAge));
        ```
        - 기존에는 for문을 사용하여야 하지만 스트림 내부 연산을 통해서 특정 값을 도출해 낼 수 있다. 
    - 특정 데이터를 기준으로 Map으로 변환
        ```java
        Member member1 = new Member("id1", "name1", 1);
        Member member2 = new Member("id2", "name1", 2);
        Member member3 = new Member("id3", "name2", 3);
        List<Member> members = List.of(member1,member2, member3);
        
        // Member 이름을 키로 가진 Map 
        Map<String, List<Member>> memberMapByName = members.stream()
                .collect(Collectors.groupingBy(Member::getName));

        // Member 이름을 키, 동일한 이름을 가진 나이의 합계가 값을 가진 Map
        Map<String, Integer> memberMapByName = members.stream()
                .collect(Collectors.groupingBy(Member::getName, Collectors.summingInt(Member::getAge)));

        // 조건에 따라 데이터를 분류후 Map으로 변환
        Map<Boolean, List<Member>> memberPartitionByAge = members.stream()
                .collect(Collectors.partitioningBy(member -> member.getAge() >= 2));
        ```
        - 그룹핑 또는 파티셔닝을 하여 어떠한 데이터를 기준으로 Map을 리턴 받을 수 있다.

### 8. 여러 스트림 생성 방법

---

#### 8.1. 배열을 스트림 객체로 변환하기
- `Arrays.stream(T[] array)`은 배열을 스트림 객체로 변환해주는 메서드이다.
    ```java
    Member member1 = new Member("id1", "name1", 1);
    Member member2 = new Member("id2", "name2", 2);
    Member member3 = new Member("id3", "name3", 3);
    Member[] members = {member1, member2, member3};

    Arrays.stream(members)
            .forEach(System.out::println);
    ```
#### 8.2. 데이터 항목을 이용해서 스트림 객체 만들기
- Stream 인터페이스에서는 스트림 객체를 생성할 수 있는 `Stream.of`를 제공한다.
    > 자바8 이후부터 객체를 생성하는 메서드명으로 of를 자주 사용하다.
    ```java
    Stream.of(new Member("id1", "name1", 1),
            new Member("id2", "name2", 2),
            new Member("id3", "name3", 3))
            .forEach(System.out::println);
    ```
#### 8.3. 스트림 빌더를 통해서 스트림 객체 만들기
- Stream.of와 같이 Stream.\<T>builder를 통해서 스트림 객체를 생성할 수 있다.
    ```java
    Stream<Member> streamBuilder = Stream.<Member>builder()
            .add(new Member("id1", "name1", 1))
            .add(new Member("id2", "name2", 2))
            .add(new Member("id3", "name3", 3)).build();

    streamBuilder.forEach(System.out::println);
    ```

### 9. 데이터 평면화

---

- 1차원 배열과 같은 형태의 배열과 컬렉션은 기존에 사용하던 스트림을 활용하여 데이터를 처리하는데 큰 문제가 없다.
- 하지만 배열 내부에 배열이 있거나 컬렉션 내부에 컬렉션이 선언되어 있을 경우 데이터 처리가 번거러워 질 수 있다.
- 이런 ***다차원의 데이터가 있는 환경에서 필요한 기능 중 하나가 데이터 평면화***이다.
  - 데이터 평면화를 위해 스트림에서는 `<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);` 메서드를 제공한다.

#### 9.1. 데이터 평면화가 필요한 사례
- 2차원 배열의 문자들을 데이터 평면화 기능을 사용하지 않고 하나의 문자열로 표현하기 
  ```java
  String[][] rawData = {
          {"a", "b"},{"c", "d"},{"e", "f"}
  };

  //a를 제외한 하나의 문자열로 표현하는 로직  
  String flatData = Arrays.stream(rawData)
              .map(data -> Arrays.stream(data)
                      .collect(Collectors.joining()))
              .map(data -> data.replace("a", ""))
              .collect(Collectors.joining());
  
  // flatData : bcdef
  ```
  - ***데이터 평면화 작업을 하지 않고, 로직들이 추가되면 추가될 수록 Steam을 사용한다고 하여도 복잡한 로직으로 된다.***
    - 위예제 코드에서도 map이면 데이터를 매핑하기 위한 기능인데 a문자열을 제거하는 로직으로 사용하고 있다.
    - 보통 무언가 맞지 않을 경우에는 filter를 많이 사용한다.
    - ***어떻게든 개발은 할 수 있지만 읽기 힘든 코드가 될 가능성이 높다.***
- 데이터 평면화 기능을 사용하여 않고 하나의 문자열로 표현하기  
  ```java
  String[][] rawData = {
          {"a", "b"},{"c", "d"},{"e", "f"}
  };

  //a를 제외한 하나의 문자열로 표현하는 로직  
  String[][] rawData = {
          {"a", "b"},{"c", "d"},{"e", "f"}
  };

  String flatData = Arrays.stream(rawData)
          .flatMap(Arrays::stream)
          .filter(data -> data.equals("a") ? false : true)
          .collect(Collectors.joining());
  
  // flatData : bcdef
  ```
  - `flatMap`을 활용하여 데이터를 평면화한 이후 `filter`기능을 활용해 a문자열을 제거하고 하나의 문자열로 표현하였다.
    - ***다차원 배열에서도 스트림 메서드가 가지고 있는 기능들을 잘 활용하여 코드로 표현할 수 있는 장점이 있다.***  
    - `flatMap(Arrays::stream)`에서 컬렉션이나 배열을을 스트림 객체로 만들어 하나의 스트림으로 합쳐준다.

### 10. 데이터 검색

---

- 원하는 데이터를 조회하기위해서 데이터를 필터링하여 원하는 데이터 집합을 조회할 수 있다.
- Stream에서는 필터링 뿐만아니라 ***특정한 패턴에 맞는 데이터를 검색하여 조회할 수 있는 메서드들도 제공***을 한다.
    ```java
    boolean anyMatch(Predicate<? super T> predicate);
    boolean allMatch(Predicate<? super T> predicate);
    boolean noneMatch(Predicate<? super T> predicate);
    Optional<T> findFirst();
    Optional<T> findAny();
    ```
    - anyMatch: 데이터가 하나로도 일치하는지 확인한다.
    - allMatch: 데이터가 모두 일치하는지 확인한다.
    - noneMatch: 데이터가 모두 일치하지 않는지 확인한다.
    - findFirst: 일치하는 데이터 중 가장 첫 번째 값을 리턴한다.
      - ***데이터의 정렬 순서가 중요하다.***
    - findAny: 일치하는 데이터 중 임의의 값을 리턴한다.
      - ***병렬처리를 통해서 값을 가져오기 때문에 실행할 때마다 값이 달라질 수 있다.***


### 11. 리듀스 연산

---

- 스트림에서의 연산은 중간연산과 최종연산으로 구분한다.
- 여기서 최종연산은 특징에 따라 두가지로 구분할 수 있다.
  - 스트림 항목들을 처리하면서 처리 결과를 바로 알 수 있는 최종 연산
    - `foreach`
    - 스트림에서 참조하는 데이터를 하나씩 꺼내서 처리하므로 데이터의 결과를 바로 알 수 있다.
  - 스트림의 데이터를 모두 소모한 후에야 그 결과를 알 수 있는 최종 연산
    - `count, sum, max, min...`
    - 데이터를 모두 확인하기 전까지는 그 결과를 알 수 없다.
- 이 중에서 ***데이터를 최종적으로 다 확인해서 결과값을 도출하는 최종 연산을 자바의 스트림 API에서는 리듀스 연산***이라고 한다.


#### 11.1 합계를 구하는 여러 방법들
```java
Member member1 = new Member("id1", "name1", 1);
Member member2 = new Member("id2", "name2", 2);
Member member3 = new Member("id3", "name3", 3);
Member member4 = new Member("id4", "name4", 4);

List<Member> members = List.of(member1, member2, member3, member4);

// 스트림 이전 전동적인 방식
int ageSum = 0;
for (Member member : members) {
    ageSum += member.getAge();
}

// IntStream 활용
int intStreamAgeSum = members.stream().mapToInt(Member::getAge).sum();

// Stream 활용
int streamAgeSum = members.stream().collect(Collectors.summingInt(Member::getAge));
```
- 전통적인 방식을 활용하면 코드양이 많아지면 이해하기 힘들 수 있고, 안정성이 떨어질 수 있다.
  - 로직의 복잡도가 올라가면 코드를 이해하기 힘들어진다.
  - 병렬처리 코드를 작성하게 될 경우 안전하게 계산될거라는 보장이 없기 때문에 노력을 들여 테스트 작업을 수행해야한다.
- 전통적인 방식을 해결하기 위해서 Stream API 에서는 다양한 방식들을 제공한다. 
    - IntStream 인터페이스 활용
    - 스트림의 collect 연산을 활용


#### 11.2. 리듀스 연산 활용하기
```java
T reduce(T identity, BinaryOperator<T> accumulator);
```
- 메서드의 첫 번째 인수는 초깃값을 의미한다.
- 메서드의 두 번째 인수는 `BinaryOperator` 인터페이스이다.
  - 두 개의 인수를 받아서 하나의 값으로 리턴하는 역할을 하는 함수형 인터페이스이다.

- 리듀스 연산을 활용한 누적 합계 구하기
    ```java
    Member member1 = new Member("id1", "name1", 1);
    Member member2 = new Member("id2", "name2", 2);
    Member member3 = new Member("id3", "name3", 3);
    Member member4 = new Member("id4", "name4", 4);

    List<Member> members = List.of(member1, member2, member3, member4);


    Integer sum = members.stream().map(Member::getAge).reduce(0, (x, y) -> {
        System.out.println("x = " + x + ", y = " + y);
        return Integer.sum(x, y);
    });

    System.out.println(sum);

    ```
    - 결과값
        ```text
        x = 0, y = 1
        x = 1, y = 2
        x = 3, y = 3
        x = 6, y = 4
        sum = 10
        ```
  - 초기값을 시작으로 값을 두개의 값을 하나의 값으로 더해주고 있는 것을 확인할 수 있다.
  - 많은 예제에서 누적 합계를 구하는 예제가 많지만 리듀스 연산은 단순히 누적합계를 구하기 위해서 만들어 놓은 것은 아니다.
  - 리듀스 연산은 `BinaryOperator` 인터페이스를 활용하여 ***x값과 y값을 계산하고 그 결과를 다음 x로 넘기는 역할***을 한다.
  - 때문에 단순히 누적값을 구하는 것이 아닌 두 값을 비교할때도 사용할 수 있다.
    ```java
    Member member1 = new Member("id1", "name1", 1);
    Member member2 = new Member("id2", "name2", 2);
    Member member3 = new Member("id3", "name3", 3);
    Member member4 = new Member("id4", "name4", 4);

    List<Member> members = List.of(member1, member2, member3, member4);

    Integer max = members.stream().map(Member::getAge).reduce(0, Integer::max);
    Integer min = members.stream().map(Member::getAge).reduce(0, Integer::min);
    ```

#### 11.3. 처리해야하는 데이터 양이 많을 경우 병렬 스트림의 리듀스 연산 사용

```java
Member member1 = new Member("id1", "name1", 1);
Member member2 = new Member("id2", "name2", 2);
Member member3 = new Member("id3", "name3", 3);
Member member4 = new Member("id4", "name4", 4);

List<Member> members = List.of(member1, member2, member3, member4);
Integer sum = members.parallelStream().map(Member::getAge).reduce(0, Integer::sum);
Integer max = members.parallelStream().map(Member::getAge).reduce(0, Integer::max);
Integer min = members.parallelStream().map(Member::getAge).reduce(1, Integer::min);

System.out.println("sum = " + sum);
```
  - 처리해야하는 데이터 양이 많을 경우에는 병렬 스트림을 활용하여 리듀스 연산을 하면 성능을 크게 올릴 수 있다.


### 12. 결론
- 스트림의 주된 목적은 매우 복잡하고 어려운 ***데이터 처리 작업을 쉽게 조회하고 필터링하고 변환하고 처리할 수 있도록 하는 것***이다.
  - 스트림 내부에서 개발자가 정의한 코드를 작성하기 때문에 스트림 메서드에 대한 내용만 이해한다면 어떤 일을 하는지 쉽게 알아볼 수 있다.
- 스트림 API는 크게 중간 연산과 최종 연산이라는 두가지 연산 종류가 있다.
  - 데이터를 정렬, 필터링, 매핑하는 등의 작업을 중간 연산
    - 중간 연산은 추가적인 중간 연산 기능을 연결할 수 있다.
  - 컬력션으로 변환, 원하는 값의 출력하는 등 최종 소모하는 작업을 최종 연산 
    - 최종연산을 사용한 스트림은 재활용할 수 없다.
- 다중 배열 형태의 데이터를 처리하기 위해서 데이터의 평면화 기능을 제공한다.
- 리듀스 연산을 활용하여 두개의 값을 계산하여 그 결과를 다음 값으로 넘길 수 있다.
  - 누적 함계, 최댓값, 최솟값
- 스트림은 기본적으로 병렬 기능을 제공하며 병렬 기능을 활용하여 성능을 극대화할 수 있다.


> Practical 모던 자바,p113-176
