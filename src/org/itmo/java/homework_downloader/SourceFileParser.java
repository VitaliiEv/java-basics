package org.itmo.java.homework_downloader;

import org.slf4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.util.Objects;
import java.util.regex.Pattern;


public class SourceFileParser implements Runnable {
    private static final Logger LOGGER = Main.getLogger();
    private static final String SEPARATOR = "\\s+";
    private static final Pattern SEPARATOR_MATCHER = Pattern.compile(SEPARATOR);
    private final String SOURCE_PATH;
    private String DESTINATION_PATH;
    private final TaskList<String, DownloadFile> TASK_LIST;
    private int linesTotal = 0;
    private int linesAdded = 0;
    private Status status = Status.NOT_STARTED;

    // todo migrate to nio
    public SourceFileParser(String sourcePath, String destinationPath) {
        try {
            this.SOURCE_PATH = Objects.requireNonNull(sourcePath);
            this.DESTINATION_PATH = Objects.requireNonNull(destinationPath);
        } catch (NullPointerException e) {
            String message = "Source path not set.";
            LOGGER.error(message, e.getMessage());
            throw new NullPointerException(message);
        }
        this.TASK_LIST = new TaskList<>();
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public void run() {
        status = Status.RUNNING;
        try (FileReader fileReader = new FileReader(this.SOURCE_PATH);
             BufferedReader bufferedReader = new BufferedReader(Objects.requireNonNull(fileReader))) {
            LOGGER.info("Buffer ready: {}", bufferedReader.ready());
            while (bufferedReader.ready()) {
                this.linesTotal++;
                Task<String, DownloadFile> task = parseTask(bufferedReader.readLine());
                if (task != null) {
                    addTask(task);
                }
            }
            String message = "Parsing source file finished. Formed " + this.linesAdded + " download tasks from " +
                    this.linesTotal + " lines";
            LOGGER.info(message);
            message = this.TASK_LIST.toString();
            LOGGER.debug(message);
        } catch (NullPointerException e) {
            LOGGER.error("Source file or file path is null, {}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Cant access or read source file, {}", e.getMessage());
        } finally {
            status = Status.FINISHED;
        }
    }

    private Task<String, DownloadFile> parseTask(String line) {
        String[] taskStr = SEPARATOR_MATCHER.split(line, 2);
        URL validUrl = validateUrl(taskStr[0]);
        String validFilename = validateFilename(taskStr[1]);
        if (validUrl != null && validFilename != null) {
            return new Task<>(validUrl.toExternalForm(), new DownloadFile(validUrl, validFilename));
        } else {
            return null;
        }
    }

    public URL validateUrl(String link) {
        try {
            // todo CHECK if valid HTTP URL
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

    public String validateFilename(String fileName) {
        try {
            //todo migrate to NIO PATH
            // somehow while parsing directory the last symbol \ is trimmed
            String absPath = this.DESTINATION_PATH + FileSystems.getDefault().getSeparator() + fileName.trim();
            return Paths.get(absPath).toString();
        } catch (InvalidPathException e) {
            // todo, revalidate filename
            LOGGER.warn("Invalid filename, task ignored, {}", e.getMessage());
        }
        return null;
    }

    public TaskList<String, DownloadFile> getTaskList() {
        return TASK_LIST;
    }

    public void addTask(Task<String, DownloadFile> task) {
        try {
            this.TASK_LIST.taskCreate(task);
            this.linesAdded++;
        } catch (UnsupportedOperationException e) {
            LOGGER.warn("Task not added, {}", e.getMessage());
        }
    }
}