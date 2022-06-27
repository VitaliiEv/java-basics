package org.itmo.java.homework_port;

public class Ship extends Storage {
    private Pierce pierce;

    public Ship(long capacity, int id) {
        super(capacity, id);
    }

    public Ship(long capacity, long cargo, int id) {
        super(capacity, cargo, id);
    }


    public Loader getPierce() {
        return pierce;
    }

    public synchronized void dockAt(Port port) {
        if (this.pierce != null) {
            throw new UnsupportedOperationException("Ship " + id + " is already docked.");
        } else {
            try {
                //  synchronized (this) {
                Pierce p;
                while ( (p = port.getUnoccupiedPierce()) == null) {
                    System.out.println("Ship №" + this.id + " waiting for free pierce");
                    //todo rework without sleep
                    // todo port shoeld tell ships to sleep or to dock
                    wait(1000);
                }
                this.pierce = p;
                port.occupyPierce(this, p);
                System.out.println("Ship №" + this.id + " docked." + port.getAllStats());
            } catch (UnsupportedOperationException e) {
                System.out.println(e);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public synchronized void unDock(Port port) {
        if (this.pierce != null) {
            port.unOccupyPierce(this.pierce);
            this.pierce = null;
            System.out.println("Ship №" + this.id + " undocked, load:" + getStats());
            System.out.println(port.getAllStats());
            notifyAll();
        }

    }
}
