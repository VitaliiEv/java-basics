package org.itmo.java.homework_port;

import java.util.Arrays;

public class Port extends Storage implements Runnable {

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

    @Override
    public void run() {
        System.out.println("Port run method" + !this.allPiercesFree());
        System.out.println(this.getAllStats());
        try {
            while (!this.allPiercesFree()) {
                System.out.println(this.getAllStats());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

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

    public void occupyPierce(Ship ship, Pierce p) {
        try {
            if (p == null) {
                throw new UnsupportedOperationException("All pierces occupied");
            } else {
                synchronized (this) {
                    p.occupy(ship);
                    setShipsNum(this.shipsNum + 1);
                }
            }
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
    }

    public void unOccupyPierce(Pierce p) {
        synchronized (this) {
            p.unOccupy();
            setShipsNum(this.shipsNum - 1);
            notifyAll();
        }
    }

    public Pierce getUnoccupiedPierce() {
        // get unoccupied pierce with max loadSpeed
        synchronized (this) {
            if (Arrays.stream(pierceList).filter(p -> p.getShip() == null).count() == 0) {
                return null;
            } else {
                return Arrays.stream(pierceList).filter(p -> p.getShip() == null).max(Loader::compareLoadSpeed).get();
            }
        }
    }

    public boolean allPiercesOccupied() {
        synchronized (this) {
            return this.shipsNum == this.pierceNum;
        }
    }

    public boolean allPiercesFree() {
        synchronized (this) {
            return this.shipsNum == 0;
        }
    }

    public String getAllStats() {
        synchronized (this) {
            String stats = "Ships: " + this.shipsNum + "/" + this.pierceNum + ", port load: " + getStats() + "\n" +
                    "Ship stats: ";
            for (int i = 0; i < this.pierceNum; i++) {
                Ship ship = this.pierceList[i].getShip();
                if (ship == null) {
                    stats += "pierce №" + i + " unoccupied";
                } else {
                    stats += "pierce №" + i + " load " + ship.getStats();
                }
                if (i == this.pierceNum - 1) {
                    stats += ".";
                } else {
                    stats += ", ";
                }
            }
        return stats;
        }
    }

    private void initPierce() {
        for (int i = 0; i < this.pierceNum; i++) {
            this.pierceList[i] = new Pierce(i);
        }
    }
}



