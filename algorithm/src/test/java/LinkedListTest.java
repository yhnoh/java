import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkedListTest {

    @Test
    public void appendTest(){
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
        System.out.println(list);
    }


    @Test
    public void getTest(){
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));

    }

    @Test
    public void insertTest(){
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.append(1);
        list.append(3);
        list.append(5);
    }

    private static class CustomLinkedList<T> {

        private Node<T> head;

        public void append(T data) {
            Node<T> newNode = new Node<>(data);

            if(head == null) {
                head = newNode;
                return;
            }

            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }

        public void insert(T data, int index){

            Node<T> findNode = this.getNode(index);
            Node<T> tempNode = findNode.next;

            findNode.next = new Node<>(data);
            if(tempNode != null) {
                findNode.next.next = tempNode;
            }

        }

        public T get(int index) {
            return this.getNode(index).data;
        }

        public Node<T> getNode(int index) {
            Node<T> node = head;
            if(node == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }

            for (int i = 0; i < index; i++) {
                node = node.next;
                if(node == null) {
                    throw new IndexOutOfBoundsException("Index out of bounds");
                }
            }
            return node;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("[");
            Node<T> current = head;
            while (current != null) {
                sb.append(current.data).append(", ");
                current = current.next;
            }
            if (sb.length() > 1) {
                sb.setLength(sb.length() - 2);
            }
            sb.append("]");

            return sb.toString();
        }

        private static class Node<T> {
            private final T data;
            private Node<T> next;

            public Node(T data) {
                this.data = data;
            }
        }
    }
}
