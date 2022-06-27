package org.itmo.java.homework4;

import java.util.Scanner;

public class Homework4part1task4 {

    public static void main(String[] args) {
//   Напишите программу, которая принимает от пользователя три целых числа и возвращает true, если второе число больше первого числа, а третье число больше второго числа
        Scanner userInput = new Scanner(System.in);
        System.out.print("Введите первое число: ");
        int a = userInput.nextInt();
        System.out.print("Введите второе число: ");
        int b = userInput.nextInt();
        System.out.print("Введите третье число: ");
        int c = userInput.nextInt();
        userInput.close();
        System.out.print("Результат: " + ((b > a) && (c > b)));
    }

}
