package org.itmo.java.lesson3;

import java.util.SimpleTimeZone;

public class StaticExample {
    public static final String STATIC_CONSTANT = "some constant";
    private static String staticString;
    private final String nonStaticString;

    static {
        System.out.println("==================");
        System.out.println("static block start");
        staticString = "staticString";
        System.out.println(staticString);
        System.out.println("static block end");
        System.out.println("==================");
    }

     {
        System.out.println("==================");
        System.out.println("non static block start");
         nonStaticString = "nonStaticString";
        System.out.println(nonStaticString);
        System.out.println("non static block end");
        System.out.println("==================");
    }

    public static void printStaticVar() {
        System.out.println(staticString + "in a static metod");
//        StaticExample staticExample = new StaticExample();
//        staticExample.printNonStaticMethod();
    }

    public static void printNonStaticMethod() {
        printStaticVar();
        staticString = "123";
    }

    public static void main(String[] args) {
        StaticExample staticExample =  new StaticExample();
        staticExample.printNonStaticMethod();

        Integer integer = null;
        int i = integer.parseInt("123");
        System.out.println(integer);
        System.out.println(i);
    }
}
