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
        int i;
        for (i = 0; i < cargo; i++) {
            // todo nested try catch
            try {
                from.setCargo(from.getCargo() - 1, this.pause / 2);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                break; // no need to continue task if one of storages is full or empty
            }
            try {
                to.setCargo(to.getCargo() + 1, this.pause - (this.pause / 2));
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                //previous operation should be reversed
                from.setCargo(from.getCargo() + 1, 0);
                break; // no need to continue task if one of storages is full or empty
            }
        }
        System.out.println("Task finished: moved " + i +"/"+ cargo + " cargo from storage " + from.getId() + " to storage " + to.getId() + ".");
    }

    private long calculatePause(double loadSpeed) {
        return Math.round(1000.0 / loadSpeed);
    }
}
