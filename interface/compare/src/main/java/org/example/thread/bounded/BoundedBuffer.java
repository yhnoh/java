package org.example.thread.bounded;

public interface BoundedBuffer {

    void produce(String data);

    String consume();
}
