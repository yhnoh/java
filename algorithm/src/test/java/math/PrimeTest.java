package math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimeTest {


    @Test
    void 소수_테스트(){
        Assertions.assertFalse(this.isPrime(1));
        Assertions.assertTrue(this.isPrime(2));
        Assertions.assertTrue(this.isPrime(3));
        Assertions.assertFalse(this.isPrime(4));
        Assertions.assertTrue(this.isPrime(5));
        Assertions.assertFalse(this.isPrime(6));
        Assertions.assertTrue(this.isPrime(7));
        Assertions.assertFalse(this.isPrime(8));
        Assertions.assertFalse(this.isPrime(9));
        Assertions.assertFalse(this.isPrime(10));
    }

    /**
     * @return 소수를 판별하는 함수
     */
    boolean isPrime(int number) {

        if(number == 1) {
            return false;
        }else if(number == 2){
            return true;
        }

        for (int i = 2; i <= number - 1; i++) {
            if(number % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Test
    void 제곱근을_이용한_소수_테스트(){
        Assertions.assertFalse(this.isSqrtPrime(1));
        Assertions.assertTrue(this.isSqrtPrime(2));
        Assertions.assertTrue(this.isSqrtPrime(3));
        Assertions.assertFalse(this.isSqrtPrime(4));
        Assertions.assertTrue(this.isSqrtPrime(5));
        Assertions.assertFalse(this.isSqrtPrime(6));
        Assertions.assertTrue(this.isSqrtPrime(7));
        Assertions.assertFalse(this.isSqrtPrime(8));
        Assertions.assertFalse(this.isSqrtPrime(9));
        Assertions.assertFalse(this.isSqrtPrime(10));
    }



    /**
     * 소수의 판별은 1과 자기자신이 제외한 다른 자연수를 가지고 있으면 안된다는 의미
     * a * b = N 이라는 의미는 1 <= a, b <= N
     * a, b 중 적어도 하나는 √N 이하의 값을 가진다.
     * ex) 16 = (1 * 16), (2 * 8), (4 * 4), (8 * 2), (16 * 1)
     * @return √N 제곱근을 이용하여 소수를 판별하는 함수
     */
    boolean isSqrtPrime(int number) {

        if(number == 1) {
            return false;
        }else if(number == 2){
            return true;
        }

        double sqrt = Math.sqrt(number);

        for (int i = 2; i <= sqrt; i++) {
            if(number % i == 0) {
                return false;
            }
        }

        return true;
    }


}
