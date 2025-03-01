package list;

public class SinglyLinkedListMain {

    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();

        list.append("data1");
        list.append("data2");
        list.append("data3");
        list.append("data4");
        list.append("data5");

        System.out.println("list = " + list);

        System.out.println("list.get(0) = " + list.get(0));
        System.out.println("list.get(1) = " + list.get(1));
        System.out.println("list.get(2) = " + list.get(2));
        System.out.println("list.get(3) = " + list.get(3));
        System.out.println("list.get(4) = " + list.get(4));

        list.insert(1, "data1_1");
        list.insert(5, "data5_1");
        System.out.println("list = " + list);

        list.remove(0);
        System.out.println("list = " + list);
        list.append("data6");

        list.remove(4);
        System.out.println("list = " + list);
        list.append("data7");

        list.remove(2);
        System.out.println("list = " + list);
    }
}
