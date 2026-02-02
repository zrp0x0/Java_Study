# Ch07. 예외 처리


### 오류와 예외
- 오류 (Error)
    - 오류는 시스템 레벨에서 프로그램에 심각한 문제를 야기하여 실행 중인 프로그램을 종료
    - 개발자가 미리 예측하여 처리할 수 없는 것이 대부분
    - 오류에 대한 처리는 불가함

- 예외 (Exception)
    - 예외는 오류와 같이 실행 중인 프로그램을 비정상적으로 종료 시킴
    - 일반적으로 개발자가 구현한 로직에서 발생
    - 개발자가 예외가 발생할 수 있는 상황을 예측하여 조치할 수 있음
    - **잘못될 상황을 예측하지 못할 경우 주로 발생함**


### 예외처리란
- 컴파일 에러 (Compile Error)
    - 문법적인 오류나 문맥이 맞지 않는 코드를 작성하여 컴파일 단계에서 발생하는 에러
    - 프로그램이 실행되지 못하고 종료되는 현상이 발생

- 런타임 에러 (Runtime Error)
    - 프로그램이 작동하면서 예기치 않은 상황에 의해 발생하는 에러
    - 프로그램이 오작동하거나 종료되는 현상이 발생


### 예외 클래스
- Error
    - IOError

- Exception
    - Checked Exception
        - IOException
        - ...
        
    - Unchecked Exception
        - RuntimeException
            - NullPointException

- 모든 예외 클래스는 Throwable 클래스를 상속 받고 있음
- Exception은 수많은 자식 클래스가 있음
- Runtime Exception은 Unchecked Exception이며, 그 외 Exception은 Checked Exception으로 볼 수 있음


### Checked Exception vs Unchecked Exception
- 반드시 예외 처리 필요 | 명시적 처리 강제하지 않음
- 컴파일 단계 | 실행 중 단계
- 롤백하지 않음 | 롤백함 - 이건 스프링 프레임워크의 @Transcational의 기본 설정 / @Transactional(rollbackFor = Exception.class) 추가 설정 가능
- IOException / SQLException | NullPointerException / Illegal ArgumentException / IndexOutOfBoundException / SystemException


### try - catch - finally
- 하나의 Exception이 너무 많은 걸 가지고 있지 않는 것이 좋음
```java
try {
    // 예외가 발생할 것으로 예상되는 코드
} catch (ExceptionClass1 e1) {
    // 예외 클래스1에 해당될 경우 실행되는 코드
} catch (ExceptionClass2 | ExceptionClass3 e2) {
    // 예외 클래스2 또는 예외 클래스3에 해당될 경우 실행되는 코드
} finally { 
    // try - catch 구분 종료 후 무조건 실행되는 코드
    // 예외와 상관없이 무조건 실행되는 블록
    // 필수적으로 사용해야하는 블록은 아님, 필요한 경우에만 사용하면 됨
}
```


### throws
- 예외 처리 방식의 또 다른 방법으로 발생된 예외 상황을 호출한 지점으로 리턴하는 방식
- 이런 예외 처리 방식은 한 메소드에서 예외 처리하기 위해 사용됨
- 예외 타입이 여러 개 발생할 것으로 예상될 경우 콤마를 사용하여 복수 입력 가능함
```java
public void method1() throws 예외클래스1, 예외클래스2 {

}
```


### throw
- 예외 상황을 인위적으로 발생시키기 위해 throw 키워드를 사용


### 실습
- TryCatch.java
- ThrowThrows.java
- 보통 예외처리는 내부에서 최대한 해결하고 
- 예외처리를 못하는 경우 로그를 찍도록 처리를 함


### 추가 내용
```java
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
            example.processUser(15);
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
```
- try-with-resources: 블록이 끝날 때 파일이나 DB 연결 닫기 같은 걸 자동으로 수행해줌



--- 
# ch11. 람다 함수 (람다식)

