package org.itmo.java.homework_downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;


public class Main {
    static {
        // must set before the Logger loads logging.properties from the classpath
        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            String path = classLoader.getResource("resources/logging.properties").getFile();
            path = URLDecoder.decode(path, "UTF-8"); //System.getProperty("file.encoding") doesnt work
            System.setProperty("java.util.logging.config.file", path);
        } catch (NullPointerException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String SOURCE_FILE_PARSER_THREAD_NAME = "Source File Parser";
    private static final String DOWNLOAD_MANAGER_THREAD_NAME = "Download Manager";

    public static void main(String[] args) throws IOException {
        LOGGER.info("Debug log is enabled: {}", LOGGER.isDebugEnabled());

        ArgsParser argsParser = new ArgsParser(args);
        int streams = argsParser.getStreams();
        Path destination = argsParser.getDestinationPath();
        Path sourceFile = argsParser.getSourcePath();
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Downloading from: ")
                .append(sourceFile.getFileName().toString())
                .append(" in ")
                .append(streams)
                .append(" streams\nto ")
                .append(destination.toString());
        LOGGER.info(logMessage.toString());
        System.out.println(logMessage);

        TaskList<String, DownloadFile> taskList = new TaskList<>();
        SourceFileParser sourceFileParser = new SourceFileParser(sourceFile, destination, taskList);
        taskList.setSourceFileParser(sourceFileParser);
        Thread sourceFileParserThread = new Thread(sourceFileParser, SOURCE_FILE_PARSER_THREAD_NAME);

        DownloadManager downloadManager = new DownloadManager(streams, taskList, sourceFileParser);
        Thread downloadManagerThread = new Thread(downloadManager, DOWNLOAD_MANAGER_THREAD_NAME);

        sourceFileParserThread.start();
        downloadManagerThread.start();


    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
