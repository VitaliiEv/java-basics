package org.itmo.java.homework_downloader;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TaskList<K, V> {
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

    public synchronized void taskCreate(K link, V task) throws UnsupportedOperationException {
        if (this.taskMap.putIfAbsent(link, task) != null) {
            throw new UnsupportedOperationException("Ignored duplicate link: " + link);
        }
        this.size++;
        Main.dmNotify();
    }

    public synchronized V taskRun() {
        Iterator<K> iterator;
        iterator = this.taskMap.keySet().iterator(); // refresh iterator
        V task = this.taskMap.remove(iterator.next());
        this.size--;
        return task;
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
