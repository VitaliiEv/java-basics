package org.itmo.java.homework2;

public class Calculator {

    public static double summ(int a, int b) {
        return a+b;
    }

    public static double summ(long a, long b) {
        return a + b;
    }

    public static double summ(double a, double b) {
        return a + b;
    }

    public static double substract(int a, int b) {
        return a - b;
    }

    public static double substract(long a, long b) {
        return a - b;
    }

    public static double substract(double a, double b) {
        return a - b;
    }

    public static double multiply(int a, int b) {
        return a * b;
    }

    public static double multiply(long a, long b) {
        return a * b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }


    public static double divide(int a, int b) {
        return (double) (a / b);
    }

    public static double divide(long a, long b) {
        return (double) (a / b);
    }

    public static double divide(double a, double b) {
        return (double) (a / b);
    }

    public static void main(String[] args) {
        int a = 100;
        int b = 5345;
        System.out.println(summ(a, b));
        System.out.println(substract(a, b));
        System.out.println(multiply(a, b));
        System.out.println(divide(a, b));

        long c = -200;
        long d = 3457567546765L;
        System.out.println(summ(c, d));
        System.out.println(substract(c, d));
        System.out.println(multiply(c, d));
        System.out.println(divide(c, d));

        double e = 45345345345.44d;
        double f = 0d;
        System.out.println(summ(e, f));
        System.out.println(substract(e, f));
        System.out.println(multiply(e, f));
        System.out.println(divide(e, f));
    }
}
