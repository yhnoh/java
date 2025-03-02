package tree;

public class Tree<T> {

    private Node<T> root;

    public void add(T value){
        if(root == null){
            root = new Node<>(value);
            return;
        }

        this.addReclusive(root, value);
    }

    /**
     *
     */
    private Node<T> addReclusive(Node<T> node, T value){
        if(node == null){
            return new Node<>(value);
        }

        if(node.compareTo(value) > 0){
            node.left = addReclusive(node.left, value);
        } else if(node.compareTo(value) < 0){
            node.right = addReclusive(node.right, value);
        }

        return node;
    }


    /**
     * 전위 순위(Preorder Traversal): 루트 -> 왼쪽 -> 오른쪽
     */
    public void preorderTraversal() {
        preorderRecursive(root);
    }

    public void preorderRecursive(Node<T> node){
        if(node != null){
            System.out.print(node.data + " ");
            preorderRecursive(node.left);
            preorderRecursive(node.right);
        }
    }

    /**
     * 중위 순회(Inorder Traversal): 왼쪽 -> 루트 -> 오른쪽
     */
    public void inorderTraversal() {
        inorderRecursive(root);
    }

    private void inorderRecursive(Node<T> node) {
        if (node != null) {
            inorderRecursive(node.left);
            System.out.print(node.data + " ");
            inorderRecursive(node.right);
        }
    }

    /**
     * 후위 순회(Postorder Traversal): 왼쪽 -> 오른쪽 -> 루트
     */
    public void postorderTraversal(){
        postorderTraversal(root);
    }

    /**
     * 왼쪽 -> 오른쪽 -> 루트
     */
    private void postorderTraversal(Node<T> node) {
        if(node != null){
            postorderTraversal(node.left);
            postorderTraversal(node.right);
            System.out.print(node.data + " ");
        }
    }


    private static class Node<T> implements Comparable<T>{
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
        }

        @Override
        public int compareTo(T o) {
            return ((Comparable<T>) data).compareTo(o);
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
