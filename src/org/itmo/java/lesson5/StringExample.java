package org.itmo.java.lesson5;

public class StringExample {
    public static void main(String[] args) {
        String java = "Java";

//        String s = java.toLowerCase();

//        System.out.println(s);

//        String java2 = new String("JaVa");

//        System.out.println(java == java2);
//        System.out.println(java.equals(java2));
//        System.out.println(java.equalsIgnoreCase(java2));
//
//
//        java = java.concat(" Developer");
//        System.out.println(java);
//

//        java = java + 123;

//        System.out.println(java);

//        java = 80 + 120 + java + (140 + 160);

//        System.out.println(java);

//        int length = java.length();
//
//        boolean empty = java.isEmpty();
//        String s = "";
//
//        System.out.println(java.substring(2));
//        System.out.println(java.substring(2, 6));
//
//        String string = " Hello world!  ";
//        System.out.println(string);
//        System.out.println(string.trim());

//        System.out.println(string.replaceAll("l", "5"));

        String text = "Ќорвеги€ отказалась пропускать грузы дл€ российских поселений на Ўпицбергене";

        String[] strings = text.split(" ");
        for (String str : strings) {
//            System.out.println(str);
        }

        Integer number = 1231241;

        String str = String.valueOf(number);
//        System.out.println(str);

        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (Character.isDigit(aChar)) {
//                System.out.println("true");
            }
        }


        StringBuilder builder = new StringBuilder(); // was text
        StringBuilder builder2 = new StringBuilder(text);
//        System.out.println(builder);

//        System.out.println(text == builder.toString());
//        System.out.println(builder == builder2);
//        System.out.println(builder.equals(builder2));
//        System.out.println(text.equals(builder.toString()));
//        System.out.println(text.equals(builder));


        for (String str1 : text.split(" ")) {
            builder.append(str1)
                    .append(", ");
        }

//        String string = builder.toString();

//        System.out.println(builder);

        String string = builder.reverse().toString();
//        System.out.println(string);

        builder.insert(3, "MOSCOW");

//        System.out.println(builder);


        StringBuffer buffer = new StringBuffer();


        String s = "ћне б€ка по-насто€щему нравитьс€ больше чего-либо в разработке ѕќ делать фреймворки, Ѕ€ка позвол€ющие другим разработчикам создавать что-то крутое. Ѕя ј »ногда, в погоне за идеальным кодом, ко мне на ум приход€т странные идеи, но б€кјпри реализации который C# может дойти до предела своих возможностей.\n" +
                "Ќе так давно произЅ€каошЄл подобный случайЅ€ а, когда Ѕя ј и мы вместе с коллегой искали способ избежать передачи большого количества типовых параметров в тех местах, где компил€тор должен был по идее их вывести. ќднако б€ а , C# так устроен, что способен выводить типы в обобщЄнных вызовах только из передаваемых параметров метода.";

        System.out.println(s);
        String word = "б€ка";
        System.out.println(replaceWord(s, word));
    }

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