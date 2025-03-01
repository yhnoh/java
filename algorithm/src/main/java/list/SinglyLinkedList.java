package list;

public class SinglyLinkedList<T> {

    private Node<T> head;
    private Node<T> last;

    public SinglyLinkedList() {
    }

    public void append(T data){
        if(this.head == null){
            this.head = new Node<>(data);
            this.last = head;
            return;
        }

        if(this.last.next == null){
            this.last.append(data);
            this.last = this.last.next;
        }
    }

    public void insert(int index, T data){
        if(index == 0){
            Node<T> newHead = new Node<>(data);
            newHead.next = this.head;
            this.head = newHead;
            return;
        }

        Node<T> current = this.findNode(index - 1);
        Node<T> currentNext = current.next;
        current.next = new Node<>(data);
        current.next.next = currentNext;
    }

    private Node<T> findNode(int index){
        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public T get(int index){
        return findNode(index).data;
    }

    public void remove(int index){
        Node<T> prev = null;
        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            prev = current;
            current = current.next;
        }

        if(current == this.head){
            this.head = this.head.next;
            return;
        }

        if(current == this.last){
            this.last = prev;
        }

        prev.next = current.next;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = this.head;
        while (current != null){
            sb.append(current);
            current = current.next;
            if(current != null){
                sb.append(", ");
            }
        }

        sb.append("]");

        return sb.toString();
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }

        private void append(T data){
            this.next = new Node<>(data);
        }

        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }

}
