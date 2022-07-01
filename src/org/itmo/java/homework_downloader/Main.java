package org.itmo.java.homework_downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.*;


/**
 * Консольная утилита для скачивания файлов по HTTP протоколу.
 * Входные параметры:
 * //TODO
 * Пример вызова:
 * java -jar utility.jar 5 output_folder links.txt
 * Формат файла со ссылками:
 * //TODO
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
    /**
     * Путь к файлу со списком ссылок
     */
    private static String sourceFile;
    /**
     * Количество одновременно качающих потоков (1,2,3,4....)
     */
    private static int streams;
    /**
     * Имя папки, куда складывать скачанные файлы
     */
    private static String destination;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        //TODO Logger level settings
//        logger.setLevel
        //TODO parse file, parse args
        streams = 1;
        sourceFile = "E:\\Документы\\Виталий\\Учеба\\ИТМО\\124-19-Java(Basics)\\Практика\\список.txt";
        destination = "E:\\Документы\\Виталий\\Учеба\\ИТМО\\124-19-Java(Basics)\\Практика\\";
        logger.info("Hello World");
//        logger.debug(1, t);

        //Todo try с ресурсами стр 65
        try {
//            File f = new File(sourceFile);
//            FileInputStream f = new FileInputStream(new File(sourceFile));
            //Todo task in map key URL, value -object
            //Todo. start download while parsing file reading
            FileInputStream f = new FileInputStream(sourceFile);
        } catch (FileNotFoundException e) {
            logger.error("Не удается найти указанный файл",e);
        } finally {
            logger.info("Goodbye in finally block");
        }
        logger.info("Goodbye");
//
    }
}
