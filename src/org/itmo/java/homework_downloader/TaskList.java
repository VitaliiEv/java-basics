package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TaskList<K, V> {
    private static final Logger LOGGER = Main.getLogger();
    private final ConcurrentMap<K, V> taskMap;
    private int size;

    public TaskList() {
        this.taskMap = new ConcurrentHashMap<>();
    }

    public synchronized int getSize() {
        return this.size;
    }

    public <T extends java.util.Map.Entry<K, V>> void taskCreate(T task) throws UnsupportedOperationException {
        taskCreate(task.getKey(), task.getValue());
    }

    public void taskCreate(K link, V task) throws UnsupportedOperationException {
        if (this.taskMap.putIfAbsent(link, task) != null) {
            throw new UnsupportedOperationException("Ignored duplicate link: " + link);
        }
        synchronized (this) {
            this.size++;
            notifyAll();
        }
    }

    public V taskRun() {
        try {
            synchronized (this) {
                while (this.size == 0 && Main.fileParserIsAlive()) {
                    LOGGER.info("Parser running, waiting for new elements");
                    wait(); //for new tasks or parser finishing its job,
                }
            }
        } catch (InterruptedException e) {
            // todo throw new RuntimeException(e);
        }
        synchronized (this) {
            Iterator<K> iterator = this.taskMap.keySet().iterator(); // refresh iterator
            V task = this.taskMap.remove(iterator.next());
            this.size--;
            return task;
        }
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
