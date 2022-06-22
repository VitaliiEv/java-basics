package org.itmo.java.homework3;

class JavaProgram {
    public static void main(String[] args) {
//        Study class
        Study javaCourse = new Study("Изучение Java - это просто!");
        System.out.println(javaCourse.printCourse());

//        Car class
        Car defaultCar = new Car();
        defaultCar.setName("Лада 2109");
        defaultCar.setColor("Вишнёвый");
        defaultCar.setWeight(0.9);
        Car hyundai = new Car("Белый", 1.3);
        hyundai.setName("Hyundai Solaris II");
        System.out.println(defaultCar);
        System.out.println(hyundai);

//        House class
        House cottage = new House();
        cottage.setFloors(-100);
        cottage.setFloors(3);
        cottage.setName("Коттедж");
        cottage.setYear(2099);
        cottage.setYear(-100);
        cottage.setYear(2017);
        System.out.println(cottage + ", возраст (лет): " + cottage.getAge());

        House skyscraper = new House();
        skyscraper.setFloors(80);
        skyscraper.setName("Небоскреб");
        skyscraper.setYear(1997);
        System.out.println(skyscraper + ", возраст (лет): " + skyscraper.getAge());

//        Tree class
        Tree ivy = new Tree(5, true, "Ива");
        Tree oak = new Tree(100, "Дуб");
        Tree unknownTree = new Tree();
        System.out.println(ivy);
        System.out.println(oak);
        System.out.println(unknownTree);

        Tree maple = new Tree(-5, false, "Клён");
        System.out.println(maple);
        // why system err called in constructor printed to console after object destroyed but not after constructor finished?
        // why system err called in method printed to console after finished?
    }
}
