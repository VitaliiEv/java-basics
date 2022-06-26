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
        PORT_CAPACITY_MAX = 200;
        PORT_CAPACITY_MIN = 100;
        PORT_CARGO_MAX = 120;
        PORT_CARGO_MIN = 20;

        PIERCE_NUM_MAX = 10;
        PIERCE_NUM_MIN = 2;
        PIERCE_SPEED_MAX = 20d;
        PIERCE_SPEED_MIN = 5d;

        SHIP_NUM_MAX = 15;
        SHIP_NUM_MIN = 5;
        SHIP_CAPACITY_MAX = 50;
        SHIP_CAPACITY_MIN = 30;
        SHIP_CARGO_MAX = 10;
        SHIP_CARGO_MIN = 2;

        TASK_CARGO_MAX = 50;
        TASK_CARGO_MIN = 10;

    }

    public static void main(String[] args) {

        Port saintPetersburg = portInit(-1);
        Ship[] ships = shipInit(-1);

        Thread port = new Thread(saintPetersburg);
        for (Ship s : ships) {
            Thread t = new Thread(() -> {
                long cargoTask1 = (new Random()).nextInt(TASK_CARGO_MAX - TASK_CARGO_MIN) + TASK_CARGO_MIN;
                System.out.println("Ship №" + s.getId() + " (" + s.getStats() + ") task 1: load " + cargoTask1 + " cargo from port to ship");
                long cargoTask2 = (new Random()).nextInt(TASK_CARGO_MAX - TASK_CARGO_MIN) + TASK_CARGO_MIN;
                System.out.println("Ship №" + s.getId() + " (" + s.getStats() + ") task 2: unload " + cargoTask2 + " cargo from ship to port");
                s.dockAt(saintPetersburg);
                s.getPierce().loadFromTo(saintPetersburg, s, cargoTask1);
                s.getPierce().loadFromTo(s, saintPetersburg, cargoTask2);
                s.unDock(saintPetersburg);
            });
            t.start();
        }
        port.start();
//        ships[0].dockAt(saintPetersburg);

//        Thread[] dockThreads = new Thread[ships.length];
//        Thread[] loadThreads = new Thread[ships.length];
//        Thread[] unLoadThreads = new Thread[ships.length];
//        Thread[] unDockThreads = new Thread[ships.length];
//
//        for (int i = 0; i < ships.length; i++) {
//            Ship s = ships[i];
//            Thread dock = new Thread(() -> s.dockAt(saintPetersburg));
//            long cargoTask1 = (new Random()).nextInt(SHIP_CAPACITY_MAX - SHIP_CAPACITY_MIN) + SHIP_CAPACITY_MIN;
//            System.out.println("Ship №" + s.getId() + " (" + s.getStats() + ") task 1: load " + cargoTask1 + " cargo from port to ship");
//            Thread load = new Thread(() -> s.getPierce().loadFromTo(saintPetersburg, s, cargoTask1));
//
//            long cargoTask2 = (new Random()).nextInt(SHIP_CAPACITY_MAX - SHIP_CAPACITY_MIN) + SHIP_CAPACITY_MIN;
//            System.out.println("Ship №" + s.getId() + " (" + s.getStats() + ") task 2: unload " + cargoTask2 + " cargo from ship to port");
//            Thread unLoad = new Thread(() -> s.getPierce().loadFromTo(s, saintPetersburg, cargoTask2));
//            Thread unDock = new Thread(() -> s.unDock(saintPetersburg));
//
//            dockThreads[i] = dock;
//            loadThreads[i] = load;
//            unLoadThreads[i] = unLoad;
//            unDockThreads[i] = unDock;
//        }
//
//        System.out.println("Docking threads started");
//        for (int i = 0; i < ships.length; i++) {
//            dockThreads[i].start();
//        }
//        System.out.println("Docking threads finished");
//
//        System.out.println("Docking threads joining");
//        for (int i = 0; i < ships.length; i++) {
//            try {
//                dockThreads[i].join();
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
//        }
//        System.out.println("Docking threads joined");
//
//        for (int i = 0; i < ships.length; i++) {
//            loadThreads[i].start();
//            unLoadThreads[i].start();
//         }
//
//        for (int i = 0; i < ships.length; i++) {
//            try {
//                loadThreads[i].join();
//                unLoadThreads[i].join();
//            } catch (InterruptedException e) {
//                System.out.println(e);
//            }
//        }
//
//        for (int i = 0; i < ships.length; i++) {
//            unDockThreads[i].start();
//        }
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