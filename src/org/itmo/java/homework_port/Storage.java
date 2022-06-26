package org.itmo.java.homework_port;

public class Storage {
    protected int id;
    protected long capacity;
    protected long cargo;
    protected boolean isOccupied;

    public Storage(long capacity, int id) {
        this.capacity = capacity;
        this.cargo = 0;
        this.isOccupied = false;
    }

    public Storage(long capacity, long cargo, int id) {
        this.capacity = capacity;
        this.cargo = cargo;
        this.isOccupied = false;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStats() {
        return this.cargo + "/" + this.capacity;
    }

    public synchronized long getCargo() {
        return this.cargo;
    }

    public synchronized void setCargo(long c, long loaderPause) {
        try {
            //todo rewrite with exceptions
            if (c > this.capacity) {
                throw new IllegalArgumentException("Storage №" + this.id + ": Cannot add cargo to full storage");
            } else if (c < 0) {
                throw new IllegalArgumentException("Storage №" + this.id + ": Cannot take cargo from empty storage");
            }
//            while (c > this.capacity) {
//                System.out.println("Storage №" + this.id + ": Cargo full");
//                wait();
//            }
//            while (c < 0) {
//                System.out.println("Storage №" + this.id + ": No cargo left in storage");
//                wait();
//            }
            while (this.isOccupied) {
                System.out.println("Storage №" + this.id + ": Cant use storage: storage occupied");
                wait();
            }
            this.isOccupied = true;
            this.cargo = c;
            Thread.sleep(loaderPause);
            this.isOccupied = false;
            System.out.println("Storage №" + this.id + " load " + this.getStats());
            notifyAll();

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

