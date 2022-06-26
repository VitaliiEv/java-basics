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

    public void dockAt(Port port) {
        try {
            if (this.pierce != null) {
                throw new UnsupportedOperationException("Ship " + id + " is already docked.");
            } else {
                synchronized (this) {
                    while (port.allPiercesOccupied()) {
                        System.out.println("Ship №" + this.id + " waiting for free pierce");
                        wait();
                    }
                }
                Pierce p = port.getUnoccupiedPierce();
                if (p == null) {
                    throw new UnsupportedOperationException("All pierces occupied");
                } else {
                    port.occupyPierce(this, p);
                    synchronized (this) {
                        this.pierce = p;
                    }
                }
                System.out.println("Ship №" + this.id + " docked." + port.getAllStats());
            }

        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public synchronized void unDock(Port port) {
        if (this.pierce != null) {
            port.unOccupyPierce(this.pierce);
            this.pierce = null;
            System.out.println("Ship №" + this.id + " undocked, load:" + getStats());
            System.out.println(port.getAllStats());
        }
        notifyAll();
    }
}
