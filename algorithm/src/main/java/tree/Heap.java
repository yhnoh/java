package tree;

import java.util.ArrayList;
import java.util.List;

public class Heap<E> {

    private Node<E> root;

    public Node<E> add(E value){

        if(root == null){
            root = new Node<>(value);
            return root;
        }

        return insert(root, value);
    }

    private Node<E> insert(Node<E> node, E value){
        if(node == null){
            return new Node<>(value);
        }

        if(node.left == null){
            node.left = insert(node.left, value);
            return node.left;
        }else {
            node.right = insert(node.right, value);
            return node.right;
        }
    }



    private static class Node<E> implements Comparable<E> {
        private E value;
        private Node<E> left;
        private Node<E> right;

        public Node(E value) {
            this.value = value;
        }

        @Override
        public int compareTo(E o) {
            return ((Comparable<E>) value).compareTo(o);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
