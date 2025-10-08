package tree.bfs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueBFS {


    /**
     * Graph <br/>
     * 0 -> [1, 2] <br/>
     * 1 -> [3] <br/>
     * 2 -> [4, 5] <br/>
     * 3 -> [] <br/>
     * 4 -> [] <br/>
     * 5 -> [] <br/>
     * <br/>
     * 0 -> 1, 2 <br/>
     * 1 -> 3 <br/>
     * 2 -> [4, 5] <br/>
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

        bfs(graph, visited, 0);
        System.out.println("visited = " + Arrays.toString(visited));
    }

    public static void bfs(List<List<Integer>> graph, boolean[] visited, int startNode) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startNode);
        visited[startNode] = true;

        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            System.out.print("node = " + node);

            for (Integer item : graph.get(node)) {
                if (!visited[item]) {
                    queue.offer(item);
                    visited[item] = true;
                }
            }

        }
    }


}
