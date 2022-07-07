package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
    private final String destinationPath;
    private final TaskList<String, DownloadFile> TASK_LIST;

    // Todo  Dw should have thread pool, so status checker can get stats
    public DownloadManager(int streams, String destinationPath, TaskList<String, DownloadFile> taskList) {
        this.STREAMS = streams;
        this.destinationPath = destinationPath;
        this.TASK_LIST = taskList;
    }


    @Override
    public void run() {
        LOGGER.info("Downloading manager started");
        //todo what if parse hasnt started yet?
        ExecutorService executor = Executors.newFixedThreadPool(this.STREAMS);
        // todo is checking on thread alive correct?  should check if thread finished
        while (this.TASK_LIST.getSize() > 0 || Main.fileParserIsAlive()) {
            boolean a = this.TASK_LIST.getSize() > 0;
            boolean b = Main.fileParserIsAlive();
            try {
                synchronized (this) {
                    // содержимое листа мняется между строками 28-32
                    while (this.TASK_LIST.getSize() == 0) {
                        LOGGER.info("Waiting for new elements");
                        wait();
                    }
                }
                DownloadFile task = this.TASK_LIST.taskRun();
                //Todo. start download while parsing file reading
                // who controls download number
                executor.execute(task);
            } catch (InterruptedException e) {
                // todo throw new RuntimeException(e);
            }
        }
        executor.shutdown();
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
