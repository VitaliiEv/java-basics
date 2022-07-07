package org.itmo.java.homework_downloader;

import java.util.AbstractMap;

public class Task<K,V> extends AbstractMap.SimpleEntry<K,V>{

    private K link;
    private V filename;

    public Task(K link, V fileName) {
        super(link, fileName);
    }
}
