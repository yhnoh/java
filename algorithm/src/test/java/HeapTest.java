import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class HeapTest {

    @Nested
    class testClass {
        @Test
        public void insertTest() {
            //given

            Heap heap = new Heap();
            // 1
            heap.insert(1);
            System.out.println(heap);

            // 2, 1
            heap.insert(2);
            System.out.println(heap);
            // 3, 1, 2
            heap.insert(3);
            System.out.println(heap);

            // 4, 3, 2, 1
            heap.insert(4);
            System.out.println(heap);

        }

        @Test
        public void popTest() {
            //given

            Heap heap = new Heap();
            // 1
            heap.insert(10);
            heap.insert(9);
            heap.insert(8);
            heap.insert(7);
            heap.insert(6);
            heap.insert(5);
            heap.insert(4);
            heap.insert(3);
            heap.insert(2);
            heap.insert(1);

            System.out.println(heap);
        }

        static class Heap {
            private final LinkedList<Integer> nodes = new LinkedList<>();

            public Heap() {
                // 0번 인덱스는 사용하지 않음
                nodes.add(-1);
            }

            public void insert(int value) {

                // 마지막에 추가
                nodes.add(value);
                int nowIndex = nodes.size() - 1;
                this.swap(nowIndex);
            }

            public void delete() {

            }

            public Integer pop() {
                boolean isEmpty = nodes.size() == 1;
                if(isEmpty) {
                    return null;
                }

                // 루트 노드 or 루트 노드와 마지막 노드만 존재하는 경우
                Integer pop = nodes.pollFirst();
                if(nodes.size() <= 3) {
                    return pop;
                }

                // 만약 오른쪽 노드가 왼쪽 노드보다 크다면 교체
                this.swap(2);
                return pop;
            }

            /**
             * 우선 순위 높은 노드가 부모에 위치하도록 교체
             */
            private void swap(int nowIndex) {
                // 루트 노드에 도달하였을 경우 종료
                if(nowIndex == 1) {
                    return;
                }

                int parentIndex = nowIndex / 2;

                // 현재 노드가 부모 노드보다 크면 교체
                Integer nowValue = nodes.get(nowIndex);
                Integer parentValue = nodes.get(parentIndex);
                if(nowValue > parentValue) {
                    nodes.set(parentIndex, nowValue);
                    nodes.set(nowIndex, parentValue);
                    this.swap(parentIndex);
                }
            }

            @Override
            public String toString() {
                return "Heap{" +
                        "nodes=" + nodes +
                        '}';
            }
        }
    }
}
