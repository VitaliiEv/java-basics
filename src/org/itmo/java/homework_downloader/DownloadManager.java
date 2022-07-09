package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
//    private final String destinationPath;
//    private final TaskList<String, DownloadFile> TASK_LIST = Main.getTaskList();

    public DownloadManager(int streams) {
        this.STREAMS = streams;
    }

    @Override
    public void run() {
        LOGGER.info("Downloading manager started");
        ExecutorService executor = Executors.newFixedThreadPool(this.STREAMS);
        List<Map.Entry<String, DownloadFile>> notStartedTasks = Main.getNewTasksList();
        while (!notStartedTasks.isEmpty()) {
            for (Map.Entry<String, DownloadFile> entry : notStartedTasks) {
                entry.getValue().setStatus(Status.IN_QUEUE);
                executor.execute(entry.getValue());
            }
            notStartedTasks = Main.getNewTasksList();
//            notStartedTasks.stream()
//                    .forEach(entry -> {
//                        entry.getValue().setStatus(Status.IN_QUEUE);
//                        executor.execute(entry.getValue());
//                    });
        }
        LOGGER.info("Downloading manager finished");
    }

    public String getCurrentStats() {
        return null;
    }

    public String getFinalStats() {
        return null;

    }

}
