package org.itmo.java.homework_port;

import java.util.Arrays;

public class Port extends Storage {

    private String id;
    private int pierceNum;
    private int shipsNum = 0;
    private Pierce[] pierceList;

    public Port(long capacity, int pierceNum) {
        super(capacity);
        this.cargo = 0;
        this.pierceNum = pierceNum;
        pierceList = new Pierce[pierceNum];
        initPierce();
    }

    public Port(long capacity, long cargo, int pierceNum) {
        super(capacity, cargo);
        this.pierceNum = pierceNum;
        pierceList = new Pierce[pierceNum];
        initPierce();
    }

    public synchronized void setShipsNum(int shipsNum) {
        try {
            if ((shipsNum < 0) || (shipsNum > this.pierceNum)) {
                throw new IllegalArgumentException("Illegal number if ships in port (" + shipsNum + ")");
            } else {
                this.shipsNum = shipsNum;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public Pierce[] getPierceList() {
        return pierceList;
    }

    public void occupyPierce(Ship ship) {
        Pierce pierce;
        try {
            synchronized (this) {
                while ((pierce = getUnoccupiedPierce()) == null) {
                    System.out.println("Ship №" + ship.getId() + " waiting for free pierce");
                    // todo change wait to notify all
                    wait(1000);
                }
//            }
//            synchronized (this) {
                setShipsNum(this.shipsNum + 1);
                ship.setPierce(pierce);
                pierce.setShip(ship);
                System.out.println("Ship №" +ship.getId()  + " docked." + getAllStats());

            }

        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public synchronized void unOccupyPierce(Ship ship) {
        System.out.println("Ship №" + ship.id + " undocked, load:" + ship.getStats());
        ship.getPierce().setShip(null);
        ship.setPierce(null);
        setShipsNum(this.shipsNum - 1);
        System.out.println(getAllStats());
    }

    public synchronized Pierce getUnoccupiedPierce() {
        // get unoccupied pierce with max loadSpeed
        if (this.shipsNum == this.pierceNum) {
//        if (Arrays.stream(pierceList).filter(p -> p.getShip() == null).count() == 0) {
            return null;
        } else {
            return Arrays.stream(pierceList).filter(p -> p.getShip() == null).max(Loader::compareLoadSpeed).get();
        }
    }

    public synchronized String getAllStats() {
        String stats = "PORT STATS: ships: " + this.shipsNum + "/" + this.pierceNum + ", port load: " + getStats() + "\n" +
                "Ship stats: ";
        Pierce p;
        Ship s;
        for (int i = 0; i < this.pierceNum; i++) {
            p = this.pierceList[i];
            s = p.getShip();
            if (s == null) {
                stats += "pierce №" + p.getId() + " unoccupied";
            } else {
                stats += "pierce №" + p.getId() + " - ship №" + s.getId() + " load " + s.getStats();
            }
            if (i == this.pierceNum - 1) {
                stats += ".";
            } else {
                stats += ", ";
            }
        }
        return stats;
    }

    private void initPierce() {
        for (int i = 0; i < this.pierceNum; i++) {
            this.pierceList[i] = new Pierce(i);
        }
    }
}



