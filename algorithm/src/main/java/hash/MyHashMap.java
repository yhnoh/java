package hash;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyHashMap <K, V>{


    private static final int CAPACITY = 8;
    private List<LinkedList<V>> keys = new ArrayList<>(CAPACITY);

    public void put(K key, V value){
        int hash = hash(key);

        if(keys.get(hash) != null){

            LinkedList<V> values = keys.get(hash);
            if(values.)
            keys.add(hash, value);
        }


    }



//    public get




    public int hash(K key){
        int hash = key.hashCode() % CAPACITY;
        return hash < 0 ? hash * -1 : hash;
    }
}
