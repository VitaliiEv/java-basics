package org.itmo.java.homework_port.init;

import org.itmo.java.homework_port.Pierce;
import org.itmo.java.homework_port.Port;
import org.itmo.java.homework_port.Ship;

import java.util.Random;

public class PortInitImplRandom implements PortInit {

    private final Random random = new Random();

    @Override
    public Port portInit() {
        int portCapacity = random.nextInt(PORT_CAPACITY_MAX - PORT_CAPACITY_MIN) + PORT_CAPACITY_MIN;
        int portCargo = random.nextInt(PORT_CARGO_MAX - PORT_CARGO_MIN) + PORT_CARGO_MIN;
        int pierceNum = random.nextInt(PIERCE_NUM_MAX - PIERCE_NUM_MIN) + PIERCE_NUM_MIN;

        Port port = new Port(portCapacity, portCargo, pierceNum);
        double newLoadSpeed;
        for (Pierce p : port.getPierceList()) {
            newLoadSpeed = random.nextDouble() * (PIERCE_SPEED_MAX - PIERCE_SPEED_MIN) + PIERCE_SPEED_MIN;
            p.setLoadSpeed(newLoadSpeed);
        }
        return port;
    }

    @Override
    public Ship[] shipInit() {
        int shipNum = random.nextInt(SHIP_NUM_MAX - SHIP_NUM_MIN) + SHIP_NUM_MIN;
        int shipCapacity;
        int shipCargo;
        Ship[] ships = new Ship[shipNum];
        for (int i = 0; i < shipNum; i++) {
            shipCapacity = random.nextInt(SHIP_CAPACITY_MAX - SHIP_CAPACITY_MIN) + SHIP_CAPACITY_MIN;
            shipCargo = random.nextInt(SHIP_CARGO_MAX - SHIP_CARGO_MIN) + SHIP_CARGO_MIN;
            ships[i] = new Ship(shipCapacity, shipCargo, String.valueOf(i));
        }
        return ships;
    }

//    public void taskInit(Ship[] ships) {
//        for (Ship ship : ships) {
//
//
//                long cargoTask1 = (long) random.nextInt((int) ship.getCargo() - 1) + 1;
//                long cargoTask2 = (long) random.nextInt((int) ship.capacity - TASK_CARGO_MIN) + TASK_CARGO_MIN;
//                System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 1: unload " + cargoTask1 + " cargo from ship to port");
//                System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 2: load " + cargoTask2 + " cargo from port to ship");
//                ship.dockAt(port);
//                ship.getPierce().loadFromTo(ship, port, cargoTask1);
//                ship.getPierce().loadFromTo(port, ship, cargoTask2);
//                ship.unDock(port);
//
//        }
//
//        int shipNum = random.nextInt(SHIP_NUM_MAX - SHIP_NUM_MIN) + SHIP_NUM_MIN;
//        int shipCapacity;
//        int shipCargo;
//        Ship[] ships = new Ship[shipNum];
//        for (int i = 0; i < shipNum; i++) {
//            shipCapacity = random.nextInt(SHIP_CAPACITY_MAX - SHIP_CAPACITY_MIN) + SHIP_CAPACITY_MIN;
//            shipCargo = random.nextInt(SHIP_CARGO_MAX - SHIP_CARGO_MIN) + SHIP_CARGO_MIN;
//            ships[i] = new Ship(shipCapacity, shipCargo, String.valueOf(i));
//        }
//
//    }

}
