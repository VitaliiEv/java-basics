package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.util.*;

public class TaskList<K, V> {
    private static final Logger LOGGER = Main.getLogger();
    private final Map<K, V> taskMap;

    public TaskList() {
        this.taskMap = new LinkedHashMap<>();
    }

    public synchronized int getSize() {
        return this.taskMap.size();
    }

    public <T extends java.util.Map.Entry<K, V>> void taskCreate(T task) throws UnsupportedOperationException {
        taskCreate(task.getKey(), task.getValue());
    }

    public synchronized void taskCreate(K link, V task) throws UnsupportedOperationException {
        // todo move to synchronized block?
        if (this.taskMap.putIfAbsent(link, task) != null) {
            throw new UnsupportedOperationException("Ignored duplicate link: " + link);
        }
        notifyAll();
    }

    public synchronized V taskRun() {
        // todo move to synchronized block?
        try {
            while (this.taskMap.size() == 0 && Main.fileParserIsAlive()) {
                LOGGER.info("Parser running, waiting for new elements");
                // todo never interrupts
                wait(); //for new tasks or parser finishing its job,
            }
        } catch (InterruptedException e) {
            // todo throw new RuntimeException(e);
        }
        Iterator<K> iterator = this.taskMap.keySet().iterator();
        return this.taskMap.remove(iterator.next());
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
