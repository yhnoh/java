import org.junit.jupiter.api.Test;

import java.util.Map;

public class DynamicProgramming {

    private Map<Integer, Integer> cache = new java.util.HashMap<>() {
        {
            put(1, 1);
            put(2, 1);
        }
    };

    /**
     * 미리 풀어난 문제가 다음 문제를 푸는데 활용할 수 있다면 동적 계획법(Dynamic Programming) 적용 가능
     */
    @Test
    public void test() {

        System.out.println(this.fibonacci(50));;
    }

    private int fibonacci(int n) {

        if(cache.containsKey(n)) {
            return cache.get(n);
        }

        int result = fibonacci(n - 1) + fibonacci(n - 2);
        cache.put(n, result);
        return result;
    }

}
