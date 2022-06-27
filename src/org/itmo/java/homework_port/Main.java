package org.itmo.java.homework_port;

import java.util.ArrayList;
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
        PIERCE_SPEED_MAX = 100d; // cargo per second
        PIERCE_SPEED_MIN = 50d; // cargo per second

        SHIP_NUM_MAX = 8;
        SHIP_NUM_MIN = 4;
        SHIP_CAPACITY_MAX = 50;
        SHIP_CAPACITY_MIN = 30;
        SHIP_CARGO_MAX = 30;
        SHIP_CARGO_MIN = 15;

        TASK_CARGO_MAX = 50;
        TASK_CARGO_MIN = 10;

    }

    /**
     Порт. Корабли заходят в порт для разгрузки загрузки контейнеров. Число контейнеров, находящихся в текущий момент
     в порту и на корабле, должно быть неотрицательным и превышающим заданную грузоподъемность судна и вместимость порта.
     В порту работает несколько причалов. У одного причала может стоять один корабль. Корабль может загружаться у причала,
     разгружаться или выполнять оба действия.
     */
    public static void main(String[] args) {

        Port port = portInit(0);
        port.setId("Spb");
        Ship[] ships = shipInit(0);
        ArrayList<Thread> shipsThread = new ArrayList<>();

//        Генерируются случайные задания на погрузку и разгрузку кораблей.
        for (Ship ship : ships) {
            Thread shipThread = new Thread(() -> {
                long cargoTask1 = (new Random()).nextInt((int) ship.getCargo() - 1) + 1;
                long cargoTask2 = (new Random()).nextInt((int) ship.capacity - TASK_CARGO_MIN) + TASK_CARGO_MIN;
                System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 1: unload " + cargoTask1 + " cargo from ship to port");
                System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 2: load " + cargoTask2 + " cargo from port to ship");
                ship.dockAt(port);
                // todo sometimes ship is not docked properly
                // Exception in thread "Thread-2" java.lang.NullPointerException: Cannot invoke
                // "org.itmo.java.homework_port.Loader.loadFromTo(org.itmo.java.homework_port.Storage, org.itmo.java.homework_port.Storage, long)"
                // because the return value of "org.itmo.java.homework_port.Ship.getPierce()" is null
                //	at org.itmo.java.homework_port.Main.lambda$main$0(Main.java:64)
                ship.getPierce().loadFromTo(ship, port, cargoTask1);
                ship.getPierce().loadFromTo(port, ship, cargoTask2);
                ship.unDock(port);
            });
            shipsThread.add(shipThread);
            shipThread.start();
        }

        // Supervisor thread
        Thread portThread = new Thread(() -> {
            System.out.println("Entering port thread. " + port.getAllStats());
            try {
                while (getActiveThreads(shipsThread) != 0) {
                    Thread.sleep(2000);
                    System.out.println("Active ship threads: "+ getActiveThreads(shipsThread)+". " + port.getAllStats());
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        });
        portThread.start();
    }

    /**
     * Генерируется порт со случайным количеством причалов и случайной вместительностю и начальным количеством
     * груза. У каждого причала своя скорость погрузчика.
     *
     * @param option : 0 - for generating port based on MIN values
     *               : 1 - for generating port based on MAX values
     *               : any other - for generating port with random fields between MIN and MAX values
    */
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

    /**
     *   Генерируется случайное количество кораблей, со случайной вместительностю и начальным количеством груза.
     *
     * @param option : 0 - for generating ships based on MIN values
     *               : 1 - for generating ships based on MAX values
     *               : any other - for generating ships with random fields between MIN and MAX values
     */
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

    public static int getActiveThreads(ArrayList<Thread> t) {
        return (int) t.stream().filter(Thread::isAlive).count();
    }
}