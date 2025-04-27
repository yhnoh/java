### Java IO Stream

- 컴퓨터의 세계에서는 데이터를 읽고 쓴다는 행위는 너무나 당연한 행위이며, 어디에 데이터를 읽고 쓸지에 대한 Source(파일, 콘솔, 메모리, 네트워크 등등...)도 다양하다.
- 자바는 데이터를 읽고 쓰기 위한 API를 제공하며, `Stream`이라는 개념으로 추상화하고 다양한 Source에 데이터를 읽고 쓰기 위하여 다양한 `Stream` 구현체들을 제공한다.
- 자바의 IO Stream은 크게 두가지로 나눌 수 있다.
    - 바이트 스트림: 바이트 단위로 데이터를 처리하며 문자열의 인코딩/디코딩 과정 직접 처리한다.
        - `InputStream`: 데이터 읽기
        - `OutputStream`: 데이터 쓰기
    - 문자 스트림: 문자 단위로 데이터를 처리하며 문자열의 인코딩/디코딩 과정을 자동으로 처리한다. 결국에는 바이트 스트림을 내부적으로 사용한다.
        - `Reader`: 데이터 읽기
        - `Writer`: 데이터 쓰기

### InputStream / OutputStream

### Reader / Writer

> [Java Docs > java.io](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html)