package binary;

public class PalindromeMain {


    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        String 토마토 = "토마토";
        System.out.println(palindrome.isPalindrome(토마토));
        System.out.println(palindrome.isPalindrome("tomato"));
        System.out.println(palindrome.isPalindrome("abcba"));
        System.out.println(palindrome.isPalindrome("abccba"));

    }
}
