package com.malcolmdeck.adventofcode2021.levels.level14;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level14 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level14\\level14data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String initialPolymer = scanner.next();
            scanner.nextLine();
            scanner.nextLine();
            Map<String, String> insertionMap = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                insertionMap.put(line.split(" -> ")[0], line.split(" -> ")[1]);
            }
            for (int i = 0; i < 10; ++i) {
                initialPolymer = iterate(initialPolymer, insertionMap);
            }
            Long[] counts = charCounts(initialPolymer);
            System.out.println("Answer: " + (counts[counts.length - 1] - counts[0]));
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level14\\level14data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String initialPolymer = scanner.next();
            scanner.nextLine();
            scanner.nextLine();
            Map<String, String> insertionMap = new HashMap<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                insertionMap.put(line.split(" -> ")[0], line.split(" -> ")[1]);
            }
            PairCounter pairCounter = new PairCounter(insertionMap.keySet());
            for (int i = 0; i < initialPolymer.length() - 1; ++i) {
                pairCounter.addToCount(initialPolymer.substring(i, i + 2), 1);
            }
            for (int i = 0; i < 40; ++i) {
                System.out.println("On iteration: " + i);
                PairCounter nextPairCounter = new PairCounter(insertionMap.keySet());
                for (String key : insertionMap.keySet()) {
                    String firstNewPair = key.substring(0, 1) + insertionMap.get(key);
                    String secondNewPair = insertionMap.get(key) + key.substring(1, 2);
                    long countToMove = pairCounter.pairCountMap.get(key);
                    nextPairCounter.addToCount(firstNewPair, countToMove);
                    nextPairCounter.addToCount(secondNewPair, countToMove);
                }
                pairCounter = nextPairCounter;
            }
            Long[] counts = pairsToLetterCounts(pairCounter.pairCountMap);
            Arrays.sort(counts);
            System.out.println("Answer: " + (counts[counts.length - 1] - counts[0]));
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static class PairCounter {
        Map<String, Long> pairCountMap;

        PairCounter(Set<String> possiblePairs) {
            this.pairCountMap = new HashMap<>(possiblePairs.size());
            for (String pair : possiblePairs) {
                pairCountMap.put(pair, 0l);
            }
        }

        void addToCount(String pair, long amount) {
            pairCountMap.put(pair, pairCountMap.get(pair) + amount);
        }
    }

    private static Long[] pairsToLetterCounts(Map<String, Long> pairCountMap) {
        Map<Character, Long> charCountMap = new HashMap<>();
        for (String key : pairCountMap.keySet()) {
            charCountMap.put(key.charAt(0), charCountMap.getOrDefault(key.charAt(0), 0l) + pairCountMap.get(key));
            charCountMap.put(key.charAt(1), charCountMap.getOrDefault(key.charAt(1), 0l) + pairCountMap.get(key));
        }
        // Adjust for ends, which are the only places not double counted
        // (i.e. ABCD becomes AB, BC, CD, so all but A and D are double counted)
        charCountMap.put('S', charCountMap.getOrDefault('S', 0l) + 1l);
        charCountMap.put('V', charCountMap.getOrDefault('V', 0l) + 1l);
        for (Character key : charCountMap.keySet()) {
            charCountMap.put(key, charCountMap.get(key) / 2);
        }
        return charCountMap.values().toArray(new Long[0]);
    }

    private static String iterate(String input, Map<String, String> key) {
        StringBuilder builder = new StringBuilder();
        builder.append(input.charAt(0));
        for (int i = 0; i < input.length() - 1; ++i) {
            builder.append(key.get(input.substring(i, i+2)));
            builder.append(input.charAt(i+1));
        }
        return builder.toString();
    }

    private static Long[] charCounts(String input) {
        Map<Character,Long> frequencies = new HashMap<>();
        for (char ch : input.toCharArray())
            frequencies.put(ch, frequencies.getOrDefault(ch, 0l) + 1);
        Long[] counts = frequencies.values().toArray(new Long[0]);
        Arrays.sort(counts);
        return counts;
    }

}
