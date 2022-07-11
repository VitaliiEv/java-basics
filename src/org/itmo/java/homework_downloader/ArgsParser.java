package org.itmo.java.homework_downloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgsParser {
    private final int STREAMS;
    private final Path SOURCE_PATH;
    private final Path DESTINATION_PATH;


    public ArgsParser(String[] arguments) throws NumberFormatException, InvalidPathException, IOException {
        // no catching - illegal arguments must crash program
        this.STREAMS = Integer.parseInt(arguments[0]);
        this.DESTINATION_PATH = Paths.get(arguments[1]).toAbsolutePath();
        if (!Files.isDirectory(this.DESTINATION_PATH)) {
            throw new IOException("Not a directory at given path");
        }
        this.SOURCE_PATH = Paths.get(arguments[2]).toAbsolutePath();
        if (!Files.isRegularFile(this.SOURCE_PATH) || !Files.exists(this.SOURCE_PATH)) {
            throw new IOException("Not a file at given path");
        }
    }

    public int getStreams() {
        return this.STREAMS;
    }

    public Path getSourcePath() {
        return this.SOURCE_PATH;
    }

    public Path getDestinationPath() {
        return this.DESTINATION_PATH;
    }
}
