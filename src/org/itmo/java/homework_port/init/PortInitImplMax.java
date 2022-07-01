package org.itmo.java.homework_port.init;

import org.itmo.java.homework_port.Pierce;
import org.itmo.java.homework_port.Port;
import org.itmo.java.homework_port.Ship;

public class PortInitImplMax implements PortInit {

    @Override
    public Port portInit() {
        Port port = new Port(PORT_CAPACITY_MAX, PORT_CARGO_MAX, PIERCE_NUM_MAX);
        for (Pierce p : port.getPierceList()) {
            p.setLoadSpeed(PIERCE_SPEED_MAX);
        }
        return port;
    }

    @Override
    public Ship[] shipInit() {
        Ship[] ships = new Ship[SHIP_NUM_MAX];
        for (int i = 0; i < SHIP_NUM_MAX; i++) {
            ships[i] = new Ship(SHIP_CAPACITY_MAX, SHIP_CARGO_MAX, String.valueOf(i));
        }
        return ships;
    }

    @Override
    public Ship[] taskInit(Port port, Ship[] ships) {
        //todo
        return null;
    }
}