### 람다 함수란?
- 람다 함수란 프로그래밍 언어에서 사용되는 개념으로 "익명 함수"를 지칭하는 용어
- 수학에서 사용하는 함수를 보다 단순하게 표현하는 법(함수를 변수처럼 사용)
- Java 8부터 기능이 포함됨


### 람다의 특징
- 이름이 필요 없음 - 익명 함수
- 파라미터가 있는 함수는 괄호 안에 지정하여 사용


### 람다식의 장점
- 코드의 라인 수가 줄어듬
    - 메소드로 표현된 코드에 비해 확연히 라인 수가 줄어듦

- 병렬 프로그래밍이 가능
    - iteration 방식은 반복 대상을 일일히 루프에서 지정하는 반면에
    - 함수형 프로그래밍에서는 반복 대상을 사용자 코드에서 직접 지정하지 않음

- 람다식으로 바로 실행문을 전달할 수 있음
    - 자바 메소드로 값이나 객체를 생성하여 전달하던 예전 방식과 달리
    - 람다식에서는 실행문 자체를 람다식으로 전달하여 구현

- 의도의 명확성
    - 가독성이 높음


### 람다식의 단점
- 재사용 불가
    - 일외용 함수 정의가 목적

- 불필요하게 너무 사용하게 되면 가독성 떨어짐
    - 같은 기능의 함수를 여러 번 정의하는 상황이 발생할 수 있음


### 람다식의 표현
- 람다식은 화살표(->)를 사용
    - (매개변수) -> {함수 구현부}

- 매개 변수가 하나일 경우 매개 변수 생략 가능
    - () -> {}

- 작성할 실행문이 단일일 경우 괄호({}) 생략 가능
- 단, return 식의 단일 실행문의 경우 괄호 생략 불가


### 실습
```java
// LamdaExample1.java
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
```

```java
// LamdaExample2.java
package ch11;

@FunctionalInterface
interface BigNumber {
    int getBigNumber(int num1, int num2);
}

public class LamdaExample2 {
    
    public static void main(String[] args) {
        
        BigNumber bigNumber = (x, y) -> {
            if (x > y) {
                return x;
            } else {
                return y;
            }
        };

        int result = bigNumber.getBigNumber(2156, 12382);
        System.out.println(result);

    }

}
```

```java
// JavaFunction.java
package ch11;

import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

public class JavaFunction {

    public static void main(String[] args) {
        // int 값을 매개변수로 받아 함수를 생성
        IntFunction intSum = (a) -> a + 10;

        System.out.println(intSum.apply(5));

        // 동일한 타입의 두 값을 받아 연산을 처리
        BinaryOperator binarySum = (a, b) -> a + " " + b;

        System.out.println(binarySum.apply(1, 2));
        System.out.println(binarySum.apply("Hello", "World"));
    }

}
```



---
# ch13. 자바 메모리 구조


### JVM이란?
- Java Virtual Machine의 줄임말
- 자바 어플리케이션을 어느 CPU나 OS에서도 실행할 수 있게 지원하는 역할을 수행
- 자바 코드를 컴파일하여 바이트 코드로 변환하여 해당 운영체제가 이해할 수 있는 기계어로 실행
- JVM의 구성은 크게 4가지로 구분됨
    - Class Loader
    - Execution Engine
    - Garbage Collector
    - Runtime Data Area


### JVM 구조
- class Loader
- Runtime Data Area
    - Method Area
    - Heap Area
    - Stack Area
    - PC Register
    - Native Method Stack

- Execution Engine <-> Native Method Interface <-> Native Method Libraries


### 자바 어플리케이션 실행 과정
- 1. 어플리케이션이 실행되면 JVM이 OS로부터 메모리를 할당 받음
    - JVM은 할당 받은 메모리를 용도에 따라 영역을 구분하여 관리

