import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ProgrammersLevel2 {

    @Nested
    class 최댓값과_최솟값 {

        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/12939
         * 문자열 s에는 공백으로 구분된 숫자들이 저장
         * 최소값 최대값 형태의 문자열 반환 필요
         */
        @Test
        public void solution() {
            //given
            //when
            System.out.println(this.solution("-1 -2 -3 -4"));
            //then
        }

        public String solution(String s) {
            String[] array = s.split(" ");

            SortedSet<Integer> set = new TreeSet<>();
            for (String str : array) {
                set.add(Integer.parseInt(str));
            }

            return set.first() + " " + set.last();
        }

    }

    @Nested
    class 올바른_괄호 {

        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/12909
         */
        @Test
        public void solution() {
            //given

            String s = "(()())()";
            boolean result = solution(s);
            //when
            System.out.println("result = " + result);
            //then
        }

        boolean solution(String s) {

            char[] chars = s.toCharArray();
            if (chars.length % 2 != 0) {
                return false;
            }

            Stack<Character> leftStack = new Stack<>();
            for (char character : chars) {

                //'('가 없는데 ')'가 나오는 경우
                if (leftStack.isEmpty() && character == ')') {
                    return false;
                }

                if (character == '(') {
                    leftStack.push(character);
                } else if (character == ')') {
                    leftStack.pop();
                }
            }

            return leftStack.size() == 0;
        }
    }

    @Nested
    class 최솟값_만들기 {
        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/12941
         */
        @Test
        public void solution() {
            //given

            //when

            //then

            int[] A = {1, 4, 2};
            int[] B = {5, 4, 4};
            int solution = this.solution(A, B);
            System.out.println("answer = " + solution);
        }

        //A: 1 2 4
        //B: 5 4 4
        // 결과: 1*4 + 2*4 + 4*5 = 4 + 8 + 20 = 32
        public int solution(int[] A, int[] B) {
            int answer = 0;

            int length = A.length;
            Arrays.sort(A);
            Arrays.sort(B);

            for (int i = 0; i < length; i++) {
                System.out.println("A = " + A[i] + ", B = " + B[length - i - 1]);
                answer += A[i] * B[length - i - 1];
            }

            return answer;
        }
    }

    @Nested
    class JadenCase_문자열_만들기 {

        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/12951
         */
        @Test
        public void solution() {
            //given

            //when
            String solution = this.solution("for the last week");
            System.out.println("solution = " + solution);
            //then
        }

        public String solution(String s) {
            StringBuilder answer = new StringBuilder();
            char[] chars = s.toCharArray();
            boolean isFirst = true;
            for (char c : chars) {
                if (c == ' ') {
                    isFirst = true;
                    answer.append(c);
                    continue;
                }

                if (isFirst) {
                    answer.append(Character.toUpperCase(c));
                } else {
                    answer.append(Character.toLowerCase(c));
                }
                isFirst = false;
            }

            return answer.toString();
        }
    }

    @Nested
    class 이진_변환_반복 {

        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/70129
         */
        @Test
        public void solution() {
            //given

            //when
//            System.out.println(this.solution("0111010"));

            System.out.println(Arrays.toString(this.solution("110010101001")));
//            this.solution("100");
//            this.solution("11");
//            this.solution("0");
            //then
        }

        public int[] solution(String s) {

            int deletedZeroCount = 0;
            int replacedCount = 0;

            while (!s.equals("1")) {
                String tempS = s;
                //0으로 변환
                String deletedZeroString = s.replace("0", "");

                //길이를 바이트로 변환
                int deletedZeroLength = deletedZeroString.length();
                s = Integer.toBinaryString(deletedZeroLength);

                deletedZeroCount += tempS.length() - deletedZeroString.length();
                replacedCount++;
            }

            return new int[]{replacedCount, deletedZeroCount};
        }
    }

    @Nested
    class 숫자의_표현 {

        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/12924
         */

        @Test
        public void solution() {
            int answer = this.solution(15);
            System.out.println("answer = " + answer);
        }

        /**
         * stopN = 15일 경우
         * n = 1이 들어올 경우, n = 2, stopN = 14
         * n = 2이 들어올 경우, n = 3, stopN = 12
         * n = 3이 들어올 경우, n = 4, stopN = 9
         * n = 4이 들어올 경우, n = 5, stopN = 4
         * n = 5이 들어올 경우, n = 6, stopN = 0
         */
        public int reclusive(int n, int stopN) {
            if (stopN < 0) {
                return 0;
            } else if (stopN == 0) {
                return 1;
            }

            return reclusive(n + 1, stopN - n);
        }

        public int solution(int n) {
            if (n == 1) {
                return 1;
            }

            int answer = 0;

            for (int i = 1; i <= n; i++) {
                answer += this.reclusive(i, n);
            }

            return answer;
        }
    }

    @Nested
    class 다음_큰_숫자 {
        @Test
        public void solution() {
            //given

            //when
//            this.solution1(78);
//            this.solution2(78);
            this.solution3(78);
            //then
        }

        /**
         * 1. n 보다 큰 숫자 중
         * 2. n과 n보다 큰 숫자를 이진수로 변환했을때 1의 개수가 같은 숫자
         * 3. 1번과 2번의 조건을 만족하는 가장 작은 숫자
         */
        public int solution1(int n) {

            String nBinary = Integer.toBinaryString(n);
            int nextN = n + 1;
            String nextBinary = Integer.toBinaryString(nextN);
            while (!nBinary.replace("0", "").equals(nextBinary.replace("0", ""))) {
                nextN++;
                nextBinary = Integer.toBinaryString(nextN);
//                System.out.println("nextN = " + nextN + " nextBinary = " + nextBinary);
            }

            return nextN;
        }

        public int solution2(int n) {

            String nBinary = Integer.toBinaryString(n);
            String deletedZeroNBinary = nBinary.replace("0", "");
            int nextN = n + 1;
            String nextBinary = Integer.toBinaryString(nextN);

            while (true) {
                if (deletedZeroNBinary.equals(nextBinary.replace("0", ""))) {
                    break;
                }

                nextN++;
                nextBinary = Integer.toBinaryString(nextN);
//                System.out.println("nextN = " + nextN + " nextBinary = " + nextBinary);
            }

            return nextN;
        }


        /**
         * Integer.bitCount: 정수를 2진수로 변환한 이후 1의 개수를 반환
         */
        public int solution3(int n) {

            int nBitCount = Integer.bitCount(n);
            int nextN = n + 1;
            int nextBitCount = Integer.bitCount(nextN);
            while (nBitCount != nextBitCount) {
                nextN++;
                nextBitCount = Integer.bitCount(nextN);
//                System.out.println("nBitCount = " + nBitCount + " nextN = " + nextN + " nextBitCount = " + nextBitCount);
            }
            return nextN;
        }
    }

    @Nested
    class 피보나치_수 {
        @Test
        public void solution() {

//            System.out.println("n = 3일 경우 " + this.solution(3));
            System.out.println("n = 4일 경우 " + this.solution(4));
            System.out.println("n = 5일 경우 " + this.solution(5));
        }

        public int solution(int n) {

            if (n == 0) {
                return 0;
            } else if(n <= 2){
                return 1;
            }

            int left = 1;
            int right = 1;

            /**
             * n = 3일 경우
             * left = 1, right = 1 + 1
             *
             * n = 5일 경우
             * i= 2, left = 1, right = 2
             * i= 3, left = 2, right = 3
             * i= 4, left = 3, right = 5
             */
            for (int i = 2; i < n; i++) {
                int temp = left % 1234567;
                left = right;
                right = temp + right;

            }

            return right % 1234567;
        }

        public int reclusive(int n, Map<Integer, Integer> cache){

            if(cache.containsKey(n)) {
                return cache.get(n);
            }


            int left = this.solution(n - 2);
            int right = this.solution(n - 1);

            cache.put(n - 2, left  % 1234567);
            cache.put(n - 1, right);

            return left + right;
        }


    }

    @Nested
    class 짝지어_제거하기 {
        @Test
        public void solution() {

            this.solution("cdcd");
        }

        /**
         * stack = [b]
         * stack = [b, a]
         * stack = [b]
         * stack = []
         * stack = [a]
         * stack = []
         */
        public int solution(String s) {

            char[] chars = s.toCharArray();
            Stack<Character> stack = new Stack<>();
            for (char c : chars) {

                if (stack.isEmpty()) {
                    stack.push(c);
                    System.out.println("stack " + stack);
                    continue;
                }

                Character pop = stack.pop();
                if (pop != c) {
                    stack.push(pop);
                    stack.push(c);
                }
                System.out.println("stack " + stack);
            }


            return stack.isEmpty() ? 1 : 0;
        }
    }

    @Nested
    class 카펫 {

        @Test
        public void solution() {
            this.solution(24, 24);
        }

        /**
         * brown = (x * 2) + (y - 2) * 2
         * <p>
         * 0 0 0
         * 0 1 0
         * 0 0 0
         * <p>
         * 0 0 0 0
         * 0 1 1 0
         * 0 0 0 0
         * <p>
         * 0 0 0 0 0
         * 0 1 1 1 0
         * 0 0 0 0 0
         * <p>
         * 0 0 0 0
         * 0 1 1 0
         * 0 1 1 0
         * 0 0 0 0
         */
        public int[] solution(int brown, int yellow) {

            int length = brown + yellow;

            for (int x = 3; x <= length; x++) {
                for (int y = 3; y <= length; y++) {
                    //사각형이 만들어지지 않는 조건
                    if (x * y != length) {
                        continue;
                    }
                    System.out.println("x = " + x + " y = " + y);
                    //사각형이 만들어 졌을때
                    if (brown == x * 2 + (y - 2) * 2) {
                        return new int[]{y, x};
                    }
                }
            }
            return new int[]{0, 0};
        }
    }


    @Nested
    class 점프와_순간_이동 {
        @Test
        public void solution() {

            System.out.println("5일 경우 " + this.solution(5));
            System.out.println("6일 경우 " + this.solution(6));
            System.out.println("5000일 경우 " + this.solution(5000));
        }

        /**
         * n = 목표 거리
         * p = 현재 위치
         * k = 배터리 사용량
         * 순간이동할 경우 현재 위치에서 * 2, 배터리 사용하지 않음
         * 그냥 이동할 경우 배터리 사용
         * <p>
         * depth = 0, {(Node=k=1, p=1)}
         * depth = 1, {(Node=k=1, p=2), (Node=k=2, p=2)}
         * depth = 2, {(Node=k=1, p=4), (Node=k=2, p=3), (Node=k=2, p=4), (Node=k=3, p=3)}
         */
        public int solution(int n) {

            // 값 초기화
            Node node = new Node(1, 1);
            Queue<Node> queue = new LinkedList<>();
            queue.add(node);

            int caseCount = 1;
            PriorityQueue<Integer> batteryQueue = new PriorityQueue<>();

            while (batteryQueue.isEmpty()) {

                // 현재 depth의 모든 노드들을 꺼내서 자식 노드들로 변환
                for (int i = 0; i < caseCount; i++) {
                    Node poll = queue.poll();
                    if(poll.getP() == n) {
                        batteryQueue.add(poll.getK());
                    }
                    queue.add(poll.move());
                    queue.add(poll.jump());
                }
                caseCount *= 2;
            }

            return batteryQueue.poll();
        }

        private static class Node {
            private final int k;
            private final int p;

            public Node(int k, int p) {
                this.k = k;
                this.p = p;
            }

            public int getK() {
                return k;
            }

            public int getP() {
                return p;
            }

            public Node move() {
                return new Node(this.k + 1, this.p + 1);
            }

            public Node jump(){
                return new Node(this.k, this.p * 2);
            }


        }

    }


    @Nested
    class 구명보트 {
        @Test
        public void solution() {

            System.out.println(this.solution2(new int[]{70, 50, 80, 50}, 100));

        }

        public int solution1(int[] people, int limit) {
            int answer = 0;

            Arrays.sort(people);

            int start = 0;
            int end = people.length - 1;

            while (start <= end) {
                int startWeight = people[start];
                int endWeight = people[end];

                if (startWeight == 0 && endWeight == 0) {
                    break;
                }

                int sum = startWeight + endWeight;


                if (sum <= limit) {
                    people[start] = 0;
                    people[end] = 0;
                    start++;
                    end--;
                    answer++;
                } else {
                    people[end] = 0;
                    end--;
                    answer++;
                }
            }

            if (people[start] != 0) {
                answer++;
            }


            return answer;
        }


        public int solution2(int[] people, int limit) {
            int answer = 0;

            Arrays.sort(people);
            LinkedList<Integer> weights = new LinkedList<>();
            for (Integer weight : people) {
                weights.add(weight);
            }

            while (!weights.isEmpty()) {
                // 더이상 꺼내올 몸무게가 없으면
                Integer minWeight = weights.pollFirst();
                if (weights.isEmpty()) {
//                    System.out.println(weights);
                    answer++;
                    break;
                }

                // sum이 limit보다 작거나 같으면
                Integer maxWeight = weights.pollLast();
                if (minWeight + maxWeight <= limit) {
//                    System.out.println(weights);
                    answer++;
                    continue;
                }

                weights.addFirst(minWeight);
                answer++;
//                System.out.println(weights);
            }

            return answer;
        }

    }

    @Nested
    class 귤_고르기 {
        @Test
        public void solution() {
            this.solution3(6, new int[]{1, 3, 2, 5, 4, 5, 2, 3});
        }

        /**
         * {1, 3, 2, 5, 4, 5, 2, 3} => {1, 2, 2, 3, 3, 4, 5, 5}
         * 2 => 2, 3, 5
         * 1 => 1, 4
         * <p>
         * 2 => 3
         * 1 => 2
         */
        public int solution1(int k, int[] tangerine) {

            if (k == 1 || tangerine.length == 1) {
                return 1;
            }

            int answer = 0;
            Arrays.sort(tangerine);

            int now = tangerine[0];
            int nowCount = 1;

            TreeMap<Integer, Integer> treeMap = new TreeMap<>();
            int length = tangerine.length;

            for (int i = 1; i < length; i++) {
                if (tangerine[i] == now) {
                    nowCount++;
                    if (i == length - 1) {
                        treeMap.put(nowCount, treeMap.getOrDefault(nowCount, 0) + 1);
                    }
                    continue;
                }

                treeMap.put(nowCount, treeMap.getOrDefault(nowCount, 0) + 1);
                now = tangerine[i];
                nowCount = 1;
            }

//            System.out.println("treeMap = " + treeMap);
            while (k > 0) {
                Map.Entry<Integer, Integer> lastEntry = treeMap.pollLastEntry();
                Integer count = lastEntry.getKey();
                Integer kinds = lastEntry.getValue();

                for (int i = 0; i < kinds; i++) {
                    if (k <= 0) {
                        break;
                    }

                    k -= count;
                    answer++;
                }

            }


            return answer;
        }

        public int solution2(int k, int[] tangerine) {

            if (k == 1) {
                return 1;
            }

            int answer = 0;
            Arrays.sort(tangerine);

            int now = tangerine[0];
            int nowCount = 1;

            int length = tangerine.length;
            TreeMap<Integer, Integer> treeMap = new TreeMap<>();
            for (int i = 1; i < length; i++) {

                int kind = tangerine[i];
                if (kind == now) {
                    nowCount++;
                    if (length - 1 == i) {
//                        System.out.println("i = " + i + " now = " + now + " nowCount = " + nowCount);
                        treeMap.put(nowCount, treeMap.getOrDefault(nowCount, 0) + 1);
                    }
                    continue;
                }

//                System.out.println("i = " + i + " now = " + now + " nowCount = " + nowCount);
                treeMap.put(nowCount, treeMap.getOrDefault(nowCount, 0) + 1);
                now = kind;
                nowCount = 1;
            }

            while (k > 0) {
                Map.Entry<Integer, Integer> lastEntry = treeMap.pollLastEntry();
                if (lastEntry == null) {
                    answer = 0;
                    break;
                }

                Integer count = lastEntry.getKey();
                Integer kinds = lastEntry.getValue();

                for (int i = 0; i < kinds; i++) {
                    if (k <= 0) {
                        break;
                    }

                    k -= count;
                    answer++;
                }
            }

            return answer;
        }

        public int solution3(int k, int[] tangerine) {

            int answer = 0;
            Map<Integer, Integer> map = new HashMap<>();
            for (Integer i : tangerine) {
                map.put(i, map.getOrDefault(i, 0) + 1);
            }

            List<Integer> counts = new ArrayList<>(map.values());
            counts.sort(Collections.reverseOrder());

//            System.out.println("counts = " + counts);
            for (Integer count : counts) {
                if (k <= 0) {
                    break;
                }

                k -= count;
                answer++;
            }
            return answer;
        }

    }

    @Nested
    class 멀리_뛰기 {
        @Test
        public void solution() {
            //given

            //when
            System.out.println("n = 4일 경우: " + this.solution(4));
            System.out.println("n = 3일 경우: " + this.solution(3));
            this.solution(4);
            //then
        }


        /**
         * stack = {0}
         * depth = 1 stack = {1, 2}
         * depth = 2 stack = {2, 3, 3, 4} answer = 1
         * depth = 3 stack = {3, 4, 4, 5, 4, 5, 5, 6}, answer = 4
         * depth = 4 stack = {4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8}
         */
        public long solution(int n) {
            long answer = 1;
            if(n == 1) {
                return answer;
            }

            Queue<Long> queue = new LinkedList<>();
            queue.add(1L);
            queue.add(2L);

            for (int i = 1; i < n; i++) {
                Queue<Long> childQueue = new LinkedList<>();
                while (!queue.isEmpty()) {
                    Long poll = queue.poll();
                    if(poll == n) {
                        answer++;
                    }

                    childQueue.add(poll + 1);
                    childQueue.add(poll + 2);
                }

                queue.addAll(childQueue);
            }

            return answer % 1234567;
        }
    }

    @Nested
    class N개의_최소공배수 {

        @Test
        public void solution() {
            //given

            //when

            System.out.println(this.solution(new int[]{2, 7}));
            System.out.println(this.solution(new int[]{2, 6, 8, 14}));
            System.out.println(this.solution(new int[]{1, 2, 3}));
            //then
        }

        public int solution(int[] arr) {

            Arrays.sort(arr);
            int length = arr.length;

            int answer = 1;
            while (true) {
                int divideNum = arr[0];
                if(divideNum == 1) {
                    break;
                }

                boolean isAllDivided = true;
                for (int i = 0; i < length; i++) {
                    if(arr[i] % divideNum != 0) {
                        isAllDivided = false;
                        break;
                    }
                }

                if(!isAllDivided) {
                    break;
                }


                for (int i = 0; i < length; i++) {
                    arr[i] = arr[i] / divideNum;
                }

                answer *= divideNum;
            }

            for (int i = 0; i < length; i++) {
                answer *= arr[i];
            }

            return answer;
        }


    }

    @Nested
    class 영어_끝말잇기 {

        @Test
        public void solution() {
            int n = 3;
            String[] words = {"tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"};
            this.solution(n, words);
        }

        /**
         * 한글자인 단어는 인정되지 않음
         * <p>
         * 끝말있기가 가능행야함
         * 이전에 사용한 단어는 인정되지 않음
         * <p>
         * {"tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"}
         */
        public int[] solution(int n, String[] words) {

            int number = 1;
            int turn = 1;

            String firstWord = words[0];
            Set<String> set = new HashSet<>();
            set.add(firstWord);
            boolean isSuccess = true;
            char lastChar = firstWord.charAt(firstWord.length() - 1);

            for (int i = 1; i < words.length; i++) {

                if (number == n) {
                    number = 1;
                    turn++;
                } else {
                    number++;
                }

                String word = words[i];

                //중복 체크
                if (set.contains(word)) {
                    isSuccess = false;
                    break;
                }

                //끝말있기가 가능한지 여부 확인
                if (lastChar != word.charAt(0)) {
                    isSuccess = false;
                    break;
                }

                set.add(word);
                lastChar = word.charAt(word.length() - 1);
//                System.out.println("number = " + number + " turn = " + turn + " word = " + word + " set = " + set + " lastChar = " + lastChar);
            }

            return isSuccess ? new int[]{0, 0} : new int[]{number, turn};
        }

        public int[] solution2(int n, String[] words) {

            Checker checker = new Checker(words[0]);
            boolean isSuccess = true;

            int number = 1;
            int turn = 1;

            for (int i = 1; i < words.length; i++) {
                if (number == n) {
                    number = 1;
                    turn++;
                } else {
                    number++;
                }

                String word = words[i];
                if (!checker.isSuccess(word)) {
                    isSuccess = false;
                    break;
                }
            }

            return isSuccess ? new int[]{0, 0} : new int[]{number, turn};
        }

        public static class Checker {
            private final Set<String> words = new HashSet<>();
            private char lastChar;

            public Checker(String word) {
                words.add(word);
                lastChar = word.charAt(word.length() - 1);
            }

            public boolean isSuccess(String nextWord) {
                if (words.contains(nextWord)) {
                    return false;
                }

                if (lastChar != nextWord.charAt(0)) {
                    return false;
                }

                words.add(nextWord);
                lastChar = nextWord.charAt(nextWord.length() - 1);
                return true;
            }

        }
    }

    @Nested
    class 예상_대진표 {
        @Test
        public void solution() {
            this.solution(8, 4, 7);
        }

        /**
         * n = 8, a = 4, b = 7
         * aPos = 2, bPos = 4, answer = 1
         * aPos = 1, bPos = 2, answer = 2
         * aPos = 1, bPos = 1, answer = 3
         */
        public int solution(int n, int a, int b) {
            int answer = 1;

            while (Math.pow(2, answer) <= n) {
                //현재의 a, b의 위치를 구함
                int aPos = a % 2 == 0 ? a / 2 : a / 2 + 1;
                int bPos = b % 2 == 0 ? b / 2 : b / 2 + 1;
//                System.out.println("aPos = " + aPos + " bPos = " + bPos + " answer = " + answer);
                //a와 b의 위치가 같으면 break
                if (aPos == bPos) {
                    break;
                }

                a = aPos;
                b = bPos;
                answer++;
            }

            return answer;
        }

    }

    @Nested
    class 연속_부분_수열_합의_개수 {

        @Test
        public void solution() {
//            this.solution(new int[]{7, 9, 1});

            System.out.println(this.solution(new int[]{7, 9, 1, 1, 4}));
        }


        /**
         * elements = {7, 9, 1, 1, 4}
         */
        public int solution(int[] elements) {

            HashSet<Integer> set = new HashSet<>();

            int[] circleElements = new int[2 * elements.length - 1];
            System.arraycopy(elements, 0, circleElements, 0, elements.length);
            System.arraycopy(elements, 0, circleElements, elements.length, elements.length - 1);

            // 모든 원소를 더한 경우의 수는 한가지
            set.add(Arrays.stream(elements).sum());

            int sumLength = 1;

            for (int i = 0; i < elements.length - 1; i++) {
                for (int j = 0; j < elements.length; j++) {
                    int sum = 0;
                    for (int k = j; k < j + sumLength; k++) {
                        sum += circleElements[k];
                    }
                    set.add(sum);
                }
                sumLength++;
            }
            return set.size();
        }

        public static class Node {

        }
    }

    @Nested
    class 할인_행사 {
        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/131127
         * <ul>
         * <li>회원을 대상으로 매일 한가지 제품을 할인</li>
         * <li>할인한 제품은 하루에 하나씩 구매 가능</li>
         * <ul/>
         */
        @Test
        public void solution() {

            this.solution(new String[]{"banana", "apple", "rice", "pork", "pot"},
                    new int[]{3, 2, 2, 2, 1},
                    new String[]{"chicken", "apple", "apple", "banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"});
        }

        /**
         * @param want:     원하는 제품들
         * @param number:   원하는 제품의 수량
         * @param discount: 할인된 제품들
         * @return 원하는 제품을 모두 할인 받을 있는 회원등록 날짜
         */
        public int solution(String[] want, int[] number, String[] discount) {

            int totalNumber = 0;
            Map<String, Integer> wants = new HashMap<>();
            for (int i = 0; i < want.length; i++) {
                wants.put(want[i], number[i]);
                totalNumber += number[i];
            }


            int discountLength = discount.length;
            int startIndex = 0;
            int endIndex = 10;
            int answer = 0;

            while (endIndex <= discountLength) {

                //열흘치의 할인된 제품과 수량
                Map<String, Integer> discounts = new HashMap<>();
                for (int i = startIndex; i < endIndex; i++) {
                    String discountItem = discount[i];
                    int discountNumber = discounts.getOrDefault(discountItem, 0) + 1;
                    discounts.put(discountItem, discountNumber);
                }

                //두가지를 비교
                boolean isSame = true;
                for (String s : want) {
                    Integer wantNumber = wants.get(s);
                    Integer discountNumber = discounts.getOrDefault(s, 0);
                    if (wantNumber > discountNumber) {
                        isSame = false;
                        break;
                    }
                }

//                System.out.println("discounts = " + discounts + " wants = " + wants + " isSame = " + isSame);

                if (isSame) {
                    answer++;
                }

                startIndex++;
                endIndex++;
            }

            return answer;
        }

    }

    @Nested
    class 괄호_회전하기 {
        @Test
        public void solution() {


//            int i1 = "()".indexOf("()");
//            int i2 = "{}".indexOf("{}");
//            int i3 = "[]".indexOf("[]");
//            int i4 = "{}()[]".indexOf("()");
//            int i5 = "{}()[]".indexOf("{}");
//            int i6 = "{}()[]".indexOf("[]");
//            int i7 = "{{".indexOf("()");
//            int i8 = "))".indexOf("{}");
//            int i9 = "]]".indexOf("[]");
//
//            System.out.println("i1 = " + i1);
//            System.out.println("i2 = " + i2);
//            System.out.println("i3 = " + i3);
//            System.out.println("i4 = " + i4);
//            System.out.println("i5 = " + i5);
//            System.out.println("i6 = " + i6);
//            System.out.println("i7 = " + i7);
//            System.out.println("i8 = " + i8);
//            System.out.println("i9 = " + i9);


//            System.out.println(this.isMatch("(({{}}))"));
//            System.out.println(this.isMatch("((}}"));

            this.solution("0123");
            //
//            boolean matches3 = "()".matches("\\(\\)");
//            boolean matches4 = "()[]{}".matches("^(\\(\\)|\\{\\}|\\[\\])$");
//            boolean matches4 = "()[]{}".matches("^\\(\\)$");


//            System.out.println("matches1 = " + matches1);
//            System.out.println("matches2 = " + matches2);
//            System.out.println("matches3 = " + matches3);
//            System.out.println("matches4 = " + matches4);

        }

        /**
         * 1. 0 1 2 3
         * 2. 1 2 3 0
         * 3. 2 3 0 1
         * 4. 3 0 1 2
         */
        public int solution(String s) {
            int length = s.length();
            //홀수일 경우에는 괄호 매칭이 불가
            if (length % 2 == 1) {
                return 0;
            }

            int answer = 0;
            //처음 셋팅
            if (this.isMatch(s)) {
                answer++;
            }


            for (int i = 1; i < length; i++) {
                char firstChar = s.charAt(0);
                s = s.substring(1, length) + firstChar;

                if (this.isMatch(s)) {
                    answer++;
                }

            }

            return answer;
        }

        public boolean isMatch(String s) {

            String replacedStr = s;
            while (true) {

                String tempStr = replacedStr;

                if (replacedStr.contains("()")) {
                    tempStr = replacedStr;
                    replacedStr = replacedStr.replace("()", "");
                }

                if (replacedStr.contains("{}")) {
                    tempStr = replacedStr;
                    replacedStr = replacedStr.replace("{}", "");
                }

                if (replacedStr.contains("[]")) {
                    tempStr = replacedStr;
                    replacedStr = replacedStr.replace("[]", "");
                }

                if (replacedStr.equals(tempStr)) {
                    break;
                }
            }
            return replacedStr.isEmpty();
        }
    }

    @Nested
    class n2_배열_자르기 {

        /**
         * https://school.programmers.co.kr/learn/courses/30/lessons/87390?language=java
         */
        @Test
        public void solution() {

//            System.out.println(this.calRow(0, 5));
//            System.out.println(this.calRow(1, 5));
//            System.out.println(this.calRow(2, 5));
//            System.out.println(this.calRow(3, 5));
//            System.out.println(this.calRow(4, 5));

//            System.out.println(Arrays.toString(this.solution1(3, 2, 5)));
//            System.out.println(Arrays.toString(this.solution2(3, 2, 5)));
            System.out.println(Arrays.toString(this.solution2(4, 7, 14)));
        }


        public int[] solution1(int n, long left, long right) {
            int[] answer = new int[(int) (right - left + 1)];

            ArrayList<Integer> list = new ArrayList<>();
            int nowLength = 0;
            for (int i = 0; i < n; i++) {
                if (nowLength >= right) {
                    break;
                }

                list.addAll(this.calRow(i, n));
                nowLength += n;
            }

            int j = 0;
            for (long i = left; i <= right; i++) {
                answer[j] = list.get((int) i);
                j++;
            }

            return answer;
        }

        /**
         * 시작 지점과 끝 지점을 정하고 난뒤
         */
        public int[] solution2(int n, long left, long right) {
            int[] answer = new int[(int) (right - left + 1)];

            int startN = (int) (left / n);
            int endN = ((int) (right / n)) + 1;


            ArrayList<Integer> list = new ArrayList<>();
            for (int i = startN; i < endN; i++) {
                List<Integer> row = this.calRow(i, n);
//                System.out.println("row = " + row);
                list.addAll(row);
            }


            left = left - (long) startN * n;
            right = right - (long) startN * n;

//            System.out.println("left = " + left + " right = " + right + " list = " + list);
            int j = 0;
            for (long i = left; i <= right; i++) {
                answer[j] = list.get((int) i);
                j++;
            }

            return answer;
        }

        /**
         * 1 2 3 4 5 2 2 3 4 5 3 3 3 4 5
         * <p>
         * 1 2 3 4 5
         * 2 2 3 4 5
         * 3 3 3 4 5
         * 4 4 4 4 5
         * 5 5 5 5 5
         */
        public List<Integer> calRow(int row, int n) {
            ArrayList<Integer> list = new ArrayList<>();
            int start = row + 1;
            int num = row + 1;
            for (int i = 0; i < start; i++) {
                list.add(num);
            }

            num += 1;
            for (int i = start; i < n; i++) {
                list.add(num);
                num += 1;
            }

            return list;
        }
    }

    @Nested
    class 행렬의_곱셈 {

        private int[][] arr1;
        private int[][] arr2;
        private int colLength;
        private int rowLength;

        @Test
        public void solution() {

            int[][] arr1 = {{2, 3, 2}, {4, 2, 4}, {3, 1, 4}};
            int[][] arr2 = {{5, 4, 3}, {2, 4, 1}, {3, 1, 1}};

//            this.arr1 = arr1;
//            this.arr2 = arr2;
//            this.colLength = arr1[0].length;
//            this.rowLength = arr1.length;


            this.solution(arr1, arr2);
        }

        /**
         * 행렬 곱셈
         * 첫째 행렬의 열의 갯수와 둘째 행렬의 행의 갯수가 동일해야한다.
         * <p>
         * arr1     arr2
         * 1 4      3 3
         * 3 2      3 3
         * 4 1
         * <p>
         * x = 0,
         * arr1[0][0] * arr2[0][0] + arr1[0][1] * arr2[1][0]
         * arr1[0][0] * arr2[0][1] + arr1[0][1] * arr2[1][1]
         * arr1[1][0] * arr2[0][1] + arr1[0][1] * arr2[1][1]
         */
        public int[][] solution(int[][] arr1, int[][] arr2) {

            this.arr1 = arr1;
            this.arr2 = arr2;
            this.colLength = arr2[0].length;
            this.rowLength = arr1.length;

            int[][] answer = new int[rowLength][colLength];
            for (int i = 0; i < rowLength; i++) {
                answer[i] = this.calculateRow(i);
            }

            return answer;
        }

        public int[] calculateRow(int y) {
            int[] row = new int[colLength];
            for (int i = 0; i < colLength; i++) {
                row[i] = calculateCell(y, i);
            }

//            System.out.println("row = " + Arrays.toString(row));
            return row;
        }

        public int calculateCell(int x, int y) {
            int cell = 0;
            for (int i = 0; i < colLength; i++) {
                int tempCell = arr1[x][i] * arr2[i][y];
//                System.out.println("arr1[" + x + "][" + i + "] * arr2[" + i + "][" + y + "] = " + tempCell);
                cell += tempCell;

            }
            return cell;
        }
    }

    @Nested
    class H_Index {


        @Test
        public void solution() {
            this.solution(new int[]{3, 0, 6, 1});
        }

        public int solution(int[] citations) {
            int answer = 0;
            Arrays.sort(citations);
            int n = citations.length;

            int rightN = 1;
            int leftN = n - rightN;
            for (int i = n - 1; i >= 0; i--) {
                //인용된 횟수
                int h = citations[i];

                if (rightN >= h && leftN <= h) {
                    return h;
                }

                rightN++;
                leftN--;
            }

            return 0;
        }
    }


    @Nested
    class 기능_개발 {
        @Test
        public void solution() {

//            this.solution(new int[]{93, 30, 55}, new int[]{1, 30, 5});
            this.solution(new int[]{93}, new int[]{1});
        }

        public int[] solution(int[] progresses, int[] speeds) {

            Queue<Integer> queue = new LinkedList<>();


            for (int i = 0; i < progresses.length; i++) {
                int progress = progresses[i];
                int speed = speeds[i];

                int restWork = 100 - progress;
                int restDay = restWork % speed == 0 ? restWork / speed : restWork / speed + 1;
                queue.offer(restDay);
            }

//            System.out.println("queue = " + queue);

            //시작 셋팅
            LinkedList<Integer> answer = new LinkedList<>();
            answer.add(1);

            Integer maxRestDay = queue.poll();
            while (!queue.isEmpty()) {

                Integer restDay = queue.poll();
                //뒤의 기능이 앞의 기능보다 먼저 개발된 경우
                if (restDay <= maxRestDay) {
                    int completedCount = answer.pollLast() + 1;
                    answer.add(completedCount);
                } else {
                    maxRestDay = restDay;
                    answer.add(1);
                }
            }

//            System.out.println("answer = " + answer);


            return answer.stream().mapToInt(Integer::intValue).toArray();
        }
    }

    @Nested
    class 피로도 {


        @Test
        void solution() {
        }

        /**
         * 일정 피로도가 있어야 던전 탐험 가능
         * <p>
         * 던전 탕험을 위한 "최소 필요 피로도"와 던전 탐험을 마쳤을 때 소모되는 "소모 피로도"
         *
         * @param k:        현재 피로도
         * @param dungeons: {최소 필요 피로도, 소모 피로도}
         *                  최소 필요 피로도 >= 소모 피로도
         *                  1 <= 피로도 <= 1000
         * @return 유저가 탐험할 수 있는 최대 던전 수
         */
        public int solution(int k, int[][] dungeons) {
            int answer = 0;

            PriorityQueue<Fatigue> priorityQueue = new PriorityQueue<>();
            for (int[] dungeon : dungeons) {
                Fatigue fatigue = new Fatigue(dungeon[0], dungeon[1]);
                priorityQueue.add(fatigue);
            }

            System.out.println("priorityQueue = " + priorityQueue);

            while (!priorityQueue.isEmpty()) {
                Fatigue fatigue = priorityQueue.poll();
                // 현재 피로도가 최소 필요 피로도 이상인 경우
                if (k >= fatigue.getNeed()) {
                    k -= fatigue.getUse();
                    answer++;
                }
            }



            return answer;
        }

        private static class Fatigue implements Comparable<Fatigue> {
            private final int need;
            private final int use;

            public Fatigue(int need, int use) {
                this.need = need;
                this.use = use;
            }

            @Override
            public String toString() {
                return "{need: " + need + ", use: " + use + "}";
            }

            public int getNeed() {
                return need;
            }

            public int getUse() {
                return use;
            }

            @Override
            public int compareTo(Fatigue o) {
                // 최소 피로도 높으순 정렬
                if(this.use != o.use) {
                    return Integer.compare(this.use, o.use);
                }

                //소모 피로도 낮은순 정렬
                return Integer.compare(this.need, o.need) * -1;
            }
        }
    }

    @Nested
    class 캐시 {

        @Test
        public void solution() {
            //given


            System.out.println(solution(3, new String[]{"A", "B", "C", "D", "E", "A", "B", "C", "D", "E"}));
            //when

            //then
        }

        public int solution(int cacheSize, String[] cities) {
            int answer = 0;
            // 캐시 사이즈가 0일 경우, 모든 도시 캐시 미스 처리
            if (cacheSize == 0) {
                return cities.length * 5;
            }

            LinkedList<String> cache = new LinkedList<>();
            for (String city : cities) {

                String lowerCityName = city.toLowerCase();
                boolean cacheHit = cache.contains(lowerCityName);

                if (cacheHit) {
                    answer += 1;
                } else {
                    answer += 5;
                }

                // LRU 알고리즘에 따라, 캐시 히트가 발생한 경우, 해당 캐시를 가장 최근에 사용된 캐시로 변경
                if (cacheHit) {
                    cache.remove(lowerCityName);
                    cache.add(lowerCityName);
                } else {
                    // LRU 알고리즘에 따라, 캐시 공간이 없을 경우, 가장 오랫동안 사용되지 않은 캐시 제거
                    if (cache.size() == cacheSize) {
                        cache.pollFirst();
                    }

                    cache.add(lowerCityName);
                }

            }

            return answer;
        }
    }

    @Nested
    class 프로세스 {

        @Test
        public void solution() {
            //given

            //when
            System.out.println(this.solution(new int[]{2, 1, 3, 2}, 2));
            System.out.println(this.solution(new int[]{1, 1, 9, 1, 1, 1}, 0));
            //then
        }

        /**
         *
         * @param priorities 프로세스 우선순위 배열
         * @param location 프로세스의 위치
         * @return location 위치에 있는 프로세스가 몇 번째로 실행되는지 확인
         * 우선 순위가 높은 프로세스 실행
         * 큐에서 프로세스를 꺼내고 난뒤 큐에 존재하는 프로세스 우선 순위 비교
         */
        public int solution(int[] priorities, int location) {
            int answer = 0;

            // 우선순위 최대 값
            int max = Arrays.stream(priorities).max().getAsInt();
            LinkedList<Process> processes = new LinkedList<>();

            int n = 0;
            for (int priority : priorities) {
                Process process = new Process(priority, n);
                processes.add(process);
                n++;
            }

            while (!processes.isEmpty()) {
                Process process = processes.pollFirst();

                if (process.getPriority() < max) {
                    //실행하지 못하고 큐에 진입
                    processes.addLast(process);
                    continue;
                }

                answer++;

                //실행
                if (process.getLocation() == location) {
                    break;
                }

                max = processes.stream()
                        .mapToInt(Process::getPriority)
                        .max()
                        .getAsInt();

            }

            return answer;
        }


        public static class Process implements Comparable<Process> {
            private final int priority;
            private final int location;

            public Process(int priority, int location) {
                this.priority = priority;
                this.location = location;
            }

            @Override
            public int compareTo(Process o) {
                return Integer.compare(priority, o.priority);
            }

            public String toString() {
                return "Process[" +
                        "priority=" + priority + ", " +
                        "location=" + location + ']';
            }

            public int getPriority() {
                return priority;
            }

            public int getLocation() {
                return location;
            }


        }


    }

    @Nested
    class 롤케이크_자르기 {

        @Test
        public void solution() {
            //given

            //when
            System.out.println(this.solution(new int[]{1, 2, 1, 3, 1, 4, 1, 2}));
            //then
        }

        /**
         *
         * @param topping 토핑 번호를 저장한 정수 배열
         * @return 공평하게 자르는 방법의 수
         * 롤케이크를 잘랐을때, 동일한 토핑의 가짓수가 동일해야 공평한다고 판단
         */
        public int solution(int[] topping) {
            int answer = 0;

            int toppingLength = topping.length;
            if(toppingLength <= 1) {
                return answer;
            }

            Map<Integer, Integer> rightToppings = new HashMap<>();
            for (int i = 0; i < toppingLength; i++) {
                rightToppings.put(topping[i], rightToppings.getOrDefault(topping[i], 0) + 1);
            }


            int sliceCount = toppingLength - 1;

            Set<Integer> leftToppings = new HashSet<>();
            leftToppings.add(topping[0]);

            for (int i = 0; i < sliceCount; i++) {

                //왼쪽 토핑 가짓수 추가
                leftToppings.add(topping[i]);

                //오른쪽 토핑 개수 감소 or 제거
                if(rightToppings.containsKey(topping[i])) {
                    int decreasedCount = rightToppings.get(topping[i]) - 1;
                    if(decreasedCount == 0) {
                        rightToppings.remove(topping[i]);
                    }else {
                        rightToppings.put(topping[i], decreasedCount);
                    }
                }

                // 조건이 충족할 경우 answer 증가
                if(leftToppings.size() == rightToppings.size()) {
                    answer++;
                }

                //왼쪽 토핑 가짓수가 많아지면 종료
                if(leftToppings.size() > rightToppings.size()) {
                    break;
                }
            }


            return answer;
        }
    }

    @Nested
    class 게임_맵_최단거리 {
        @Test
        void solution() {

            int[][] maps = {
                    {1, 0, 1, 1, 1},
                    {1, 0, 1, 0, 1},
                    {1, 0, 1, 1, 1},
                    {1, 1, 1, 0, 1},
                    {0, 0, 0, 0, 1}
            };
            int solution = this.solution(maps);
            System.out.println("solution = " + solution);
        }

        public int solution(int[][] maps) {
            int answer = 1;

            Set<Node> visitedNodes = new HashSet<>();
            Queue<Node> queue = new LinkedList<>();

            // 종료 지점을 시작 노드로 셋팅
            Node node = new Node(maps[0].length - 1, maps.length - 1);
            queue.add(node);

            while (!queue.isEmpty()) {
//                System.out.println(queue);

                LinkedList<Node> nearNodes = new LinkedList<>();
                while (!queue.isEmpty()) {
                    Node nowNode = queue.poll();
                    // 방문 이력 추가
                    visitedNodes.add(nowNode);
                    // 방문 이력을 제외한, 인접 노드 조회
                    nearNodes.addAll(this.nearNodes(maps, nowNode, visitedNodes));
                }

                // 인접 노드가 없을 경우 종료
                if(nearNodes.isEmpty()) {
                    break;
                }

                // 인접 노드 큐에 추가
                for (Node nearNode : nearNodes) {
                    if(!visitedNodes.contains(nearNode)) {
                        queue.add(nearNode);
                    }
                }

                answer++;
            }

            return visitedNodes.contains(new Node(0, 0)) ? answer : -1;
        }

        private LinkedList<Node> nearNodes(int[][] maps, Node node, Set<Node> visitedNodes) {
            LinkedList<Node> list = new LinkedList<>();

            Node leftNode = node.movedNode("left", maps);
            if(leftNode != null && !visitedNodes.contains(leftNode)) {
                list.add(leftNode);
            }

            Node rightNode = node.movedNode("right", maps);
            if(rightNode != null && !visitedNodes.contains(rightNode)) {
                list.add(rightNode);
            }

            Node upNode = node.movedNode("up", maps);
            if(upNode != null && !visitedNodes.contains(upNode)) {
                list.add(upNode);
            }

            Node downNode = node.movedNode("down", maps);
            if(downNode != null && !visitedNodes.contains(downNode)) {
                list.add(downNode);
            }

            return list;
        }

        private static class Node {
            private final int x;
            private final int y;

            public Node(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public Node movedNode(String direction, int[][] maps){
                int x = this.x;
                int y = this.y;

                if("left".equals(direction)) {
                    x = x - 1;
                } else if("right".equals(direction)) {
                    x = x + 1;
                } else if("up".equals(direction)) {
                    y = y - 1;
                } else if("down".equals(direction)) {
                    y = y + 1;
                }else {
                    throw new IllegalArgumentException("해당 방향으로 이동할 수 없습니다. direction = " + direction);
                }

                // 음수 좌표인 경우
                if(x < 0 || y < 0) {
                    return null;
                }

                // 맵의 범위를 벗어난 경우
                if(x >= maps[0].length || y >= maps.length) {
                    return null;
                }

                // 이동할 수 없는 칸인 경우
                if(maps[y][x] == 0) {
                    return null;
                }

                return new Node(x, y);
            }

            @Override
            public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                Node node = (Node) o;
                return x == node.x && y == node.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }

            @Override
            public String toString() {
                return "{x:" + x + ", y:" + y +"}";
            }
        }
    }
    @Nested
    class 방문_길이 {

        @Test
        public void solution() {
            //given

            //when
            System.out.println(this.solution("ULURRDLLU"));
            //then
        }

        /**
         *
         * @param dirs: U(위, 1), D(아래, -1), R(오른쪽, -1), L(왼쪽, 1)
         * @return 방문한 길이의 수
         * 이미 방문한 길은 카운팅에서 제외
         * 좌표를 벗어난는 움직임은 무시
         */
        public int solution(String dirs) {

            Set<Path> paths = new HashSet<>();
            Position now = new Position(0, 0);
            char[] directions = dirs.toCharArray();

            for (char direction : directions) {
                Position move = now.move(direction);
                if (move.canMove()) {
                    paths.add(new Path(now, move));
                    now = move;
                }
            }

            return paths.size();
        }

        public static class Path {
            private final Position p1;
            private final Position p2;

            public Path(Position p1, Position p2) {
                this.p1 = p1;
                this.p2 = p2;
            }

            /**
             * equals와 hashCode를 오버라이딩하여, p1과 p2의 순서에 상관없이 동일한 경로로 인식
             */
            @Override
            public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                Path path = (Path) o;
                return (Objects.equals(p1, path.p1) && Objects.equals(p2, path.p2))
                        || (Objects.equals(p1, path.p2) && Objects.equals(p2, path.p1));
            }

            @Override
            public int hashCode() {
                return Objects.hash(p1.hashCode() + p2.hashCode());
            }

            @Override
            public String toString() {
                return "Path{" +
                        "p1=" + p1 +
                        ", p2=" + p2 +
                        '}';
            }
        }

        public static class Position {
            private final int x;
            private final int y;

            public Position(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public Position move(char dir) {
                if (dir == 'U') {
                    return new Position(x, y + 1);
                } else if (dir == 'D') {
                    return new Position(x, y - 1);
                } else if (dir == 'R') {
                    return new Position(x + 1, y);
                } else if (dir == 'L') {
                    return new Position(x - 1, y);
                }
                throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다. dir = " + dir);
            }

            /**
             * @return x, y 좌표가 -5 ~ 5 사이인지 여부 확인
             */
            public boolean canMove() {
                return x >= -5 && x <= 5 && y >= -5 && y <= 5;
            }

            @Override
            public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                Position position = (Position) o;
                return x == position.x && y == position.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }

            @Override
            public String toString() {
                return "Position{" +
                        "x=" + x +
                        ", y=" + y +
                        '}';
            }
        }
    }

    @Nested
    class 뒤에_있는_큰_수_찾기 {

        @Test
        public void solution(){
            this.solution(new int[]{2, 3, 3, 5});
        }

        public int[] solution(int[] numbers) {
            int numberLength = numbers.length;

            int[] answer = new int[numberLength];

            /**
             * {2, 3, 3, 5}
             *
             * 2 -> 0
             * 3 -> 1, 2
             * 5 -> 3
             */
            TreeMap<Integer, SortedSet<Integer>> map = new TreeMap<>();

            for (int i = 0; i < numberLength; i++) {
                int number = numbers[i];
                SortedSet<Integer> numberIndexes = map.getOrDefault(number, new TreeSet<>());
                numberIndexes.add(i);
                map.put(number, numberIndexes);
            }

            for (int i = 0; i < numberLength; i++) {

                int number = numbers[i];
                Integer higherNumber = map.higherKey(number);
                // 현재 요소보다 큰 숫자가 존재하지 않는 경우 -1
                if(higherNumber == null) {
                    answer[i] = -1;
                    this.removeIfEmpty(map, number, i);
                    continue;
                }

                // 큰 숫자의 모든 인덱스를 저장
                TreeSet<Integer> higherNumberIndexes = new TreeSet<>();
                while (higherNumber != null) {
                    higherNumberIndexes.addAll(map.get(higherNumber));
                    higherNumber = map.higherKey(higherNumber);
                }

                Integer higherIndex = higherNumberIndexes.higher(i);
                if(higherIndex == null) {
                    // 현재 요소보다 큰 숫자는 존재하지만, 현재 요소 뒤에 존재하는 큰 숫자가 없는 경우 -1
                    answer[i] = -1;
                    this.removeIfEmpty(map, number, i);
                }else {
                    // 현재 요소보다 큰 숫자는 존재하며, 현재 요소 뒤에 존재하는 큰 숫자가 있는 경우 해당 숫자
                    answer[i] = numbers[higherIndex];
                    this.removeIfEmpty(map, number, i);
                }

            }
            return answer;
        }

        private void removeIfEmpty(TreeMap<Integer, SortedSet<Integer>> map, int number, int index) {
            SortedSet<Integer> set = map.get(number);
            set.remove(index);
            if(set.isEmpty()) {
                map.remove(number);
            }
        }
    }

    @Nested
    class N진수_게임 {

        @Test
        public void test() {

            System.out.println(Integer.toString(30, 16));
            System.out.println(Integer.toHexString(1000));
            this.solution(2, 4, 2, 1);
//            this.solution(16, 16, 2, 1);
//            this.solution(16, 16, 2, 2);
        }

        /**
         *
         * @param n 진법
         * @param t 미리 구할 숫자의 갯수
         * @param m 게임에 참가하는 인원
         * @param p 튜브의 순서
         * @return
         */
        public String solution(int n, int t, int m, int p) {
            String answer = "";

            String totalStr = "";
            // n진법으로 변환한 문자열의 길이를 대략적으로 측정
            int totalLength = t * m;
            int i = 0;
            while (totalStr.length() < totalLength) {
                totalStr += Integer.toString(i, n).toUpperCase();
                i++;
            }

            totalStr = totalStr.substring(0, totalLength);
            totalStr = " " + totalStr;

            for (int j = 0; j < t; j++) {
                answer += totalStr.charAt(m * j + p);
            }


            return answer;
        }
    }

    @Nested
    class 이중우선순위큐 {


        @Test
        void solution() {
            System.out.println(Arrays.toString(this.solution(new String[]{"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"})));
            System.out.println(Arrays.toString(this.solution(new String[]{"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"})));

        }

        public int[] solution(String[] operations) {
            TreeMap<Integer, Queue<Integer>> map = new TreeMap<>();
            for (String operation : operations) {

                if(operation.startsWith("I ")) {
                    // 삽입
                    int key = Integer.parseInt(operation.substring(2));
                    Queue<Integer> queue = map.getOrDefault(key, new LinkedList<>());
                    queue.add(key);
                    map.put(key, queue);
                    continue;
                }

                // 빈 데이터 삭제 명령어 무시
                if(map.isEmpty()) {
                    continue;
                }

                int key = 0;
                if("D 1".equals(operation)) {
                    // 최댓값 삭제
                    key = map.lastKey();
                }else if("D -1".equals(operation)){
                    // 최솟값 삭제
                    key = map.firstKey();
                }

                Queue<Integer> queue = map.get(key);
                queue.poll();

                if(queue.isEmpty()) {
                    map.remove(key);
                }

            }

            if(map.isEmpty()) {
                return new int[]{0, 0};
            }

            return new int[]{map.lastKey(), map.firstKey()};
        }
    }

    @Nested
    class 야근_지수 {

        @Test
        void solution() {
        }

        public long solution(int n, int[] works) {


            long answer = 0;


            return answer;
        }
    }


    @Nested
    class 더_맵게 {

        public int solution(int[] scoville, int K) {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
            for (Integer s : scoville) {
                priorityQueue.add(s);
            }


            int answer = 0;
            int minScoville = -1;
            while (priorityQueue.size() > 1) {

                Integer poll1 = priorityQueue.poll();
                Integer poll2 = priorityQueue.poll();

                // 섞기 전에 두 음식의 스코빌 지수가 K 미만일 경우
                if(poll1 < K || poll2 < K) {
                    answer++;
                    // 공식에 따라서 섞어줌
                    minScoville = poll1 + (poll2 * 2);
                    priorityQueue.offer(minScoville);
                    continue;
                }

                break;
            }

            if(minScoville < K) {
                return - 1;
            }

            return answer;
        }
    }
    @Nested
    class 압축 {



        public int[] solution(String msg) {
            int[] answer = {};
            return answer;
        }
    }

    @Nested
    class 단어_변환 {

        public int solution(String begin, String target, String[] words) {
            int answer = 0;

            boolean isExist = Arrays.stream(words)
                    .anyMatch(x -> x.equals(target));
            if(!isExist) {
                return answer;
            }

            Map<String, List<String>> graph = new HashMap<>();

            for (int i = 0; i < words.length; i++) {
                if(begin.equals(words[i])) {
                    continue;
                }

                graph.put(words[i], new ArrayList<>());

            }

            // 시작


            return answer;
        }
    }
    @Nested
    class 가장_큰_수 {

        @Test
        public void solution() {


//            System.out.println(this.solution(new int[]{6, 10, 2}));
            System.out.println(this.solution(new int[]{3, 30, 34, 5, 9}));
        }


        public String solution(int[] numbers) {
            StringBuilder answer = new StringBuilder();

            // 하나의 Char로 비교하여 가장 큰 숫자로 정렬하기 위해 우선순위 큐 사용
            PriorityQueue<SortedNumber> sortedNumbers = new PriorityQueue<>();
            for (int i = 0; i < numbers.length; i++) {
                sortedNumbers.add(new SortedNumber(numbers[i]));
            }

            String firstNumber = sortedNumbers.poll().getNumber();
            // 모든 숫자가 0인 경우
            if("0".equals(firstNumber)) {
                return "0";
            }

            answer.append(firstNumber);
            // 정렬된 숫자들을 하나씩 꺼내서 문자열로 합침
            while (!sortedNumbers.isEmpty()) {
                answer.append(sortedNumbers.poll().getNumber());
            }

            return answer.toString();
        }

        private static class SortedNumber implements Comparable<SortedNumber> {

            private final String number;

            public SortedNumber(int number) {
                this.number = String.valueOf(number);
            }

            @Override
            public int compareTo(SortedNumber o) {

                int length = number.length();
                int otherLength = o.number.length();
                int lastIndex = length - 1;
                int otherLastIndex = otherLength - 1;
                int maxLength = Math.max(length, otherLength);

                for (int i = 0; i < maxLength; i++) {
                    char thisChar = lastIndex > i ? number.charAt(i) : number.charAt(lastIndex);
                    char oChar = otherLastIndex > i ? o.number.charAt(i) : o.number.charAt(otherLastIndex);
                    if(thisChar != oChar) {
                        return Character.compare(thisChar, oChar) * -1;
                    }
                }

                return 0;
            }

            public String getNumber() {
                return number;
            }
        }

    }

    @Nested
    class 베스트앨범 {

        @Test
        public void solution() {
            int[] result1 = this.solution(new String[]{"classic", "pop", "classic", "classic", "pop", "classic"}, new int[]{500, 600, 150, 800, 2500, 800});
            System.out.println("result1 = " + Arrays.toString(result1));
        }

        public int[] solution(String[] genres, int[] plays) {


            // 장르별 재생 수 합계 맵 (내림차순)
            Map<String, Integer> genrePlayCountMap = new TreeMap<>(Comparator.reverseOrder());

            // 장르별 재생 목록 맵
            Map<String, PriorityQueue<Play>> genrePlayMap = new HashMap<>();

            int length = genres.length;
            for (int i = 0; i < length; i++) {
                String genre = genres[i];
                int playCount = plays[i];

                genrePlayCountMap.put(genre, genrePlayCountMap.getOrDefault(genre, 0) + playCount);

                PriorityQueue<Play> playQueue = genrePlayMap.getOrDefault(genre, new PriorityQueue<>());
                playQueue.add(new Play(i, playCount));
                genrePlayMap.put(genre, playQueue);
            }

            List<Integer> result = new LinkedList<>();

            for (String genre : genrePlayCountMap.keySet()) {

                PriorityQueue<Play> playQueue = genrePlayMap.get(genre);

                // 한 장르에서 많이 재생된 노래 2곡씩 추가
                for (int i = 0; i < 2; i++) {
                    if(playQueue.isEmpty()) {
                        break;
                    }
                    Play play = playQueue.poll();
                    result.add(play.getIndex());
                }
            }

            return result.stream().mapToInt(Integer::intValue).toArray();
        }

        static class Play implements Comparable<Play>{
            private final int index;
            private final int playCount;

            public Play(int index, int playCount) {
                this.index = index;
                this.playCount = playCount;
            }

            @Override
            public int compareTo(Play o) {
                if(playCount != o.playCount) {
                    return Integer.compare(playCount, o.playCount) * -1;
                }

                return Integer.compare(index, o.index);
            }

            public int getIndex() {
                return index;
            }

            public int getPlayCount() {
                return playCount;
            }

            @Override
            public String toString() {
                return "Play{" +
                        "index=" + index +
                        ", playCount=" + playCount +
                        '}';
            }
        }
    }

    @Nested
    class 호텔_대실 {


        @Test
        public void solution() {
            BookingTime bookingTime = new BookingTime(LocalTime.of(21, 00), LocalTime.of(23, 50));
            bookingTime.isNextDay();
//            String[][] bookTime = {
//                    {"15:00", "17:00"},
//                    {"16:40", "18:20"},
//                    {"14:20", "15:20"},
//                    {"14:10", "19:20"},
//                    {"18:20", "21:20"}
//            };
//            this.solution(bookTime);
        }


        public int solution(String[][] book_time) {
            PriorityQueue<BookingTime> bookingTimeQueue = new PriorityQueue<>();
            for (String[] times : book_time) {
                BookingTime bookingTime = new BookingTime(LocalTime.parse(times[0]), LocalTime.parse(times[1]));
                bookingTimeQueue.add(bookingTime);
            }

            List<Queue<BookingTime>> oneDaySchedule = new ArrayList<>();
            for (int i = 0; i < book_time.length; i++) {
                BookingTime bookingTime = bookingTimeQueue.poll();

                //기존 스케쥴에서 예약 가능한지 확인
                boolean isBooked = false;
                for (Queue<BookingTime> bookingTimes : oneDaySchedule) {
                    BookingTime bookedTime = bookingTimes.poll();

                    if(bookedTime.canNextBooking(bookingTime)) {
                        isBooked = true;
                        bookingTimes.offer(bookingTime);
                        break;
                    }
                    bookingTimes.offer(bookedTime);
                }

                // 기존 스케쥴에서 예약이 불가능한 경우, 새로운 스케쥴 생성
                if(!isBooked) {
                    Queue<BookingTime> newSchedule = new LinkedList<>();
                    newSchedule.add(bookingTime);
                    oneDaySchedule.add(newSchedule);
                }

            }

            return oneDaySchedule.size();
        }


        static class BookingTime implements Comparable<BookingTime> {
            private final LocalTime startTime;
            private final LocalTime endTime;
            private final long breakTime = 10;

            public BookingTime(LocalTime startTime, LocalTime endTime) {
                this.startTime = startTime;
                this.endTime = endTime;
            }

            private boolean isNextDay(){
                LocalDateTime dateTime = LocalDateTime.of(LocalDate.MIN, endTime);
                LocalDateTime nextDateTime = LocalDateTime.of(LocalDate.MIN, endTime).plusMinutes(breakTime);
                return nextDateTime.getDayOfMonth() > dateTime.getDayOfMonth();
            }

            public boolean canNextBooking(BookingTime bookingTime) {
                if(this.isNextDay()) {
                    return false;
                }

                LocalTime ownEndTime = this.endTime.plusMinutes(breakTime);
                LocalTime otherStartTime = bookingTime.startTime;
                return otherStartTime.equals(ownEndTime) || otherStartTime.isAfter(ownEndTime);
            }

            @Override
            public int compareTo(BookingTime o) {
                return startTime.compareTo(o.startTime);
            }

            @Override
            public String toString() {
                return "BookingTime{" +
                        "startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", breakTime=" + breakTime +
                        '}';
            }
        }
    }


    @Nested
    class 네트워크 {

        @Test
        void solution(){
            int n = 3;
            int[][] computers = {
                    {1, 1, 0},
                    {1, 1, 0},
                    {0, 0, 1}
            };
            this.solution(n, computers);

            computers = new int[][]{
                    {1, 1, 0},
                    {1, 1, 1},
                    {0, 1, 1}
            };
            this.solution(n, computers);
        }

        public int solution(int n, int[][] computers) {
            int answer = 0;

            Map<Integer, List<Integer>> graph = new LinkedHashMap<>();
            int start = 0;
            graph.put(start, new LinkedList<>());

            for (int i = 1; i < n; i++) {

                boolean isConnected = computers[i][i - 1] == 1 && computers[i - 1][i] == 1;
                if(isConnected) {
                    List<Integer> list = graph.get(start);
                    list.add(i);
                    graph.put(start, list);
                } else {
                    graph.put(i, new LinkedList<>());
                    start = i;
                }
            }

//            System.out.println(graph);
//            for (Integer key : graph.keySet()) {
//
//                if(!graph.getOrDefault(key, new LinkedList<>()).isEmpty()) {
//                    answer++;
//                }
//            }


            return graph.size();
        }
    }

    @Nested
    class 소수_찾기 {
        @Test
        void solution(){
            System.out.println(Math.sqrt(71));
            System.out.println(Math.sqrt(24));
//            System.out.println(this.solution("17"));
//            System.out.println(this.solution("011"));
        }

        public int solution(String numbers) {
            int answer = 0;

            return answer;
        }
    }

    @Nested
    class 디스크_컨트롤러 {

        @Test
        void solution(){
            System.out.println(this.solution(new int[][]{
                    {0, 3},
                    {1, 9},
                    {3, 5}
            }));
        }

        /**
         *
         * @param jobs, 인덱스: 작업 번호, s: 작업 요청 시간, l: 작업 소요 시간
         * @return 작업 요청부터 종료까지 걸린 평균 시간
         */
        public int solution(int[][] jobs) {
            int length = jobs.length;

//            Worker worker = waitQueue.poll();
            int requestMillis = jobs[0][0];
            int requiredMillis = jobs[0][1];
            int progressMillis = requestMillis + requiredMillis;
            int answer = requiredMillis - requestMillis;

            PriorityQueue<Worker> waitQueue = new PriorityQueue<>();
            for (int i = 1; i < length; i++) {
                waitQueue.add(new Worker(i, jobs[i][0], jobs[i][1]));
            }



            while (!waitQueue.isEmpty()) {
                Worker worker = waitQueue.poll();
                requestMillis = worker.getRequestMillis();
                requiredMillis = worker.getRequiredMillis();


                if(requestMillis >= progressMillis) {
                    // 이전 작업이 끝난 이후 요청된 작업인 경우
                    progressMillis = requiredMillis + requestMillis;
                    answer += requiredMillis;
                }else {
                    // 이전 작업이 진행중인데 요청한 경우
                    progressMillis += requiredMillis;
                    answer += progressMillis - requestMillis;
                }
            }

            return answer / length;
        }

        private static class Worker implements Comparable<Worker> {

            private final int number;
            private final int requestMillis;
            private final int requiredMillis;

            public Worker(int number, int requestMillis, int requiredMillis) {
                this.number = number;
                this.requestMillis = requestMillis;
                this.requiredMillis = requiredMillis;
            }

            @Override
            public int compareTo(Worker o) {
                if(requiredMillis != o.requiredMillis) {
                    return Integer.compare(requiredMillis, o.requiredMillis);
                }

                if(requestMillis != o.requestMillis) {
                    return Integer.compare(requestMillis, o.requestMillis);
                }

                return Integer.compare(number, o.number);
            }

            public int getNumber() {
                return number;
            }

            public int getRequestMillis() {
                return requestMillis;
            }

            public int getRequiredMillis() {
                return requiredMillis;
            }
        }
    }
}
