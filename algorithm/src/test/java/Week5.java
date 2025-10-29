import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Week5 {


    @Nested
    class 나_잡아_봐라 {

        @Test
        public void solution(){

        }

        // 방문에 대한 처리 필요 확인
        public int solution(int conyLoc, int brownLoc){

            int answer = 0;

            Queue<Integer> brownQueue = new LinkedList<>();
            brownQueue.add(brownLoc);

            int conyDistance = 1;
            while (conyLoc <= 200000) {
//                // 코니 위치가 범위 초과 시 잡을 수 없음
//                if(conyLoc > 200000) {
//                    break;
//                }

                Queue<Integer> tempBrownQueue = new LinkedList<>();
                while (!brownQueue.isEmpty()){
                    // 브라운과 코니 위치 확인
                    int brownPosition = brownQueue.poll();
                    if(brownPosition == conyLoc) {
                        break;
                    }
                    tempBrownQueue.add(brownPosition);
                }

                // 코니 위치 이동
                conyLoc += conyDistance;
                conyDistance += 1;

                // 브라운 위치 이동
                while (!tempBrownQueue.isEmpty()){
                    this.addBrownQueue(brownQueue, tempBrownQueue.poll());
                }

                answer++;
            }


            return answer;
        }

        private void addBrownQueue(Queue<Integer> brownQueue, int brownLoc) {
            // B - 1인 경우 범위 확인
            if(brownLoc - 1 >= 0) {
                brownQueue.add(brownLoc - 1);
            }

            // B + 1인 경우 범위 확인
            if(brownLoc + 1 <= 200000) {
                brownQueue.add(brownLoc + 1);
            }

            // B + 2인 경우 범위 확인
            if(brownLoc * 2 <= 200000) {
                brownQueue.add(brownLoc + 1);
            }

        }

    }

    @Nested
    class 문자열_압축 {

        @Test
        void soultion() {

            System.out.println(this.compress("aabbaccc", 1));
        }

        public int solution(String input){
            if(input.length() <= 2){
                return input.length();
            }

            int answer = input.length();

            int caseCount = input.length() / 2;
            for (int i = 0; i < caseCount; i++) {
                String compressed = this.compress(input, i + 1);
                answer = Math.min(answer, compressed.length());
            }

            return answer;
        }

        /**
         * 내용이 복잡하여 for문을 두개 사용하였는데
         * for문을 하나로 줄일 수 있는 방법을 고민해볼것
         */
        private String compress(String input, int caseCount){

            StringBuilder result = new StringBuilder();

            int beginIndex = 0;
            int endIndex = caseCount;
            int length = input.length();


            Queue<String> queue = new LinkedList<>();
            while (beginIndex < length){
                String substring = input.substring(beginIndex, endIndex);
                queue.add(substring);

                beginIndex += caseCount;
                endIndex = Math.min(endIndex + caseCount, length);
            }

            String start = queue.poll();
            int sameCount = 0;
            while (!queue.isEmpty()){
                String next = queue.poll();
                if(start.equals(next)){
                    sameCount++;
                    continue;
                }

                if(sameCount != 0){
                    result.append(sameCount + 1).append(start);
                } else {
                    result.append(start);
                }

                start = next;
                sameCount = 0;
            }

            if(sameCount != 0){
                result.append(sameCount + 1).append(start);
            } else {
                result.append(start);
            }


            return result.toString();
        }

    }

    @Nested
    class 올바른_괄호_문자열_만들기 {


        @Test
        void solution(){

        }

/*
        public String solution(String input){
            LinkedList<Integer> character = input.chars()
                    .boxed()
                    .collect(Collectors.toCollection(LinkedList::new));
        }
*/

        private boolean isValid(String input) {
            String start = input;
            boolean isValid = true;
            while (true) {
                String replace = start.replace("()", "");
                if (replace.length() == start.length()) {
                    isValid = false;
                    break;
                }
            }
            return isValid;
        }
    }

    @Nested
    class 새로운_게임 {
        @Test
        public void solution() {
            //given

            //when

            //then
        }

        int solution(int k, int[][] board, int[][] pieces) {
            int answer = -1;

            return answer;
        }

        public static class Node{
            private int x;
            private int y;
            private int direction;
            private List<Node> pieces;


        }
    }
}
