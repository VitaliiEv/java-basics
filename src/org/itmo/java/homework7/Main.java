package org.itmo.java.homework7;

public class Main {
    public static void main(String[] args) {
        Plane p = new Plane();
        Plane.Wing wingLeft = new Plane().createWing(12d);
        Plane.Wing wingRight = new Plane().new Wing(12.5d);
        Plane.Wing wingRear = p.new Wing(12.5d);
        System.out.println(wingLeft.getWeight());
        System.out.println(wingRight.getWeight());
        System.out.println(wingRear.getWeight());
    }
}
