import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Level2 {


    public int function1(int n){
        if(n == 0) {
            return 0;
        }else if(n == 1){
            return 1;
        }

        return function1(n - 1) + function1(n - 2);
    }

    public int function2(int n){

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
    public void 피보니치_수(){
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
     *
     *
     */
    @Test
    public void 점프와_순간_이동(){



//        System.out.println(calculateK(1));
        System.out.println(calculateK(5));
        System.out.println(calculateK(6));
    }

    public int calculateK(int n){

        int startK = n;
        int mink = n;
        while (startK != 1){
            startK = startK / 2;
            int calculateK = calculateK(startK, n);
            if(mink > calculateK){
                mink = calculateK;
            }
        }

        return mink;
    }


    public int calculateK(int startK, int n){
        int p = startK;
        while (p < n) {
            //순간이동
            p = p * 2;
            if (p == n) {
//                System.out.println("startK = " + startK + " p = " + p +  " moreK = " + 0);
                return startK;
            }
        }


        int moreK = n - (p / 2);
//        System.out.println("startK = " + startK + " p = " + p + " moreK = " + moreK);
        return startK + moreK;
    }

    @Test
    public void 튜플(){
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

        public int[] getResult(){
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
                if(!lastKey.equals(k)){
                    sb.append(",");
                }
            });
            sb.append("]");
            return sb.toString();
        }
    }

    public static class Tuple implements Comparable<Tuple>{
        private final Set<Integer> tuple = new HashSet<>();
        private int size = 0;

        public Tuple(String tuple){
            String[] split = tuple.split(",");
            for (String s : split) {
                this.tuple.add(Integer.valueOf(s));
                size++;
            }
        }

        public int getSize() {
            return size;
        }

        public Integer getFirstData(){
            return tuple.iterator().next();
        }

        public void remove(Integer data){
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
    public class 해시{

        @Test
        public void result(){

        }

        /**
         * String[] phone_book 전화번호 배열이 담겨 있음
         * 전번화부 배열에 어떤 번호가 다른 번호의 접두어인 경우 false
         * 아닌 경우 ture
         */
        @Test
        public void 해시(){
            String[] phone_book = {"12","123","1235","567","88"};
            Result prefixResult = new Result(phone_book);
            boolean result = prefixResult.getResult();
            System.out.println("prefixResult = " + prefixResult);
        }

        public static class Result {
            private final String[] phoneBook;

            public Result(String[] phoneBook) {
                this.phoneBook = phoneBook;
            }

            public boolean getResult(){
                final SortedSet<PhoneNumber> phoneNumbers = new TreeSet<>();
                for (String phoneNumber : phoneBook) {
                    boolean add = phoneNumbers.add(new PhoneNumber(phoneNumber));
//                    if(!add){
//                        return false;
//                    }
                }

                return phoneNumbers.size() == phoneBook.length;

            }


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

                return -1;
            }
        }
    }

    @Nested
    class 행렬의_곱셈{

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
        private Queue<String> caches = new LinkedList<>();

        @Test
        public void 결과(){
            int cacheSize = 3;
            String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"};

            int result = 0;
            int hit = 1;
            int miss = 5;
            for (String city : cities) {
                if(cacheSize == 0){
                    result += miss;
                    continue;
                }

                String lowerCaseCity = city.toLowerCase();

                if(caches.contains(lowerCaseCity)){
                    result += hit;
                    //LRU 알고리즘 적용
                    caches.offer(caches.poll());
                }else {
                    int nowCacheSize = caches.size();
                    if(nowCacheSize >= cacheSize){
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
        void 결과(){

        }

        public static class Node{

            private List<Integer> items = new LinkedList<>();
            private int start = 0;
            private int count = 0;
            public Node(int n) {
                items.add(0);

            }

            public void exclusive(int level){
                items.add(start);
                for (int i = 0; i < level; i++) {
                    int root = items.get(i);

                }
            }

            public void addItem(int item){

            }


        }

    }


    @Nested
    class 뉴스_클러스터링 {

        @Test
        public void 결과(){
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
                if(set1.containsKey(key) && set2.containsKey(key)){
                    setSum += Math.min(set1.get(key), set2.get(key));
                    allSum += Math.max(set1.get(key), set2.get(key));
                }else if(set1.containsKey(key)) {
                    allSum += set1.get(key);
                }else if(set2.containsKey(key)){
                    allSum += set2.get(key);
                }
            }

//            System.out.println("setSum = " + setSum);
//            System.out.println("allSum = " + allSum);

            float jakad = allSum == 0  ? 1 : (float) setSum / allSum;
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

            private Map<String, Integer> set = new LinkedHashMap<>();
            public JakadSet(String str) {

                int length = str.length();
                for (int i = 0; i < length - 1; i++) {
                    char c1 = str.charAt(i);
                    char c2 = str.charAt(i + 1);

                    if(isAlphabet(c1) && isAlphabet(c2)){
                        String key = String.valueOf(c1).toUpperCase() + String.valueOf(c2).toUpperCase();
                        set.put(key, set.getOrDefault(key, 0) + 1);
                    }
                }
            }

            public Map<String, Integer> getSet() {
                return set;
            }

            private boolean isAlphabet(char c){
                return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            }

            @Override
            public String toString() {
                return set.toString();
            }
        }
    }

}
