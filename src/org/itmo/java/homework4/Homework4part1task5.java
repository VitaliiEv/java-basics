package org.itmo.java.homework4;

public class Homework4part1task5 {

    public static void main(String[] args) {

//     Напишите программу, чтобы проверить, появляется ли число 3 как первый или последний элемент массива целых чисел. Длина массива должна быть больше или равна двум.
        int[] array = {3, -3, 7, 4, 5, 4, 3};
        System.out.println(checkStartFinish(array, 3));
        int[] array1 = {4, -3, 7, 4, 5, 4, 3};
        System.out.println(checkStartFinish(array1, 3));
        int[] array2 = {3, -3, 7, 4, 5, 4, 4};
        System.out.println(checkStartFinish(array2, 3));
        int[] array3 = {4, -3, 7, 4, 5, 4, 4};
        System.out.println(checkStartFinish(array3, 3));
    }

    public static boolean checkStartFinish(int[] array, int number) {
        return array[0] == number || array[array.length-1] == number;
    }
}
