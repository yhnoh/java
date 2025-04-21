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
        //https://school.programmers.co.kr/learn/courses/30/lessons/12941

        @Test
        public void solution() {
            //given

            //when

            //then
        }

        public int solution(int[] A, int[] B) {
            int answer = 0;

            // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
            System.out.println("Hello Java");

            return answer;
        }
    }

    public int function1(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        return function1(n - 1) + function1(n - 2);
    }

    public int function2(int n) {

        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(1);

        for (int i = 0; i < n - 1; i++) {
            Integer f_1 = stack.pop();
            Integer f_2 = stack.pop();
            int f = f_1 + f_2;

            stack.push(f_1);
            stack.push(f % 1234567);
        }

        return stack.pop();
    }

    @Test
    public void 피보니치_수() {
//        System.out.println("n = 2일 뗴 = " + function1(2));
//        System.out.println("n = 3일 뗴 = " + function1(3));
//        System.out.println("n = 4일 뗴 = " + function1(4));
//        System.out.println("n = 5일 뗴 = " + function1(5));

        System.out.println("n = 2일 뗴 = " + function2(2) % 1234567);
        System.out.println("n = 3일 뗴 = " + function2(3) % 1234567);
        System.out.println("n = 4일 뗴 = " + function2(4) % 1234567);
        System.out.println("n = 5일 뗴 = " + function2(5) % 1234567);
        System.out.println("n = 6일 뗴 = " + function2(6) % 1234567);
        System.out.println("n = 7일 뗴 = " + function2(7) % 1234567);
        System.out.println("n = 10000일 뗴 = " + function2(10000) % 1234567);


    }

    /**
     * n = 목표위치
     * p = 현재위치
     * k = 점프거리, 점프시 k만큼의 배터리 소모
     * 순간이동 가능, 현재 위치에서 * 2만큼 이동
     */
    @Nested
    class 점프와_순간_이동 {
        @Test
        public void 결과() {

            // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
            int n = 5000000;
            int result = result(n);
        }

        public int result(int n) {
            int result = 1;
            if (n == 1) {
                return result;
            }
            //n과 k 데이터를 저장하기 위한 Queue
            Queue<Node> queue = new LinkedList<>();
            //k를 정렬하기 위한 Set
            SortedSet<Node> sortedSet = new TreeSet<>();
            queue.offer(new Node(1, 1));

            //경우의수는 2가지, 에너지를 소비하고 한칸 가냐? 아니면 2배씩 순간이동 하냐?
            int way = 2;
            while (sortedSet.isEmpty()) {

                for (int i = 0; i < way / 2; i++) {
                    Node node = queue.poll();
                    Node jumpNode = node.getJumpNode();
                    Node teleportNode = node.getTeleportNode();

                    queue.offer(jumpNode);
                    queue.offer(teleportNode);

                    if (jumpNode.getN() == n) {
                        sortedSet.add(jumpNode);
                    }
                    if (teleportNode.getN() == n) {
                        sortedSet.add(teleportNode);
                    }
                }

                way = way * 2;
            }

            return sortedSet.first().getK();
        }

        public static class Node implements Comparable<Node> {
            private final int n;
            private final int k;

            public Node(int n, int k) {
                this.n = n;
                this.k = k;
            }

            public Node getJumpNode() {
                return new Node(n + 1, k + 1);
            }

            public Node getTeleportNode() {
                return new Node(n * 2, k);
            }

            @Override
            public int compareTo(Node o) {
                return Integer.compare(k, o.k);
            }

            public int getN() {
                return n;
            }

            public int getK() {
                return k;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "n=" + n +
                        ", k=" + k +
                        '}';
            }
        }
    }


    @Test
    public void 튜플() {
        String s = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
        TupleResult tupleResult = new TupleResult(s);
        System.out.println("tupleResult = " + tupleResult);
        System.out.println(Arrays.toString(tupleResult.getResult()));
    }

    public static class TupleResult {
        private final TreeMap<Integer, Tuple> tuples = new TreeMap<>();

        public TupleResult(String s) {
            String[] tuples = s.substring(2, s.length() - 2).split("},\\{");
            for (String tuple : tuples) {
                Tuple item = new Tuple(tuple);
                this.tuples.put(item.getSize(), item);
            }
        }

        public int[] getResult() {
            Integer size = tuples.lastKey();
            int[] result = new int[size];
            for (int i = 1; i <= size; i++) {
                Tuple tuple = tuples.get(i);
                Integer data = tuple.getFirstData();
                result[i - 1] = data;

                for (int j = i; j <= size; j++) {
                    tuples.get(j).remove(data);
                }
            }

            return result;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            Integer lastKey = tuples.lastKey();
            tuples.forEach((k, v) -> {
                sb.append(v);
                if (!lastKey.equals(k)) {
                    sb.append(",");
                }
            });
            sb.append("]");
            return sb.toString();
        }
    }

    public static class Tuple implements Comparable<Tuple> {
        private final Set<Integer> tuple = new HashSet<>();
        private int size = 0;

        public Tuple(String tuple) {
            String[] split = tuple.split(",");
            for (String s : split) {
                this.tuple.add(Integer.valueOf(s));
                size++;
            }
        }

        public int getSize() {
            return size;
        }

        public Integer getFirstData() {
            return tuple.iterator().next();
        }

        public void remove(Integer data) {
            tuple.remove(data);
        }

        @Override
        public int compareTo(Tuple tuple) {
            return Integer.compare(size, tuple.size);
        }

        @Override
        public String toString() {
            return tuple.toString();
        }
    }


    @Nested
    public class 전화번호_목록 {

        /**
         * String[] phone_book 전화번호 배열이 담겨 있음
         * 전번화부 배열에 어떤 번호가 다른 번호의 접두어인 경우 false
         * 아닌 경우 ture
         * <p>
         * 1. 문자열 정렬을 통해서 사전순으로 정렬
         * 2. 정렬된 배열을 순회하면서 다음 문자열이 접두사인지 확인
         */
        @Test
        public void 결과() {
            String[] phone_book = {"1235", "567", "88", "123", "12"};
            Arrays.sort(phone_book);

//            System.out.println("sorted phone_book = " + Arrays.toString(phone_book));
            boolean result = true;
            for (int i = 0; i < phone_book.length - 1; i++) {
                String str1 = phone_book[i];
                String str2 = phone_book[i + 1];

                if (str2.startsWith(str1)) {
                    result = false;
                    break;
                }
            }


            System.out.println("result = " + result);
        }

        public static class PhoneNumber implements Comparable<PhoneNumber> {

            private final String phoneNumber;

            public PhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            @Override
            public String toString() {
                return phoneNumber;
            }

            @Override
            public int compareTo(PhoneNumber o) {
                //접두사일 경우 정렬하지 않는다.
                if (this.phoneNumber.startsWith(o.phoneNumber)) {
                    return 0;
                }

                return this.phoneNumber.compareTo(o.phoneNumber);
            }
        }
    }

    @Nested
    class 행렬의_곱셈 {

        @Test
        public void 결과() {
            int[][] arr1 = {{2, 3, 2}, {4, 2, 4}, {3, 1, 4}};
            int[][] arr2 = {{5, 4, 3}, {2, 4, 1}, {3, 1, 1}};

            int arr1RowCount = arr1.length;
            int arr1ColumLenth = arr1[0].length;
            int arr2RowLenth = arr2.length;

            int[][] result = new int[arr1RowCount][arr1ColumLenth];

            for (int i = 0; i < arr1RowCount; i++) {
                for (int j = 0; j < arr1ColumLenth; j++) {
                    int value = 0;
                    for (int k = 0; k < arr2RowLenth; k++) {
//                        System.out.print("arr1[" + j + "][" + k + "] = " + arr1[j][k] + " ");
//                        System.out.print("arr2[" + k + "][" + j + "] = " + arr2[k][j] + " ");
//                        System.out.print("value = " + (arr1[i][k] * arr2[k][j]) + " ");
                        value += arr1[i][k] * arr2[k][j];
                    }
//                    System.out.println();

                    result[i][j] = value;
//                    System.out.println("i = " + i + " j = " + j + " value = " + value);
                }
            }

        }


    }


    @Nested
    class 프로세스 {

        /**
         * 큐에서 프로세스를 하나 꺼낸다.
         * 만약 우선순위가 더 높은 프로세스가 있다면 방금 꺼낸 프로세스는 다시 큐에 넣는다.
         * 만약 그런 프로세스가 없다면 방금 꺼낸 프로세스를 실행한다.
         */
        @Test
        public void 결과() {
            int[] priorities;
            int location;
        }


    }

    @Nested
    class 캐시 {

        //        private LinkedHashSet<String> caches = new LinkedHashSet<>();
        private final Queue<String> caches = new LinkedList<>();

        @Test
        public void 결과() {
            int cacheSize = 3;
            String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"};

            int result = 0;
            int hit = 1;
            int miss = 5;
            for (String city : cities) {
                if (cacheSize == 0) {
                    result += miss;
                    continue;
                }

                String lowerCaseCity = city.toLowerCase();

                if (caches.contains(lowerCaseCity)) {
                    result += hit;
                    //LRU 알고리즘 적용
                    caches.offer(caches.poll());
                } else {
                    int nowCacheSize = caches.size();
                    if (nowCacheSize >= cacheSize) {
                        //LRU 알고리즘 적용
                        caches.poll();
                    }
                    caches.add(lowerCaseCity);

                    result += miss;
                }
            }

            System.out.println("result = " + result);
        }
    }

    @Nested
    class 멀리_뛰기 {

        @Test
        void 결과() {

        }

        public static class Node {

            private final List<Integer> items = new LinkedList<>();
            private final int start = 0;
            private final int count = 0;

            public Node(int n) {
                items.add(0);

            }

            public void exclusive(int level) {
                items.add(start);
                for (int i = 0; i < level; i++) {
                    int root = items.get(i);

                }
            }

            public void addItem(int item) {

            }


        }

    }


    @Nested
    class 뉴스_클러스터링 {

        @Test
        public void 결과() {
            String str1 = "E=M*C^2";
            String str2 = "e=m*c^2";


            JakadSet jakadSet1 = new JakadSet(str1);
            JakadSet jakadSet2 = new JakadSet(str2);

            System.out.println("set1 = " + new JakadSet(str1));
            System.out.println("set2 = " + new JakadSet(str2));

            Map<String, Integer> set1 = jakadSet1.getSet();
            Map<String, Integer> set2 = jakadSet2.getSet();

            Set<String> keys = new HashSet<>();
            keys.addAll(set1.keySet());
            keys.addAll(set2.keySet());

            int setSum = 0;
            int allSum = 0;
            for (String key : keys) {
                if (set1.containsKey(key) && set2.containsKey(key)) {
                    setSum += Math.min(set1.get(key), set2.get(key));
                    allSum += Math.max(set1.get(key), set2.get(key));
                } else if (set1.containsKey(key)) {
                    allSum += set1.get(key);
                } else if (set2.containsKey(key)) {
                    allSum += set2.get(key);
                }
            }

//            System.out.println("setSum = " + setSum);
//            System.out.println("allSum = " + allSum);

            float jakad = allSum == 0 ? 1 : (float) setSum / allSum;
            int result = (int) (jakad * 65536);
            System.out.println("result = " + result);
        }


        /**
         * 다중 집합을 만들어야 한다.
         * 다중 집합 규칙
         * 1. 두글자씩 끊어서 다중 집합을 만든다.
         * 2. 대소문자를 구분하지 않는다.
         * 3. 영문자로 된 글자만 가능하다.
         */
        public static class JakadSet {

            private final Map<String, Integer> set = new LinkedHashMap<>();

            public JakadSet(String str) {

                int length = str.length();
                for (int i = 0; i < length - 1; i++) {
                    char c1 = str.charAt(i);
                    char c2 = str.charAt(i + 1);

                    if (isAlphabet(c1) && isAlphabet(c2)) {
                        String key = String.valueOf(c1).toUpperCase() + String.valueOf(c2).toUpperCase();
                        set.put(key, set.getOrDefault(key, 0) + 1);
                    }
                }
            }

            public Map<String, Integer> getSet() {
                return set;
            }

            private boolean isAlphabet(char c) {
                return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            }

            @Override
            public String toString() {
                return set.toString();
            }
        }
    }


    @Nested
    class 의상 {

        /**
         * 코니는 매일 의상을 갈아입는다.
         * -
         * - 의상은 종류별(얼굴, 상의, 하의, 겉옷)로 하나만 입을 수 있다.
         * - 매일 옷을 갈아 입을 때 종류를 변경하거나 종류를 추가할 경우 다른 의상으로 판단한다.
         * - 하루에 최소 한개의 의상을 입는다.
         */
        @Test
        public void 결과() {

//            String[][] clothes = {{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"}};
//            String[][] clothes = {{"yellow_hat", "headgear"}};
//            String[][] clothes = {{"headgear1", "headgear"}, {"eyewear1", "eyewear"}};
            String[][] clothes = {{"검정 선글라스", "얼굴"}, {"동그란 안경", "얼굴"}, {"파란색 티셔츠", "상의"}, {"청바지", "하의"}, {"긴코트", "겉옷"}};
//            String[][] clothes = {{"yellow_hat", "headgear"}, {"yellow_hat2", "headgear"}};

            /**
             * headgear : yellow_hat, green_turban
             * eyewear : blue_sunglasses
             */

            LinkedHashMap<String, List<String>> clotheMap = new LinkedHashMap<>();

            for (String[] clothe : clothes) {
                String name = clothe[0];//의상 이름
                String key = clothe[1];//의상 종류

                List<String> value = clotheMap.getOrDefault(key, new ArrayList<>());
                value.add(name);
                clotheMap.put(key, value);
            }

            System.out.println("clotheMap = " + clotheMap);

            Set<String> keys = clotheMap.keySet();

            for (int i = 1; i < clotheMap.size(); i++) {
                int result = 0;

            }


            int result1 = 0;
            int result2 = 1;
            for (String key : clotheMap.keySet()) {
                List<String> value = clotheMap.get(key);
                result1 += value.size();
                result2 *= value.size();
            }

            if (clotheMap.size() >= 2) {
                System.out.println("result1 = " + result1 + " result2 = " + result2);
            } else {
                System.out.println("result1 = " + result1);
            }

            int result = clotheMap.size() >= 2 ? result1 + result2 : result1;

        }
    }

    @Nested
    class 주식가격 {
        @Test
        void 결과() {
            int[] prices = {1, 2, 3, 2, 3};
            int[] result = new int[prices.length];

            for (int i = 0; i < prices.length; i++) {

                int price1 = prices[i];
                int seconds = 0;
                for (int j = i + 1; j < prices.length; j++) {
                    int price2 = prices[j];
                    if (price1 <= price2) {
                        seconds++;
                    } else {
                        seconds++;
                        break;
                    }
                }

                result[i] = seconds;
            }

            System.out.println("result = " + Arrays.toString(result));
        }


    }

}
