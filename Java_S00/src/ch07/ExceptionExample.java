package ch07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// 사용자 정의 예외 (Unchecked)
class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String message) {
        super(message);
    }
}

public class ExceptionExample {

    public static void main(String[] args) {
        ExceptionExample example = new ExceptionExample();
        
        try {
            example.processUser(19);
            example.readFile("non_existent_file.txt");
        } catch (InvalidAgeException e) {
            System.err.println("비즈니스 로직 에러: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("시스템 파일 에러: " + e.getMessage());
        }
    }

    // 1. throw & Custom Exception 사용
    public void processUser(int age) {
        if (age < 19) {
            // 인위적으로 Unchecked 예외 발생 (throws 생략 가능)
            throw new InvalidAgeException("미성년자는 가입할 수 없습니다.");
        }
    }

    // 2. try-with-resources & throws (Checked Exception)
    public void readFile(String path) throws IOException {
        // try(...) 안에 선언하면 finally에서 close()를 호출할 필요가 없음
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            System.out.println(br.readLine());
        }
        // IOException은 Checked Exception이므로 반드시 throws하거나 catch해야 함
    }
}
