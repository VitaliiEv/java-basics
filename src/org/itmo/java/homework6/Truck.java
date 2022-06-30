package org.itmo.java.homework6;

public class Truck extends Car {
    private int wheels;
    // по условиям задачи "максимальный вес", а не переопределение super.weight
    private double maxWeight;

    public void newWheels(int wheels) {
        if (wheels < 0) {
            throw new IllegalArgumentException();
        }
        this.wheels = wheels;
        System.out.println(this.wheels);
    }

    public Truck(int w, String m, char c, float s, int wheels, double maxWeight) {
        super(w, m, c, s);
        if (maxWeight < super.weight || maxWeight < 0 || wheels < 0) {
            throw new IllegalArgumentException();
        }
        this.wheels = wheels;
        this.maxWeight = maxWeight;
    }

    @Override
    public String toString() {

        return "Truck{" +
                "wheels=" + wheels +
                ", maxWeight=" + maxWeight +
                ", weight=" + weight +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", speed=" + speed +
                '}';
    }
}
