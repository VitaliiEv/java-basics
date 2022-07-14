package org.itmo.java.homework_downloader;

import java.util.AbstractMap;

public class Task<K,V> extends AbstractMap.SimpleEntry<K,V>{

    public Task(K link, V fileName) {
        super(link, fileName);
    }
}
