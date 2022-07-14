package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.itmo.java.homework_downloader.Status.*;


public class SourceFileParser implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private static final String SEPARATOR = "\\s+";
    private static final Pattern SEPARATOR_MATCHER = Pattern.compile(SEPARATOR);
    private final Path SOURCE_PATH;
    private final Path DESTINATION_PATH;
    private final TaskList<String, DownloadFile> TASK_LIST;
    private int linesTotal = 0;
    private int linesAdded = 0;
    private Status status = NOT_STARTED;


    public SourceFileParser(Path sourceFile, Path destinationPath, TaskList<String, DownloadFile> taskList) {
        this.SOURCE_PATH = sourceFile;
        this.DESTINATION_PATH = destinationPath;
        this.TASK_LIST = taskList;
    }

    public Status getStatus() {
        return this.status;
    }

    public int getLinesTotal() {
        return linesTotal;
    }

    @Override
    public void run() {
        String message;
        this.status = RUNNING;
        try (Stream<String> lines = Files.lines(this.SOURCE_PATH, Charset.defaultCharset())) {
            message = this.SOURCE_PATH.getFileName().toString();
            LOGGER.info("Parsing source file: {}", message);
            for (String s: lines.collect(Collectors.toList())) {
                this.linesTotal++;
                Task<String, DownloadFile> task = parseTask(s);
                if (task != null) {
                    addTask(task);
                }
            }
            this.status = FINISHED;
            this.TASK_LIST.updateNewTasksList(); // in order to notify DM that job is done
            message = "Parsing source file finished. Formed " + this.linesAdded + " download tasks from " +
                    this.linesTotal + " lines";
            LOGGER.info(message);
            message = String.format("Formed list of tasks: %n") + this.TASK_LIST.toString();
            LOGGER.debug(message);

        } catch (NullPointerException e) {
            LOGGER.error("Source file or file path is null, {}", e.getMessage());
            throw new NullPointerException(e.getMessage());
        } catch (IOException e) {
//            LOGGER.error("Cant access or read source file, {}", e.getMessage());
            this.status = FAILED;
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addTask(Task<String, DownloadFile> task) {
        try {
            this.TASK_LIST.taskCreate(task);
            this.linesAdded++;
        } catch (UnsupportedOperationException e) {
            LOGGER.warn("Task not added, {}", e.getMessage());
        }
    }

    private Task<String, DownloadFile> parseTask(String line) {
        String[] taskStr = SEPARATOR_MATCHER.split(line, 2);
        URL validUrl = validateUrl(taskStr[0]);
        Path validFilename = validateFilename(taskStr[1]);
        if (validUrl != null && validFilename != null) {
            return new Task<>(validUrl.toExternalForm(), new DownloadFile(validUrl, validFilename));
        } else {
            return null;
        }
    }

    public URL validateUrl(String link) {
        try {
            URL url = new URL(link);
            if (!url.getProtocol().equals("http")) {
                throw new UnsupportedOperationException("only http supported: " + link);
            }
            return url;
        } catch (UnsupportedOperationException | MalformedURLException e) {
            LOGGER.warn("Invalid URL, task ignored, {}", e.getMessage());
        }
        return null;
    }

    public Path validateFilename(String fileName) {
        try {
            return this.DESTINATION_PATH.resolve(Paths.get(fileName.trim()));
        } catch (InvalidPathException e) {
            LOGGER.warn("Invalid filename, task ignored, {}", e.getMessage());
        }
        return null;
    }
}