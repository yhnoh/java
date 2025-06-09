### Buffer
- `java.nio.Buffer`는 `java.nio.Channel`에서 ***데이터를 읽거나 쓰기 위해 사용하는 컨테이너 역할***을 한다.
    - `java.io`에서 제공하는 스트림의 경우 1바이트 단위로 데이터를 읽거나 쓰지만 `java.nio`에서는 기본적으로 버퍼 단위로 데이터를 읽거나 쓴다.

#### Buffer의 특징
- 원시 데이터 타입 버퍼 제공
    - Buffer의 하위 클래스로 `ByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer, ShortBuffer`가 구현되어 있는것을 확인할 수 있듯이 다양한 원시 데이터 타입을 제공한다.
- Non DirectBuffer & Direct Buffer
  - 
- 인덱스 기반으로 하는 데이터 처리
  - Buffer에 데이터 저장전, 저장후, 읽기 및 쓰기, 자원정리등의 행위들을 인덱스기반으로 처리하게 된다.

#### Buffer의 주요 구성 요소
- 인덱스 기반으로 데이터를 처리하기 위하여 Buffer에서 제공하는 몇가지 구성 요소들이 존재한다.
  - `capacity`: 버퍼가 데이터를 담을 수 있는 총량
  - `limit`: 버퍼에서 데이터를 읽거나 쓰기 위한 마지막 위치
  - `position`: 버퍼에서 데이터를 읽거나 쓰기 위한 현재의 위치
  - `mark`: position의 위치를 기억하기 위한 변수
    - `mark()` 호출시 현재의 `position`을 기억하고, `reset()` 호출시 마킹한 위치로 `position`을 되돌린다.

#### Buffer의 일반적인 사용 흐름


### Channel

- `java.nio.Channel`은 `java.io`에서 제공하는 스트림처럼 I/O 작업을 수행하는 클래스다.
    - 스트림의 경우 단방향인 반면 채널은 읽기 및 쓰기 작업이 하나의 객체로 가능하다.
-

### Selector

## Reference

> https://medium.com/@ujjawalr/java-nio-complete-tutorial-69c2c6a8d2a0