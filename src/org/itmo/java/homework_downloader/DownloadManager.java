package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
    private final String destinationPath;
    private final TaskList<String, DownloadFile> TASK_LIST;

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
//        try {
        synchronized (this) {
//            while (this.TASK_LIST.getSize() > 0 || Main.fileParserIsAlive()) {
                while (Main.fileParserIsAlive()) {
//                    boolean a = this.TASK_LIST.getSize() > 0;
//                    boolean b = Main.fileParserIsAlive();
//                    a = this.TASK_LIST.getSize() > 0;
//                    while (this.TASK_LIST.getSize() == 0) {
//                        LOGGER.info("Waiting for new elements");
//                        a = this.TASK_LIST.getSize() > 0;
//                        b = Main.fileParserIsAlive();
//                        wait(); //for new tasks or parser finishing its job,
//                    }
                    DownloadFile task = this.TASK_LIST.taskRun();
//                executor.execute(task);
                }
            }

//        } catch (InterruptedException e) {
//            // todo throw new RuntimeException(e);
//        }
        }

        public String getCurrentStats () {
            return null;
        }

        public String getFinalStats () {
            return null;

        }

        public synchronized void dmNotify () {
            notifyAll();
        }
    }
