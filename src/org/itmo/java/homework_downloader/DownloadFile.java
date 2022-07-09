package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import static org.itmo.java.homework_downloader.Status.*;

public class DownloadFile implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private URL url;
    private String file;
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
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void run() {
        LOGGER.info(this.url.toExternalForm(), "Downloading: {}");
        this.status = RUNNING;
        // Emulate doing something
        try {
            for (int i = 0; i < this.url.getPath().length(); i++) {
                Thread.sleep(50);
                LOGGER.error("Thread {} doing something {}", Thread.currentThread().getName(), i);
            }
            this.status = FINISHED;
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted", e.getMessage());
            this.status = FAILED;
        }
//        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
//             FileOutputStream fileOutputStream = new FileOutputStream(this.file)) {
//            fileOutputStream.getChannel().transferFrom(readableByteChannel,0, Long.MAX_VALUE);
//            this.status = FINISHED;
//        } catch (IOException e) {
//            LOGGER.error("Cant open connection, {}", e.getMessage());
//            this.status = FAILED;
//        }
    }
}
