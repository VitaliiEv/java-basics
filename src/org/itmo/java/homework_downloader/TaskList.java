package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static org.itmo.java.homework_downloader.Status.*;

public class TaskList<K, V extends DownloadFile> {
    private static final Logger LOGGER = Main.getLogger();
    private final Map<K, V> TASK_MAP;
    private List<V> newTaskList; // iterable list of tasks with "NOT_STARTED" Status
    private SourceFileParser sourceFileParser;

    public TaskList() {
        this.TASK_MAP = new HashMap<>();
    }

    public void setSourceFileParser(SourceFileParser sourceFileParser) {
        if (sourceFileParser == null) {
            throw new NullPointerException("Cant set source file parser");
        }
        this.sourceFileParser = sourceFileParser;
    }

    public synchronized <T extends java.util.Map.Entry<K, V>> void taskCreate(T task) throws UnsupportedOperationException {
        taskCreate(task.getKey(), task.getValue());
    }

    public synchronized void taskCreate(K link, V task) throws UnsupportedOperationException {
        V t = this.TASK_MAP.putIfAbsent(link, task);
        notifyAll(); // notify dm in any case: when task is created or not
        if (t != null) {
            throw new UnsupportedOperationException("Ignored duplicate link: " + link);
        }

    }

    public synchronized List<V> getNewTasks() {
        updateNewTasksList(); // firstly try to update to  add new tasks and filter out queued tasks
        if (this.newTaskList.isEmpty() && this.sourceFileParser.getStatus() != FINISHED && this.sourceFileParser.getStatus() != FAILED) {
            // if after update list is empty and  parser running - wait for new elements
            // todo check what happens if sourcefileparser failed
            waitForNewTasks();
            updateNewTasksList(); // reinitialize newTasksList
        }
        return this.newTaskList; //must return empty only if parser finished
    }

    public synchronized List<V> getFilteredTasks(Status status) {
        return this.TASK_MAP.values().stream()
                .filter(v -> v.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public synchronized long countFilteredTasks(Status status) {
        return this.TASK_MAP.entrySet().stream()
                .filter(kvEntry -> kvEntry.getValue().getStatus().equals(status))
                .count();
    }

    private synchronized void waitForNewTasks() {
        while (this.sourceFileParser.getStatus() != FINISHED) {
            LOGGER.info("Parser running, waiting for new elements");
            try {
                wait(); //for new tasks or parser finishing its job,
            } catch (InterruptedException e) {
                // todo throw new RuntimeException(e);
            }
        }
    }

    public synchronized void updateNewTasksList() {
        this.newTaskList = getFilteredTasks(NOT_STARTED);
        notifyAll();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<K, V> e : this.TASK_MAP.entrySet()) {
            str.append(e.getKey())
                    .append(" ")
                    .append(e.getValue().getFileName().toString())
                    .append("\n");
        }
        return String.valueOf(str);
    }

}
