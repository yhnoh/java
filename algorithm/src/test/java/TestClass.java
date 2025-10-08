import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;


public class TestClass {

    @Nested
    class 농심_라면_공장 {

        @Test
        void 결과() {
            int stock = 4;
            int[] supplyDates = {4, 10, 15};
            int[] supplyStocks = {20, 5, 10};
            int k = 30;

            int length = supplyDates.length;

            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

            int lastDateIndex = 0;
            int result = 0;
            while (stock <= k) {
                for (int i = lastDateIndex; i < length; i++) {
                    //공급받는 기준은 현재 재고보다 같거나 작은 날짜
                    if (supplyDates[i] <= stock) {
                        maxHeap.offer(supplyStocks[i]);
                        lastDateIndex = i;
                    }
                }
                //공급 받을 때
                stock += maxHeap.poll();
                maxHeap.clear();
                result++;
            }

            System.out.println("result = " + result);
        }

    }

    @Nested
    class 샤오미_로봇_청소기 {

        @Test
        void 결과() {

            int[][] roadMap = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1, 1, 1, 1, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                    {1, 0, 1, 1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 1, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 1, 1, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            };

            int r = 7;
            int c = 4;
            int d = 0;

            int solution = this.solution(roadMap, r, c, d);
            System.out.println("solution = " + solution);
        }

        public int[] getBackStepPosition(int rSize, int cSize, int nowR, int nowC, int nowD) {
            int[][] canVisitPosition = new int[][]{
                    {-1, 0}, //북
                    {0, 1}, //동
                    {1, 0}, //남
                    {0, -1} //서
            };
            int tempR = -1;
            int tempC = -1;
            int backStepPosition = -1;
            if (nowD == 0) {
                backStepPosition = 2;
            } else if (nowD == 1) {
                backStepPosition = 3;
            } else if (nowD == 2) {
                backStepPosition = 0;
            } else if (nowD == 3) {
                backStepPosition = 1;
            }
            tempR = nowR + canVisitPosition[backStepPosition][0];
            tempC = nowC + canVisitPosition[backStepPosition][0];

            return tempR <= 0 || tempR == rSize || tempC <= 0 || tempC == cSize ? null : new int[]{tempR, tempC};
        }

        public int[] getPosition(int rSize, int cSize, int nowR, int nowC, int nowD) {

            int[][] canVisitPosition = new int[][]{
                    {-1, 0}, //북
                    {0, 1}, //동
                    {1, 0}, //남
                    {0, -1} //서
            };

            int tempR = nowR + canVisitPosition[nowD][0];
            int tempC = nowC + canVisitPosition[nowD][1];

            return tempR <= 0 || tempR == rSize || tempC <= 0 || tempC == cSize ? null : new int[]{tempR, tempC};
        }

        public boolean canVisit(int[][] roadMap, int r, int c) {

            int[][] canVisitPosition = new int[][]{
                    {-1, 0}, //북
                    {0, 1}, //동
                    {1, 0}, //남
                    {0, -1} //서
            };

            for (int i = 0; i < canVisitPosition.length; i++) {
                int visitR = r + canVisitPosition[i][0];
                int visitC = c + canVisitPosition[i][1];
                if (roadMap[visitR][visitC] == 0) {
                    return true;
                }
            }
            return false;
        }

        public int solution(int[][] roadMap, int r, int c, int d) {

            /**
             * 0: 북 -> 서, 0 -> 3
             * 1: 동 -> 북 1 -> 0
             * 2: 남 -> 동 2 -> 1
             * 3: 서 -> 남 3 -> 2
             */
            int visitedCount = 1;
            roadMap[r][c] = 1;
            int rSize = roadMap.length;
            int cSize = roadMap[0].length;
            while (true) {
                if (roadMap[r][c] == 0) {
                    roadMap[r][c] = 1;
                    visitedCount++;
                }

                //방향 전환이 가능하면
                int[] position = null;
                if (canVisit(roadMap, r, c)) {
                    position = getPosition(rSize, cSize, r, c, d);
                    r = position[0];
                    c = position[1];
                    System.out.println("position = " + Arrays.toString(position));

                    continue;
                }

                //후진이 가능하면
                int[] backPosition = getBackStepPosition(rSize, cSize, r, c, d);
                if (backPosition != null && canVisit(roadMap, backPosition[0], backPosition[1])) {
                    for (int i = 0; i < 2; i++) {
                        position = getPosition(rSize, cSize, r, c, d);
                    }
                    r = position[0];
                    c = position[1];
                    System.out.println("position = " + Arrays.toString(position));
                    continue;
                }

                break;
            }

            return visitedCount;

        }


    }
}
