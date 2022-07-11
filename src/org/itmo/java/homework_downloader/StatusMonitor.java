package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.itmo.java.homework_downloader.Status.*;

public class StatusMonitor implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final TaskList<String, DownloadFile> TASK_LIST;

    public StatusMonitor(TaskList<String, DownloadFile> taskList) {
        this.TASK_LIST = taskList;
    }

    @Override
    public void run() {
//        while (Main.getDMStatus() == RUNNING) {
//            try {
//                System.out.println(getOverallStats());
//                Thread.sleep(1000); //todo
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//
//            }
//        }
        System.out.println(getOverallStats());
    }
    /**
     * <p>
     * Загружается файл: %ИМЯ%
     * Файл %ИМЯ% загружен: 1 MB за 1 минуту
     * <p>
     * 3. После завершения работы программа выводит:
     * Загружено: 17 файлов, 2.3 MB
     * Время: 2 минуты 13 секунд
     * Средняя скорость: 17.2 kB/s
     */
    private String progressBar() {
        return null; // todo
    }


    /**
     * 3. После завершения работы программа выводит:
     * Загружено: 17 файлов, 2.3 MB
     * Время: 2 минуты 13 секунд
     * Средняя скорость: 17.2 kB/s
     */
    private String getOverallStats() {
        StringBuilder stringBuilder = new StringBuilder();
        List<DownloadFile> finished = this.TASK_LIST.getFilteredTasks(FINISHED);
        long fileNumber = finished.size();
        long fileNumberTotal = Main.getLinesTotal();

        double sizeTotal = getOverallSize(finished); // todo not working
        long timeTotal = 0; //todo from DM start to the latest time
        long averageSpeed = 0; //todo
        stringBuilder.append("Загружено: ")
                .append(fileNumber)
                .append(" файлов из ")
                .append(fileNumberTotal)
                .append(", общим размером: ")
                .append(sizeTotal) // todo human readable
                .append(" байт\n")
                .append("Время: ")
                .append(timeTotal)
                .append("\n")
                .append("Средняя скорость: ")
                .append(averageSpeed);
        return String.valueOf(stringBuilder);
    }

    private double getOverallSize(List<DownloadFile> list) {
        return list.stream()
                 .mapToDouble(DownloadFile::getFileSize)
                .sum();
    }

    private double getOverallTime(List<Map.Entry<String, DownloadFile>> list) {
        return 0; //todo
    }
}
