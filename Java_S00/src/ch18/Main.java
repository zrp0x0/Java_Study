package ch18;

import java.util.HashMap;

public class Main {
    
    public static void main(String[] args) {

        ClassName<Integer> className1 = new ClassName<>();
        ClassName<String> className2 = new ClassName<>();

        className1.set(1);
        System.out.println(className1.get());
        System.out.println(className1.getClass());
        System.out.println(className1.get().getClass());

        System.out.println("===========================");

        LimitClassName<Integer> limitClassName = new LimitClassName<>();
        limitClassName.set(1);
        System.out.println(limitClassName.get());
        System.out.println(limitClassName.getClass());
        System.out.println(limitClassName.getClass().getName());
        System.out.println(limitClassName.get().getClass());
        System.out.println(limitClassName.get().getClass().getName());

        // LimitClassName<String> limitClassName2 = new LimitClassName<>();

        System.out.println("===========================");

        Man<Freelancer> freelancerMan = new Man<>(new Freelancer());

        System.out.println("===========================");

        HashMap<Integer, String> hashMap = new HashMap<>(); // <K, V> <Integer, String>
        hashMap.put(1, "first");

    }

}
