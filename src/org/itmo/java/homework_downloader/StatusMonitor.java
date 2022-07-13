package org.itmo.java.homework_downloader;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.List;


import static org.itmo.java.homework_downloader.Status.*;

public class StatusMonitor implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final TaskList<String, DownloadFile> TASK_LIST;
    private final SourceFileParser SOURCE_FILE_PARSER;
    private final DownloadManager DOWNLOAD_MANAGER;

    public StatusMonitor(TaskList<String, DownloadFile> taskList, SourceFileParser sourceFileParser,
                         DownloadManager downloadManager) {
        if (taskList == null || sourceFileParser == null || downloadManager == null) {
            throw new NullPointerException("All arguments must not be null");
        }
        this.TASK_LIST = taskList;
        this.SOURCE_FILE_PARSER = sourceFileParser;
        this.DOWNLOAD_MANAGER = downloadManager;
    }

    @Override
    public void run() {
        clearConsole();
//        while (this.DOWNLOAD_MANAGER.DMStatus() == RUNNING) {
        try {

            Thread.sleep(20000); //todo
            System.out.println(getOverallStats());
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
//        }
    }

    public void clearConsole() {
//        System.out.
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
        long fileNumberTotal = this.SOURCE_FILE_PARSER.getLinesTotal();

        double sizeTotal = getOverallSize(finished); // todo not working
        Duration timeTotal = getOverallDuration(finished); //todo from DM start to the latest time
        double averageSpeed = sizeTotal / timeTotal.getSeconds(); //todo
        stringBuilder.append("Загружено: ")
                .append(fileNumber)
                .append(" файлов из ")
                .append(fileNumberTotal)
                .append(", общим размером: ")
                .append(sizeTotal) // todo human readable
                .append(" байт\n")
                .append("Время: ")
                .append(timeTotal.getSeconds())
                .append(" секунд\n")
                .append("Средняя скорость: ")
                .append(averageSpeed)
                .append("байт/с\n");
        return String.valueOf(stringBuilder);
    }

    private double getOverallSize(List<DownloadFile> list) {
        return list.stream()
                .mapToDouble(DownloadFile::getFileSize)
                .sum();
    }

    private Duration getOverallDuration(List<DownloadFile> list) {
        Temporal start = list.stream()
                .map(DownloadFile::getStartTime)
                .min(Comparator.comparing(Instant.class::cast))
                .orElse(null);
        Temporal finish = list.stream()
                .map(DownloadFile::getFinishTime)
                .max(Comparator.comparing(Instant.class::cast))
                .orElse(null);
        return Duration.between(start, finish);
    }

}
