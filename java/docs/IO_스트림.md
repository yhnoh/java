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
        String path = "tmp/byte.txt";
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        //문자열 -> 바이트 -> 쓰기
        try (OutputStream os = new FileOutputStream(path)) {
                byte[] bytes = str.getBytes(charset);
                for (byte b : bytes) {
                os.write(b);
                }
        }

        //바이트 -> 문자열 -> 읽기
        try (InputStream in = new FileInputStream(path)) {
                int c = 0;
                while ((c = in.read()) != -1) {
                System.out.print((char) c);
                }
        }
```

### Character Stream

- 문자 단위로 데이터를 처리하며 `Reader, Writer`을 통해서 데이터를 읽고 쓸 수 있다.
    - `Reader, Writer`의 구현체들이 존재하며, 해당 구현체들을 통해서 다양한 Source에 데이터를 읽고 쓸 수 있다.

```java
        //문자열 -> 바이트 -> 쓰기
        String path = "tmp/character.txt";
        try (Writer writer = new FileWriter(path)) {
            writer.write("Hello World!");
        }

        //바이트 -> 문자열 -> 읽기
        try (Reader reader = new FileReader(path)) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
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

        String path = "tmp/buffed.txt";
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        //문자열 -> 바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 쓰기
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(path), DEFAULT_MAX_BUFFER_SIZE)) {
            os.write(str.getBytes(charset));
        }

        //바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 문자열 -> 읽기
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path), DEFAULT_MAX_BUFFER_SIZE)) {
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        }

```

#### Bufferd Stream을 이용한 Character Stream 예제

```java
        String path = "tmp/buffed.txt";

        //문자열 -> 바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 쓰기
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path), DEFAULT_MAX_BUFFER_SIZE)) {
            writer.write("Hello World!");
        }

        //바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 문자열 -> 읽기
        try (BufferedReader reader = new BufferedReader(new FileReader(path), DEFAULT_MAX_BUFFER_SIZE)) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        }
```

### Data Stream과 Object Stream

- Stream을 활용하기 위해서는 직접 byte를 다뤄야하는 번거로움이 존재하는데, 직접 byte를 다루지 않고 I/O를 가능하게 하는 것이 바로 Data Stream과 Object Stream이다.
- 기본 데이터 유형(boolean, char, byte, short, int, long, float, double)과 문자열을 다룰 수 있도록 지원하는 Data Stream과 객체를 다를 수 있도록 지원하는 Object Stream이 존재한다.
- 두개의 Stream 모두 `DataInput, DataOutput`인터페이스를 상속받고있으며, `DataInput` 인터페이스는 더이상 읽을 데이터가 없을 경우 `EOFException` 예외를 터트린다.

#### Data Stream
- Data Stream은 기본 데이터 유형과 문자열을 이용하여 I/O가 가능하도록 하는 스트림이다.

```java
        String path = "tmp/data.txt";
        try (DataOutputStream os = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            double[] prices = {19.99, 9.99, 15.99, 3.99, 4.99};
            int[] units = {12, 8, 13, 29, 50};
            String[] descs = {
                    "Java T-shirt",
                    "Java Mug",
                    "Duke Juggling Dolls",
                    "Java Pin",
                    "Java Key Chain"
            };

            for (int i = 0; i < prices.length; i++) {
                os.writeDouble(prices[i]);
                os.writeInt(units[i]);
                os.writeUTF(descs[i]);
            }

        }

        try (DataInputStream is = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            try {
                while (true) {

                    double price = is.readDouble();
                    int unit = is.readInt();
                    String desc = is.readUTF();

                    System.out.printf("price = %f, unit = %d, desc = %s%n", price, unit, desc);
                }
            } catch (EOFException e) {
                System.out.println("End of file");
            }
        }
```

#### Object Stream
- Object Stream은 객체를 이용하여 I/O가 가능하도록 하는 스트림이다.
- 객체를 바이트로 변환하는 과정에서 직렬화 및 역직렬화 과정이 이루어지는데, Object Stream에서 해당 객체의 클래스가 직렬화가 가능하려면 `java.io.Serializable` 마커 인터페이스를 구현해야 객체를 이용한 I/O가 가능해진다.
  - 만약 `java.io.Serializable` 인터페이스를 상속받지 않은 객체를 I/O하려고 하는 경우 `NotSerializableException` 예외가 발생한다.

```java
        String path = "tmp/object.txt";
        try (ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            os.writeObject(new Person("John Doe", 30));
        }

        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            try {
                while (true) {
                    Person person = (Person) is.readObject();
                    System.out.println("person = " + person);
                }
            } catch (EOFException e) {
                System.out.println("End of file");
            }

        }
```

#### java.io.Serializable
- Serializable 인터페이스는 직렬화가 가능한 객체라는 것을 식별하기 위한 마커 인터페이스다.
  - 마커 인터페이스이기 때문에 어떠한 기능적 요소가 들어간 것이 아니다.
- transient을 이용한 직렬화 대상 제외
  - 만약 특정 필드를 직렬화에서 제외하고자 한다면 필드에 `transient` 예약어를 붙이면 된다.
- serialVersionUID를 통한 버전 관리
  - 객체를 이용한 I/O 작업시 해당 객체의 형태를 식별하는 고유한 별칭을 선언할 수 있다. 만약 해당 별칭을 선언하기 않을 경우 클래스 메타 데이터 정보를 바탕으로 자바 내부에서 자동으로 별칭을 부여하게되며 ***버전 관리를 하지 않은 상황에서 클래스의 변경사항이 발생할 경우 `InvalidClassException`이 발생할 수 있다.***
  > If a serializable class does not explicitly declare a serialVersionUID, ***then the serialization runtime will calculate a default serialVersionUID value for that class based on various aspects of the class***, as described in the Java Object Serialization Specification.

> [Java Docs > Serializable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/Serializable.html)



> [Java Docs > java.io](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html) <br/>
> [Java Docs Tutorial > io](https://docs.oracle.com/javase/tutorial/essential/io/index.html) <br/>
> [예제](../src/main/java/org/example/io/iostream)