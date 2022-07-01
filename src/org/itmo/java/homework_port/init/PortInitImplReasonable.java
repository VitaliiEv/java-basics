package org.itmo.java.homework_port.init;

import org.itmo.java.homework_port.Pierce;
import org.itmo.java.homework_port.Port;
import org.itmo.java.homework_port.Ship;

import java.util.Random;

public class PortInitImplReasonable implements PortInit {

    private final Random random = new Random();

    @Override
    public Port portInit() {
        int portCapacity = (PORT_CAPACITY_MAX + PORT_CAPACITY_MIN)/2;
        int portCargo = (PORT_CARGO_MAX + PORT_CARGO_MIN)/2;
        int pierceNum = (PIERCE_NUM_MAX + PIERCE_NUM_MIN) /2;

        Port port = new Port(portCapacity, portCargo, pierceNum);
        for (Pierce p : port.getPierceList()) {
            p.setLoadSpeed((PIERCE_SPEED_MAX + PIERCE_SPEED_MIN) /2);
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

    @Override
    public Ship[] taskInit(Port port, Ship[] ships) {
        for (Ship ship : ships) {
            long cargoTask1 = (long) random.nextInt((int) ship.getCargo() - 1) + 1;
            long cargoTask2 = (long) random.nextInt((int) ship.getCapacity() - TASK_CARGO_MIN) + TASK_CARGO_MIN;
            ship.setShipTask(port, cargoTask1, cargoTask2);
            System.out.print(ship.getShipTask().toString());
        }
        return ships;
    }

}
