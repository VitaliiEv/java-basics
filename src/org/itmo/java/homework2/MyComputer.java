package org.itmo.java.homework2;

public class MyComputer {
    public String processor;
    public long hddSize;
    public long ram;
    public int coreNumber;
    public String videoCard;

    public MyComputer() {
        this.processor = "Без дверки";
        this.hddSize = Integer.MAX_VALUE;
        this.ram = 640 * 1024;
        this.coreNumber = 1;
        this.videoCard = "Самая лучшая";
    }

    public MyComputer(String processor, long hddSize, long ram, int coreNumber, String videoCard) {
        this.processor = processor;
        this.hddSize = hddSize;
        this.ram = ram;
        this.coreNumber = coreNumber;
        this.videoCard = videoCard;
    }



    @Override
    public String toString() {
        return "MyComputer{" +
                "processor='" + processor + '\'' +
                ", hddSize=" + hddSize +
                ", ram=" + ram +
                ", coreNumber=" + coreNumber +
                ", videoCard='" + videoCard + '\'' +
                '}';
    }



    public static void main(String[] args) {
        System.out.println(new MyComputer());
        MyComputer comp = new MyComputer("Core i5", 2 *  (long) Math.pow(1024, 4), 16 * (long) Math.pow(1024, 2), 4, "Nvidia");
        System.out.println(comp);

    }
}
