package org.itmo.java.lesson1;

import java.util.Scanner;

public class Homework1 {
    public static void main(String[] args) {
//        1. Выведите строки в следующем порядке:
        System.out.println("Я");
        System.out.println("хорошо");
        System.out.println("знаю");
        System.out.println("Java.");

//        2. Посчитайте результат выражения
        double d = (46 + 10) * ((double) 10 / 3);
        System.out.println(d);
        int i = (29) * (4) * (-15);
        System.out.println(i);

//        3. В переменной number, лежит целое число 10500. В переменной result посчитайте следующее выражение: (number / 10) / 10. Результат выведите на консоль
        int number = 10500;
        double result0 = (double) number / 10 / 10;
        System.out.println("Задание 3: " + i);

//      4.  Даны три числа: 3.6, 4.1, 5.9. В переменной result посчитайте произведение этих чисел
        double result4 = (double) (3.6 + 4.1 + 5.9);
        System.out.println("Задание 4: " + result4);


//        5. В этой задаче вы должны считать целые числа из стандартного ввода, а затем вывести. Каждое целое число нужно печатать с новой строки. Формат ввода:
        Scanner input = new Scanner(System.in);
        System.out.println("Enter first number:");
        int number1 = input.nextInt();
        System.out.println("Enter second number:");
        int number2 = input.nextInt();
        System.out.println("Enter third number:");
        int number3 = input.nextInt();
        System.out.println(number1);
        System.out.println(number2);
        System.out.println(number3);

        //     6.
        System.out.println("Задание 6: Enter Number");
        int b = input.nextInt();
        if (b % 2 != 0) {
            System.out.println("Нечетное Odd");
        } else {
            if (b > 100) {
                System.out.println("Выход за пределы диапазона Out of bounds");
            } else {
                System.out.println("Четное Even");
            }
        }

    }
}