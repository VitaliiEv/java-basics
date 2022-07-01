package org.itmo.java.homework_port;

public class Ship extends Storage {
    private Pierce pierce;
    private ShipTask shipTask;

    public Ship(long capacity, int id) {
        super(capacity, id);
    }

    public Ship(long capacity, long cargo, String id) {
        super(capacity, cargo, id);
    }


    public synchronized Pierce getPierce() {
        return pierce;
    }

    public synchronized void setPierce(Pierce pierce) {
        try {
            if (this.pierce != null && pierce != null) {
                throw new UnsupportedOperationException("Ship " + id + " is already docked.");
            } else {
                this.pierce = pierce;
            }
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
    }

    public ShipTask getShipTask() {
        return shipTask;
    }

    public void setShipTask(ShipTask shipTask) {
        if (shipTask == null) {
            throw new NullPointerException("Cannot set empty task to ship");
        } else {
            this.shipTask = shipTask;
        }
    }

    public synchronized void dockAt(Port port) {
        port.occupyPierce(this);
    }

    public synchronized void unDock(Port port) {
        port.unOccupyPierce(this);
    }
}
