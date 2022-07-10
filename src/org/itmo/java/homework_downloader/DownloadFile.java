package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.itmo.java.homework_downloader.Status.*;

public class DownloadFile implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private URL url;
//    private String file;
    private Path downloadFilePath;
    private Status status;
    private Double fileSize;
    private Double startTime;
    private Double finishTime;
    private Double currentlyDownloadedSize;
    private Double currentTime;
    /**
     * Консольная утилита для скачивания файлов по HTTP протоколу.
     * Входные параметры:
     * Пример вызова:
     * java -jar utility.jar 5 output_folder links.txt
     * <p>
     * Выходные данные:
     * 1. Все файлы загружаются в n потоков.
     * 2. В процессе работы утилита должна выводить статистику — время работы и
     * количество скачанных байт виде:
     * <p>
     * Загружается файл: %ИМЯ%
     * Файл %ИМЯ% загружен: 1 MB за 1 минуту
     * <p>
     * 3. После завершения работы программа выводит:
     * Загружено: 17 файлов, 2.3 MB
     * Время: 2 минуты 13 секунд
     * Средняя скорость: 17.2 kB/s
     */

    public DownloadFile(URL url, Path file) throws NullPointerException {
        //Parameters are checked for validity in SourceFileParser
        if (url == null || file == null) {
            throw new NullPointerException();
        }
        this.url = url;
        this.status = NOT_STARTED;
        this.downloadFilePath = file;
    }

        public Status getStatus() {
        return this.status;
    }

    public Path getDownloadFilePath() {
        return this.downloadFilePath;
    }

    public Path getFileName() {
        return this.downloadFilePath.getFileName();
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public void run() {
        String logMessage;
        logMessage = this.url.toExternalForm();
        LOGGER.info("Downloading: {}", logMessage);
        this.status = RUNNING;
        if (Files.exists(this.downloadFilePath)) {
            logMessage = this.downloadFilePath.getFileName().toString();
            LOGGER.info("Found duplicate file: {}", logMessage);
            this.downloadFilePath = getNewFileName(this.downloadFilePath);
        }

        try (ReadableByteChannel readableByteChannel = Channels.newChannel(this.url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(this.downloadFilePath.toString())) {//todo use NIO
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            logMessage = this.url.toExternalForm();
            LOGGER.info("Finished: {}", logMessage);
            this.status = FINISHED;
        } catch (IOException e) {
            LOGGER.error("Cant open connection, {}", e.getMessage());
            this.status = FAILED;
        }
    }

    private Path getNewFileName(Path p) {
        Path fileName = p.getFileName();
        String str = fileName.toString();
        int i = 1;
        while (Files.exists(p)) {
            String newStr = str.replaceFirst("[.]", " (" + i + ").");
            p = p.getParent().resolve(Paths.get(String.valueOf(newStr)));
            i++;
        }
        return p;
    }
}
