package org.itmo.java.homework_port;

public class Loader /*Implements Runnable*/ {
    private double loadSpeed; // containers per second
    private long pause; // millisecond

    public Loader() {
        this.loadSpeed = 10d;
        this.pause = calculatePause(this.loadSpeed);
    }

    public Loader(double loadSpeed) {
        this.loadSpeed = loadSpeed;
        this.pause = calculatePause(this.loadSpeed);
    }

    public double getLoadSpeed() {
        return this.loadSpeed;
    }

    public void setLoadSpeed(double loadSpeed) {
        this.loadSpeed = loadSpeed;
        this.pause = calculatePause(loadSpeed);
    }

    public int compareLoadSpeed(Loader l) {
        return Double.compare(this.loadSpeed, l.getLoadSpeed());
    }

    public void loadFromTo(Storage from, Storage to, long cargo) {
        for (int i = 0; i < cargo; i++) {
               // todo in syncronized block&
                try {
                    from.setCargo(from.getCargo() - 1, this.pause / 2);
                    to.setCargo(to.getCargo() + 1, this.pause - this.pause / 2);
                } catch (IllegalArgumentException e) {
                    System.out.println(e);
                }

        }
        System.out.println("Task finished: moved " + cargo + " cargo from storage №" + from.getId() + " to storage №" + to.getId() + ".");
    }

//    public void loadFromToParallel(Storage from, Storage to, long cargo, long cargoReversed) {
//        for (int i = 0; i < cargo; i++) {
//            from.setCargo(from.getCargo() - 1, this.pause/2);
//            to.setCargo(to.getCargo() + 1, this.pause-this.pause/2);
//            //Reversed
//            to.setCargo(to.getCargo() - 1, this.pause/2);
//            from.setCargo(from.getCargo() + 1, this.pause-this.pause/2);
//        }
//        //todo corgo - cargoRevesed
//
//        System.out.println("Task finished: loaded " + cargo + " cargo from storage №" + from.getId() + " to storage №" + to.getId() + ", " +
//                + cargoReversed + " cargo from storage №" + to.getId() + " to storage №" + from.getId() + ".");
//    }

    private long calculatePause(double loadSpeed) {
        return Math.round(1000.0 / loadSpeed);
    }
}
