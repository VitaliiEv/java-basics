package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgsParser {
    private static final Logger LOGGER = Main.getLogger();
    private int streams;
    private String sourcePath;
    private String destinationPath;


    public ArgsParser(String[] arguments) {
        try {
            this.streams = parseStreams(arguments[0]);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid number of streams {}", e.getMessage());
        }
        try {
            this.destinationPath = parseDestinationPath(arguments[1]);
        } catch (IOException e) {
            LOGGER.error("Invalid destination path {}", e.getMessage());
        }
        try {
            this.sourcePath = parseSourcePath(arguments[2]);
        } catch (IOException e) {
            LOGGER.error("Invalid source path {}", e.getMessage());
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
        //todo migrate to NIO PATH, add exceptions
        Path f = Paths.get(path).normalize().toAbsolutePath();
        if (Files.isRegularFile(f)) {
            return f.toString();
        } else {
            throw new IOException("Not a file at given path");
        }
    }

    private String parseDestinationPath(String path) throws IOException {
        //todo migrate to NIO PATH, add exceptions
        Path d = Paths.get(path).normalize().toAbsolutePath();
        if (Files.isDirectory(d)) {
            return d.toString();
        } else {
            throw new IOException("Not a directory at given path");
        }
    }
}
