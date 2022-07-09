package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class TaskList<K, V extends DownloadFile> {
    private static final Logger LOGGER = Main.getLogger();
    private final Map<K, V> taskMap;
    private List<Map.Entry<K, V>> newTaskList; // iterable list of tasks with "NOT_STARTED" Status

    public TaskList() {
        this.taskMap = new LinkedHashMap<>();
    }

    public synchronized <T extends java.util.Map.Entry<K, V>> void taskCreate(T task) throws UnsupportedOperationException {
        taskCreate(task.getKey(), task.getValue());
    }

    public synchronized void taskCreate(K link, V task) throws UnsupportedOperationException {
        // todo move to synchronized block?
        V t = this.taskMap.putIfAbsent(link, task);
        notifyAll(); // notify dm in any case: when task is created or not
        if (t != null) {
            throw new UnsupportedOperationException("Ignored duplicate link: " + link);
        }

    }

    public synchronized List<Map.Entry<K, V>> getNewTasks() {
        // todo move to synchronized block?
        if (this.newTaskList.isEmpty()) {
            updateNewTasksList(); // firstly try to update
        }
        if (this.newTaskList.isEmpty() && Main.fileParserIsAlive()) { // if after update list is empty and parser
            // running - wait for new elements
            waitForNewTasks();
            updateNewTasksList(); // reinitialize newTasksList
        }
        return this.newTaskList;
    }

    private synchronized void waitForNewTasks() {
        while (Main.fileParserIsAlive()) {
            LOGGER.info("Parser running, waiting for new elements");
            // todo never interrupts
            try {
                wait(); //for new tasks or parser finishing its job,
            } catch (InterruptedException e) {
                // todo throw new RuntimeException(e);
            }
        }
    }

    public synchronized void updateNewTasksList() {
        this.newTaskList = this.taskMap.entrySet().stream()
                .filter(kvEntry -> kvEntry.getValue().getStatus() == Status.NOT_STARTED)
                .collect(Collectors.toList());
        //todo check that no Objects created!
        notifyAll();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<K, V> e : this.taskMap.entrySet()) {
            str.append(e.getKey()).append(" ").append(e.getValue()).append("\n");
        }
        return String.valueOf(str);
    }

}
