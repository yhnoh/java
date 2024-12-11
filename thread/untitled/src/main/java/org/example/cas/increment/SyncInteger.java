package org.example.cas.increment;

public class SyncInteger implements IncrementInteger{

    private int value = 0;

    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public int get() {
        return value;
    }
}
