package org.itmo.java.homework4;

public class Homework4part1task6 {

    public static void main(String[] args) {

//   Напишите программу, чтобы проверить, что массив содержит число 1 или 3.
        int[] array = {3, -3, 7, 4, 5, 4, 3};
        System.out.println(checkTwoNumbers(array, 1,3));
        int[] array1 = {4, -3, 7, 4, 5, 4, 3};
        System.out.println(checkTwoNumbers(array1, 1,3));
        int[] array2 = {3, -3, 7, 4, 5, 4, 4};
        System.out.println(checkTwoNumbers(array2, 1,3));
        int[] array3 = {4, -3, 7, 4, 5, 4, 4};
        System.out.println(checkTwoNumbers(array3, 1,3));
    }

    public static boolean checkTwoNumbers(int[] array, int number1, int number2) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] ==number1 || array[i] ==number2) {
                return true;
            }
        }
        return false;
    }
}
