package org.itmo.java.homework6;

/**
 * В класс Работник банка добавьте:
 * наследование класса Человек;
 * реализация функции для вывода информации;
 * строковое поле «название банка»;
 * конструктор для установки всех значений;
 */
public class BankEmployee extends Human implements GetInfo {

    private String bankName;

    protected BankEmployee(String name, String surName, String bankName) {
        super(name, surName);
        if (bankName == null) {
            throw new IllegalArgumentException();
        } else {
            this.bankName = bankName;
        }
    }

    @Override
    public String getInfo() {
        return "Employee of " + this.bankName + ": " + super.name + " " + super.surName + ".";
    }
}
