package ch11;

// @FunctionalInterface // 구현해야할 추상 메소드가 한개인 인터페이스
interface Print {
    void print(int a, int b);
}

class Test {
    public void testMethod(Print print) {
        print.print(1, 2);
        System.out.println("콘솔 출력 실행문");
    }
}

public class LamdaExample1 {
    
    public static void main(String[] args) {
        Test noLamda = new Test();
        noLamda.testMethod(new Print() { // 흠,, 인터페이스로 객체 생성을 할 수 없는거 아닌가?
            @Override
            public void print(int a, int b) {
                System.out.println("a와 b의 합은 " + (a + b));
                System.out.println("a와 b의 차는 " + (a - b));
            }
        });

        Test lamdaTest = new Test();
        lamdaTest.testMethod((a, b) -> {
            System.out.println("a와 b의 합은 " + (a + b));
            System.out.println("a와 b의 차는 " + (a - b));
        });
    }

}
