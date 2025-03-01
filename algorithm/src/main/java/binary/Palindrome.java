package binary;

public class Palindrome {


    public Palindrome() {
    }

    public boolean isPalindrome(String target){

        if(target.length() <= 1){
            return true;
        }

        int startIndex = 0;
        int endIndex = target.length() - 1;
        char startChar = target.charAt(0);
        char endChar = target.charAt(target.length() - 1);

        if(startChar != endChar){
            return false;
        }

        return isPalindrome(target.substring(startIndex + 1, endIndex));
    }
}
