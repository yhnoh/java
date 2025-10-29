import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Week2 {

    @Nested
    class 두_링크드_리스트의_합_계산 {

        @Test
        void solution() {

            LinkedList<Integer> list1 = new LinkedList<>();
//            list1.add(2);
            list1.add(4);
            list1.add(3);

            LinkedList<Integer> list2 = new LinkedList<>();
            list2.add(5);
            list2.add(6);
            list2.add(4);

            int result = solution(list1, list2);
            System.out.println("result = " + result); // 807
        }

        public int solution(LinkedList<Integer> list1, LinkedList<Integer> list2) {

            StringBuilder left = new StringBuilder();
            StringBuilder right = new StringBuilder();
            int length = Math.max(list1.size(), list2.size());

            Iterator<Integer> iterator1 = list1.iterator();
            Iterator<Integer> iterator2 = list2.iterator();
            for (int i = 0; i < length; i++) {
                if(iterator1.hasNext()) {
                    left.append(iterator1.next());
                }

                if(iterator2.hasNext()) {
                    right.append(iterator2.next());
                }
            }

            System.out.println("left = " + left + ", right = " + right);

            return Integer.parseInt(left.toString()) + Integer.parseInt(right.toString());
        }

    }

    @Nested
    class 요세푸스_문제 {


        @Test
        void solution() {

            System.out.println(Arrays.toString(this.solution(7 ,3)));

        }

        public int[] solution(int n, int k) {
            int[] answer = new int[n];

            LinkedList<Integer> list = IntStream.range(1, n + 1)
                    .boxed()
                    .collect(Collectors.toCollection(LinkedList::new));


            for (int i = 0; i < n; i++) {
                if(list.size() < k) {
                    answer[i] = list.pollFirst();
                    continue;
                }

                for (int j = 0; j < k; j++) {
                    list.addLast(list.pollFirst());
                }
                answer[i] = list.pollLast();
            }

            return answer;
        }
    }
}
