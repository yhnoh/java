import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

            System.out.println("i = " + this.solution1(10000));
        }

        /**
         * F(2) = F(0) + F(1)
         * F(3) = F(1) + F(2)
         * F(4) = F(2) + F(3)
         * F(n) = F(n-2) + F(n-1)
         */
        public int solution1(int n) {

            long n1 = 0; //F(n - 2)
            long n2 = 1; //F(n - 1)
            long sum = 0; //F(n)

            for (int i = 2; i <= n; i++) {
                System.out.print("i = " + i + " n1 = " + n1 + " n2 = " + n2);
//                System.out.print(" n1 = " + (n1 % 1234567) + " n2 = " + n2 % 1234567);

                sum = (n1 + n2) % 1234567;
                n1 = n2;
                n2 = sum;
                System.out.println(" sum = " + sum);
            }

            return (int) sum;
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
            this.solution(6);
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

            Queue<Node> queue = new LinkedList<>();
            queue.add(new Node(1, 1));

            while (true) {
                int length = queue.size();
                int depth = (length * 2) / 2;

                for (int i = 0; i < depth; i++) {
                    Node node = queue.poll();

                    //목표지점이 동일하면 리턴
                    if (node.getP() == n) {
                        return node.getK();
                    }

                    //순간이동
                    Node leftNode = node.createTeleportingNode(node);
                    //점프
                    Node rightNode = node.createJumpingNode(node);

                    queue.offer(leftNode);
                    queue.offer(rightNode);

                }
//                System.out.println("queue = " + queue);
            }

//            return 1;
        }


        public static class Node {
            int k;
            int p;

            public Node(int k, int p) {
                this.k = k;
                this.p = p;
            }

            public Node createJumpingNode(Node node) {
                return new Node(node.k + 1, node.p + 1);
            }

            public Node createTeleportingNode(Node node) {
                return new Node(node.k, node.p * 2);
            }


            public int getK() {
                return k;
            }

            public int getP() {
                return p;
            }


            @Override
            public String toString() {
                return "Node{" +
                        "k=" + k +
                        ", p=" + p +
                        '}';
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
            this.solution2(4);
            //then
        }


        /**
         * stack = {0}
         * depth = 1 stack = {1, 2}
         * depth = 2 stack = {2, 3, 3, 4} answer = 1
         * depth = 3 stack = {3, 4, 4, 5, 4, 5, 5, 6}, answer = 4
         * depth = 4 stack = {4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8}
         */
        public long solution1(int n) {
            long answer = 0;

            Queue<Integer> queue = new LinkedList<>();
            queue.add(0);

            int depth = 1;

            while (depth <= n) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Integer poll = queue.poll();
                    int leftNode = poll + 1;
                    int rightNode = poll + 2;

                    if (leftNode == n) {
                        answer++;
                    }

                    if (rightNode == n) {
                        answer++;
                    }

                    queue.add(leftNode);
                    queue.add(rightNode);

                }

//                System.out.println("depth = " + depth + " queue = " + queue + " answer = " + answer);
                depth++;

            }


            return answer % 1234567;
        }

        public long solution2(int n) {
            long answer = 0;
            if (n <= 2) {
                return n;
            }
            Queue<Integer> leftQueue = new LinkedList<>();
            leftQueue.add(1);

            Queue<Integer> rightQueue = new LinkedList<>();
            rightQueue.add(2);

            int depth = 2;
            while (depth <= n) {
                int size = leftQueue.size();
                for (int i = 0; i < size; i++) {
                    answer += this.getAnswer(n, leftQueue);
                    answer += this.getAnswer(n, rightQueue);
                }


//                System.out.println("depth = " + depth + " leftQueue = " + leftQueue + " rightQueue = " + rightQueue + " answer = " + answer);
                depth++;
            }

            return answer % 1234567;
        }

        public long getAnswer(int n, Queue<Integer> queue) {
            long answer = 0;
            Integer poll = queue.poll();
            int leftNode = poll + 1;
            int rightNode = poll + 2;

            if (leftNode == n) {
                answer++;
            }

            if (rightNode == n) {
                answer++;
            }

            queue.add(leftNode);
            queue.add(rightNode);
            return answer;
        }

    }

    @Nested
    class N개의_최소공배수 {

        @Test
        public void solution() {
            //given

            //when
            int solution = this.solution(new int[]{2, 6, 8, 14});
            System.out.println("solution = " + solution);
            //then
        }

        public int solution(int[] arr) {
            int answer = 1;

            Arrays.sort(arr);

            int length = arr.length;

            int min = 2;
            int max = arr[length - 1];
            boolean isContinue = true;
            while (isContinue) {

                int divideValue = 1;
                for (int i = min; i <= max; i++) {
                    boolean can = true;
                    int[] tempArr = new int[length];

                    for (int j = 0; j < length; j++) {
                        //최소공배수가 가능하냐에 대한 여부
                        if (arr[j] % i != 0) {
                            can = false;
                            break;
                        }

                        tempArr[j] = arr[j] / i;
                    }

                    //최소공배수가 가능하다면
                    if (can) {
                        arr = tempArr;
                        max = tempArr[length - 1];
                        divideValue = i;
                        break;
                    }

                    //더이상 나눌것이 없다면
                    if (i == max) {
                        isContinue = false;
                        break;
                    }
                }

                answer *= divideValue;
//                System.out.println("arr" + Arrays.toString(arr) + " divideValue = " + divideValue + " answer = " + answer);
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
            this.solution(new int[]{7, 9, 1});
//            this.solution(new int[]{7, 9, 1, 1, 4});
        }


        /**
         * elements = {7, 9, 1, 1, 4}
         */
        public int solution(int[] elements) {
            SortedSet<Integer> set = new TreeSet<>();
            int length = elements.length;
            int start = 1;

            while (start <= length) {
                LinkedList<Integer> list = new LinkedList<>();
                for (int i = 0; i < length; i++) {
                    list.add(elements[i]);
                }

                //연속도니 합의 수열을 구하는 방법
                for (int i = 0; i < length; i++) {
                    int sum = 0;
                    for (int j = 0; j < start; j++) {
                        Integer value = list.pollFirst();
                        sum += value;
                        list.addLast(value);
                    }
                    set.add(sum);
                }

                System.out.println("start = " + start + " set = " + set);
                start++;
            }

            return set.size();
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

            return answer;
        }

        public boolean canExplore(int currentFatigue, int need, int use) {
            return currentFatigue >= need && currentFatigue - use >= 0;
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
}
