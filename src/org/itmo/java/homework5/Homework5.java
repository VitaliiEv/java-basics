package org.itmo.java.homework5;

import java.sql.SQLOutput;

public class Homework5 {

    public static void main(String[] args) {
        String example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec egestas, nisi et auctor " +
                "ultricies, metus lacus condimentum justo, non venenatis lacus mi eu libero. Nullam vulputate, tellus" +
                " bibendum accumsan congue, risus dui accumsan massa, et condimentum velit libero et velit. Nunc " +
                "luctus lacus vitae enim pellentesque, ac commodo nulla venenatis. Aliquam in massa in est ultricies " +
                "venenatis. Vivamus ac lobortis erat. Nam consequat cursus ipsum, sed eleifend mi aliquam vel. " +
                "Phasellus elementum ligula et nisl viverra, ac fermentum magna maximusasdasdasd.";
//        1.	Написать метод для поиска самого длинного слова в тексте.
        System.out.println(findLongest(example));

//        2.	Написать метод, который проверяет является ли слово палиндромом.
        System.out.println(findPalindrome("Tenet"));
        System.out.println(findPalindrome("Топот"));
        System.out.println(findPalindrome("Java"));

//        3.	Напишите метод, заменяющий в тексте все вхождения слова «бяка» на «[вырезано цензурой]»
        String example2 = "Мне бяка по-настоящему нравиться больше чего-либо в разработке ПО делать фреймворки, Бяка " +
                "позволяющие другим разработчикам создавать что-то крутое. БЯКА Иногда, в погоне за идеальным кодом, " +
                "ко мне на ум приходят странные идеи, но бякАпри реализации который C# может дойти до предела своих " +
                "возможностей.\n" +
                "Не так давно произошёл подобный случай, когда БЯКА и мы вместе с коллегой искали способ избежать " +
                "передачи большого количества типовых параметров в тех местах, где компилятор должен был по идее их " +
                "вывести. Однако бяКа , C# так устроен, что способен выводить типы в обобщённых вызовах только из " +
                "передаваемых параметров метода.";
        System.out.println(censorBadWord(example2, "Бяка", "[censored]"));
        System.out.println();
        System.out.println(replaceWord(example2, "Бяка"));

//        4.	Имеются две строки. Найти количество вхождений одной (являющейся подстрокой) в другую.
        String example3 = censorBadWord(example2, "Бяка", "[censored]");
        System.out.println(countSubstring(example3, "censored"));

//        5.	Напишите метод, который инвертирует слова в строке. Предполагается, что в строке нет знаков
//        препинания, и слова разделены пробелами.
//                Sample Output:
//        The given string is: This is a test string
//        The string reversed word by word is:
//        sihT si a tset gnirts
        String reversed = wordReverse("This is a test string");

    }

    public static String findLongest(String str) {
        String[] splittedStr = str.split(" ");
        String longest = splittedStr[0];
        for (int i = 1; i < splittedStr.length; i++) {
            splittedStr[i] = splittedStr[i].replace(",", "").replace(".", "");
            if (splittedStr[i].length() > longest.length()) {
                longest = splittedStr[i];
            }
        }
        return longest;
    }

    public static boolean findPalindrome(String str) {
        return str.equalsIgnoreCase(String.valueOf((new StringBuilder(str)).reverse()));
    }

    public static String censorBadWord(String str, String badWord, String censored) {
        // Возможно неоптимальное решение, т.к не разбивает предварительно строку на множество коротких, однако
        // при этом не зависит от типа разделителя.
        StringBuilder strNew = new StringBuilder(str);
        String tempStr = String.valueOf(str).toLowerCase();
        badWord = badWord.toLowerCase();
        int badWordLength = badWord.length();
        int censoredLength = censored.length();
        int currentIndex = 0;
        int badWordIndex = tempStr.indexOf(badWord);
        while (badWordIndex != -1) {
            currentIndex += badWordIndex; // пересчитываем индекс с учетом первого вхождения обрезанной строки
            tempStr = tempStr.substring(badWordIndex + badWordLength); // обрезаем строку слева
            strNew.replace(currentIndex, currentIndex + badWordLength, censored);
            currentIndex += censoredLength; // пересчитываем индекс первого вхождения с учетом длины censored
            badWordIndex = tempStr.indexOf(badWord);
        }
        return String.valueOf(strNew);
    }

    public static int countSubstring(String str, String subStr) {
        String tempStr = str;
        int subStrLength = subStr.length();
        int index = 0;
        while (tempStr.contains(subStr)) {
            index++;
            tempStr = tempStr.substring(tempStr.indexOf(subStr) + subStrLength);
        }
        return index;
    }

    public static String wordReverse(String str) {
        System.out.println("The given string is: "+ str);
    //        The given string is: This is a test string
//        The string reversed word by word is:
//        sihT si a tset gnirts
        StringBuilder strNew = new StringBuilder();
        String separator = " ";
        for (String s : str.split(separator)) {
            strNew.append((new StringBuilder(s)).reverse())
                    .append(separator);
        }
        str = String.valueOf(strNew);
        System.out.println("The string reversed word by word is:");
        System.out.println(str);
        return str;

    }


    // Решение Артура
    private static String replaceWord(String text, String word) {
        StringBuilder builder = new StringBuilder();
        String str = "[censored]";
        for (String s : text.split(" ")) {
            if (s.equalsIgnoreCase(word)) {

                builder.append(str);

            } else if (s.toLowerCase().contains(word.toLowerCase())) {
                int beginIndex = s.toLowerCase().indexOf(word.toLowerCase());
                String substring = s.substring(beginIndex, word.length() + beginIndex);
                String s1 = s.replaceAll(substring, str);
                builder.append(s1);

            } else {
                builder.append(s);
            }

            builder.append(" ");
        }

        return builder.toString();

    }
}
