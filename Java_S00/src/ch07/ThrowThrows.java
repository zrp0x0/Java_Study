package ch07;

public class ThrowThrows {
    
    public ThrowThrows() {
        
        try {
            test1();
        } catch (Exception e) {
            System.out.println("test1 Exception");
        }

        try {
            test2();
        } catch (RuntimeException e) {
            System.out.println("test2 outer RuntimeException");
        }

    }

    private void test1() throws Exception {
        throw new Exception();
    }

    private void test2() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("test2 inner RuntimeException");
        }
    }

    public static void main(String[] args) {
        new ThrowThrows();
    }

}
