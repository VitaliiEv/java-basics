package org.itmo.java.homework_port;

public class Pierce extends Loader {

    private int id;
    private Ship ship;

    public Pierce(int id) {
        super();
        this.id = id;
    }

    public Pierce(double loadSpeed, int id) {
        super(loadSpeed);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Ship getShip() {
        return ship;
    }

    public synchronized void setShip(Ship ship) {
        try {
            if (this.ship != null && ship != null) {
                throw new UnsupportedOperationException("Pierce " + id + " is already occupied.");
            } else {
                this.ship = ship;
            }
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
    }
}