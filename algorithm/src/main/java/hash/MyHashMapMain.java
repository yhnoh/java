package hash;

public class MyHashMapMain {

    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap<>();

        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");
        map.put("key6", "value6");
        map.put("key7", "value7");
        map.put("key8", "value8");
        map.put("key9", "value9");
        map.put("key10", "value10");
        map.put("key11", "value11");

        System.out.println("map = " + map);

        System.out.println("map.get(\"key1\") = " + map.get("key1"));

    }
}
