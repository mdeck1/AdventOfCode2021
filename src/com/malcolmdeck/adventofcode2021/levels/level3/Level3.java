package com.malcolmdeck.adventofcode2021.levels.level3;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Level3 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level3\\level3data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[] counts = new int[12];
            int numLines = 0;
            for (int i = 0; i < 12; ++i) {
                counts[i] = 0;
            }
            while (scanner.hasNextLine()) {
                numLines++;
                String reader = scanner.next();
                for (int i = 0; i < 12; ++i) {
                    if (reader.charAt(i) == '1') {
                        counts[i]++;
                    }
                }
            }
            StringBuilder gammaBuilder = new StringBuilder(12);
            StringBuilder epsilonBuilder = new StringBuilder(12);
            for (int i = 0; i < 12; ++i) {
                if (counts[i]*2 >= numLines) {
                    gammaBuilder.append("1");
                    epsilonBuilder.append("0");
                } else {
                    gammaBuilder.append("0");
                    epsilonBuilder.append("1");
                }
            }
            int mult = Integer.valueOf(gammaBuilder.toString(), 2) * Integer.valueOf(epsilonBuilder.toString(), 2);
            System.out.println("Result: " + mult);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    /**
     * I know the code here is ugly (and probably pretty slow), but it seemed more fun to me to just get the answer
     * than to write it more efficiently by using integers as pointers into the ArrayList.
     * Also, I know sorting takes this from O(N) to O(N*log(N)), but the input size is small enough that it doesn't
     * really matter. I don't mind being philosophically impure, here, because there's no reason to worry about
     * efficiency in any case but the presented one. (Classic YAGNI).
     */
    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level3\\level3data.txt");
        try {
            Scanner scanner = new Scanner(file);
            ArrayList<String> allValues = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                allValues.add(scanner.next());
            }
            Collections.sort(allValues);
            ArrayList<String> oxygenList = new ArrayList(allValues);
            ArrayList<String> co2List = new ArrayList(allValues);
            for (int i = 0; i < 12; ++i) {
                if (oxygenList.size() > 1) {
                    oxygenList = stripForOxygenRating(oxygenList, i);
                }
                if (co2List.size() > 1) {
                    co2List = stripForCO2Rating(co2List, i);
                }
            }
            int mult = Integer.valueOf(oxygenList.get(0), 2) * Integer.valueOf(co2List.get(0), 2);
            System.out.println("Result: " + mult);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static ArrayList stripForOxygenRating(ArrayList<String> input, int bit) {
        ArrayList<String> stripped = new ArrayList<>();
        int zeroCount = 0;
        for (String value : input) {
            if (value.charAt(bit) == '0') {
                ++zeroCount;
            }
        }
        if (zeroCount*2 > input.size()) {
            stripped.addAll(input.subList(0, zeroCount));
        } else {
            stripped.addAll(input.subList(zeroCount, input.size()));
        }
        return stripped;
    }

    private static ArrayList stripForCO2Rating(ArrayList<String> input, int bit) {
        ArrayList<String> stripped = new ArrayList<>();
        int zeroCount = 0;
        for (String value : input) {
            if (value.charAt(bit) == '0') {
                ++zeroCount;
            }
        }
        if (zeroCount*2 <= input.size()) {
            stripped.addAll(input.subList(0, zeroCount));
        } else {
            stripped.addAll(input.subList(zeroCount, input.size()));
        }
        return stripped;
    }

}
