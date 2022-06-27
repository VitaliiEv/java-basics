package org.itmo.java.homework4;

public class Homework4part1task1 {
//   Напишите программу, которая выводит на консоль нечетные числа от 1 до 99. Каждое число печатается с новой строки.
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            if (i % 2 == 1) {
                System.out.println(i);
            }
        }
    }
}
