package org.itmo.java.homework4;

import java.util.Arrays;
import java.util.Scanner;

public class Homework4part2 {

    public static void main(String[] args) {
//   Напишите программу, которая проверяет отсортирован ли массив по возрастанию. Если он отсортирован по возрастанию
//   то выводится “OK”, если нет, то будет выводиться текст “Please, try again”
        int[] array = {3, -3, 7, 4, 5, 4, 3};
        isSorted(array);
//      Напишите программу, которая считывает с клавиатуры длину массива (например, пользователь вводит цифру 4),
//      затем пользователь вводит 4 числа и на новой строке выводится массив из 4 элементов. Пример вывода:
        int[] array2 = arrayBuild();
        isSorted(array2);

//      Напишите метод, который меняет местами первый и последний элемент массива. Пример вывода:
        switchFirstLast(array2);

//      Дан массив чисел. Найдите первое уникальное в этом массиве число.
//      Например, для массива [1, 2, 3, 1, 2, 4] это число 3.
        int[] array3 = {-3, -3, 7, 4, 7, 4, 3};
        System.out.println(Arrays.toString(array3));
        firstUnique(array3);

    }

    public static void isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                System.out.println("Please, try again");
                break;
            }
        }
        System.out.println("OK");
    }

    public static int[] arrayBuild() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter array length: ");
        int a = userInput.nextInt();
        System.out.println("Enter numbers of array: ");
        int[] array = new int[a];
        for (int i = 0; i < array.length; i++) {
            array[i] = userInput.nextInt();
        }
        System.out.println("Result: " + Arrays.toString(array));
        return array;
    }

    public static void switchFirstLast(int[] array) {
        int temp;
        System.out.println(Arrays.toString(array));
        temp = array[0];
        array[0] = array[array.length - 1];
        array[array.length - 1] = temp;
        System.out.println(Arrays.toString(array));
    }

    public static void firstUnique(int[] array) {
        int unique = -1;
        int index = -1;
        for (int i = 0; i < array.length-1; i++) {
            for (int j = i+1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    break;
                } else if (j == array.length - 1) {
                    unique = array[i];
                    index = i;
                    break;
                }
            }
            if (unique != -1) {
                break;
            }
        }
        if (unique == -1)
            System.out.println("No unique");
        else {
            System.out.println("Unique number " + unique + " at position " + index);
        }

    }
}
