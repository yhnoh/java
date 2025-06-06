### 문자열 인코딩 디코딩

- 컴퓨터는 0과1만을 인지할 수 있기 때문에, 사람이 이해하고 있는 문자를 컴퓨터가 이해할 수 없다.
- 때문에 ***사람의 언어를 컴퓨터가 인지하기 위하여 여러 규칙을 만들고 해당 규칙에 따라서 바이트로 변환해주는 과정***을 통해서 컴퓨터가 문자를 인지할 수 있도록하였다.
  > 위와같은 과정을 인코딩 디코딩 과정이라 한다. <br/>
  > 인코딩(Encoding)은 컴퓨터가 인지할 수 있도록 바이트로 변환하는 과정이다. <br/>
  > 디코딩(Decoding)은 컴퓨터가 인지하고 있는 바이트를 문자로 변환하는 과정이다.

### 문자 집합 (Character Set)

- ***사람의 언어를 컴퓨터가 인지하기 여러 규칙을 정의하는데 그 정의가 바로 문자 집합(Character Set)*** 이다.
  > 문자 집합 은 문자, 숫자, 문장 부호, 공백 등의 문자들을 인식하는 방법을 컴퓨터에 알려주는 인코딩 시스템입니다. <br>
  > https://developer.mozilla.org/ko/docs/Glossary/Character_set
- 문자 집합은 각 문자에 대해 고유한 숫자 값을 부여하여 컴퓨터가 문자를 인식할 수 있도록한다.
    - 예를 틀어 ASCII 문자 집합에서는 대문자 A는 65, 소문자 a는 97로 정의 되어 있다.
  ```java
  //ASCII 인코딩
  Charset charset = StandardCharsets.US_ASCII;
  System.out.println("A 인코딩된 바이트 (" + charset.name() + ") = " + Arrays.toString("A".getBytes(charset)));
  System.out.println("a 인코딩된 바이트 (" + charset.name() + ") = " + Arrays.toString("a".getBytes(charset)));
  
  //결과 값
  A 인코딩된 바이트 (US-ASCII) = [65]
  a 인코딩된 바이트 (US-ASCII) = [97] 
  ```
- 이러한 규칙들을 이용하여 문자열을 인코딩하고 디코딩하여 문자를 인식할 수 있도록 한다.

#### 문자 집합(Character Set)의 종류

- 문자 집합은 다양한 종류가 있으며 인코딩 과정과 디코딩 과정에서 다른 문자 집합을 사용하는 경우 문제가 발생할 수 있다.
- 대표적인 문자 집합
    - ASCII: 영문자, 숫자, 기호, 특수문자등 총 128자를 숫자로 표현한 문자 집합
    - KSC5601, EUC-KR, CP949 (MS949): 한글을 표현하기 위한 문자 집합
        - 한글을 지원하기 위해서 만들었지만 각 문자 집합마다 지원하는 문자 수가 다르기 때문에, 인코딩 디코딩 과정에서 다른 문자 집합을 사용할 경우 문제가 발생할 수 있다.
    - 유니코드: 유니코드는 전세계의 모든 문자를 동일하게 표현하기 위한 문자 집합
        - 전세계의 언어를 표현하기 위하여 만들어졌기 때문에 가자 많이 사용되는 문자 집합이다.
        - 대표적으로 UTF-8이 있으며 4byte로 표현할 수 있는 모든 문자를 지원한다.

> https://nuli.navercorp.com/community/article/1079940

### 자바의 문자열 인코딩 디코딩

- 자바는 `java.nio.charset`를 통해서 문자열 인코딩과 디코딩을 지원한다.
- `java.nio.charset` 내부에 있는 `Charset, CharsetEncoder, CharsetDecoder`을 이용하여 인코딩 디코딩이 가능하다.
    - 인코딩: `String -> char[] -> byte[]`
    - 디코딩: `byte[] -> char[] -> String`
  ```java
      public static void main(String[] args) throws CharacterCodingException {
  
          Charset charset = StandardCharsets.UTF_8;
          String str = "Hello World!";
          //인코딩
          byte[] encodedBytes = getEncodedBytes(charset, str);
          System.out.println("인코딩된 바이트 (" + charset.name() + ") = " + Arrays.toString(encodedBytes));
          
          //디코딩
          String decodedString = getDecodedString(charset, encodedBytes);
          System.out.println("디코딩된 바이트 (" + charset.name() + ") = " + decodedString);
          
      }
  
      private static byte[] getEncodedBytes(Charset charset, String str) throws CharacterCodingException {
          CharsetEncoder charsetEncoder = charset.newEncoder();
          ByteBuffer byteBuffer = charsetEncoder.encode(CharBuffer.wrap(str));
          return byteBuffer.array();
      }
  
      public static String getDecodedString(Charset charset, byte[] bytes) throws CharacterCodingException {
          CharsetDecoder charsetDecoder = charset.newDecoder();
          CharBuffer charBuffer = charsetDecoder.decode(ByteBuffer.wrap(bytes));
          return charBuffer.toString();
      }
  ```
- 하지만 개발자 입장에서 위 클래스를 직접 사용하지는 않고 주로 `String` 클래스를 통하여 사용된다.
  ```java
    public static void main(String[] args) {
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";

        //인코딩
        byte[] encodedBytes = str.getBytes(charset);
        System.out.println("인코딩된 바이트 (" + charset.name() + ") = " + Arrays.toString(encodedBytes));

        //디코딩
        String decodedString = new String(encodedBytes, charset);
        System.out.println("디코딩된 바이트 (" + charset.name() + ") = " + decodedString);
    }
  ```
- 참고로 charset을 명시하지 않을 경우 현재 시스템에서 사용하는 기본 문자 집합을 사용한다.

> [Java Docs > java.nio.charset](https://docs.oracle.com/en/java/javase/21//docs/api/java.base/java/nio/charset/package-summary.html) <br/>
> [예제]()