package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.itmo.java.homework_downloader.Status.*;

public class DownloadManager implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
    private final TaskList<String, DownloadFile> TASK_LIST;
    private Status status = NOT_STARTED;

    public DownloadManager(int STREAMS, TaskList<String, DownloadFile> TASK_LIST) {
        this.STREAMS = STREAMS;
        this.TASK_LIST = TASK_LIST;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public void run() {
        LOGGER.info("Downloading manager started");
        this.status = RUNNING;
        ExecutorService executor = Executors.newFixedThreadPool(this.STREAMS);
        List<Map.Entry<String, DownloadFile>> notStartedTasks = TASK_LIST.getNewTasks();
        while (!notStartedTasks.isEmpty()) {
//            for (Map.Entry<String, DownloadFile> entry : notStartedTasks) {
//                entry.getValue().setStatus(IN_QUEUE);
//                executor.execute(entry.getValue());
//            }
            notStartedTasks.stream()
                    .forEach(entry -> {
                        entry.getValue().setStatus(Status.IN_QUEUE);
                        executor.execute(entry.getValue());
                    });
            notStartedTasks = TASK_LIST.getNewTasks();
        }
        executor.shutdown();
        LOGGER.info("Download manager finished");
        this.status = FINISHED;
    }

}
