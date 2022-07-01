package org.itmo.java.homework_port;

import org.itmo.java.homework_port.init.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * Порт. Корабли заходят в порт для разгрузки загрузки контейнеров. Число контейнеров, находящихся в текущий момент
     * в порту и на корабле, должно быть неотрицательным и превышающим заданную грузоподъемность судна и вместимость
     * порта.
     * В порту работает несколько причалов. У одного причала может стоять один корабль. Корабль может загружаться у
     * причала,
     * разгружаться или выполнять оба действия.
     */
    public static void main(String[] args) {

        Port port = (new PortInitImplRandom()).portInit();
        port.setId("Spb");
        System.out.println(port.getAllStats());
        Ship[] ships = (new PortInitImplRandom()).shipInit();
        (new PortInitImplReasonable()).taskInit(port, ships);

        List<Thread> shipsThread = new ArrayList<>();
        for (Ship ship : ships) {
            Thread task = new Thread(ship.getShipTask());
            shipsThread.add(task);
            task.start();
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