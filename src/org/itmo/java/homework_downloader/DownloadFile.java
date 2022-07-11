package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.itmo.java.homework_downloader.Status.*;

public class DownloadFile implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private URL url;
    private URLConnection connection;
    private Path downloadFilePath;
    private Status status;
    private double fileSize;
    private Double startTime;
    private Double finishTime;
    private double currentSize = 0;
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

    public double getFileSize() {
        return this.fileSize;
    }

    public Double getStartTime() {
        return this.startTime;
    }

    public Double getFinishTime() {
        return this.finishTime;
    }

    public double getCurrentSize() {
        return this.currentSize;
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
        try {
            this.connection = this.url.openConnection();
            this.fileSize = FileSizeHumanReadable.getBinary(this.connection.getContentLengthLong());
            download();
        } catch (IOException e) {
            LOGGER.error("Cant open connection, {}", e.getMessage());
            this.status = FAILED;
        }
    }

    private void download() {
        String logMessage;
        try (BufferedInputStream bis = new BufferedInputStream(this.connection.getInputStream());
         FileOutputStream fos = new FileOutputStream(this.downloadFilePath.toString())) {
            int b;
            while ((b = bis.read()) != -1) {
                fos.write(b);
                this.currentSize++;
            }
            logMessage = this.url.toExternalForm();
            LOGGER.info("Finished: {}", logMessage);
            this.status = FINISHED;
        } catch (FileNotFoundException e) {
            LOGGER.error("Cant open destination file, {}", e.getMessage());
            this.status = FAILED;
        } catch (IOException e) {
            LOGGER.error("Cant create input stream from connection, {}", e.getMessage());
            this.status = FAILED;
        }
    }

    private Path getNewFileName(Path p) {
        Path fileName = p.getFileName();
        String str = fileName.toString();
        int i = 1;
        while (Files.exists(p)) {
            String newStr = str.replaceFirst("[.]", " (" + i + ").");
            p = p.getParent().resolve(Paths.get(newStr));
            i++;
        }
        return p;
    }

    public String getProgress() {
        return String.format("%1$5.2f", this.currentSize / this.fileSize * 100);
    }

    public double getProgressRaw() {
        return this.currentSize / this.fileSize;
    }
}
