package org.itmo.java.lesson4;


import org.itmo.java.lesson2.Car;
import org.itmo.java.lesson2.Color;

import java.util.Arrays;

public class Array {
    public static void main(String[] args) {
        int[] intArray = new int[10];

        intArray[0] = 20;
        intArray[1] = 23;
        intArray[2] = 244;
        intArray[3] = 202;
        intArray[4] = 210;
        intArray[5] = 230;
        intArray[6] = 2330;


//        System.out.println(Arrays.toString(intArray));


        String[] strings = new String[5];
//        System.out.println(Arrays.toString(strings));


        long[] longs = new long[]{123L, 321L, 333L};
//        System.out.println(Arrays.toString(longs));

        Car[] cars = new Car[3];
        cars[0] = new Car("Lada", "Vesta", Color.GREEN, true);
        cars[1] = new Car("Lada", "Vesta", Color.BLACK, true);
        cars[2] = new Car("Lada", "Vesta", Color.BLACK, true);

//        System.out.println(Arrays.toString(cars));

//        System.out.println(cars[0].getBrand());


        for (int i = 0; i < longs.length; i++) {
//            System.out.println(longs[i]);
        }

        for (Car car : cars) {
            if(car.getColor() == Color.GREEN) {
                continue;
            }
//            System.out.println(car);
        }

        int x = 0;

//        do {
//            //logic
////            System.out.println(++x);
//        } while (x != 10);


//        while (x != 5) {
//            //logic
//            System.out.println(x++);
//            if(x == 3) {
//                break;
//            }
//
//            if(x == 2) {
//                continue;
//            }
//
//        }

        varargMethod("qwerty", "qwe", "asdasf");

        System.out.println(Arrays.toString(intArray));

        Arrays.sort(intArray);
        System.out.println(Arrays.toString(intArray));

        System.out.println(Arrays.binarySearch(intArray, 202));
        System.out.println(Arrays.binarySearch(intArray, 2500));



//        Collections.reverse(Arrays.asList(intArray));
//        System.out.println(Arrays.toString(intArray));

        // for revers order https://www.techiedelight.com/ru/sort-primitive-array-descending-order-in-java/

    }


    public static void varargMethod(String liner, String... line){
//        System.out.println(Arrays.toString(line));
    }
}