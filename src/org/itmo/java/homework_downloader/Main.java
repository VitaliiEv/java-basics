package org.itmo.java.homework_downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Консольная утилита для скачивания файлов по HTTP протоколу.
 * Входные параметры:
 * //TODO
 * Пример вызова:
 * java -jar utility.jar 5 output_folder links.txt
 * Формат файла со ссылками:
 * <HTTP ссылка><пробел><имя файла, под которым его надо
 * сохранить>
 * пример:
 * http://example.com/archive.zip my_archive.zip
 * http://example.com/image.jpg picture.jpg
 * ......
 * В HTTP ссылке нет пробелов, нет encoded символов и прочего — это всегда
 * обычные ссылки с английскими символами без специальных символов в именах
 * файлов и прочего. Ссылкам можно не делать decode. Ссылки без авторизации, не
 * HTTPS/FTP — всегда только HTTP-протокол
 * Ссылки могут повторяться в файле, но с разными именами для сохранения,
 * например:
 * http://example.com/archive.zip first_archive.zip
 * http://example.com/archive.zip second_archive.zip
 * <p>
 * Одинаковые ссылки — это нормальная ситуация, ее необходимо учитывать. То
 * есть, нет смысла загружать одно и тоже дважды, особенно если речь идет о
 * гигабайтах.
 * <p>
 * Потоки
 * Подразумевается, что один поток качает один файл, не надо качать один файл в
 * несколько потоков. То есть если файлов 2, а потоков 3, то надо запустить два
 * потока, каждый из которых будет загружать один файл.
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

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static SourceFileParser sourceFileParser;
    private static Thread sourceFileParserThread;
    private static final String SOURCE_FILE_PARSER_THREAD_NAME = "Source File Parser";
    private static DownloadManager downloadManager;
    private static Thread downloadManagerThread;
    private static final String DOWNLOAD_MANAGER_THREAD_NAME = "Download Manager";
    private static final TaskList<String, DownloadFile> TASK_LIST = new TaskList<>();

//    private static final Status STATUS;
//    private static final String STATUS_THREAD_NAME = "";

    public static void main(String[] args) {
        //TODO Logger level settings
        LOGGER.info("Debug log is enabled: {}", LOGGER.isDebugEnabled());
        args = new String[3];
        args[0] = "4";
        args[1] = "E:\\Документы\\Виталий\\Учеба\\ИТМО\\124-19-Java(Basics)\\Практика\\";
        args[2] = "E:\\Документы\\Виталий\\Учеба\\ИТМО\\124-19-Java(Basics)\\Практика\\список.txt";

        ArgsParser argsParser = new ArgsParser(args);
        int streams = argsParser.getStreams();
        String destination = argsParser.getDestinationPath();

        sourceFileParser = new SourceFileParser(argsParser.getSourcePath(), destination, TASK_LIST);
        sourceFileParserThread = new Thread(sourceFileParser, SOURCE_FILE_PARSER_THREAD_NAME);

        downloadManager = new DownloadManager(streams, TASK_LIST);
        downloadManagerThread = new Thread(downloadManager, DOWNLOAD_MANAGER_THREAD_NAME);

        sourceFileParserThread.start();
        downloadManagerThread.start();
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static synchronized boolean fileParserIsAlive() {
        return sourceFileParserThread.isAlive();
    }

    public static Status getParserStatus() {
        return sourceFileParser.getStatus();
    }
}
