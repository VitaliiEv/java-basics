package org.itmo.java.homework_port;

public class Storage {
    protected String id;
    protected long capacity;
    protected long cargo;
    protected boolean isOccupied;

    public Storage(long capacity, String id) {
        this.capacity = capacity;
        this.cargo = 0;
        this.isOccupied = false;
        this.id = id;
    }

    public Storage(long capacity, long cargo, String id) {
        this.capacity = capacity;
        this.cargo = cargo;
        this.isOccupied = false;
        this.id = id;
    }

    public Storage(long capacity) {
        this.capacity = capacity;
        this.cargo = 0;
        this.isOccupied = false;
    }

    public Storage(long capacity, long cargo) {
        this.capacity = capacity;
        this.cargo = cargo;
        this.isOccupied = false;
}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCapacity() {
        return capacity;
    }

    public synchronized long getCargo() {
        return this.cargo;
    }

    public String getStats() {
        return this.cargo + "/" + this.capacity;
    }

    public void setCargo(long c, long loaderPause) {
        if (c > this.capacity) {
            throw new IllegalArgumentException("Storage " + this.id + ": Cargo cant be more than storage capacity");
        } else if (c < 0) {
            throw new IllegalArgumentException("Storage " + this.id + ": Cargo cant be less than zero");
        } else {
            try {
                synchronized (this) {
                    while (this.isOccupied) {
                        System.out.println("Storage " + this.id + ": Cant use storage: storage occupied");
                        wait();
                    }
                    this.isOccupied = true;
                    this.cargo = c;
                }
                Thread.sleep(loaderPause);
                synchronized (this) {
                    this.isOccupied = false;
                    notifyAll();
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }
    }
}

