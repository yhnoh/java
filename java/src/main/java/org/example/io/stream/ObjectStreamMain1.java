package org.example.io.stream;

import java.io.*;

public class ObjectStreamMain1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


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

    }

    static class Person implements Serializable {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
