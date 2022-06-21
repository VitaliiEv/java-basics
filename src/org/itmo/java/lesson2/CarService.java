package org.itmo.java.lesson2;

public class CarService {
    public static long totalPrice(Car car, Integer servicePrice) {
        if(servicePrice == null) {
             return 0;
            } else {
                return car.getPrice() + servicePrice;
            }
    }
}
