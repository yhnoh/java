package hash;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MyHashMap <K, V>{


    private static final int CAPACITY = 8;
    //key는 해쉬값을 통해서 배열에서 데이터를 가져온다.
    private LinkedList<Tuple<K, V>>[] items = new LinkedList[CAPACITY];

    public void put(K key, V value){
        int hash = hash(key);

        LinkedList<Tuple<K, V>> tuples = items[hash];
        if(tuples == null){
            tuples = new LinkedList<>();
            tuples.add(new Tuple<>(key, value));
            items[hash] = tuples;
        }else {
            if(!tuples.equals(key)){
                tuples.add(new Tuple<>(key, value));
            }
        }
    }

    public V get(K key){

        if(key == null){
            return null;
        }

        int hash = hash(key);
        LinkedList<Tuple<K, V>> tuples = items[hash];
        if(tuples == null){
            return null;
        };

        for (Tuple<K, V> tuple : tuples) {
            if(tuple.equals(key)){
                return tuple.getValue();
            }
        }

        return null;
    }

    public int size(){
        int size = 0;
        for (LinkedList<Tuple<K, V>> tuples : items) {
            if(tuples != null){
                size += tuples.size();
            }
        }
        return size;
    }

    @Override
    public String toString() {


        StringBuilder sb = new StringBuilder();
        sb.append("[");

        int count = 0;
        for (LinkedList<Tuple<K, V>> tuples : items) {
            if(tuples == null){
                continue;
            }

            for (Tuple<K, V> tuple : tuples) {
                sb.append(tuple.toString()).append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public int hash(K key){
        int hash = key.hashCode() % CAPACITY;
        return hash < 0 ? hash * -1 : hash;
    }



    private static class Tuple<K, V> {
        private K key;
        private V value;

        public Tuple(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return key.equals(o);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return "{" + key + " = " + value + "}";
        }
    }


}
