package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

import static java.nio.file.StandardOpenOption.*;
import static org.itmo.java.homework_downloader.Status.*;

public class DownloadFile implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    //    private static final String USER_AGENT = "Download Manager v.1 by Solomonov V.E.";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:102.0) Gecko/20100101 " +
            "Firefox/102.0";
    private URL url;
    private URLConnection connection;
    private Path downloadFilePath;
    private Status status;
    private long fileSize;
    private Temporal startTime;
    private Temporal finishTime;

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

    public Path getFileName() {
        return this.downloadFilePath.getFileName();
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public Temporal getStartTime() {
        return this.startTime;
    }

    public Temporal getFinishTime() {
        return this.finishTime;
    }

    private Duration getDuration() {
        return Duration.between(this.startTime, this.finishTime);
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public void run() {
        this.startTime = Instant.now();
        String logMessage;
        logMessage = "Downloading file: " + this.downloadFilePath.getFileName() + " from " + this.url.toExternalForm();
        LOGGER.info(logMessage);
        System.out.println(logMessage);
        this.status = RUNNING;
        if (Files.exists(this.downloadFilePath)) {
            logMessage = this.downloadFilePath.getFileName().toString();
            LOGGER.info("Found duplicate file: {}, renaming", logMessage);
            this.downloadFilePath = getNewFileName(this.downloadFilePath);
        }
        try {
            this.connection = this.url.openConnection();
            this.connection.setRequestProperty("User-Agent", USER_AGENT);
            this.fileSize = this.connection.getContentLengthLong();
            download();
        } catch (IOException e) {
            LOGGER.error("Cant open connection, {}", e.getMessage());
            this.status = FAILED;
        }
    }

    private void download() {
        String logMessage;
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(this.connection.getInputStream());
             FileChannel fileChannel = FileChannel.open(this.downloadFilePath, WRITE, CREATE_NEW)) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            this.finishTime = Instant.now();

            logMessage = "Finished downloading file: " + this.downloadFilePath.getFileName() + " from " +
                    this.url.toExternalForm() + "\n" + "File size: " + FileSizeHumanReadable.getSize(this.fileSize) +
                    ". Download time: " + Statistics.durationToString(getDuration());
            LOGGER.info(logMessage);
            System.out.println(logMessage);
            this.status = FINISHED;
        } catch (IOException e) {
            LOGGER.error("Cant create input stream from connection. {}", e.getMessage());
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


}
