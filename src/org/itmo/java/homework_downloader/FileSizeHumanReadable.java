package org.itmo.java.homework_downloader;

public class FileSizeHumanReadable {

    private static double get(double size, int base) {
        return (double) size; // todo
    }

    public static double getBinary(double size) {
        return (double) get(size, 2);
    }

    public static double getDecimal(double size) {
        return (double) get(size, 10);
    }
}
