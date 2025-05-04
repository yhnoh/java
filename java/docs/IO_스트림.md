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
try(
OutputStream os = new FileOutputStream("tmp/hello.txt")){
        os.

write(str.getBytes(charset));
        }

//바이트 -> 문자열 -> 읽기
        try(
InputStream in = new FileInputStream("tmp/hello.txt")){
        System.out.

println(new String(in.readAllBytes(),charset));
        }
```

### Character Stream

- 문자 단위로 데이터를 처리하며 `Reader, Writer`을 통해서 데이터를 읽고 쓸 수 있다.
    - `Reader, Writer`의 구현체들이 존재하며, 해당 구현체들을 통해서 다양한 Source에 데이터를 읽고 쓸 수 있다.

```java
//문자열 -> 바이트 -> 쓰기
try(Writer writer = new FileWriter("tmp/hello.txt")){
        writer.

write("Hello World!");
}

//바이트 -> 문자열 -> 읽기
        try(
Reader reader = new FileReader("tmp/hello.txt")){
int c;
        while((c =reader.

read())!=-1){
        System.out.

print((char) c);
        }
        }
```

### Bufferd Stream

- 데이터를 읽고 쓸때 버퍼를 사용하여 성능을 향상시키는 스트림이다.
- Byte Stream 및 Character Stream에서는 하나의 바이트 단위로 데이터를 읽거나 쓰거나 직접 버퍼를 생성하여 데이터를 읽거나 쓸 수 있다.
- 하지만 위와 같은 방식은 몇가지 문제점이 존재한다.
    - 하나의 바이트 단위마다 데이터를 읽거나 쓸때에는 I/O가 잦아지기 때문에 성능 이슈가 발생할 수 있다.
    - 개발자가 직접 버퍼를 생성하여 버퍼단위로 데이터를 읽거나 쓸 수 있지만 이러한 작업은 번거러운 작업이 될 수 있고 실수가 일어날 수 있다.
- 자바에서는 위와같은 문제를 해결하기 위한 Bufferd Stream을 제공한다.
- Bufferd Stream의 경우 직접 사용할 수 는 없고, Byte Stream 및 Character Stream의 구현체들과 함께 사용한다.
    - Bufferd Stream은 단순히 버퍼 사이즈만큼 메모리에 담아두는 작업을 할 뿐, I/O 작업들은 Byte Stream 및 Character Stream이 진행한다.

#### Bufferd Stream을 이용한 Byte Stream 예제

```java

Charset charset = StandardCharsets.UTF_8;
String str = "Hello World!";
//문자열 -> 바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 쓰기
try(
BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream("tmp/hello.txt"), DEFAULT_MAX_BUFFER_SIZE)){
        os.

write(str.getBytes(charset));
        }

//바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 문자열 -> 읽기
        try(
BufferedInputStream in = new BufferedInputStream(new FileInputStream("tmp/hello.txt"), DEFAULT_MAX_BUFFER_SIZE)){
int c;
        while((c =in.

read())!=-1){
        System.out.

print((char) c);
        }
        }

```

#### Bufferd Stream을 이용한 Character Stream 예제

```java

//문자열 -> 바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 쓰기
try(BufferedWriter writer = new BufferedWriter(new FileWriter("tmp/hello.txt"), DEFAULT_MAX_BUFFER_SIZE)){
        writer.

write("Hello World!");
}

//바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 문자열 -> 읽기
        try(
BufferedReader reader = new BufferedReader(new FileReader("tmp/hello.txt"), DEFAULT_MAX_BUFFER_SIZE)){
int c;
        while((c =reader.

read())!=-1){
        System.out.

print((char) c);
        }
        }
```

### 이외에 알아볼만한 Stream 구현체

#### Data Streams

#### Object Streams

> [Java Docs > java.io](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html) <br/>
> [Java Docs Tutorial > io](https://docs.oracle.com/javase/tutorial/essential/io/index.html) <br/>
> [예제](../src/main/java/org/example/io/iostream)