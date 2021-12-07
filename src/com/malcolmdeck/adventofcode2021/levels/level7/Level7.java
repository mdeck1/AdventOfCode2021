package com.malcolmdeck.adventofcode2021.levels.level7;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Level7 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level7\\level7data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String input = scanner.next();
            String[] inputArray = input.split(",");
            int[] crabPositions = new int[inputArray.length];
            for (int i = 0; i < crabPositions.length; ++i) {
                crabPositions[i] = Integer.parseInt(inputArray[i]);
            }
            Arrays.sort(crabPositions);
            System.out.println("Sum: " + computeSum(crabPositions));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static int computeSum(int[] positions) {
        int middleIndex = positions.length / 2;
        int sum = 0;
        for (int i = 0; i < positions.length; ++i) {
            sum += Math.abs(positions[i] - positions[middleIndex]);
        }
        return sum;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level7\\level7data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String input = scanner.next();
            String[] inputArray = input.split(",");
            Integer[] crabPositions = new Integer[inputArray.length];
            for (int i = 0; i < crabPositions.length; ++i) {
                crabPositions[i] = Integer.parseInt(inputArray[i]);
            }
            Arrays.sort(crabPositions);
            Integer sum = Integer.MAX_VALUE;
            for (int i = 0; i < 1914; ++i) {
                int value = computeSum(crabPositions, i);
                if (value < sum) {
                    sum = value;
                }
            }
            System.out.println("Min Sum: " + sum);

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static int computeSum(Integer[] positions, int value) {
        int sum = 0;
        for (int i = 0; i < positions.length; ++i) {
            int diff = Math.abs(positions[i] - value);
            sum += (int) (((double) diff * (diff + 1))/ 2.0);
        }
        return sum;
    }

}
