package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.List;


import static org.itmo.java.homework_downloader.Status.*;

public class Statistics {

    private Statistics() {
        throw new IllegalStateException("Utility class");
    }


    public static String getOverallStats(TaskList<String, DownloadFile> taskList, SourceFileParser sourceFileParser) {
        List<DownloadFile> finished = taskList.getFilteredTasks(FINISHED);
        long fileNumber = finished.size();
        long fileNumberTotal = sourceFileParser.getLinesTotal();
        double sizeTotal = getOverallSize(finished);
        Duration duration = getOverallDuration(finished);
        String timeTotal = durationToString(duration);
        double averageSpeed = sizeTotal / duration.getSeconds();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Downloaded: ")
                .append(fileNumber)
                .append(" files from ")
                .append(fileNumberTotal)
                .append(", total size: ")
                .append(FileSizeHumanReadable.getSize((long) sizeTotal))
                .append("\n")
                .append("Time: ")
                .append(timeTotal)
                .append("\n")
                .append("Average Speed: ")
                .append(FileSizeHumanReadable.getSize((long) averageSpeed))
                .append("/s\n");
        return String.valueOf(stringBuilder);
    }

    private static double getOverallSize(List<DownloadFile> list) {
        return list.stream()
                .mapToLong(DownloadFile::getFileSize)
                .sum();
    }

    private static Duration getOverallDuration(List<DownloadFile> list) {
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

    public static String durationToString(Duration d) {
        StringBuilder result = new StringBuilder();
        if (d.getSeconds() == 0 || d.toMinutes() == 0) {
            result.append(d.getSeconds())
                    .append(".")
                    .append(d.toMillis())
                    .append(" seconds");
            return result.toString();
        }

        if (d.toHours() > 0) {

            result.append(d.toHours())
                    .append(" hours ");
        }
        if (d.toMinutes() > 0) {
            d = d.minusHours(d.toHours());
            result.append(d.toMinutes())
                    .append(" minutes ");
        }
        if (d.getSeconds() > 0) {
            d = d.minusMinutes(d.toMinutes());
            result.append(d.getSeconds())
                    .append(" seconds");
        }
        return result.toString();
    }

}
