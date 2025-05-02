### Java IO Stream

- 컴퓨터의 세계에서는 데이터를 읽고 쓴다는 행위는 너무나 당연한 행위이며, 어디에 데이터를 읽고 쓸지에 대한 Source(파일, 콘솔, 메모리, 네트워크 등등...)도 다양하다.
- 자바는 데이터를 읽고 쓰기 위한 API를 제공하며, `Stream`이라는 개념으로 추상화하고 다양한 Source에 데이터를 읽고 쓰기 위하여 다양한 `Stream` 구현체들을 제공한다.
- 자바의 IO Stream은 크게 세가지로 나눌 수 있다.
    - 바이트 스트림 (Byte Stream): 바이트 단위로 데이터를 처리하며 문자열의 인코딩/디코딩 과정 직접 처리한다.
        - `InputStream`: 데이터 읽기
        - `OutputStream`: 데이터 쓰기
    - 문자 스트림 (Character Stream): 문자 단위로 데이터를 처리하며 문자열의 인코딩/디코딩 과정을 자동으로 처리한다. 결국에는 바이트 스트림을 내부적으로 사용한다.
        - `Reader`: 데이터 읽기
        - `Writer`: 데이터 쓰기
    - 버퍼 스트림 (Bufferd Stream): 데이터를 읽고 쓸때, 버퍼를 사용하여 성능을 향상시키는 스트림이다.
        - 버퍼의 크기만큼 메모리에 읽고 쓴 이후에 버퍼가 가득차면 I/O를 수행한다.
        - `BufferedInputStream, BufferedOutputStream`: 바이트 스트림을 위한 버퍼 스트림
        - `BufferedReader, BufferedWriter`: 문자 스트림을 위한 버퍼 스트림

### Byte Stream

- 바이트 단위로 데이터를 처리하며 `InputStream, OutputStream`을 통해서 데이터를 읽고 쓸 수 있다.
    - `InputStream, OutputStream`의 구현체들이 존재하며, 해당 구현체들을 통해서 다양한 Source에 데이터를 읽고 쓸 수 있다.

```java
Charset charset = StandardCharsets.UTF_8;
String str = "Hello World!";
//문자열 -> 바이트 -> 쓰기
try (OutputStream os = new FileOutputStream("tmp/hello.txt")) {
        os.write(str.getBytes(charset));
}

//바이트 -> 문자열 -> 읽기
try (InputStream in = new FileInputStream("tmp/hello.txt")) {
        System.out.println(new String(in.readAllBytes(), charset));
}
```

### Character Stream
- 문자 단위로 데이터를 처리하며 `Reader, Writer`을 통해서 데이터를 읽고 쓸 수 있다.
    - `Reader, Writer`의 구현체들이 존재하며, 해당 구현체들을 통해서 다양한 Source에 데이터를 읽고 쓸 수 있다.
 
```java
//문자열 -> 바이트 -> 쓰기
try (Writer writer = new FileWriter("tmp/hello.txt")) {
        writer.write("Hello World!");
}

//바이트 -> 문자열 -> 읽기
try (Reader reader = new FileReader("tmp/hello.txt")) {
        int c;
        while ((c = reader.read()) != -1) {
        System.out.print((char) c);
        }
}
```

### Bufferd Stream
- 데이터를 읽고 쓸때, 버퍼를 사용하여 성능을 향상시키는 스트림이다.
- 버퍼

> [Java Docs > java.io](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html)
> (Java Docs Tutorial > io)[https://docs.oracle.com/javase/tutorial/essential/io/index.html]