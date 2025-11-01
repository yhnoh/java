import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

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

    @Nested
    class 크레인_인형뽑기_게임 {

        @Test
        public void solution() {
            //given

            //when
            int[][] board = {
                    {0,0,0,0,0},
                    {0,0,1,0,3},
                    {0,2,5,0,1},
                    {4,2,4,4,2},
                    {3,5,1,3,1}
            };

            int[] moves = {1,5,3,5,1,2,1,4};
            // 4 -> 3 -> 1 -> 1 -> 3 -> 2 -> 4
            System.out.println("solution = " + this.solution(board, moves));;

            //then
        }

        public int solution(int[][] board, int[] moves) {
            int answer = 0;

            Stack<Integer> stack = new Stack<>();
            int rowLength = board.length;
            for (int move : moves) {

                for (int i = 0; i < rowLength; i++) {
                    int cell = board[i][move - 1];
                    if(cell == 0) {
                        continue;
                    }

                    stack.add(cell);
                    board[i][move - 1] = 0;
                    break;
                }

                if(stack.size() >= 2) {
                    Integer last = stack.pop();
                    Integer lastBefore = stack.pop();

                    if(Objects.equals(lastBefore, last)) {
                        answer += 2;
                        continue;
                    }
                    stack.add(lastBefore);
                    stack.add(last);
                }
            }

            return answer;
        }
    }

    @Nested
    class 방금_그곡 {


        @Test
        public void solution() {
            //given
//            System.out.println(this.solution("ABCDEFG", new String[]{"12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"}));
            System.out.println(this.solution("ABC", new String[]{"12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"}));
//            this.solution("CC#BCC#BCC#BCC#B", new String[]{"03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"});
            //when

//            List<Integer> integers1 = List.of(1, 2);
//            List<Integer> integers2 = List.of(2, 1);
//            System.out.println(integers1.equals(integers2));
            //then
        }
        /**
         *
         * @param m
         * @param musicinfos [시작시각, 끝난시각, 음악제목, 악보정보]
         * @return
         */
        public String solution(String m, String[] musicinfos) {
            String answer = "";

            LinkedList<String> mInfoList = this.getInfoList(m);
            for (String musicinfo : musicinfos) {
                String[] arrMusicinfo = musicinfo.split(",");

                List<String> info = this.getInfo(arrMusicinfo[0], arrMusicinfo[1], arrMusicinfo[3]);

                int length = info.size() - mInfoList.size() - 1;
                for (int i = 0; i < length; i++) {
                    if(mInfoList.subList(0, mInfoList.size()).equals(info.subList(i, i + mInfoList.size()))) {
                        answer = arrMusicinfo[2];
                        break;

                    }
                }
            }
            return answer;
        }

        public List<String> getInfo(String startTime, String endTime, String info){
            String[] arrStartTime = startTime.split(":");
            String[] arrEndTime = endTime.split(":");
            int startMinutes = Integer.parseInt(arrStartTime[0]) * 60 + Integer.parseInt(arrStartTime[1]);
            int endMinutes = Integer.parseInt(arrEndTime[0]) * 60 + Integer.parseInt(arrEndTime[1]);

            int diffMinutes = endMinutes - startMinutes;

            LinkedList<String> linkedList = this.getInfoList(info);

            StringBuilder totalInfo = new StringBuilder();
            for (int i = 0; i < diffMinutes; i++) {
                String pollFirst = linkedList.pollFirst();
                totalInfo.append(pollFirst);
                linkedList.addLast(pollFirst);
            }

            return this.getInfoList(totalInfo.toString());
        }

        private LinkedList<String> getInfoList(String info) {
            LinkedList<String> linkedList = new LinkedList<>();
            while (!info.isEmpty()) {
                String tempInfo = "";
                if(info.startsWith("C#") || info.startsWith("D#") || info.startsWith("F#") || info.startsWith("G#") || info.startsWith("A#")) {
                    tempInfo = info.substring(0, 2);
                }else if (info.startsWith("C") || info.startsWith("D") || info.startsWith("E") || info.startsWith("F") || info.startsWith("G") || info.startsWith("A")|| info.startsWith("B")){
                    tempInfo = info.substring(0, 1);
                }
                linkedList.add(tempInfo);
                info = info.substring(tempInfo.length());
            }
            return linkedList;
        }

    }

}
