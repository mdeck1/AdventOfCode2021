package com.malcolmdeck.adventofcode2021.levels.level20;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level20 {

    private static final boolean DEBUG = true;
    private static final int SIZE = 100;

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level20\\level20data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String codex = scanner.nextLine();
            scanner.nextLine();
            boolean[][] grid = new boolean[SIZE][SIZE];
            int rowCounter = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int i = 0; i < line.length(); ++i) {
                    grid[rowCounter][i] = line.charAt(i) == '.' ? false : true;
                }
                rowCounter++;
            }
            if (DEBUG) {
                printGrid(grid);
                System.out.println("Count after 0: " + countLitPixelsInGrid(grid));
            }
            grid = enhance(grid, codex, false);
            grid = enhance(grid, codex, true);
            System.out.println("Answer: " + countLitPixelsInGrid(grid));
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level20\\level20data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String codex = scanner.nextLine();
            scanner.nextLine();
            boolean[][] grid = new boolean[SIZE][SIZE];
            int rowCounter = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int i = 0; i < line.length(); ++i) {
                    grid[rowCounter][i] = line.charAt(i) == '.' ? false : true;
                }
                rowCounter++;
            }
            if (DEBUG) {
                printGrid(grid);
                System.out.println("Count after 0: " + countLitPixelsInGrid(grid));
            }
            for (int i = 0; i < 50; ++i) {
                grid = enhance(grid, codex, i % 2 == 1);
            }
            System.out.println("Answer: " + countLitPixelsInGrid(grid));
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static boolean[][] enhance(boolean[][] input, String codex, boolean isOdd) {
        int[][] expandedCopy = new int[input.length + 4][input[0].length + 4];
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[0].length; ++j) {
                expandedCopy[i+2][j+2] = input[i][j] ? 1 : 0;
            }
        }
        if (isOdd) {
            for (int j = 0; j < input.length + 4; ++j) {
                expandedCopy[0][j] = 1;
                expandedCopy[1][j] = 1;
                expandedCopy[input.length + 2][j] = 1;
                expandedCopy[input.length + 3][j] = 1;
                expandedCopy[j][0] = 1;
                expandedCopy[j][1] = 1;
                expandedCopy[j][input.length + 2] = 1;
                expandedCopy[j][input.length + 3] = 1;
            }
        }
        boolean[][] result = new boolean[input.length + 2][input[0].length + 2];
        for (int i = 0; i < result.length; ++i) {
            for (int j = 0; j < result[0].length; ++j) {
                StringBuilder builder = new StringBuilder(9);
                builder.append(expandedCopy[i][j]);
                builder.append(expandedCopy[i][j+1]);
                builder.append(expandedCopy[i][j+2]);
                builder.append(expandedCopy[i+1][j]);
                builder.append(expandedCopy[i+1][j+1]);
                builder.append(expandedCopy[i+1][j+2]);
                builder.append(expandedCopy[i+2][j]);
                builder.append(expandedCopy[i+2][j+1]);
                builder.append(expandedCopy[i+2][j+2]);
                result[i][j] = translateFromCodex(builder.toString(), codex);
            }
        }
        if (DEBUG) {
            printGrid(result);
        }
        return result;
    }

    private static boolean translateFromCodex(String input, String codex) {
        return codex.charAt(Integer.parseInt(input, 2)) == '#';
    }

    private static void printGrid(boolean[][] grid) {
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j]) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private static int countLitPixelsInGrid(boolean[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

}