- 2. 자바 컴파일러(javac.ext)가 자바 소스코드(.java)를 읽어 바이트 코드(.class)로 변환
- 3. Class Loader를 통해 바이트 코드를 JVM으로 로딩
- 4. 로딩된 바이트 코드는 Execution Engine을 통해 해석됨
- 5. 해석된 바이트 코드는 Runtime Data Areas에 배치되어 실행됨
    - 실행되는 과정에서 GC 같은 작업이 수행됨


### Execution Engine
- Runtime Data Area에 할당된 바이트 코드를 실행시키는 주체
- 코드를 실행하는 방식은 크게 2가지 방식이 존재
    - Interpreter
        - 바이트 코드를 해석하여 실행하는 역할을 수행
        - 다만 같은 메소드라도 여러 번 호출될 때 매번 새로 수행해야함

    - JIT(Just In Time) Compiler
        - Interpreter의 단점을 해소
        - 반복되는 코드를 발견하여 전체 바이트 코드를 컴파일하고 그것을 Native Code로 변경하여 사용

    - Garbage Collector
        - 더 이상 참조되지 않는 메뢰 객체를 모아 제거하는 역할을 수행
        - 일반적으로 자동으로 실행되지만, 수동으로 실행하기 위해 'System.gc()'를 사용할 수 있음(다만, 실행이 보장되지는 않음)
        - 나중에 퍼포먼스 튜닝을 하는데 많이 사용함!!

    - Native의 의미: 자바에서 부모가 되는 C/C++/Assembly를 의미


### Garbage Collector
- 앞으로 사용되지 않는 객체의 메모리를 Garbage라고 부름
- 이런 Garbage를 정해진 스케줄에 의해 정리해주는 것을 GC라고 부름

- Stop The World
    - GC를 수행하기 위해 JVM이 멈추는 현상을 의미
    - GC가 작동하는 동안 GC 관련 Thread를 제외한 모든 Thread는 멈춤
    - 일반적으로 "튜닝"이라는 것은 이 시간을 최소화하는 것을 의미함

- GC의 종류
    - Serial GC
    - Parallel GC: Java 8
    - CMS GC: 
    - G1 GC: Java 9 10 
    - Z GC: Java 11 이후 실험적
    - 뭐가 좋다는 없음


### Class Loader
- .class(바이트 코드)들을 Class Loader로 이동
- Loding -> Linking -> Initialization -> |Runtime Data Area|

- JVM으로 바이트 코드를 로드하고, 링크를 통해 배치하는 작업을 수행하는 모듈
- 로드된 바이트 코드들을 엮어서 JVM의 메모리 영역인 Runtime Data Areas에 배치함
- 클래스를 메모리에 올리는 로딩 기능은 한 번에 메모리에 올리지 않고, 어플리케이션에서 필요한 경우 동적으로 메모리에 적재하게 됨

- 클래스 파일의 로딩은 3단계로 구성됨
    - Loading -> Linking -> Initialization


### Runtime Data Area
- 어플리케이션이 동작하기 위해 OS에서 할당받은 메모리 공간을 의미
- 크게 5가지로 구성되어 있음
    - Method Area
    - Heap Area
    - Stack Area
    - PC Register
    - Native Method Stack


### Method Area
- static으로 선언된 변수들을 포함하여 Class 레벨의 모든 데이터가 이곳에 저장됨
- JVM마다 단 하나의 Method Area가 존재
- Method Area에는 Runtime Constant Pool이라는 별도의 영역이 존재
    - 상수 자료형을 저장하여 참조하는 역할

- 저장되는 정보의 종류
    - Field Info: 멤버 변수의 이름, 데이터 타입, 접근 제어자의 정보
    - Method Info: 메소드 이름, Return 타입, 매개변수, 접근 제어자의 정보
    - Type Info: Class인지 Interface인지 여부 저장, Type의 속성, 이름, Super Class의 이름

- Heap과 마찬가지로 GC 관리 대상임


