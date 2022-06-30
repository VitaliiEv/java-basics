package org.itmo.java.homework6;

/**
 * В класс Человек добавьте:
 * 	поля: имя, фамилия;
 * 	метод для получение имени;
 * 	метод для получение фамилии;
 * 	абстрактный метод для вывода всей информации;
 * 	конструктор для установки значений.
 */
public abstract class Human {
    protected String name;
    public String surName;

    protected Human(String name, String surName) {
        if (name ==null || surName == null) {
            throw new IllegalArgumentException();
        } else {
            this.name = name;
            this.surName = surName;
        }
    }

     protected String getName() {
        return name;
    }

    protected String getSurName() {
        return surName;
    }

// Реализован через интерфейс, см. задание 2
//    protected abstract String getInfo();
}
