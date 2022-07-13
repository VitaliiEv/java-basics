package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgsParser {
    private static final Logger LOGGER = Main.getLogger();
    private final int STREAMS;
    private final Path SOURCE_PATH;
    private final Path DESTINATION_PATH;


    public ArgsParser(String[] arguments) throws NumberFormatException, InvalidPathException, IOException {
        // no catching - illegal arguments must crash program
        if (Integer.parseInt(arguments[0]) < 0) {
            LOGGER.warn("Incorrect streams number. Falling back to default");
            this.STREAMS = 1;
        } else {
            this.STREAMS = Integer.parseInt(arguments[0]);
        }
        this.DESTINATION_PATH = Paths.get(arguments[1]).toAbsolutePath();
        if (!Files.isDirectory(this.DESTINATION_PATH)) {
            throw new IOException("Not a directory at given path");
        }
        this.SOURCE_PATH = Paths.get(arguments[2]).toAbsolutePath();
        if (!Files.isRegularFile(this.SOURCE_PATH)) {
            throw new IOException("Not a file at given path");
        }
        if (!Files.exists(this.SOURCE_PATH)) {
            throw new IOException("File does not exist");
        }
        if (!Files.probeContentType(this.SOURCE_PATH).equals("text/plain")) {
            throw new IOException("Not a text file at given path");
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
