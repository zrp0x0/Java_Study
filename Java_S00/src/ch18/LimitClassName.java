package ch18;

public class LimitClassName<E extends Number> { // Number 클래스를 상속 받고 있는 클래스만 사용할 수 있다는 의미
    
    private E element;

    public E get() {
        return element;
    }

    public void set(E element) {
        this.element = element;
    }

}
