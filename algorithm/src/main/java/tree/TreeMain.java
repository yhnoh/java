package tree;

import java.util.SortedSet;
import java.util.TreeSet;

public class TreeMain {


    public static void main(String[] args) {


        Tree<Integer> tree = new Tree<>();
        tree.add(10);
        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(15);
        tree.add(20);
        tree.add(25);

        tree.preorderTraversal();
        System.out.println();
        tree.inorderTraversal();
        System.out.println();
        tree.postorderTraversal();

//        int compare = Integer.compare(5, 6);
//        System.out.println("compare = " + compare);
//        Tree<Integer> tree = new Tree<>();
//        tree.add(5);
//        tree.add(6);
//        tree.add(1);
//        tree.add(2);
//        tree.add(3);
//        tree.add(4);
//        tree.add(4);
//
//        tree.inorderTraversal();
    }
}
