package tree.dfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StackDFS {


    /**
     * Graph <br/>
     * 0 -> [1, 2] <br/>
     * 1 -> [3] <br/>
     * 2 -> [4, 5] <br/>
     * 3 -> [] <br/>
     * 4 -> [] <br/>
     * 5 -> [] <br/>
     * <br/>
     * 0 -> 2 <br/>
     * 2 -> 5 <br/>
     * 5 -> [] <br/>
     * 4 -> [] <br/>
     * 1 -> 3 <br/>
     */
    public static void main(String[] args) {

        int length = 6;
        List<List<Integer>> graph = new LinkedList<>();
        boolean[] visited = new boolean[length];
        for (int i = 0; i < length; i++) {
            graph.add(new LinkedList<>());
        }


        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);
        graph.get(2).add(4);
        graph.get(2).add(5);

        dfs(graph, visited, 0);
        System.out.println();
        System.out.println("visited = " + Arrays.toString(visited));
    }

    private static void dfs(List<List<Integer>> graph, boolean[] visited, int startNode) {
        Stack<Integer> stack = new Stack<>();
        stack.push(startNode);
        visited[startNode] = true;

        while (!stack.isEmpty()) {
            Integer node = stack.pop();
            System.out.print(node + " ");
            for (Integer item : graph.get(node)) {
                if (!visited[item]) {
                    stack.push(item);
                    visited[item] = true;
                }
            }
        }

    }

}
