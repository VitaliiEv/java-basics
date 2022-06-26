package org.itmo.java.homework_port;

import java.util.Arrays;

public class Port extends Storage /*implements Runnable*/ {

    private static int id = -1;
    private int pierceNum;
    private int shipsNum = 0;
    private Pierce[] pierceList;


    public Port(long capacity, int pierceNum) {
        super(capacity, id);
        this.cargo = 0;
        this.pierceNum = pierceNum;
        pierceList = new Pierce[pierceNum];
        initPierce();
    }

    public Port(long capacity, long cargo, int pierceNum) {
        super(capacity, cargo, id);
        this.pierceNum = pierceNum;
        pierceList = new Pierce[pierceNum];
        initPierce();
    }

//    @Override
//    public void run() {
//        System.out.println("Port run Thread -"+this.getAllStats());
//        try {
//            while (!this.allPiercesFree()) {
//                System.out.println(this.getAllStats());
//                Thread.sleep(1000);
//            }
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
//    }

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

    public void occupyPierce(Ship ship, Pierce p) {
                p.occupy(ship);
                synchronized (this) {
                    setShipsNum(this.shipsNum + 1);
                }
    }

    public void unOccupyPierce(Pierce p) {
        p.unOccupy();
        synchronized (this) {
            setShipsNum(this.shipsNum - 1);
            notifyAll();
        }
    }

    public synchronized Pierce getUnoccupiedPierce() {
        // get unoccupied pierce with max loadSpeed
        if (Arrays.stream(pierceList).filter(p -> p.getShip() == null).count() == 0) {
            return null;
        } else {
            return Arrays.stream(pierceList).filter(p -> p.getShip() == null).max(Loader::compareLoadSpeed).get();
        }

    }

    public synchronized boolean allPiercesOccupied() {
        return this.shipsNum == this.pierceNum;
    }

    public synchronized boolean allPiercesFree() {
        return this.shipsNum == 0;
    }

    public String getAllStats() {
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
                stats += "pierce №" + p.getId() + " - ship №" + s.getId() +" load " + s.getStats();
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



