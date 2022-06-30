package org.itmo.java.homework6;

public class NewIntegerChild extends NewInteger {

    protected int printNewInteger() {
        System.out.println("CHILD METHOD. NewInteger = " + newInt);
        return super.newInt;
    }
}
