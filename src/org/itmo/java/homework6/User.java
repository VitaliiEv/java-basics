package org.itmo.java.homework6;

import java.util.Scanner;

public class User extends Human {
    protected int age;
    // Scanner would be opened util destruction of object
    protected final Scanner SCANNER_INSTANCE = new Scanner(System.in);

    protected User(String name, String surName) {
        super(name, surName);
    }

    public void setAge() {
        System.out.printf("%s method. Enter your age: ", this.getClass().getSimpleName());
            int ageTemp = SCANNER_INSTANCE.nextInt();
            if (ageTemp < 0) {
                throw new IllegalArgumentException();
            } else {
                this.age = ageTemp;
            }
    }

    protected void setName(String name) {
        System.out.printf("%s method ", this.getClass().getSimpleName());
        if (name == null) {
            throw new IllegalArgumentException();
        } else {
            super.name = name;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + super.name + '\'' +
                ", surName='" + super.surName + '\'' +
                '}';
    }
}

