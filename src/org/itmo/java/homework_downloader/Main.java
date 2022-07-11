package org.itmo.java.homework_downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;

/**
 * Консольная утилита для скачивания файлов по HTTP протоколу.
 * Входные параметры:
 * Пример вызова:
 * java -jar utility.jar 5 output_folder links.txt
 * <p>
 * Выходные данные:
 * 1. Все файлы загружаются в n потоков.
 * 2. В процессе работы утилита должна выводить статистику — время работы и
 * количество скачанных байт виде:
 * <p>
 * Загружается файл: %ИМЯ%
 * Файл %ИМЯ% загружен: 1 MB за 1 минуту
 * <p>
 * 3. После завершения работы программа выводит:
 * Загружено: 17 файлов, 2.3 MB
 * Время: 2 минуты 13 секунд
 * Средняя скорость: 17.2 kB/s
 */
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
    private static final TaskList<String, DownloadFile> TASK_LIST = new TaskList<>();
    private static SourceFileParser sourceFileParser;
    private static DownloadManager downloadManager;

//    private static final Status STATUS;
//    private static final String STATUS_THREAD_NAME = "";

    public static void main(String[] args) throws IOException {
        LOGGER.info("Debug log is enabled: {}", LOGGER.isDebugEnabled());
        args = new String[3];
        args[0] = "4";
        args[1] = "E:\\Документы\\Виталий\\Учеба\\ИТМО\\124-19-Java(Basics)\\Практика\\";
        args[2] = "E:\\Документы\\Виталий\\Учеба\\ИТМО\\124-19-Java(Basics)\\Практика\\список.txt";

        ArgsParser argsParser = new ArgsParser(args);
        int streams = argsParser.getStreams();
        Path destination = argsParser.getDestinationPath();
        Path sourceFile = argsParser.getSourcePath();
        sourceFileParser = new SourceFileParser(sourceFile, destination, TASK_LIST);
        Thread sourceFileParserThread = new Thread(sourceFileParser, SOURCE_FILE_PARSER_THREAD_NAME);

        downloadManager = new DownloadManager(streams, TASK_LIST);
        Thread downloadManagerThread = new Thread(downloadManager, DOWNLOAD_MANAGER_THREAD_NAME);

        sourceFileParserThread.start();
        downloadManagerThread.start();
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static Status getParserStatus() {
        return sourceFileParser.getStatus();
    }
}
