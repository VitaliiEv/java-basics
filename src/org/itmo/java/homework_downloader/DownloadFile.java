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
    private String file;
    private Path downloadFilePath;
    private Status status;


    // todo migrate NIO
    public DownloadFile(URL url, String file) throws NullPointerException {
        //Parameters are checked for validity in SourceFileParser
        if (url == null || file == null) {
            throw new NullPointerException();
        }
        this.url = url;
        this.file = file;
        this.status = NOT_STARTED;
        this.downloadFilePath = Paths.get(this.file);
    }

    public Status getStatus() {
        return status;
    }

    public Path getDownloadFilePath() {
        return downloadFilePath;
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
