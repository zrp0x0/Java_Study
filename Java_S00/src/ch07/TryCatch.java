package ch07;

import java.util.Scanner;

public class TryCatch {

    public TryCatch() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next(); // String으로 문자열 받음

        try {
            int changedInput = Integer.parseInt(input); // 숫자로 바꿀 때, 숫자가 아니면 에러가 발생함 / RuntimeException
            System.out.println("숫자: " + changedInput);
        } catch (NumberFormatException e) {
            System.out.println("변경되지 못한 값: " + input);
        } finally {
            System.out.println("finally 구문");
        }

    }
    
    public static void main(String[] args) {
        new TryCatch();
    }

}
