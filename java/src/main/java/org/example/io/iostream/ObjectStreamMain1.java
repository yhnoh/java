package org.example.io.iostream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectStreamMain1 {

    public static void main(String[] args) throws IOException {


        try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream("object.txt")))) {

        }
    }
}
