import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GraphTest {

    @Nested
    class DFSTest {
        @Test
        public void test() {
            //given
            Map<Integer, List<Integer>> graph = new HashMap<>();
            graph.put(1, List.of(2, 5, 9));
            graph.put(2, List.of(1, 3));
            graph.put(3, List.of(2, 4));
            graph.put(4, List.of(3));
            graph.put(5, List.of(1, 6, 8));
            graph.put(6, List.of(5, 7));
            graph.put(7, List.of(6));
            graph.put(8, List.of(5));
            graph.put(9, List.of(1, 10));
            graph.put(10, List.of(9));


            ArrayList<Integer> result = new ArrayList<>();

            reclusive(graph, 1, result);
            System.out.println(result);

            this.stack(graph, 1);
        }

        @Test
        public void reclusiveTest(){
            Map<Integer, List<Integer>> graph = new HashMap<>();
            graph.put(1, List.of(2, 5, 9));
            graph.put(2, List.of(1, 3));
            graph.put(3, List.of(2, 4));
            graph.put(4, List.of(3));
            graph.put(5, List.of(1, 6, 8));
            graph.put(6, List.of(5, 7));
            graph.put(7, List.of(6));
            graph.put(8, List.of(5));
            graph.put(9, List.of(1, 10));
            graph.put(10, List.of(9));


            ArrayList<Integer> result = new ArrayList<>();
            this.reclusive(graph, 1, result);
            System.out.println("재귀함수를 통한 DFS 구현, result = " + result);
        }

        /**
         * 재귀함수를 통한 DFS 구현
         * 1. 루트 노드에서 시작한다.
         * 2. 방문을 표시한다.
         * 3. 방문한 노드의 인접한 노드를 가져온다.
         * 4. 인접한 노드 중 방문하지 않은 노드가 있다면 해당 노드를 현재 노드로 설정하고 2번으로 돌아간다.
         */
        public void reclusive(Map<Integer, List<Integer>> graph, int current, List<Integer> visited) {

            visited.add(current);
            List<Integer> values = graph.get(current);
            for (Integer value : values) {
                if(!visited.contains(value)) {
                    this.reclusive(graph, value, visited);
                }
            }
        }

        @Test
        public void stackTest(){
            Map<Integer, List<Integer>> graph = new HashMap<>();
            graph.put(1, List.of(2, 5, 9));
            graph.put(2, List.of(1, 3));
            graph.put(3, List.of(2, 4));
            graph.put(4, List.of(3));
            graph.put(5, List.of(1, 6, 8));
            graph.put(6, List.of(5, 7));
            graph.put(7, List.of(6));
            graph.put(8, List.of(5));
            graph.put(9, List.of(1, 10));
            graph.put(10, List.of(9));

            List<Integer> result = this.stack(graph, 1);
            System.out.println("스택을 통한 DFS 구현, result = " + result);
        }


        /**
         * 스택을 통한 DFS 구현
         * 1. 루트 노드를 Stack에 넣고 시작한다.
         * 2. Stack을 꺼내 방문을 표시한다.
         * 3. 방문한 노드의 인접한 노드를 가져온다.
         * 4. 인접한 노드 중 방문하지 않은 노드가 있다면 해당 노드를 현재 노드로 설정하고 2번으로 돌아간다.
         */
        public List<Integer> stack(Map<Integer, List<Integer>> graph, int current) {
            Stack<Integer> stack = new Stack<>();
            List<Integer> visited = new ArrayList<>();

            stack.push(current);

            while (!stack.isEmpty()) {
//                System.out.println("stack = " + stack + ", visited = " + visited);
                Integer pop = stack.pop();
                visited.add(pop);

                List<Integer> values = graph.get(pop);
                for (Integer value : values) {
                    if (!visited.contains(value)) {
                        stack.push(value);
                    }
                }
            }

            return visited;
        }
    }


    @Nested
    class BFSTest {

        @Test
        public void solution() {

            Map<Integer, List<Integer>> graph = new HashMap<>();
            graph.put(1, List.of(2, 5, 9));
            graph.put(2, List.of(1, 3));
            graph.put(3, List.of(2, 4));
            graph.put(4, List.of(3));
            graph.put(5, List.of(1, 6, 8));
            graph.put(6, List.of(5, 7));
            graph.put(7, List.of(6));
            graph.put(8, List.of(5));
            graph.put(9, List.of(1, 10));
            graph.put(10, List.of(9));
            List<Integer> result = this.queue(graph, 1);
            System.out.println(result);
        }


        /**
         * 큐를 통한 BFS 구현
         * 1. 루트 노드를 Queue에 넣고 시작한다.
         * 2. Queue를 꺼내 방문을 표시한다.
         * 3. 방문한 노드의 인접한 노드를 가져온다.
         * 4. 인접한 노드 중 방문하지 않은 노드가 있다면 해당 노드를 Queue에 넣고 2번으로 돌아간다.
         */
        public List<Integer> queue(Map<Integer, List<Integer>> graph, int start) {
            Queue<Integer> queue = new LinkedList<>();
            List<Integer> visited = new ArrayList<>();

            queue.offer(start);

            while (!queue.isEmpty()) {
                System.out.println("queue = " + queue + ", visited = " + visited);
                Integer poll = queue.poll();
                visited.add(poll);

                List<Integer> values = graph.get(poll);
                for (Integer value : values) {
                    if (!visited.contains(value)) {
                        queue.offer(value);
                    }
                }
            }

            return visited;
        }
    }

}
