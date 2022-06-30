package org.itmo.java.homework6;

public class SuperUser extends User {

    public SuperUser(String name, String surName) {
        super(name, surName);
    }

    //   @Override - не нужен, т.к. поменялся contract метода
    protected void setName() {
        System.out.printf("%s method. Enter your name: ", this.getClass().getSimpleName());
            String nameTemp = super.SCANNER_INSTANCE.next();
            if (nameTemp == null) {
                throw new IllegalArgumentException();
            } else {
                this.name = nameTemp;
            }
    }

}
