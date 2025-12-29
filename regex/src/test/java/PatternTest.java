import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PatternTest {


    @Test
    void test() {

        Pattern pattern = Pattern.compile("정규표현식");
        Matcher matcher = pattern.matcher("문자열");

        boolean found = matcher.find();

    }

    @Test
    void 리터럴_테스트(){
        String log = "[2024-06-10 14:23:45] [INFO] Application started successfully.";

        Pattern pattern = Pattern.compile("INFO");
        Matcher matcher = pattern.matcher(log);

        if(matcher.find()) {
            System.out.println("INFO 로그가 포함되어 있습니다.");
            System.out.println("위치: start = " + matcher.start() + " end = " + matcher.end());
            System.out.println("substring = " + log.substring(matcher.start(), matcher.end()));
        } else {
            System.out.println("INFO 로그가 포함되어 있지 않습니다.");
        }
    }


    /**
     * \ ^ $ . | [ ] ( ) * + ? { }
     */
    @Test
    void 메타문자_테스트(){

        // Dot(.): 임의의 문자 하나
        System.out.println(".: 임의의 문자 하나");
        Pattern pattern1 = Pattern.compile("a.c");
        System.out.println(pattern1.matcher("abc").find());  // true
        System.out.println(pattern1.matcher("acc").find());  // true
        System.out.println(pattern1.matcher("a c").find());  // true (공백도 포함)
        System.out.println(pattern1.matcher("ac").find());   // false
        System.out.println(pattern1.matcher("axxc").find()); // false
        System.out.println();

        // 캐럿(^): 문자열의 시작
        System.out.println("캐럿(^): 문자열의 시작");
        Pattern pattern2 = Pattern.compile("^Hello");
        System.out.println(pattern2.matcher("Hello")
                .find()); // true
        System.out.println(pattern2.matcher("Hello, World!")
                .find()); // true
        System.out.println(pattern2.matcher(" Hello")
                .find()); // false
        System.out.println(pattern2.matcher("Say Hello")
                .find()); // false
        System.out.println();

        // 달러($): 문자열의 끝
        System.out.println("달러($): 문자열의 끝");
        Pattern pattern3 = Pattern.compile("world$");
        System.out.println(pattern3.matcher("Hello, world")
                .find()); // true
        System.out.println(pattern3.matcher("Hello")
                .find()); // false, world로 끝나지 않음
        System.out.println(pattern3.matcher("Say Hello")
                .find()); // false, world로 끝나지 않음
        System.out.println(pattern3.matcher("Hello, world!")
                .find()); // false, !로 끝나기 때문
        System.out.println(pattern3.matcher("Hello, World")
                .find()); // false, 대소문자 구분
        System.out.println();

        // Asterisk(*): 0회 이상 반복
        System.out.println("Asterisk(*): 0회 이상 반복");
        Pattern pattern4 = Pattern.compile("a*c");
        System.out.println(pattern4.matcher("abc").find()); // trye
        System.out.println(pattern4.matcher("abbc").find()); // true
        System.out.println(pattern4.matcher("ac").find()); // true
        System.out.println(pattern4.matcher("ad").find()); // false, 'c'로 끝나지 않음
        System.out.println();

        // Plus(+): 1회 이상 반복
        System.out.println("Plus(+): 1회 이상 반복");
        Pattern pattern5 = Pattern.compile("a+c");
        System.out.println(pattern5.matcher("ac").find()); // true, 'a'가 1회 이상 존재
        System.out.println(pattern5.matcher("aac").find()); // true
        System.out.println(pattern5.matcher("aaac").find()); // true
        System.out.println(pattern5.matcher("ac").find()); // false, 1회이상 'a'가 없음
        System.out.println(pattern5.matcher("abc").find()); // false, 1회이상 'a'가 없음
        System.out.println(pattern5.matcher("aad").find()); // false, 'c'로 끝나지 않음
        System.out.println();

        // Question Mark(?): 0회 또는 1회
        System.out.println("Question Mark(?): 0회 또는 1회");
        Pattern pattern6 = Pattern.compile("ab?c");
        System.out.println(pattern6.matcher("ac").find()); // true, 'b'가 0회 존재
        System.out.println(pattern6.matcher("abc").find()); // true, 'b'가 1회 존재
        System.out.println(pattern6.matcher("abbc").find()); // false, 'b'가 2회 존재
        System.out.println();

        // 파이프(|): OR 연산자
        System.out.println("파이프(|): OR 연산자");
        Pattern pattern7 = Pattern.compile("cat|dog");
        System.out.println(pattern7.matcher("I have a cat").find()); // true
        System.out.println(pattern7.matcher("I have a dog").find()); // true
        System.out.println(pattern7.matcher("I have a bird").find()); // false
        System.out.println();

        // 이스케이프(\): 메타문자 리터럴로 사용
        System.out.println("이스케이프(\\): 메타문자 리터럴로 사용");
        Pattern pattern8 = Pattern.compile("\\.com");
        System.out.println(pattern8.matcher("example.com").find()); // true
        System.out.println(pattern8.matcher("exampleasdcom").find()); // false
        System.out.println();

        // 소괄호(): 그룹핑
        System.out.println("소괄호(): 그룹핑");
        Pattern pattern9 = Pattern.compile("(abc)+");
        System.out.println(pattern9.matcher("abc").find()); // true
        System.out.println(pattern9.matcher("abcabc").find()); // true
        System.out.println(pattern9.matcher("abcabcabc").find()); // true
        System.out.println(pattern9.matcher("abcab").find()); // true, 1회 이상 'abc'가 존재
        System.out.println(pattern9.matcher("").find()); // false
        System.out.println(pattern9.matcher("ab").find()); // false

        System.out.println("소괄호(): 그룹핑 OR 연산자와 조합");
        Pattern pattern10 = Pattern.compile("image\\.(jpg|png|gif)");
        System.out.println(pattern10.matcher("image.jpg").find()); // true
        System.out.println(pattern10.matcher("image.png").find()); // true
        System.out.println(pattern10.matcher("image.gif").find()); // true
        System.out.println(pattern10.matcher("image.bmp").find()); // true
        System.out.println();

        System.out.println("소괄호(): 그룹핑 캡쳐그룹");
        Pattern pattern11 = Pattern.compile("(\\d{3})-(\\d{4})-(\\d{4})");
        Matcher matcher = pattern11.matcher("010-1234-5678");
        if(matcher.find()) {
            System.out.println("전체: " + matcher.group(0));
            System.out.println("그룹1: " + matcher.group(1));
            System.out.println("그룹2: " + matcher.group(2));
            System.out.println("그룹3: " + matcher.group(3));
        }


        System.out.println("중괄호{}: 반복횟수를 정확하게 지정");
        // {n}: 정확히 n번
        Pattern pattern12 = Pattern.compile("a{3}");
        System.out.println(pattern12.matcher("aa").find());    // false
        System.out.println(pattern12.matcher("aaa").find());   // true
        System.out.println(pattern12.matcher("aaaa").find());  // true (aaa 포함)
        System.out.println();

        // {n,}: n번 이상
        Pattern pattern13 = Pattern.compile("a{2,}");
        System.out.println(pattern13.matcher("a").find());     // false
        System.out.println(pattern13.matcher("aa").find());    // true
        System.out.println(pattern13.matcher("aaaaa").find()); // true
        System.out.println();

        // {n,m}: n번 이상 m번 이하
        Pattern pattern14 = Pattern.compile("a{2,4}");
        System.out.println(pattern14.matcher("a").find());     // false
        System.out.println(pattern14.matcher("aa").find());    // true
        System.out.println(pattern14.matcher("aaa").find());   // true
        System.out.println(pattern14.matcher("aaaa").find());  // true
        System.out.println(pattern14.matcher("aaaaa").find()); // true (aaaa 포함)
        System.out.println();

        System.out.println("대괄호[]: 하나의 문자 위치에 여러 문자가 하나 나올 수 있음을 표현");
        // [abc]: a, b, c 중 하나
        Pattern pattern15 = Pattern.compile("[abc]");
        System.out.println(pattern15.matcher("a").find());  // true
        System.out.println(pattern15.matcher("b").find());  // true
        System.out.println(pattern15.matcher("d").find());  // false
        System.out.println();
        // [a-z]: a부터 z까지 (범위)
        Pattern pattern16 = Pattern.compile("[a-z]");
        System.out.println(pattern16.matcher("m").find());  // true
        System.out.println(pattern16.matcher("A").find());  // false (대문자)
        System.out.println();
        // [a-zA-Z]: 모든 영문자
        Pattern pattern17 = Pattern.compile("[a-zA-Z]");
        System.out.println(pattern17.matcher("A").find());  // true
        System.out.println(pattern17.matcher("z").find());  // true
        System.out.println();
        // [0-9]: 숫자
        Pattern pattern18 = Pattern.compile("[0-9]");
        System.out.println(pattern18.matcher("5").find());  // true
        System.out.println(pattern18.matcher("a").find());  // false
        System.out.println();

        // 대괄호 안에서는 대부분의 메타문자가 리터럴로 취급됨
        Pattern pattern19 = Pattern.compile("[.]");  // 점을 그대로 찾음
        System.out.println(pattern19.matcher(".").find());  // true

        // 하지만 ^ - ] \는 여전히 특별한 의미
        Pattern pattern20 = Pattern.compile("[a-z]");    // 범위
        Pattern pattern21 = Pattern.compile("[a\\-z]");  // 하이픈 리터럴
        Pattern pattern22 = Pattern.compile("[\\]]"); // 대괄호 닫기 리터럴

        System.out.println(pattern20.matcher("-").find());  // false
        System.out.println(pattern21.matcher("-").find());  // true
        System.out.println(pattern22.matcher("]").find());  // true

        // 대괄호 안의 캐럿(^): 부정(not)
        // [^0-9]: 숫자가 아닌 것
        Pattern pattern23 = Pattern.compile("[^0-9]");
        System.out.println(pattern23.matcher("5").find());  // false
        System.out.println(pattern23.matcher("a").find());  // true

        // [^aeiou]: 모음이 아닌 것
        Pattern pattern24 = Pattern.compile("[^aeiou]");
        System.out.println(pattern24.matcher("a").find());  // false
        System.out.println(pattern24.matcher("b").find());  // true

    }

    @Test
    void Log() {
        //given

        System.out.println(isErrorOrWarn("[INFO] Something went wrong."));
        System.out.println(isErrorOrWarn("[ERROR] Something went wrong."));
        System.out.println(isErrorOrWarn("[WARN] Something went wrong."));
        System.out.println(isErrorOrWarn("Something [WARN] went wrong."));
        //when

        System.out.println(hasValidProtocol("http://example.com"));
        System.out.println(hasValidProtocol("https://example.com"));
        System.out.println(hasValidProtocol("ftp://example.com"));
        //then
    }

    public static boolean isErrorOrWarn(String log){

        Pattern pattern = Pattern.compile("^(\\[ERROR]|\\[WARN])");
        return pattern.matcher(log)
                .find();
    }

    public static boolean hasValidProtocol(String url){
        Pattern pattern = Pattern.compile("^(http://|https://)");
        return pattern.matcher(url).find();
    }


    public static boolean simplePhoneValid(String url){
        Pattern pattern = Pattern.compile("^010(\\d{8})$");
        return pattern.matcher(url).find();
    }

    @Test
    void matches_find_lookingAt_차이점(){
        String text = "abc123def";
        Pattern pattern = Pattern.compile("\\d+"); // 숫자 1개 이상

        // reset() : Matcher를 다시 처음부터 검사할 수 있도록 초기화

        Matcher matcher = pattern.matcher(text);
        // matches(): 전체 문자열이 패턴과 일치하는지 확인, ^...$ 자동 추가
        System.out.println("matches() : " + matcher.matches());

        // find(): 문자열 내에서 패턴과 일치하는 부분이 있는지 확인
        matcher.reset();
        System.out.println("find() : " + matcher.find());

        // lookingAt(): 문자열의 시작부터 패턴과 일치하는지 확인, ^ 자동 추가
        matcher.reset();
        System.out.println("lookingAt() : " + matcher.lookingAt());

    }

    @Test
    void start_end_테스트(){
        String text = "The price is $100 and discount is $20";
        Pattern pattern = Pattern.compile("\\$\\d+"); // $로 시작하는 숫자 패턴
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()){
            System.out.println("group() = " + matcher.group() + " start() = " + matcher.start() + " end() = " + matcher.end());
        }

        String text2 = "2025-12-31";
        Pattern pattern2 = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})"); // $로 시작하는 숫자 패턴
        Matcher matcher2 = pattern2.matcher(text2);

        // ()를 통해서 그룹핑한 경우, 각 그룹의 start end 위치도 확인 가능
        if(matcher2.find()){
            for (int i = 0; i <= matcher2.groupCount(); i++) {
                System.out.println("group " + i + " = " + matcher2.group(i) + " start() = " + matcher2.start(i) + " end() = " + matcher2.end(i));
            }
        }

        String log = """
            [INFO] Application started
            [ERROR] Connection timeout
            [DEBUG] Processing request
            [ERROR] Database unreachable
            """;
        Pattern pattern3 = Pattern.compile("\\[ERROR\\][^\\n]*");
        Matcher matcher3 = pattern3.matcher(log);
        StringBuilder result = new StringBuilder();
        int lastEnd = 0;
        while(matcher3.find()) {

            // 매칭 전까지의 텍스트
            result.append(log, lastEnd, matcher3.start());
            // 에러 부분은 강조
            result.append(">>> ")
                    .append(matcher3.group())
                    .append(" <<<");

            lastEnd = matcher3.end();
        }

        result.append(log.substring(lastEnd));

        System.out.println(result);
    }

    @Test
    void appendReplacement_테스트() {
        String text = "Price: 100, Discount: 20, Tax:";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);

        // 단순 치환의 경우에는 replaceAll 사용 권장
        // replaceAll()과 유사하지만, replaceAll()은 매칭된 부분을 단순하게 치환만 가능
        // appendReplacement()은 매칭된 부분에 커스텀 로직 추가 가능
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            int doubled = number * 2;

            // 매칭된 부분을 변경된 값으로 대체 가능
            matcher.appendReplacement(result, String.valueOf(doubled));
        }

        // 나머지 부분 추가
        matcher.appendTail(result);
        System.out.println(result);
    }

    @Test
    void test2() {
        //given
        String replacedText = Matcher.quoteReplacement(File.separator);
        System.out.println("replacedText = " + File.separator);
        System.out.println("replacedText = " + replacedText);
        //when

        //then
    }


    /**
     * 전화번호를 찾아서 하이픈 형식으로 변환
     * "01012345678" → "010-1234-5678"
     *
     * appendReplacement 사용
     */
    @Test
    void phoneFormatter(){
        String text = "연락처: 01012345678, 비상연락망: 01098765432";
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{4})(\\d{4})");
        Matcher matcher = pattern.matcher(text);

        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3)));
        }
        matcher.appendTail(sb);
        System.out.println("sb = " + sb);
    }

    /**
     * 텍스트에서 모든 가격을 찾아서 10% 인상
     * "$100" → "$110"
     *
     * appendReplacement 사용
     */
    @Test
    void priceIncreaser(){
        String text = "Item A: $100, Item B: $50, Total: $150";
        Pattern pattern = Pattern.compile("(\\$)(\\d+)");
        Matcher matcher = pattern.matcher(text);

        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {

            String currency = matcher.group(1);
            int amount = (int) (Integer.parseInt(matcher.group(2)) * 1.1);

            matcher.appendReplacement(sb, Matcher.quoteReplacement(currency + amount));
        }
        matcher.appendTail(sb);

        System.out.println(sb);
    }


    /**
     * 미리 정의된 문자 클래스
     */
    @Nested
    class PredefinedCharacterClasses {

        @Test
        void digitTest() {
            String text = "abc123def";

            // \d: 숫자 문자 [0-9]
            Pattern digitPattern = Pattern.compile("\\d+");
            Matcher digitMatcher = digitPattern.matcher(text);
            while(digitMatcher.find()) {
                System.out.println("digit group() = " + digitMatcher.group());
            }

            // \D: 숫자가 아닌 문자 [^0-9]
            Pattern nonDigitPattern = Pattern.compile("\\D+");
            Matcher nonDigitMatcher = nonDigitPattern.matcher(text);
            while(nonDigitMatcher.find()) {
                System.out.println("non digit group() = " + nonDigitMatcher.group());
            }
        }

        @Test
        void wordPatternTest() {
            String text = "hello_123 world!";

            // \w: 단어 문자 [a-zA-Z0-9_]
            Pattern wordPattern = Pattern.compile("\\w+");
            Matcher wordMatcher = wordPattern.matcher(text);
            while(wordMatcher.find()) {
                System.out.println("word group() = " + wordMatcher.group());
            }

            // \W: 단어 문자가 아닌 문자 [^a-zA-Z0-9_]
            Pattern nonWordPattern = Pattern.compile("\\W+");
            Matcher nonWordMatcher = nonWordPattern.matcher(text);
            while(nonWordMatcher.find()) {
                System.out.println("non word group() = " + nonWordMatcher.group());
            }
        }

        @Test
        void whiteSpaceTest(){

            String text = "Hello,\nWorld!\tThis is a test.";

            // \s: 공백 문자 [\t\n\r\f]
            Pattern spacePattern = Pattern.compile("\\s+");
            Matcher spaceMatcher = spacePattern.matcher(text);
            while(spaceMatcher.find()) {
                System.out.println("space group() = " + spaceMatcher.group().length());
            }

            // \S: 공백 문자가 아닌 문자 [^\t\n\r\f]
            Pattern nonSpacePattern = Pattern.compile("\\S+");
            Matcher nonSpaceMatcher = nonSpacePattern.matcher(text);
            while(nonSpaceMatcher.find()) {
                System.out.println("non space group() = " + nonSpaceMatcher.group());
            }
        }
    }


    /**
     * * + ? {n,m}
     */
    @Nested
    class QuantifierTest {

        @Test
        void exampleTest() {

            String html = "<div><div>Content 1</div><div>Content 2</div><div/>";

            // 탐욕적 수량자, 기호 없음
            Pattern.compile("<div>.*</div>")  // Greedy;
                    .matcher(html)
                    .results()
                    .forEach(m -> System.out.println("Greedy: " + m.group()));

            // 개으른 수량자, ?: 최소의 문자 덩어리만 일치
            Pattern.compile("<div>.*?</div>")  // Reluctant;
                    .matcher(html)
                    .results()
                    .forEach(m -> System.out.println("Reluctant: " + m.group()));

            // 소유적 수량자, +
            Pattern.compile("<div>.*+</div>")  // Possessive;
                    .matcher(html)
                    .results()
                    .forEach(m -> System.out.println("Possessive found: " + m.group()));
        }

        /**
         * 사용자명 규칙:
         * - 4-20자
         * - 영문 소문자, 숫자, 하이픈, 언더스코어만 허용
         * - 하이픈이나 언더스코어로 시작하거나 끝날 수 없음
         * - 연속된 하이픈이나 언더스코어 불가
         *
         * 힌트:
         * - ^[a-z0-9] : 소문자나 숫자로 시작
         * - [a-z0-9]$ : 소문자나 숫자로 끝
         * - (?!.*--) : 연속 하이픈 금지 (부정 전방탐색, 다음 주 배울 내용이지만 미리 써보세요)
         * - (?!.*__) : 연속 언더스코어 금지
         */
        @Test
        void usernameValidator(){

            // 통과해야 하는 케이스
            System.out.println(isValid("user123"));        // true
            System.out.println(isValid("user_name"));      // true
            System.out.println(isValid("user-123"));       // true
            System.out.println(isValid("test_user_01"));   // true

            // 실패해야 하는 케이스
            System.out.println(isValid("_user"));          // false (시작이 _)
            System.out.println(isValid("user_"));          // false (끝이 _)
            System.out.println(isValid("-user"));          // false (시작이 -)
            System.out.println(isValid("user-"));          // false (끝이 -)
            System.out.println(isValid("user__name"));     // false (연속 _)
            System.out.println(isValid("user--name"));     // false (연속 -)
            System.out.println(isValid("usr"));            // false (너무 짧음)
            System.out.println(isValid("a".repeat(21)));   // false (너무 김)
            System.out.println(isValid("User123"));        // false (대문자)
            System.out.println(isValid("user@name"));      // false (특수문자)
        }

        public boolean isValid(String username){

            Pattern lengthPattern = Pattern.compile("^.{4,20}$");
            Pattern stringPattern = Pattern.compile("^[a-z0-9].*[a-z0-9]$");
            Pattern notMatchPattern = Pattern.compile("(--|__)");

            if(!lengthPattern.matcher(username).matches()){
                return false;
            }

            if(!stringPattern.matcher(username).matches()) {
                return false;
            }

            if(notMatchPattern.matcher(username).find()) {
                return false;
            }

            return true;
        }

        @Test
        void HtmlContentExtractorTest() {

            String html = """
            <div>
                <p>First paragraph</p>
                <p>Second paragraph</p>
                <span>A span</span>
                <p>Third paragraph</p>
            </div>
            """;

            System.out.println(extractContents(html, "p"));
            System.out.println(extractContents(html, "span"));
            System.out.println(extractContents(html, "div"));
        }

        public List<String> extractContents(String html, String tag) {

            return Pattern.compile("<" + tag + ">(.*?)</" + tag + ">")
                    .matcher(html).results()
                    .map(x -> x.group())
                    .collect(Collectors.toList());
        }



        /**
         * 로그에서 각 레벨(ERROR, WARN, INFO, DEBUG)의 개수 세기
         *
         * 요구사항:
         * - [LEVEL] 형태의 로그 레벨 추출
         * - 각 레벨별 개수 카운트
         * - 대소문자 구분
         *
         * 힌트:
         * - \[(\w+)\] : 대괄호 안의 단어 캡처
         * - Map에 카운트 저장
         * - matcher.group(1)로 레벨명 추출
         */
        @Test
        void LogLevelCounterTest() {

            String log = """
            [2024-12-26 10:00:00] [ERROR] Connection failed
            [2024-12-26 10:00:01] [INFO] Retrying connection
            [2024-12-26 10:00:02] [ERROR] Connection timeout
            [2024-12-26 10:00:03] [WARN] Memory usage high
            [2024-12-26 10:00:04] [INFO] Connection established
            [2024-12-26 10:00:05] [DEBUG] Processing request
            [2024-12-26 10:00:06] [INFO] Request completed
            [2024-12-26 10:00:07] [ERROR] Database error
            [2024-12-26 10:00:08] [DEBUG] Closing connection
            """;

            Map<String, Integer> counts = countLevels(log);
            System.out.println("counts = " + counts);
        }
        public static Map<String, Integer> countLevels(String log) {
            // 여기에 코드 작성
            Pattern pattern = Pattern.compile("\\[(DEBUG|INFO|WARN|ERROR)\\]");
            Matcher matcher = pattern.matcher(log);

            Map<String, Integer> levelCounts = new HashMap<>();
            while(matcher.find()) {
                String level = matcher.group(1);
                levelCounts.put(level, levelCounts.getOrDefault(level, 0) + 1);
            }

            return levelCounts;
        }

    }

    /**
     * 캡쳐 그룹
     * - 정규표현식의 소괄호()를 사용하여 그룹핑한 부분을 캡쳐하여 추출 가능
     * - matcher.group(n)을 통해 각 그룹에 접근 가능
     * - (?: 패턴: 그룹 내에서 ?가 붙게되면 캡쳐 그룹에 포함되지 않음
     * (?:X)	X, as a non-capturing group
     * Groups beginning with (? are either pure, non-capturing groups that do not capture text and do not count towards the group total, or named-capturing group.
     */
    @Nested
    class CapturingGroupsTest {


        @Test
        @DisplayName("캡처 그룹 테스트")
        void capturingGroupTest() {
            String text = "2024-12-31";
            Pattern notGroupPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Matcher notGroupMatcher = notGroupPattern.matcher(text);

            if (notGroupMatcher.matches()) {
                System.out.println("groupCount() = " + notGroupMatcher.groupCount());
                System.out.println("group() = " + notGroupMatcher.group());
                System.out.println("group(0) = " + notGroupMatcher.group(0));
            }

            Pattern groupPattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
            Matcher groupMatcher = groupPattern.matcher(text);

            if (groupMatcher.matches()) {
                System.out.println("groupCount() = " + groupMatcher.groupCount());
                System.out.println("group() = " + groupMatcher.group());
                System.out.println("group(0) = " + groupMatcher.group(0));
                System.out.println("group(1) = " + groupMatcher.group(1));
                System.out.println("group(2) = " + groupMatcher.group(2));
                System.out.println("group(3) = " + groupMatcher.group(3));
            }

            String emailText = "user@example.com";
            Pattern emailPattern = Pattern.compile("([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})");
            Matcher emailMatcher = emailPattern.matcher(emailText);

            if (emailMatcher.matches()) {
                System.out.println("groupCount() = " + emailMatcher.groupCount());
                System.out.println("group() = " + emailMatcher.group());
                System.out.println("group(0) = " + emailMatcher.group(0));
                System.out.println("group(1) = " + emailMatcher.group(1));
                System.out.println("group(2) = " + emailMatcher.group(2));
                System.out.println("group(3) = " + emailMatcher.group(3));
            }
        }

        @Test
        @DisplayName("중첨된 캡쳐 그룹 테스트")
        void nestedCapturingGroupTest() {
            String text = "2025-12-31 12:11:30";
            Pattern pattern = Pattern.compile("((\\d{4})-(\\d{2})-(\\d{2})) ((\\d{2}):(\\d{2}):(\\d{2}))");
            Matcher matcher = pattern.matcher(text);

            if (matcher.matches()) {
                System.out.println("groupCount() = " + matcher.groupCount());
                System.out.println("group() = " + matcher.group());
                System.out.println("group(0) = " + matcher.group(0));
                System.out.println("group(1) = " + matcher.group(1)); // 전체 날짜
                System.out.println("group(2) = " + matcher.group(2)); // 연도
                System.out.println("group(3) = " + matcher.group(3)); // 월
                System.out.println("group(4) = " + matcher.group(4)); // 일
                System.out.println("group(5) = " + matcher.group(5)); // 전체 시간
                System.out.println("group(6) = " + matcher.group(6)); // 시
                System.out.println("group(7) = " + matcher.group(7)); // 분
                System.out.println("group(8) = " + matcher.group(8)); // 초
            }
        }

        @Test
        @DisplayName("캡쳐 그룹 Position 테스트")
        void capturingGroupPositionTest(){
            String text = "2024-12-31";
            Pattern pattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
            Matcher matcher = pattern.matcher(text);

            if(matcher.matches()) {
                System.out.println(String.format("group(0) = %s, start = %s, end = %s ", matcher.group(0), matcher.start(0), matcher.end(0)));
                System.out.println(String.format("group(0) = %s, start = %s, end = %s ", matcher.group(1), matcher.start(1), matcher.end(1)));
                System.out.println(String.format("group(0) = %s, start = %s, end = %s ", matcher.group(2), matcher.start(2), matcher.end(2)));
                System.out.println(String.format("group(0) = %s, start = %s, end = %s ", matcher.group(3), matcher.start(3), matcher.end(3)));

            }
        }

        @Test
        void urlParserTest() {
            String[] urls = {
                    "https://example.com",
                    "http://example.com:8080/api/users",
                    "https://example.com/path?key=value"
            };

            for (String url : urls) {
                System.out.println(UrlParser.parse(url));
            }

        }

        static class UrlParser {
            private static final Pattern URL_PATTERN = Pattern.compile("(https?)" +    // 프로토콜
                    "://" +
                    "([^:/]+)" + // 호스트
                    "(?::(\\d+))?" + // 포트 (선택적)
                    "(/[^?]*)?" + // 경로 (선택적)
                    "(\\?.*)?" // 쿼리 문자열 (선택적)
            );

            private String protocol;
            private String host;
            private String port;
            private String path;
            private String query;

            public static UrlParser parse(String url) {
                Matcher matcher = URL_PATTERN.matcher(url);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid URL: " + url);
                }

                UrlParser urlParser = new UrlParser();
                urlParser.protocol = matcher.group(1);
                urlParser.host = matcher.group(2);
                urlParser.port = matcher.group(3);
                urlParser.path = matcher.group(4);
                urlParser.query = matcher.group(5);

                return urlParser;
            }

            @Override
            public String toString() {
                return String.format("protocol=%s, host=%s, port=%s, path=%s, query=%s",
                        protocol, host, port, path, query);
            }
        }

        @Test
        void phoneNumberParserTest(){
            String[] phoneNumbers = {
                    "010-1234-5678",
                    "02-1234-5678",
                    "031-123-4567"
            };

            for (String phoneNumber : phoneNumbers) {
                System.out.println(PhoneNumberParser.parse(phoneNumber));
            }
        }

        static class PhoneNumberParser {
            private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(0\\d{1,2})-(\\d{3,4})-(\\d{4})");
            private String areaCode;
            private String middle;
            private String last;

            public static PhoneNumberParser parse(String phone){
                Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phone);

                if(!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid Phone Number: " + phone);
                }
                PhoneNumberParser phoneNumberParser = new PhoneNumberParser();
                phoneNumberParser.areaCode = matcher.group(1);
                phoneNumberParser.middle = matcher.group(2);
                phoneNumberParser.last = matcher.group(3);
                return phoneNumberParser;
            }

            @Override
            public String toString() {
                return String.format("%s-%s-%s", areaCode, middle, last);
            }
        }

        @Test
        void filePathParserTest(){

            String[] filePaths = {
                    "/home/user/document.txt",
                    "C:\\Users\\user\\image.png",
                    "/var/log/system.log"
            };

            for (String filePath : filePaths) {
                System.out.println(FilePathParser.parse(filePath));
            }
        }

        static class FilePathParser {

            private static final Pattern FILE_PATH_PATTERN = Pattern.compile(
                    "(.+)[/\\\\]" + // 디렉토리 경로
                    "([^/\\\\]+)" + // 파일 이름
                    "\\." +
                    "([^.]+)$" // 확장자
            );
            private String directory;
            private String filename;
            private String extension;

            public static  FilePathParser parse(String filePath){

                Matcher matcher = FILE_PATH_PATTERN.matcher(filePath);

                if(!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid File Path: " + filePath);
                }

                FilePathParser filePathParser = new FilePathParser();
                filePathParser.directory = matcher.group(1);
                filePathParser.filename = matcher.group(2);
                filePathParser.extension = matcher.group(3);
                return filePathParser;
            }

            @Override
            public String toString() {
                return String.format("directory=%s, filename=%s, extension=%s",
                        directory, filename, extension);
            }
        }
    }


    /**
     *
     * - named-capturing group
     * - 이름을 부여하여, 캡쳐 그룹에 이름을 통해서 접근 가능, 가독성 향상
     * - (?<이름>패턴)으로 정규식 작성시, matcher.group("이름") 으로 접근
     * - 이름 규칙: 대소문자 영어 및 숫자
     */
    @Nested
    class NamedCapturingGroupTest {

        @Test
        void namedGroupTest() {
            String email = "user@example.com";
            Pattern pattern = Pattern.compile("(?<local>[a-zA-Z0-9._%+-]+)@(?<domain>[a-zA-Z0-9.-]+)\\.(?<tld>[a-zA-Z]{2,})");
            Matcher matcher = pattern.matcher(email);

            if(matcher.matches()) {
                System.out.println("local = " + matcher.group("local"));
                System.out.println("domain = " + matcher.group("domain"));
                System.out.println("tld = " + matcher.group("tld"));
            }
        }

        @Test
        void urlParserTest() {
            String[] urls = {"https://example.com", "http://example.com:8080/api/users",
                    "https://example.com/search?q=java&lang=ko"};

            for(String url : urls) {
                System.out.println(UrlParser.parse(url));
            }
        }

        static class UrlParser {
            private static final Pattern URL_PATTERN = Pattern.compile("(?<protocol>https?)" +    // 프로토콜
                    "://" +
                    "(?<host>[^:/]+)" + // 호스트
                    "(?::(?<port>\\d+))?" + // 포트 (선택적)
                    "(?<path>/[^?]*)?" + // 경로 (선택적)
                    "(?<query>\\?.*)?" // 쿼리 문자열 (선택적)
            );

            private String protocol;
            private String host;
            private String port;
            private String path;
            private String query;

            public static UrlParser parse(String url) {
                Matcher matcher = URL_PATTERN.matcher(url);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid URL: " + url);
                }

                UrlParser urlParser = new UrlParser();
                urlParser.protocol = matcher.group("protocol");
                urlParser.host = matcher.group("host");
                urlParser.port = matcher.group("port");
                urlParser.path = matcher.group("path");
                urlParser.query = matcher.group("query");

                return urlParser;
            }

            @Override
            public String toString() {
                return String.format("protocol=%s, host=%s, port=%s, path=%s, query=%s",
                        protocol, host, port, path, query);
            }
        }


        @Test
        void httpParserTest() {
            String[] requests = {
                    "GET /api/users HTTP/1.1",
                    "POST /api/orders?status=pending HTTP/1.1",
                    "DELETE /api/users/123 HTTP/2.0"
            };

            for(String request : requests) {
                System.out.println(HttpParser.parse(request));
            }
        }

        static class HttpParser {

            private static final Pattern HTTP_LINE_PATTERN = Pattern.compile("(?<method>GET|POST|PUT|DELETE|HEAD|OPTIONS|PATCH)"
                    + "\\s+"
                    + "(?<path>/[^\s?]+)"
                    + "(?:\\?(?<query>[^\s]+))?"
                    + "\\s+"
                    + "HTTP/(?<version>[\\d.]+)"
            );
            private String method;
            private String path;
            private String query;
            private String version;

            public static HttpParser parse(String requestLine){

                Matcher matcher = HTTP_LINE_PATTERN.matcher(requestLine);
                if(!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid HTTP Request Line: " + requestLine);
                }

                HttpParser httpParser = new HttpParser();
                httpParser.method = matcher.group("method");
                httpParser.path = matcher.group("path");
                httpParser.query = matcher.group("query");
                httpParser.version = matcher.group("version");
                return httpParser;
            }


            @Override
            public String toString() {
                return String.format("method=%s, path=%s, query=%s, version=%s",
                        method, path, query, version);
            }
        }

        @Test
        void versionParserTest() {

            String[] versions = {
                    "1.2.3",
                    "2.0.0-beta",
                    "3.1.4-alpha.1"
            };

            for(String version : versions) {
                System.out.println(VersionParser.parse(version));
            }
        }

        static class VersionParser {
            private int major;
            private int minor;
            private int patch;
            private String preRelease;

            public static VersionParser parse(String version) {
                Pattern pattern = Pattern.compile("(?<major>\\d+)\\.(?<minor>\\d+)\\.(?<patch>\\d+)(?:-(?<preRelease>[0-9A-Za-z.-]+))?");
                Matcher matcher = pattern.matcher(version);

                if (!matcher.matches()) {
                    throw new IllegalArgumentException("Invalid version string: " + version);
                }

                VersionParser versionParser = new VersionParser();
                versionParser.major = Integer.parseInt(matcher.group("major"));
                versionParser.minor = Integer.parseInt(matcher.group("minor"));
                versionParser.patch = Integer.parseInt(matcher.group("patch"));
                versionParser.preRelease = matcher.group("preRelease");

                return versionParser;
            }


            @Override
            public String toString() {
                return String.format("major=%d, minor=%d, patch=%d, preRelease=%s",
                        major, minor, patch, preRelease);
            }
        }

    }


    /**
     * 백레퍼런스(Backreference)
     * - ()으로 그룹화된 패턴의 값을 다시 참조할 때 사용
     */
    @Nested
    class BackreferenceTest {


        @Test
        void backreferenceTest() {

            /**
             * (\w+): 그룹1 캡쳐
             * \s+: 공백 문자 1개 이상
             * \1: 그룹1과 동일한 내용이 다시 나와야 함 (백레퍼런스)
             */
            Pattern pattern = Pattern.compile("(\\w+)\\s+\\1");
            String text1 = "the the cat";
            String text2 = "the cat sat";

            Matcher matcher1 = pattern.matcher(text1);
            Matcher matcher2 = pattern.matcher(text2);

            System.out.println(matcher1.find());
            System.out.println(matcher2.find());

            /**
             * 여러 그룹 참조
             * (\w): 그룹1 캡쳐
             * (\w): 그룹2 캡쳐
             * \2: 그룹2와 동일한 문자
             * \1: 그룹1과 동일한 문자
             */
            Pattern pattern2 = Pattern.compile("(\\w)(\\w)\\2\\1");
            String[] texts = {"abba", "1221", "abcd", "aabb"};

            for(String text : texts) {
                System.out.println(pattern2.matcher(text).matches());
            }
        }

        @Test
        void findDuplicates() {
            // 대소문자 구분 없이 단어의 중복 찾기
            Pattern pattern = Pattern.compile("\\b(\\w+)\\s+\\1\\b", Pattern.CASE_INSENSITIVE);
            String text = "The the cat sat on the the mat mat";

            List<String> duplicates = new ArrayList<>();
            Matcher duplicateMatcher = pattern.matcher(text);

            // 중복된 단어 찾기
            while(duplicateMatcher.find()) {
                duplicates.add(duplicateMatcher.group(1));
            }
            System.out.println("duplicates = " + duplicates);

            // 중복 단어 제거
            String removedDuplicates = pattern.matcher(text)
                    .replaceAll("$1");
            System.out.println("removedDuplicates = " + removedDuplicates);
        }

        @Test
        void namedBackreferenceTest(){
            String text = "The the cat sat on the the mat mat";
            Pattern pattern = Pattern.compile("\\b(?<word>\\w+)\\s+\\k<word>\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            while(matcher.find()) {
                System.out.println("Found duplicate: " + matcher.group("word"));
            }

            String[] htmlTags = {
                    "<p>Hello</p>",           // 매칭
                    "<div>Content</div>",     // 매칭
                    "<span>Text</p>",         // 매칭 안됨 (태그 불일치)
                    "<h1>Title</h2>"          // 매칭 안됨 (태그 불일치)
            };
            Pattern htmlTagPattern = Pattern.compile("<(?<tag>\\w+)>.*?</\\k<tag>>",
                    Pattern.DOTALL);
            for(String html : htmlTags) {
                System.out.println("html tag match = " + htmlTagPattern.matcher(html).matches());
            }
        }

        @Test
        void replaceAllTest() {
            String text = "Jone Doe, Jane Smith, Bob Johnson";

            Pattern pattern = Pattern.compile("(\\w+)\\s+(\\w+)");
            String result = pattern.matcher(text)
                    .replaceAll("$2, $1");

            System.out.println("원본 = " + text);
            System.out.println("변환 = " + result);
        }

    }


    /**
     * 일치하는 패턴의 문자열을 찾지만, 해당 패턴 자체를 결과에 포함하지 않는 특수한 패턴
     *
     * 같은 위치에서 여러 조건을 체크
     * X(?=Y): X이후 Y가 오는 패턴 (긍정형 전방탐색)
     * X(?!Y): X이후 Y가 오지 않는 패턴 (부정형 전방탐색)
     * (?<=Y)X: Y 패턴이후 X가 오는 패턴 (긍정형 후방탐색)
     * (?<!Y)X: Y 패턴이 아닌 이후 X가 오지 않는 패턴 (부정형 후방탐색)
     * Lookahead: 접미사를 확인하는 용도
     * Lookbehind: 접두사를 확인하는 용도
     * (?=pattern) Positive Lookahead
     * (?!pattern) Negative Lookahead
     */
    @Nested
    class lookaheadAssertionsTest {


        /**
         *
         */
        @Test
        void lookaheadTest() {

            // Positive Lookahead 예제
            // 숫자와 "원"을 포함하는 패턴 찾기, "원"은 결과에 포함
            String text = "price: 100원, tax: 10원, total: 110원";

            Pattern pattern = Pattern.compile("\\d+원");
            Matcher matcher = pattern.matcher(text);

            while(matcher.find()) {
                System.out.println(matcher.group());
            }

            System.out.println();

            // 숫자와 "원"을 포함하는 패턴을 찾되, "원"은 결과에 포함하지 않음
            Pattern positiveLookaheadPattern = Pattern.compile("\\d+(?=원)");
            Matcher positiveLookaheadMatcher = positiveLookaheadPattern.matcher(text);

            while(positiveLookaheadMatcher.find()) {
                System.out.println(positiveLookaheadMatcher.group());
            }

            // Negative Lookahead 예제
            String negativeText = "I love Java and JavaScript but not Javadocs";

            // "Java" 다음에 "Script"가 오지 않는 패턴 찾기, "Java"이외의 문자열은 포함하지 않음
            Pattern negativeLookaheadPattern = Pattern.compile("Java(?!Script)");
            Matcher negativeLookaheadMatcher = negativeLookaheadPattern.matcher(negativeText);

            while(negativeLookaheadMatcher.find()) {
                System.out.println(negativeLookaheadMatcher.group());
            }
        }

        @Test
        void passwordValidTest() {
            Pattern passwordPattern = Pattern.compile(
                    "^"
                    + "(?=.*[a-z])" // 소문자 최소 1개
                    + "(?=.*[A-Z])" // 대문자 최소 1개
                    + "(?=.*\\d)"   // 숫자 최소 1개
                    + "(?=.*[@$!%*?&])" // 특수문자 최소 1
                    + "[A-Za-z\\d@$!%*?&]{8,20}" // 전체 길이 8-20자
                    + "$"
            );

            String[] passwords = {
                    "Pass123!",      // true
                    "password",      // false (대문자, 숫자, 특수문자 없음)
                    "PASSWORD123!",  // false (소문자 없음)
                    "Pass!",         // false (8자 미만, 숫자 없음)
                    "Pass1234"       // false (특수문자 없음)
            };

            for(String password : passwords) {
                System.out.println(password + ": " + passwordPattern.matcher(password)
                        .matches());
            }
        }

        @Test
        void domainRestrictedEmailValidatorTest() {


            String[] emails = {
                    "user@gmail.com",       // true
                    "user@naver.com",       // true
                    "user@kakao.com",       // true
                    "user@daum.net",        // false
                    "user@company.com"      // false
            };

            Pattern pattern = Pattern.compile("^.+@(gmail|naver|kakao)\\.com$");

            for(String email : emails) {
                System.out.println("email = " + email + ", matches = " + pattern.matcher(email)
                        .matches());
            }
        }

        @Test
        void safeFilenameValidatorTest() {
            String[] fileNames = {
                    "document.txt",         // true
                    "my-file_123.pdf",      // true
                    ".hidden",              // false (점으로 시작)
                    "file..txt",            // false (연속 점)
                    "noextension",          // false (확장자 없음)
                    "file.name.txt"         // true
            };

            Pattern noneArounDot = Pattern.compile("^(?!\\.).+(?!\\.)$");
            Pattern pattern = Pattern.compile("^(?!\\.)(?!.*\\.\\.)\\.(txt|jpg|pdf)$");


            for(String fileName : fileNames) {
                System.out.println("fileName = " + fileName + ", isValid = " + pattern.matcher(fileName)
                        .matches());
            }
        }
    }

    @Nested
    class lookbehindAssertionsTest {

        @Test
        void lookbehindTest() {

            String text = "price: $100, tax: $10, total: $110";
            Pattern noneLookBehindPattern = Pattern.compile("\\$\\d+");
            Matcher noneLookBehindMatcher = noneLookBehindPattern.matcher(text);

            while(noneLookBehindMatcher.find()) {
                System.out.println(noneLookBehindMatcher.group());
            }

            System.out.println();

            Pattern positiveLookBehindPattern = Pattern.compile("(?<=\\$)\\d+");
            Matcher positiveLookBehindMatcher = positiveLookBehindPattern.matcher(text);

            while(positiveLookBehindMatcher.find()) {
                System.out.println(positiveLookBehindMatcher.group());
            }

            System.out.println();
            String negativeLookbehindText = "file.txt image.png document.pdf script.js";

            Pattern negativeLookBehindPattern = Pattern.compile("(?<!file|image)\\.[a-z]{2,}");
            Matcher negativeLookBehindMatcher = negativeLookBehindPattern.matcher(negativeLookbehindText);

            while(negativeLookBehindMatcher.find()) {
                System.out.println(negativeLookBehindMatcher.group());
            }
        }
    }

    @Nested
    class BoundaryMatchersTest {

        /**
         * \b단어\b: 일치하는 독립단어
         * \B단어\B: 단어 중간의 일치하는 단어
         * \b단어 : 단어의 시작
         * 단어\b : 단어의 끝
         */
        @Test
        void boundaryTest() {

            /**
             * cat 단어만 찾고 싶지만, category에도 cat이 포함되어 있음
             */
            String text = "The cat and category";
            Pattern pattern = Pattern.compile("cat");
            Matcher matcher = pattern.matcher(text);

            while(matcher.find()) {
                System.out.println(matcher.group());
            }

            System.out.println();

            /**
             * \b: 단어 경계
             * 단어 경계를 통해서 "cat"단어만 찾기
             */
            Pattern boundaryPattern = Pattern.compile("\\bcat\\b");
            Matcher boundaryMatcher = boundaryPattern.matcher(text);

            while(boundaryMatcher.find()) {
                System.out.println(boundaryMatcher.group());
            }


            System.out.println();

            /**
             * \B: 단어 경계가 아닌 위치
             * 단어 중간의 "cat" 찾기를 통해서 "scatter" 단어에서만 "cat" 찾기
             */
            text = "thecat and category and scatter";
            Pattern noneBoundaryPattern = Pattern.compile("\\Bcat\\B");
            Matcher noneBoundaryMatcher = noneBoundaryPattern.matcher(text);

            while(noneBoundaryMatcher.find()) {
                System.out.println(noneBoundaryMatcher.group());
            }
        }


        /**
         * ^: 문자열의 시작, 라인의 시작
         * $: 문자열의 끝, 라인의 끝
         */
        @Test
        void lineBoundaryTest(){
            // ^: 문자열 시작
            Pattern p1 = Pattern.compile("^Hello");
            System.out.println(p1.matcher("Hello World").find());    // true
            System.out.println(p1.matcher("Say Hello").find());      // false

            // $: 문자열 끝
            Pattern p2 = Pattern.compile("World$");
            System.out.println(p2.matcher("Hello World").find());    // true
            System.out.println(p2.matcher("World Hello").find());    // false

            // ^ + $: 전체 매칭
            Pattern p3 = Pattern.compile("^Hello World$");
            System.out.println(p3.matcher("Hello World").matches());   // true
            System.out.println(p3.matcher("Hello World!").matches());  // false
        }


        /**
         * \A: 입력 문자열의 시작
         * \z: 입력 문자열의 끝
         * \Z: 입력 문자열의 끝(마지막 줄바꿈 문자 제외)
         */
        @Test
        void absoluteBoundaryTest(){
            String text = "Line1\nLine2\nLine3";

            Pattern p1 = Pattern.compile("\\ALine1");
            System.out.println(p1.matcher(text).find()); // true

            Pattern p2 = Pattern.compile("Line3\\z");
            System.out.println(p2.matcher(text).find()); // true

            String textWithNewline = "Line1\nLine2\nLine3\n";
            Pattern p3 = Pattern.compile("Line3\\Z");
            System.out.println(p3.matcher(textWithNewline).find()); // true

            Pattern p4 = Pattern.compile("Line3\\z");
            System.out.println(p4.matcher(textWithNewline).find()); // false
        }

        @Test
        void multilineTest() {
            String text = "Line1\nLine2\nLine3";

            // 기본모드: ^는 전체 문자열에서 시작만 체크
            Pattern p1 = Pattern.compile("^Line2");
            System.out.println(p1.matcher(text).find()); // false

            // 멀티라인 모드: ^가 각 라인의 시작을 체크
            Pattern p2 = Pattern.compile("^Line2", Pattern.MULTILINE);
            System.out.println(p2.matcher(text).find()); // true

            // 기본모드: $는 전체 문자열에서 끝만 체크
            Pattern p3 = Pattern.compile("Line2$");
            System.out.println(p3.matcher(text).find()); // false

            int a = 1 | 2;
            // 멀티라인 모드: $가 각 라인의 끝을 체크
            Pattern p4 = Pattern.compile("Line2$", Pattern.MULTILINE);
            System.out.println(p4.matcher(text).find()); // true

        }

        @Test
        void extractDomainTest() {
            String[] emails = {
                    "user@example.com",
                    "admin@company.co.kr",
                    "test@sub.domain.org"
            };

            String regex = "(?<=@)[^\\s@]+";
            Pattern pattern = Pattern.compile(regex);


            for(String email : emails) {

                Matcher matcher = pattern.matcher(email);
                if(matcher.find()) {
                    System.out.println("group = " + matcher.group());
                }
            }
        }

        public String extractDomain(String email) {
            Pattern pattern = Pattern.compile("(?<=@)[^\\s@]+");
            Matcher matcher = pattern.matcher(email);
            if(matcher.find()) {
                return matcher.group();
            }
            return null;
        }

        @Test
        void commentRemoverTest() {


            String code = """
            // This is a comment
            public class Test {
                // Another comment
                int value = 10; // inline comment (keep this)
                // More comments
            }
            """;


            System.out.println(this.removeLineComments(code));

        }

        public String removeLineComments(String code) {
            Pattern pattern = Pattern.compile("^*//.*$", Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(code);
            StringBuilder sb = new StringBuilder();
            while(matcher.find()) {
                matcher.appendReplacement(sb, "");
            }

            matcher.appendTail(sb);
            return sb.toString();
        }

        @Test
        void wordCountTest() {
            String text = "Java is great. I love Java programming. JavaScript is also Java-based.";

            // 2 (JavaScript, Java-based는 제외)
            System.out.println("'Java' 출현 횟수: " + countWord(text, "Java"));
            System.out.println("'is' 출현 횟수: " + countWord(text, "is"));
        }

        private int countWord(String text, String word) {

            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b",
                    Pattern.CASE_INSENSITIVE);

            Matcher matcher = pattern.matcher(text);

            int result = 0;
            while(matcher.find()) {
                result++;
            }

            return result;
        }

    }


    /**
     * 플래그: 정규식의 동작 방식을 제어
     */
    @Nested
    class PlagTest {

        @Test
        void caseInsensitiveTest(){
            String text = "Hello World, HELLO everyone, hello there";

            // 대소문자 구분 하여 "hello" 찾기
            Pattern p1 = Pattern.compile("hello");
            Matcher m1 = p1.matcher(text);

            while(m1.find()) {
                System.out.println(m1.group());
            }

            System.out.println();

            // 대소문자 구분 없이 "hello" 찾기
            Pattern p2 = Pattern.compile("hello", Pattern.CASE_INSENSITIVE);
            Matcher m2 = p2.matcher(text);

            while(m2.find()) {
                System.out.println(m2.group());
            }

            System.out.println();

            // (?i) 플래그를 사용하여 대소문자 구분 없이 "hello" 찾기
            Pattern p3 = Pattern.compile("(?i)hello");
            Matcher m3 = p3.matcher(text);

            while(m3.find()) {
                System.out.println(m3.group());
            }
        }

        @Test
        void multilineTest() {
            String text = "Line1\nLine2\nLine3";
            text = """
                    Line1
                    Line2
                    Line3
                    """;

            // 기본모드: ^ 테스트
            Pattern p1 = Pattern.compile("^Line2");
            Matcher m1 = p1.matcher(text);
            System.out.println(m1.find()); // false

            System.out.println();

            // 기본모드: ^ 테스트
            Pattern p2 = Pattern.compile("Line2$");
            Matcher m2 = p2.matcher(text);
            System.out.println(m2.find()); // false

            System.out.println();

            // 멀티라인 상황에서 ^ 테스트
            Pattern p3 = Pattern.compile("^Line2", Pattern.MULTILINE);
            Matcher m3 = p3.matcher(text);
            System.out.println(m3.find()); // true

            System.out.println();

            // 멀티라인 상황에서 $ 테스트
            Pattern p4 = Pattern.compile("Line2$", Pattern.MULTILINE);
            Matcher m4 = p4.matcher(text);
            System.out.println(m4.find()); // true

            System.out.println();

            // 모든 라인 출력
            Pattern p5 = Pattern.compile("^.*$", Pattern.MULTILINE);
            Matcher m5 = p5.matcher(text);
            while(m5.find()) {
                System.out.println(m5.group());
            }

            System.out.println();

            // 모든 라인 출력
            Pattern p6 = Pattern.compile("(?m)^.*$");
            Matcher m6 = p6.matcher(text);
            while(m6.find()) {
                System.out.println(m6.group());
            }
        }

        @Test
        void dotallTest() {
            String text = "Line1\nLine2\nLine3";

            // 기본모드: 정규표현식 .패턴은 줄바꿈 문자를 제외한 모든 문자와 매칭
            Pattern p1 = Pattern.compile("Line1.*Line3");
            Matcher m1 = p1.matcher(text);
            System.out.println(m1.find()); // false

            System.out.println();

            // DOTALL 모드: .이 개행 포함
            Pattern p2 = Pattern.compile("Line1.*Line3", Pattern.DOTALL);
            Matcher m2 = p2.matcher(text);
            System.out.println(m2.find()); // true

            Pattern p3 = Pattern.compile("(?s)Line1.*Line3");
            Matcher m3 = p3.matcher(text);
            System.out.println(m3.find()); // true

            String html = "<div>\n    <p>Hello World</p>\n</div>";
            Pattern p4 = Pattern.compile("<div>.*</div>", Pattern.DOTALL);
            Matcher matcher = p4.matcher(html);
            while(matcher.find()) {
                System.out.println(matcher.group());
            }
        }
    }

}
