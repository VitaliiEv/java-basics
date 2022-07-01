package org.itmo.java.homework_port.init;

import org.itmo.java.homework_port.Pierce;
import org.itmo.java.homework_port.Port;
import org.itmo.java.homework_port.Ship;

public class PortInitImplMin implements PortInit {

    @Override
    public Port portInit() {
        Port port = new Port(PORT_CAPACITY_MIN, PORT_CARGO_MIN, PIERCE_NUM_MIN);
        for (Pierce p : port.getPierceList()) {
            p.setLoadSpeed(PIERCE_SPEED_MIN);
        }
        return port;
    }

    @Override
    public Ship[] shipInit() {
        Ship[] ships = new Ship[SHIP_NUM_MIN];
        for (int i = 0; i < SHIP_NUM_MIN; i++) {
            ships[i] = new Ship(SHIP_CAPACITY_MIN, SHIP_CARGO_MIN, String.valueOf(i));
        }
        return ships;
    }

    @Override
    public Ship[] taskInit(Port port, Ship[] ships) {
        //todo
        return null;
    }
}
