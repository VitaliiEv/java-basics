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

    public void setShipTask(Port port, long cargoTask1, long cargoTask2) {
        if (port == null) {
            throw new NullPointerException("Ship should have destination port");
        } else {
            this.shipTask = new ShipTask(port, this, cargoTask1, cargoTask2);
        }
    }

    public synchronized void dockAt(Port port) {
        port.occupyPierce(this);
    }

    public synchronized void unDock(Port port) {
        port.unOccupyPierce(this);
    }
}
