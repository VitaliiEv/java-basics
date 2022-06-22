package org.itmo.java.homework3;

public class Tree {

    private int age;
    private boolean isAlive;
    private String name;

    public Tree(int age, String name) {
        if (age >= 0) {
            this.age = age;
        } else {
            System.err.println("Возраст не может быть менее 0");
        }
        this.name = name;
    }

    public Tree(int age, boolean isAlive, String name) {
        if (age >= 0) {
            this.age = age;
        } else {
            System.err.println("Возраст не может быть менее 0");
        }
        this.isAlive = isAlive;
        this.name = name;
    }

    public Tree() {
        System.out.println("Пустой конструктор без параметров сработал");
    }

    @Override
    public String toString() {
        return "Tree{" +
                "age=" + age +
                ", isAlive=" + isAlive +
                ", name='" + name + '\'' +
                '}';
    }
}
