package org.itmo.java.homework6;

import java.util.Scanner;

public class NewInteger {
    protected int newInt;
    private final Scanner SCANNER_INSTANCE = new Scanner(System.in);

    protected NewInteger() {
        System.out.println("PARENT CONSTRUCTOR. Enter new number: ");
            this.newInt = SCANNER_INSTANCE.nextInt();
    }

}
