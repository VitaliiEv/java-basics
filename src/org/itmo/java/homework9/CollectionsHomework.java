package org.itmo.java.homework9;

import java.util.*;

public class CollectionsHomework {
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        // задание 2
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
            list.add(i + 1);
        }
        Set<Integer> setWithoutDuplicates = removeDuplicate(list);
        System.out.println(list);
        System.out.println(setWithoutDuplicates);

        // задание 3
        List<Integer> arraylist1m = genList(new ArrayList<>(), 1000000);
        List<Integer> linkedList1m = genList(new LinkedList<>(), 1000000);
        System.out.println(measureProductivity(arraylist1m) + " nanoseconds");
        System.out.println(measureProductivity(linkedList1m) + " nanoseconds");


        // задание 4
        Map<User, Integer> scoreTable = new HashMap<>();
        scoreTable.put(new User("Иван"), 100);
        scoreTable.put(new User("Вася"), 20);
        scoreTable.put(new User("Петя"), 40);
        System.out.println(scoreTable);
        getUserScore(scoreTable);
    }

    public static <T, L extends List<T>> Set<T> removeDuplicate(L list) {
        Set<T> set = new HashSet<>();
        for (T item : list) {
            if (!set.add(item)) {
                System.out.println("Duplicate entry: " + item.toString());
            }
        }
        return set;
    }

    public static <T extends List<Integer>> T genList(T list, int length) {
        list.clear();
        for (int i = 0; i < length; i++) {
            list.add(RANDOM.nextInt(Integer.MAX_VALUE));
        }
        return list;
    }

    public static <T extends List<Integer>> double measureProductivity(T list) {
        int length = list.size();
        long start;
        long finish;
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            list.get(RANDOM.nextInt(length));
        }
        finish = System.nanoTime();

        return (double) finish - start;
    }

    static class User {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) {return false;}

            User user = (User) o;

            return name.equals(user.getName());
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static <T extends Map<User, Integer>> void getUserScore(T scoreTable) {
        System.out.print("To get user score enter username (case sensitive): "); // предполагаем что username уникально
        String userName = SCANNER.next();
        User userToFind = new User(userName);
        User result = null;
        for (User u : scoreTable.keySet()) {
            if (u.equals(userToFind)) {
                result = u;
                break;
            }
        }
        if (result != null) {
            System.out.println(userName + " score: " + scoreTable.get(result));
        } else {
            System.out.println("No such player");
        }
    }
}
