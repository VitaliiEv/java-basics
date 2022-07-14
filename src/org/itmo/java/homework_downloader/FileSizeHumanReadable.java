package org.itmo.java.homework_downloader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileSizeHumanReadable {

     enum Units {
        BYTE(1L, "B"),
        KB(1000L, "kB"), // 10^3
        MB(1000000L, "MB"), // 10^6
        GB(1000000000L, "GB"), // 10^9
        TB(1000000000000L, "TB"), // 10^12
        PB(1000000000000000L, "PB"); // 10^15

        private final long SIZE;
        private final String UNITS_SUFFIX;

        Units(long size, String unitsSuffix) {
            this.SIZE = size;
            this.UNITS_SUFFIX = unitsSuffix;
        }

        public long getSize() {
            return this.SIZE;
        }

        public String getUnits() {
            return this.UNITS_SUFFIX;
        }
    }

    public static String getSize(long size) {
        String result = String.format("%1$d %2$s", size , Units.BYTE.getUnits());
        List<Units> units = Arrays.asList(Units.values());
        Collections.reverse(units);
        for (Units u : units) {
            if (size / u.getSize() > 1) {
                result = String.format("%1$.2f %2$s", (double) size / u.getSize(), u.getUnits());
                break;
            }
        }
        return result;
    }
}
