package org.itmo.java.homework3;

import java.net.HttpURLConnection;
import java.time.Year;

public class House {

    private int floors;
    private int year;
    private String name;

    public House() {
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        if (floors >0) {
            this.floors = floors;
        } else {
            System.err.println("Количество этажей не может быть менее 1");
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year <= Year.now().getValue()) {
            this.year = year;
        } else {
            System.err.println("Год не может быть больше текущего года");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return Year.now().getValue() - this.year;
    }

    @Override
    public String toString() {
        return name + ", " + year + "-го года посторойки, " + floors + " этажа(ей)";
    }

    public static void main(String[] args) {

    }
}
