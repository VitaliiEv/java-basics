package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DownloadManager implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
    private final TaskList<String, DownloadFile> TASK_LIST;
    private final SourceFileParser SOURCE_FILE_PARSER;

    public DownloadManager(int streams, TaskList<String, DownloadFile> taskList, SourceFileParser sourceFileParser) {
        if (taskList == null || sourceFileParser == null) {
            throw new NullPointerException("Arguments must not be null");
        }
        this.STREAMS = streams;
        this.TASK_LIST = taskList;
        this.SOURCE_FILE_PARSER = sourceFileParser;
    }


    @Override
    public void run() {
        String logMessage;
         LOGGER.info("Downloading manager thread started");

        ExecutorService executor = Executors.newFixedThreadPool(this.STREAMS);
        List<DownloadFile> notStartedTasks = this.TASK_LIST.getNewTasks();
        while (!notStartedTasks.isEmpty()) {
            notStartedTasks.forEach(task -> {
                task.setStatus(Status.IN_QUEUE);
                executor.execute(task);
            });
            notStartedTasks = TASK_LIST.getNewTasks();
        }
        while (!this.TASK_LIST.hasUnfinishedTasks()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error("Download manager interrupted");
                throw new RuntimeException(e);
            }
        }
        executor.shutdown();
        logMessage = Statistics.getOverallStats(this.TASK_LIST, this.SOURCE_FILE_PARSER);
        LOGGER.info(logMessage);
        System.out.println(logMessage);
    }

}
