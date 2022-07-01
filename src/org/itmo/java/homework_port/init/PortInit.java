package org.itmo.java.homework_port.init;

import org.itmo.java.homework_port.Port;
import org.itmo.java.homework_port.Ship;


public interface PortInit {
// Configuration Values
    int PORT_CAPACITY_MAX = 300;
    int PORT_CAPACITY_MIN = 100;
    int PORT_CARGO_MAX = 180;
    int PORT_CARGO_MIN = 100;
    int PIERCE_NUM_MAX = 5;
    int PIERCE_NUM_MIN = 2;
    double PIERCE_SPEED_MAX = 50d; // cargo per second
    double PIERCE_SPEED_MIN = 20d; // cargo per second
    int SHIP_NUM_MAX = 8;
    int SHIP_NUM_MIN = 4;
    int SHIP_CAPACITY_MAX = 50;
    int SHIP_CAPACITY_MIN = 30;
    int SHIP_CARGO_MAX = 30;
    int SHIP_CARGO_MIN = 15;
    int TASK_CARGO_MAX = 50;
    int TASK_CARGO_MIN = 10;

    Port portInit();

    Ship[] shipInit();

    Ship[] taskInit(Port port, Ship[] ships);

}
