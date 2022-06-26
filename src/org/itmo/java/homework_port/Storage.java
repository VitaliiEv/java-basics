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

    public synchronized String getStats() {
        return this.cargo + "/" + this.capacity;
    }

    public synchronized long getCargo() {
        return this.cargo;
    }

    public synchronized void setCargo(long c, long loaderPause) {
        try {
            while (c > this.capacity) {
                System.out.println("Storage №" + this.id + ": Cargo full");
                wait();
            }
            while (c < 0) {
                System.out.println("Storage №" + this.id + ": No cargo left in storage");
                wait();
            }
            while (this.isOccupied) {
                System.out.println("Storage №" + this.id + ": Cant use storage: storage occupied");
                wait();
            }
            this.isOccupied = true;
            this.cargo = c;
            Thread.sleep(loaderPause);
            this.isOccupied = false;
            notifyAll();

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    @Deprecated
    public void setCargo1(long c, long loaderPause) {
        try {
            synchronized (this) {
                while (c > this.capacity) {
                    System.out.println("Storage №" + this.id + ": Cargo full");
                    wait();
                }
                while (c < 0) {
                    System.out.println("Storage №" + this.id + ": No cargo left in storage");
                    wait();
                }
                while (this.isOccupied) {
                    System.out.println("Storage №" + this.id + ": Cant use storage: storage occupied");
                    wait();
                }
                this.isOccupied = true;
                this.cargo = c;
                Thread.sleep(loaderPause);
                this.isOccupied = false;
                notifyAll();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

