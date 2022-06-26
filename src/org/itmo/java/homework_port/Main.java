package org.itmo.java.homework_port;

import java.util.Random;

public class Main {

    private static final int PORT_CAPACITY_MAX;
    private static final int PORT_CAPACITY_MIN;
    private static final int PORT_CARGO_MAX;
    private static final int PORT_CARGO_MIN;
    private static final double PIERCE_SPEED_MAX;
    private static final double PIERCE_SPEED_MIN;
    private static final int PIERCE_NUM_MAX;
    private static final int PIERCE_NUM_MIN;

    private static final int SHIP_NUM_MAX;
    private static final int SHIP_NUM_MIN;
    private static final int SHIP_CAPACITY_MAX;
    private static final int SHIP_CAPACITY_MIN;
    private static final int SHIP_CARGO_MAX;
    private static final int SHIP_CARGO_MIN;

    private static final int TASK_CARGO_MAX;
    private static final int TASK_CARGO_MIN;

    static {
        PORT_CAPACITY_MAX = 300;
        PORT_CAPACITY_MIN = 200;
        PORT_CARGO_MAX = 180;
        PORT_CARGO_MIN = 100;

        PIERCE_NUM_MAX = 5;
        PIERCE_NUM_MIN = 2;
        PIERCE_SPEED_MAX = 4d;
        PIERCE_SPEED_MIN = 2d;

        SHIP_NUM_MAX = 8;
        SHIP_NUM_MIN = 3;
        SHIP_CAPACITY_MAX = 50;
        SHIP_CAPACITY_MIN = 30;
        SHIP_CARGO_MAX = 15;
        SHIP_CARGO_MIN = 5;

        TASK_CARGO_MAX = 50;
        TASK_CARGO_MIN = 10;

    }

    public static void main(String[] args) {


        Port saintPetersburg = portInit(0);
        Ship[] ships = shipInit(0);
//        Thread port = new Thread(saintPetersburg);
        ships[0].setCargo(-1, 100);
//        ThreadGroup shipGroup = new ThreadGroup("ships");
//        Thread port = new Thread(() -> {
//            System.out.println("Port run Thread - " + saintPetersburg.getAllStats());
//            try {
//                while (shipGroup.activeCount() != 0) {
//                    System.out.println("Port run Thread - " + saintPetersburg.getAllStats());
//                    Thread.sleep(1000);
//                }
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
//        });
//
//        for (Ship s : ships) {
//            Thread t = new Thread(shipGroup, () -> {
//                long cargoTask1 = (new Random()).nextInt((int) s.getCargo() - 1) + 1;
//                long cargoTask2 = (new Random()).nextInt((int) s.capacity - TASK_CARGO_MIN) + TASK_CARGO_MIN;
//                System.out.println("Ship №" + s.getId() + " (" + s.getStats() + ") task 2: unload " + cargoTask1 + " cargo from ship to port");
//                System.out.println("Ship №" + s.getId() + " (" + s.getStats() + ") task 1: load " + cargoTask2 + " cargo from port to ship");
//                s.dockAt(saintPetersburg);
//                s.getPierce().loadFromTo(s, saintPetersburg, cargoTask1);
//                s.getPierce().loadFromTo(saintPetersburg, s, cargoTask2);
//                s.unDock(saintPetersburg);
//            });
//            t.start();
//        }
//        port.start();
    }

    public static Port portInit(int option) {
        switch (option) {
            case 0:
                return portInitMin();
            case 1:
                return portInitMax();
            default:
                return portInitRandom();
        }
    }

    public static Port portInitRandom() {
        Random random = new Random();
        int portCapacity = random.nextInt(PORT_CAPACITY_MAX - PORT_CAPACITY_MIN) + PORT_CAPACITY_MIN;
        int portCargo = random.nextInt(PORT_CARGO_MAX - PORT_CARGO_MIN) + PORT_CARGO_MIN;
        int pierceNum = random.nextInt(PIERCE_NUM_MAX - PIERCE_NUM_MIN) + PIERCE_NUM_MIN;

        Port port = new Port(portCapacity, portCargo, pierceNum);
        double newLoadSpeed;
        for (Pierce p : port.getPierceList()) {
            newLoadSpeed = random.nextDouble(PIERCE_SPEED_MAX - PIERCE_SPEED_MIN) + PIERCE_SPEED_MIN;
            p.setLoadSpeed(newLoadSpeed);
        }
        return port;
    }

    public static Port portInitMin() {
        Port port = new Port(PORT_CAPACITY_MIN, PORT_CARGO_MIN, PIERCE_NUM_MIN);
        for (Pierce p : port.getPierceList()) {
            p.setLoadSpeed(PIERCE_SPEED_MIN);
        }
        return port;
    }

    public static Port portInitMax() {
        Port port = new Port(PORT_CAPACITY_MAX, PORT_CARGO_MAX, PIERCE_NUM_MAX);
         for (Pierce p : port.getPierceList()) {
            p.setLoadSpeed(PIERCE_SPEED_MAX);
        }
        return port;
    }

    public static Ship[] shipInit(int option) {
        switch (option) {
            case 0:
                return shipInitMin();
            case 1:
                return shipInitMax();
            default:
                return shipInitRandom();
        }

    }

    public static Ship[] shipInitRandom() {
        Random random = new Random();
        int shipNum = random.nextInt(SHIP_NUM_MAX - SHIP_NUM_MIN) + SHIP_NUM_MIN;
        int shipCapacity;
        int shipCargo;
        Ship[] ships = new Ship[shipNum];
        for (int i = 0; i < shipNum; i++) {
            shipCapacity = random.nextInt(SHIP_CAPACITY_MAX - SHIP_CAPACITY_MIN) + SHIP_CAPACITY_MIN;
            shipCargo = random.nextInt(SHIP_CARGO_MAX - SHIP_CARGO_MIN) + SHIP_CARGO_MIN;
            ships[i] = new Ship(shipCapacity, shipCargo, i);
        }
        return ships;
    }

    public static Ship[] shipInitMax() {
        Ship[] ships = new Ship[SHIP_NUM_MAX];
        for (int i = 0; i < SHIP_NUM_MAX; i++) {
            ships[i] = new Ship(SHIP_CAPACITY_MAX, SHIP_CARGO_MAX, i);
        }
        return ships;
    }

    public static Ship[] shipInitMin() {
        Ship[] ships = new Ship[SHIP_NUM_MIN];
        for (int i = 0; i < SHIP_NUM_MIN; i++) {
            ships[i] = new Ship(SHIP_CAPACITY_MIN, SHIP_CARGO_MIN, i);
        }
        return ships;
    }
}