### Heap Area (Java 8)
- 객체를 저장하기 위한 메모리 영역
- new 연산자로 생성된 모든 Object와 Instance 변수, 그리고 배열을 저장
- Heap 영역은 물리적으로 두 영역으로 구분할 수 있음
    - Young Generation: 생명 주기가 짧은 객체를 GC 대상으로 하는 영역
        - Eden에 할당 후 Survivor 0과 1을 거쳐 오래 사용되는 객체를 Old Generation으로 이동시킴
    - Old Generation: 생명 주기가 긴 객체를 GC 대상으로 하는 영역

- Garbage Collection 생명 주기에 의해 지속적으로 메모리가 정리됨
    - Minor GC
    - Major GC

- **Method Area와 Heap Area는 여러 스레드들 간에 공유되는 메모리**


### Stack Area
- 각 스레드를 위한 분리된 Runtime Stack 영역
- 메소드를 호출할 때마다 Stack Frame으로 불리는 Entry가 Stack Area에 생성됨
- 스레드의 역할이 종료되면 바로 소멸되는 특정의 데이터를 저장
- 각종 형태의 변수나 임시 데이터, 스레드 또는 메소드의 정보를 저장


### PC Register
- Program Counter
- 각 Thread가 시작될 때 생성되며, 현재 실행중인 상태 정보를 저장하는 영역
- Thread가 로직을 처리하면서 지속적으로 갱신됨
- Thread가 생성될 때마다 하나씩 존재함
- 어떤 명령을 실행해야 할지에 대한 기록(현재 수행중인 부분의 주소를 가짐)


### Native Method Stack
- 바이트 코드가 아닌 실제 실행할 수 있는 기계어로 작성된 프로그램을 실행 시키는 영역
- 또한 Java가 아닌 다른 언어로 작성된 코드를 위한 영역
- Java Native Interface를 통해 바이트 코드로 전환하여 저장
- 각 스레드 별로 생성됨


### JNI(Java Native Interface)
- 자바가 다른 언어로 만들어진 어플리케이션과 상호 작용할 수 있는 인터페이스를 제공
- JWM이 Native Method를 적재하고 수행할 수 있도록 함
- 실질적으로 제대로 동작하는 언어는 C/C++



---
# ch17. Garbage Collection
- 일단 이해보단 그냥 들어보자.


### 
- System.gc(): 보장이 되지 않고, 성능을 떨어뜨릴 수 있기 때문에 사용을 권장하지 않음
- 생각보다 규모가 큰 프로젝트가 아니면 체감을 하기 쉽지 않음
- CMS-GC를 가장 많이 사용함
- 더 깊은 내용을 원하면 공부해야지



---
# ch18. Generic


### Generic이란?
- 데이터 형식에 의존하지 않고, 여러 데이터 타입을 가질 수 있도록 하는 것을 의미
- 어떤 자료구조를 작성할 경우, 여러 타입을 지원하고 싶을 때 제네릭 형식으로 작성하여 설계
- 대표적인 제네릭 활용 예시로 Collection이 있음
```java
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable { }

public interface Collection<E> extends Iterable<E> { }
```
- 위 예시와 같이 꺽쇠를 활용하여 타입을 선언할 수 있게 선언되어 있음
- 이런 객체는 사용하기 위해 선언되면서 타입이 지정됨


### 제네릭의 장점
- 타입만 다르고 코드의 내용이 비슷할 경우 제네릭을 활용하여 코드의 재사용성을 높일 수 있음
- 컴파일 단계에서 타입을 체크하기 때문에 에러를 사전에 방지할 수 있음


### 제네릭 사용 방법
- 대표적인 제네릭 타입은 아래와 같음
- T : Type
- E | Element
- K | Key
- V | Value
- N | Number

- 근데 대중적으로 저렇게 많이 사용하는 거지, 반드시 따라야할 필요는 없음


### 실습
```java
public class LimitClassName<E extends Number> // Number를 상속한 객체만 사용가능해짐
```
