package com.malcolmdeck.adventofcode2021.levels.level8;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level8 {

    public static void partOne() throws Exception {
    File file = FileHelper.getFile("level8\\level8data.txt");
        try {
        Scanner scanner = new Scanner(file);
        int sum = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();;
            String firstHalf = line.split("\\|")[0];
            String secondHalf = line.split("\\|")[1];
            String[] outputValues = secondHalf.split(" ");
            for (int i = 0; i < outputValues.length; ++i) {
                if (outputValues[i].length() == 2 ||
                        outputValues[i].length() == 3 ||
                        outputValues[i].length() == 4 ||
                        outputValues[i].length() == 7) {
                    ++sum;
                }
            }
        }
        System.out.println("Sum: " + sum);
    } catch (
    FileNotFoundException e) {
        System.out.println("FileNotFound: " + e.getMessage());
    }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level8\\level8data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int sum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();;
                String firstHalf = line.split(" \\| ")[0];
                String secondHalf = line.split(" \\| ")[1];
                Decoder decoder = new Decoder(firstHalf);
                sum += decoder.decode(secondHalf);
            }
            System.out.println("Sum: " + sum);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static class Decoder {

        Map<String, Integer> decode;

        Decoder(String input) {
            decode = new HashMap<>(10);
            String[] numbers = input.split(" ");
            numbers = normalizeStrings(numbers);
            Arrays.sort(numbers, Comparator.comparingInt(String::length));
            decode.put(numbers[0], 1);
            decode.put(numbers[1], 7);
            decode.put(numbers[2], 4);
            decode.put(numbers[9], 8);
            for (int i = 3; i < 6; ++i) {
                if (numCharsInCommon(numbers[0], numbers[i]) == 2) {
                    decode.put(numbers[i], 3);
                } else if (numCharsInCommon(numbers[2], numbers[i]) == 3) {
                    decode.put(numbers[i], 5);
                } else {
                    decode.put(numbers[i], 2);
                }
            }
            for (int i = 6; i < 9; ++i) {
                if (numCharsInCommon(numbers[2], numbers[i]) == 4) {
                    decode.put(numbers[i], 9);
                } else if (numCharsInCommon(numbers[0], numbers[i]) == 2) {
                    decode.put(numbers[i], 0);
                } else {
                    decode.put(numbers[i], 6);
                }
            }
        }

        int decode(String input) {
            String[] digits = input.split(" ");
            digits = normalizeStrings(digits);
            int sum = 0;
            for (int i = 0; i < digits.length; ++i) {
                sum *= 10;
                try {
                    sum += decode.get(digits[i]);
                } catch (Exception e) {
                    throw e;
                }
            }
            return sum;
        }

        private static int numCharsInCommon(String a, String b) {
            int sum = 0;
            for (int i = 0; i < a.length(); ++i) {
                for (int j = 0; j < b.length(); ++j) {
                    if (a.charAt(i) == b.charAt(j)) {
                        ++sum;
                    }
                }
            }
            return sum;
        }
    }

    private static String[] normalizeStrings(String[] input) {
        String[] output = new String[input.length];
        for (int i = 0; i < input.length; ++i) {
            char[] chars = input[i].toCharArray();
            Arrays.sort(chars);
            output[i] = new String(chars);
        }
        return output;
    }
}
