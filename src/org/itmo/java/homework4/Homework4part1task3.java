package org.itmo.java.homework4;

import java.util.Scanner;

public class Homework4part1task3 {

    public static void main(String[] args) {
//        Напишите программу, чтобы вычислить сумму двух целых чисел и вернуть true, если сумма равна третьему целому числу.
        Scanner userInput = new Scanner(System.in);
        System.out.print("Введите первое число: ");
        int a = userInput.nextInt();
        System.out.print("Введите второе число: ");
        int b = userInput.nextInt();
        System.out.print("Введите третье число: ");
        int c = userInput.nextInt();
        userInput.close();
        System.out.print("Результат: " + (a + b == c));

    }

}
