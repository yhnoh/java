package org.example.io.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


/**
 * Java에서는 외부 데이터를 읽고 쓰기위한 추상클래스 InputStream, OutputStream을 제공한다.
 * 해당 추상 클래스의 구현체들을 통해서 파일, 콘솔, 네트워크등 다양한 소스로부터 데이터를 읽고 쓸 수 있다.
 *
 * 동일 인터페이스를 활용하여 개발을 진행하기 때문에 데이터를 읽고 쓰는 소스의 변경을 할때 최소한의 코드 수정으로 해결할 수 있으며,
 * 코드의 일관성이 유지되는 장점이 있다. 또한 해당 인터페이스를 직접 구현하여 데이터 입/출력을 구현할 수 있다.
 * public abstract int read() throws IOException
 * public int read(byte[] b) throws IOException
 * public int read(byte[] b, int off, int len) throws IOException
 * public byte[] readAllBytes()
 *
 * public abstract void write(int b) throws IOException
 * public void write(byte[] b) throws IOException
 * public void write(byte[] b, int off, int len) throws IOException
 */
public class FileStreamMain1 {


    public static void main(String[] args) throws IOException {

        FileOutputStream os = new FileOutputStream("tmp/hello.txt");
        os.write(65);
        os.write(66);
        os.write(67);
        os.close();

        FileInputStream is = new FileInputStream("/tmp/hello.txt");
        byte[] bytes = is.readAllBytes();
        System.out.println("bytes = " + Arrays.toString(bytes) + ", readString = " + new String(bytes));
        is.close();
    }
}
