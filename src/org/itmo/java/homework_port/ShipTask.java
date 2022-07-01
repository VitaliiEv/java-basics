package org.itmo.java.homework_port;

public class ShipTask implements Runnable {
    private Port port;
    private Ship ship;
    private long cargoTask1;
    private long cargoTask2;


    public ShipTask(Port port, Ship ship, long cargoTask1, long cargoTask2) {
        // todo add exceptions
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
        //todo to string
//        System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 1: unload " + cargoTask1 +
//                " cargo from ship to port");
//        System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 2: load " + cargoTask2 + " " +
//                "cargo from port to ship");
        return String.valueOf(this);
    }
}
