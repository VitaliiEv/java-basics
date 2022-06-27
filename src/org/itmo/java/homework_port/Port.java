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
        // todo rework this
                p.occupy(ship);
                synchronized (this) {
                    setShipsNum(this.shipsNum + 1);
                }
    }

    public void unOccupyPierce(Pierce p) {
        p.unOccupy();
        // todo rework this
        synchronized (this) {
            setShipsNum(this.shipsNum - 1);
            // todo notifiy must be in setShipsNum
            notifyAll();
        }
    }

    public synchronized Pierce getUnoccupiedPierce() {
        // get unoccupied pierce with max loadSpeed
        if (allPiercesOccupied()) {
            return null;
        } else {
            return Arrays.stream(pierceList).filter(p -> p.getShip() == null).max(Loader::compareLoadSpeed).get();
        }
    }

    public synchronized boolean allPiercesOccupied() {
        return this.shipsNum == this.pierceNum;
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



