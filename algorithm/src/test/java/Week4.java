import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Week4 {

    @Nested
    class 농심_라면_공장 {

        @Test
        void solution() {

            this.solution(4,
                    List.of(4, 10, 15),
                    List.of(20, 5, 10),
                    30);
        }

        /**
         * 매일 밀가루를 1톤씩 소비
         * @param stock: 현재 남아 있는 밀가루 톤수
         * @param dates: 밀가루 공급 일정
         * @param supplies: 밀가루 공급 수량
         * @param k: 밀가루 공급 가능 일자
         */
        public int solution(int stock, List<Integer> dates, List<Integer> supplies, int k){
            int now = stock;
            int need = k - stock;

            PriorityQueue<Integer> pq = new PriorityQueue<>();
            int start = 0;
            int end = dates.size() - 1;

            return 0;
        }

//        public int getDate(List<Integer> dates, List<Integer> supplies, int start){
//            PriorityQueue<Integer> queue = new PriorityQueue<>();
//            int length = dates.size();
//
//            for (int i = 0; i < start; i++) {
//                queue.poll();
//            }
//
//
//        }



        private static class Supply {
            private final int date;
            private final int supply;

            public Supply(int date, int supply) {
                this.date = date;
                this.supply = supply;
            }

            public int getDate() {
                return date;
            }

            public int getSupply() {
                return supply;
            }
        }
    }


    @Nested
    class 샤오미_로봇_청소기 {


        /**
         *
         * @param r 북쪽으로부터 떨어진 칸
         * @param c 서쪽으로부터 떨아진 칸
         * @param d: 방향 0:북, 1:동, 2:남, 3:서
         * @param roomMap
         * @return
         */
        public int solution(int r, int c, int d, int[][] roomMap){
            int x = roomMap.length + r - 1;
            int y = roomMap[0].length + c - 1;
            boolean isStop = false;
            int result = 0;
            Robot robot = new Robot(x, y, d);
            if(robot.clean(roomMap)) {
                result++;
            }

            robot.turnLeft();
//            if(robot.canClean(roomMap)) {
//                robot.moved();
//                robot.clean(roomMap);
//            };
            while (true) {

            }

        }

        static class Robot {
            private int x;
            private int y;
            private int d;

            public Robot(int x, int y, int d) {
                this.x = x;
                this.y = y;
                this.d = d;
            }



            /**
             * 청소 가능가 가능하면 청소
             */
            public boolean clean(int[][] roomMap) {
                boolean canClean = roomMap[y][x] == 0;
                if(canClean) {
                    roomMap[y][x] = 1;
                    return true;
                }
                return false;
            }


            /**
             * 네 방향 모두 청소가 되어있는지 확인
             */
            public boolean isAllDirectionCleaned(int[][] roomMap){
                return roomMap[y - 1][x] == 1 &&
                       roomMap[y][x + 1] == 1 &&
                       roomMap[y + 1][x] == 1 &&
                       roomMap[y][x - 1] == 1;
            }

            /**
             * 네 방향 모두 청소가 되어있는지 확인
             */
            public boolean moveBack(int[][] roomMap){

                int x = this.x;
                int y = this.y;

                if(d == 0){
                    x = x;
                    y = y - 1;
                }else if(d == 1){
                    x = x + 1;
                    y = y;
                }else if(d == 2){
                    x = x;
                    y = y + 1;
                }else if (d == 3){
                    x = x - 1;
                    y = y;
                }


                return roomMap[y - 1][x] == 1 &&
                        roomMap[y][x + 1] == 1 &&
                        roomMap[y + 1][x] == 1 &&
                        roomMap[y][x - 1] == 1;
            }

            /**
             * 왼쪽으로 방향 전환
             */
            public void turnLeft(){
                switch (d){
                    case 0:
                        d = 3;
                        break;
                    case 1:
                        d = 0;
                        break;
                    case 2:
                        d = 1;
                        break;
                    case 3:
                        d = 2;
                        break;
                }

                throw new IllegalArgumentException("Invalid direction");
            }

            public boolean canMove(int[][] roomMap){
                Robot movedRobot = this.movedRobot();
                return roomMap[movedRobot.y][movedRobot.x] == 0;
            }

            public boolean move(){
                int x = this.x;
                int y = this.y;

                if(d == 0){
                    x = x;
                    y = y - 1;
                }else if(d == 1){
                    x = x + 1;
                    y = y;
                }else if(d == 2){
                    x = x;
                    y = y + 1;
                }else if (d == 3){
                    x = x - 1;
                    y = y;
                }

                if(x < 0 || y < 0){
                    return false;
                }

                this.x = x;
                this.y = y;
                return true;
            }

            /**
             * 이동
             */
            private Robot movedRobot(){
                if(d == 0){
                    return new Robot(x, y - 1, d);
                }else if (d == 1){
                    return new Robot(x + 1, y, d);
                }else if (d == 2){
                    return new Robot(x, y + 1, d);
                }else if (d == 3){
                    return new Robot(x - 1, y, d);
                }
                throw new IllegalArgumentException("Invalid direction");
            }

            public Robot movedBack(){
                if(d == 0){
                    return new Robot(x, y - 1, d);
                }else if (d == 1){
                    return new Robot(x + 1, y, d);
                }else if (d == 2){
                    return new Robot(x, y + 1, d);
                }else if (d == 3){
                    return new Robot(x - 1, y, d);
                }
                throw new IllegalArgumentException("Invalid direction");
            }


        }
    }

    @Nested
    class CGV_극장_좌석_자리_구하기 {
        @Test
        void solution(){

        }


        private int solution(int seatCount, int[] vipSeats){

            List<Integer> seats = IntStream.range(1, vipSeats.length + 1)
                    .boxed()
                    .collect(Collectors.toList());

            Set<Integer> vipSeatSet = Arrays.stream(vipSeats)
                    .boxed()
                    .collect(Collectors.toSet());

            int fromIndex = 0;
            int endIndex = vipSeats[0];

            List<List<Integer>> nonVipSeatsGroup = new ArrayList<>();
            Queue<Integer> queue = new LinkedList<>();
            for (Integer seat : seats) {

                if(!vipSeatSet.contains(seat)) {
                    queue.add(seat);
                    continue;
                }

                ArrayList<Integer> nonVipSeats = new ArrayList<>();
                while (!queue.isEmpty()) {
                    nonVipSeats.add(queue.poll());
                }
                nonVipSeatsGroup.add(nonVipSeats);
            }

            if(nonVipSeatsGroup.size() == 0) {
                return 1;
            }


            int result = 1;
            for (List<Integer> nonVipSeats : nonVipSeatsGroup) {
                result *= nonVipSeats.size();
            }
            return result;
        }
    }
}
