package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ArgsParser {
    private static final Logger LOGGER = Main.getLogger();

    /**
     * Количество одновременно качающих потоков (1,2,3,4....)
     */
    private int streams;
    /**
     * Путь к файлу со списком ссылок
     */
    private String sourcePath;
    /**
     * Имя папки, куда складывать скачанные файлы
     */
    private String destinationPath;


    public ArgsParser(String[] arguments) {
        try {
            this.streams = parseStreams(arguments[0]);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid number of streams", e.getMessage());
        }
        try {
            this.destinationPath = parseDestinationPath(arguments[1]);
        } catch (IOException e) {
            LOGGER.error("Invalid destination path", e.getMessage());
        }
        try {
            this.sourcePath = parseSourcePath(arguments[2]);
        } catch (IOException e) {
            LOGGER.error("Invalid source path", e.getMessage());
        }
    }

    public int getStreams() {
        return streams;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    private int parseStreams(String streams) throws NumberFormatException {
        return Integer.parseInt(streams);
    }

    private String parseSourcePath(String path) throws IOException {
        //todo migrate to NIO PATH
        File f = new File(path);
        if (f.isFile()) {
            return f.getCanonicalPath();
        } else {
            throw new IOException("Not a file at given path");
        }
    }

    private String parseDestinationPath(String path) throws IOException {
        //todo migrate to NIO PATH
        File d = new File(path);
        if (d.isDirectory()) {
            return d.getCanonicalPath();
        } else {
            throw new IOException("Not a directory at given path");
        }
    }
}
