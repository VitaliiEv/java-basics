package org.itmo.java.homework6;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        /* Задания 1 и 2 */
//        Customer badCustomer = new Customer("Василий", null);
        Customer customer = new Customer("Василий", "Васильев", "Тиньков");
        System.out.println(customer.getInfo());
        System.out.println(Arrays.toString(customer.getClass().getDeclaredFields()));
//        BankEmployee badBankEmployee = new BankEmployee("Иван", "Иванов", null);
        BankEmployee bankEmployee = new BankEmployee("Иван", "Иванов", "Сбер");
        System.out.println(bankEmployee.getInfo());

        /*
3.	Добавьте класс Грузовик, который будет наследовать все от класса Автомобиль.
В классе Грузовик создайте поля: количество колес; максимальный вес.
Также создайте метод newWheels, который устанавливает новое значение в поле «количество колес» и выводит его в консоль.
В класс Грузовик сделайте конструктор, устанавливающий все значения в конструктор главного класса и все значения в
 класс Грузовик.
        * */

        Car lada = new Car(900, "Lada", 't', 80f);
        lada.outPut();
        Truck truck = new Truck(9001, "MAN", 'w', 200f, 6, 30000);
//        Truck truck2 = new Truck(9001, "MAN", 'w', 200f, -6, -30000);
        truck.outPut();
        System.out.println(truck);
//        truck.newWheels(-1);
        truck.newWheels(8);
        truck.outPut();
        System.out.println(truck);

    /* 4.	Создайте два класса: главный и класс-наследник.
В главном классе: создайте поле для ввода целого числа;
В классе-наследнике: метод для вывода переменной из главного класса.
Создайте объект на основе класса наследника и выведите через метод переменную с главного класса. */
        NewIntegerChild newNumber = new NewIntegerChild();
        newNumber.printNewInteger();

            /*5.	Создайте главный класс. Добавьте в него: метод для получения возраста пользователя;
Создайте класс-наследник. Добавьте в него: переопределенный метод для получения имени пользователя;
Создайте объект на основе класса-наследника и используйте для него метод из класса.
Подсказка: подумайте над переменными, так как объект класса Scanner удобнее создать лишь один раз в главном классе */
        User user = new User("Петр", "Петров");
        user.setAge();
        user.setName("Иван");
        System.out.println(user);

        SuperUser superUser = new SuperUser("Коля", "Сидоров");
        superUser.setAge(); //User method
        superUser.setName(); //SuperUser method
        System.out.println(superUser);
    }




}
