package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.itmo.java.homework_downloader.Status.*;

public class DownloadManager implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
    private final TaskList<String, DownloadFile> TASK_LIST;
    private final SourceFileParser SOURCE_FILE_PARSER;
    private Status status = NOT_STARTED;

    public DownloadManager(int streams, TaskList<String, DownloadFile> taskList, SourceFileParser sourceFileParser) {
        if (taskList == null || sourceFileParser == null) {
            throw new NullPointerException("Arguments must not be null");
        }
        this.STREAMS = streams;
        this.TASK_LIST = taskList;
        this.SOURCE_FILE_PARSER = sourceFileParser;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        LOGGER.info("Downloading manager started");
        this.status = RUNNING;
        ExecutorService executor = Executors.newFixedThreadPool(this.STREAMS);
        List<DownloadFile> notStartedTasks = TASK_LIST.getNewTasks();
        while (!notStartedTasks.isEmpty()) {
            notStartedTasks.forEach(task -> {
                task.setStatus(Status.IN_QUEUE);
                executor.execute(task);
            });
            notStartedTasks = TASK_LIST.getNewTasks();
        }
        executor.shutdown();
        LOGGER.info("Download manager finished");
        this.status = FINISHED;
    }

}
