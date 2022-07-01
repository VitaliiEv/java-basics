package org.itmo.java.homework_port;

public class ShipTask implements Runnable {
    private Port port;
    private Ship ship;
    private long cargoTask1;
    private long cargoTask2;


    public ShipTask(Port port, Ship ship, long cargoTask1, long cargoTask2) {
        this.port = port;
        this.ship = ship;
        this.cargoTask1 = cargoTask1;
        this.cargoTask2 = cargoTask2;
    }

    @Override
    public void run() {
            ship.dockAt(port);
            ship.getPierce().loadFromTo(ship, port, cargoTask1);
            ship.getPierce().loadFromTo(port, ship, cargoTask2);
            ship.unDock(port);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Ship №")
                .append(this.ship.getId())
                .append(" (")
                .append(this.ship.getStats())
                .append(") task 1: unload ")
                .append(this.cargoTask1)
                .append(" cargo from ship to port.")
                .append(System.getProperty("line.separator"));
        str.append("Ship №")
                .append(this.ship.getId())
                .append(" (")
                .append(this.ship.getStats())
                .append(") task 2: load ")
                .append(this.cargoTask2)
                .append(" cargo from port to ship.")
                .append(System.getProperty("line.separator"));
        return String.valueOf(str);
    }
}
