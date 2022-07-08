package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
    private final String destinationPath;
    private final TaskList<String, DownloadFile> TASK_LIST = Main.getTaskList();

    public DownloadManager(int streams, String destinationPath) {
        this.STREAMS = streams;
        this.destinationPath = destinationPath;
    }

    @Override
    public void run() {
        LOGGER.info("Downloading manager started");
        ExecutorService executor = Executors.newFixedThreadPool(this.STREAMS);
        while (this.TASK_LIST.getSize() > 0 || Main.fileParserIsAlive()) {
            DownloadFile task = this.TASK_LIST.taskRun();
            executor.execute(task);
        }
    }

    public String getCurrentStats() {
        return null;
    }

    public String getFinalStats() {
        return null;

    }

    public synchronized void dmNotify() {
        notifyAll();
    }
}
