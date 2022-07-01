package org.itmo.java.homework_port;

import org.itmo.java.homework_port.init.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    //    private static final int TASK_CARGO_MAX;
    private static final int TASK_CARGO_MIN;
// todo Delete whis from main
    private static final Random random = new Random();

    static {

//       TASK_CARGO_MAX = 50;
        TASK_CARGO_MIN = 10;

    }

    /**
     * Порт. Корабли заходят в порт для разгрузки загрузки контейнеров. Число контейнеров, находящихся в текущий момент
     * в порту и на корабле, должно быть неотрицательным и превышающим заданную грузоподъемность судна и вместимость
     * порта.
     * В порту работает несколько причалов. У одного причала может стоять один корабль. Корабль может загружаться у
     * причала,
     * разгружаться или выполнять оба действия.
     */
    public static void main(String[] args) {

        Port port = (new PortInitImplMin().portInit());
        port.setId("Spb");
        Ship[] ships = (new PortInitImplMin().shipInit());
        List<Thread> shipsThread = new ArrayList<>();

//        Генерируются случайные задания на погрузку и разгрузку кораблей.
        for (Ship ship : ships) {
            Thread shipThread = new Thread(() -> {
                long cargoTask1 = (long) random.nextInt((int) ship.getCargo() - 1) + 1;
                long cargoTask2 = (long) random.nextInt((int) ship.capacity - TASK_CARGO_MIN) + TASK_CARGO_MIN;
                System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 1: unload " + cargoTask1 + " cargo from ship to port");
                System.out.println("Ship №" + ship.getId() + " (" + ship.getStats() + ") task 2: load " + cargoTask2 + " cargo from port to ship");
                ship.dockAt(port);
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
                    System.out.println("Active ship threads: " + getActiveThreads(shipsThread) + ". " + port.getAllStats());
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        });
        portThread.start();
    }

    public static int getActiveThreads(List<Thread> t) {
        return (int) t.stream().filter(Thread::isAlive).count();
    }
}