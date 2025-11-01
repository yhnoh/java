package problem.gemini;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GeminiTest {

    @Nested
    class 인접한_영역_찾기 {

        /**
         * 문제 1: 인접한 영역 찾기 (Connected Components)문제 설명:$N \times M$ 크기의 2차원 배열(지도)이 주어집니다. 이 지도에서 값이 1인 부분은 연결된 영역을 나타내며, 값이 0인 부분은 빈 공간을 나타냅니다. 상하좌우로 인접한 1은 하나의 연결된 영역으로 간주합니다.DFS를 사용하여 지도에 있는 연결된 영역(1로 이루어진 덩어리)이 총 몇 개인지 구하는 자바 코드를 작성하세요.입력: 지도의 크기 $N$과 $M$, 그리고 지도 데이터 (2차원 배열).출력: 연결된 영역의 총 개수.
         */
        @Test
        public void solution(){

            int solution1 = this.solution(4, 5, new int[][]{
                    {1, 1, 0, 0, 0},
                    {1, 1, 0, 1, 1},
                    {0, 0, 0, 1, 1},
                    {0, 0, 0, 0, 0}
            });
            Assertions.assertEquals(2, solution1);
        }

        private final int[][] directions = {
                {-1, 0}, // 상
                {1, 0},  // 하
                {0, -1}, // 좌
                {0, 1}   // 우
        };

        public int solution(int n, int m, int[][] map){
            int result = 0;

            boolean[][] visited = new boolean[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if(!visited[i][j]){
                        if(map[i][j] == 1) {
                            this.findConnectedComponents(i, j, n, m, map, visited);
                            result++;
                        } else {
                            visited[i][j] = true;
                        }
                    }
                }
            }

            return result;
        }

        public void findConnectedComponents(int startY, int startX, int n, int m, int[][] map, boolean[][] visited){

            Stack<int[]> stack = new Stack<>();
            stack.add(new int[]{startY, startX});

            while (!stack.isEmpty()){
                int[] current = stack.pop();
                visited[current[0]][current[1]] = true;

                for (int[] direction : directions) {
                    int y = current[0] + direction[0];
                    int x = current[1] + direction[1];

                    if(y >= 0 && y < n && x >= 0 && x < m) {
                        if(!visited[y][x]) {
                            if(map[y][x] == 1) {
                                stack.add(new int[]{y, x});
                            } else {
                                visited[y][x] = true;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 2. 난이도: 보통 (Medium) - BFS 최단 거리 & 구현 능력문제 2: 미로 탈출 최단 거리문제 설명:$N \times M$ 크기의 미로가 주어집니다. 미로의 각 칸은 0 (벽, 이동 불가) 또는 1 (통로, 이동 가능)로 이루어져 있습니다. 당신은 $(0, 0)$ 위치에서 출발하여 $(N-1, M-1)$ 위치까지 최소 몇 칸을 이동해야 도착할 수 있는지 구하려고 합니다.BFS를 사용하여 시작점에서 도착점까지의 최단 거리를 구하는 자바 코드를 작성하세요. (시작점과 도착점도 이동 횟수에 포함됩니다.)입력: 미로의 크기 $N$과 $M$, 그리고 미로 데이터 (2차원 배열).출력: 시작점에서 도착점까지의 최소 이동 칸 수. 만약 도착할 수 없다면 -1을 출력합니다.예시 입력:N=4, M=4
     * Maze = [
     *     [1, 1, 0, 1],
     *     [0, 1, 0, 1],
     *     [0, 1, 1, 1],
     *     [1, 1, 1, 1]
     * ]
     * 예시 출력: 7 (경로: (0,0) $\to$ (0,1) $\to$ (1,1) $\to$ (2,1) $\to$ (2,2) $\to$ (2,3) $\to$ (3,3))
     */
    @Nested
    class 미로_탈출_최단_거리 {

        @Test
        public void solution(){
            int solution = this.solution(4, 4, new int[][]{
                    {1, 1, 0, 1},
                    {0, 1, 0, 1},
                    {0, 1, 1, 1},
                    {1, 1, 1, 1}
            });

            System.out.println("solution = " + solution);
        }

        private final int[][] directions = {
                {-1, 0}, // 상
                {1, 0},  // 하
                {0, -1}, // 좌
                {0, 1}   // 우
        };


        public int solution(int n, int m, int[][] maze){

            int[][] distances = new int[n][m];
            boolean[][] visited = new boolean[n][m];


            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{0 ,0});
            distances[0][0] = 1;

            while (!queue.isEmpty()){
                int[] current = queue.poll();
                int currentY = current[0];
                int currentX = current[1];
                visited[currentY][currentX] = true;


                for (int[] direction : directions) {
                    int y = currentY + direction[0];
                    int x = currentX + direction[1];

                    if(y >= 0 && y < n && x >= 0 && x < m) {
                        if(!visited[y][x]) {
                            if(maze[y][x] == 1) {
                                queue.add(new int[]{y, x});
                                distances[y][x] = distances[currentY][currentX] + 1;
                            }else {
                                visited[y][x] = true;
                            }
                        }
                    }
                }
            }

            for (int[] distance : distances) {
                System.out.println(Arrays.toString(distance));
            }

            return distances[n - 1][m - 1] == 0 ? -1 : distances[n - 1][m - 1];
        }

    }

    @Nested
    class 벽_부수고_이동하기 {

    }

    @Nested
    class 프로그래밍 {


        public int[] solution(int[][] v) {

            Map<Integer, Integer> xMap = new HashMap<>();
            Map<Integer, Integer> yMap = new HashMap<>();

            for (int[] xy : v) {
                xMap.put(xy[1], xMap.getOrDefault(xy[1], 0) + 1);
                yMap.put(xy[0], yMap.getOrDefault(xy[0], 0) + 1);
            }

            int y = 0;
            int x = 0;

            for (Integer key : xMap.keySet()) {
                if(xMap.get(key) == 1) {
                    x = key;
                    break;
                }
            }

            for (Integer key : yMap.keySet()) {
                if(yMap.get(key) == 1) {
                    y = key;
                    break;
                }
            }

            return new int[]{y, x};
        }
    }
}
