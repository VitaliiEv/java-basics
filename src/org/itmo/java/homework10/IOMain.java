package org.itmo.java.homework10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOMain {

    static {
        // must set before the Logger loads logging.properties from the classpath
        try {
            ClassLoader classLoader = IOMain.class.getClassLoader();
            String path = classLoader.getResource("resources/logging.properties").getFile();
            path = URLDecoder.decode(path, "UTF-8"); //System.getProperty("file.encoding") doesn't work
            System.setProperty("java.util.logging.config.file", path);
        } catch (NullPointerException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(IOMain.class);

    public static void main(String[] args) {

//        1.	Написать метод, который читает текстовый файл и возвращает его в виде списка строк.
        String message = fileToLines("files/list.txt").toString();
        System.out.println(message);

//        2.	Написать метод, который записывает в файл строку, переданную параметром.
        writeLine("files/out.txt", "test");
        writeLine("files/out.txt", "test2");

        //        3.	Используя решение 1 и 2, напишите метод, который склеивает два текстовый файла один.
        concatFile("files/out2.txt", "files/out.txt", "files/out2.txt");

//        4.	Написать метод который заменяет в файле все кроме букв и цифр на знак ‘$’
        replaceWithSymbol("[^а-яА-Яa-zA-Z\\d]",'&', "files/out3.txt");
        replaceWithSymbol("[e]",'%', "files/out3.txt");
        replaceWithSymbol("[в]","РУССКАЯ БУКВА", "files/out3.txt");
    }

    public static List<String> fileToLines(String path) {
        List<String> linesList = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(path), Charset.defaultCharset())) {
            linesList = lines.collect(Collectors.toList());
        } catch (IOException | InvalidPathException e) {
            LOGGER.error("Something went wrong", e);

        }
        return linesList; //returns empty list
    }

    public static void writeLine(String path, String line) {
        List<String> lines = fileToLines(path);
        lines.add(line);
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
            for (String l : lines) {
                br.write(l);
                br.newLine();
            }
        } catch (FileNotFoundException e) {
            IOMain.LOGGER.error("File not found: ", e);
        } catch (IOException e) {
            IOMain.LOGGER.error("Something went wrong", e);
        }
    }

    public static void concatFile(String path1, String path2, String result) {
        List<String> lines1 = fileToLines(path1);
        List<String> lines2 = fileToLines(path2);
        lines1.addAll(lines2);
        try (BufferedWriter br = new BufferedWriter(new FileWriter(result))) {
            for (String l : lines1) {
                br.write(l);
                br.newLine();
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: ", e);
        } catch (IOException e) {
            LOGGER.error("Something went wrong", e);
        }
    }

    public static void replaceWithSymbol(String pattern, char replace, String path) {
        replaceWithSymbol(pattern, String.valueOf(replace), path);
    }

    public static void replaceWithSymbol(String pattern, String replace, String path) {
        List<String> lines = fileToLines(path);
        Pattern p = Pattern.compile(pattern);
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
            for (String l : lines) {
                br.write(p.matcher(l).replaceAll(replace));
                br.newLine();
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: ", e);
        } catch (IOException e) {
            LOGGER.error("Something went wrong", e);
        }
    }
}
