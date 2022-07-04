package org.itmo.java.homework7;

public class Plane {
//    private Wing wings;

    private class Wing {
        private double weight;

        public Wing(double weight) {
            this.weight = weight;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    private static class Seat {
        private int size;

        public Seat(int size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Seat{" +
                    "size=" + size +
                    '}';
        }
    }

    public Wing createWing(double weight) {
        return new Wing(weight);
    }

    public static void main(String[] args) {
        // instantiate nested non-static class
        Plane.Wing wingLeft = new Plane().createWing(12d);
        Plane.Wing wingRight = new Plane().new Wing(12.5d);
        Plane p = new Plane();
        Plane.Wing wingRear = p.new Wing(0.5d);

        System.out.println(wingLeft.getWeight());
        System.out.println(wingRight.getWeight());
        System.out.println(wingRear.getWeight());

        // instantiate nested static class
        Plane.Seat seats = new Plane.Seat(300);
        System.out.println(seats);
    }

}
