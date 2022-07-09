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

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void run() {
        LOGGER.info("Downloading: {}", this.url.toExternalForm());
        this.status = RUNNING;
        if (Files.exists(this.downloadFilePath)) {
            this.downloadFilePath = getNewFileName(this.downloadFilePath);
        }
        // Emulate doing something
//        try {
//            for (int i = 0; i < this.url.getPath().length()/10; i++) {
//                Thread.sleep(5);
//                LOGGER.error("Thread {} doing something {}, link: {}", Thread.currentThread().getName(), i, this
//                .url.toExternalForm());
//            }
//            LOGGER.info("Finished: {}", this.url.toExternalForm());
//            this.status = FINISHED;
//        } catch (InterruptedException e) {
//            LOGGER.error("Interrupted", e.getMessage());
//            this.status = FAILED;
//        }
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(this.url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(this.downloadFilePath.toString())) {//todo use NIO
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            LOGGER.info("Finished: {}", this.url.toExternalForm());
            this.status = FINISHED;
        } catch (IOException e) {
            LOGGER.error("Cant open connection, {}", e.getMessage());
            this.status = FAILED;
        }
    }

    public Path getNewFileName(Path p) {
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
