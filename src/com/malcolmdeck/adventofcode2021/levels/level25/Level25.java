package com.malcolmdeck.adventofcode2021.levels.level25;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level25 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level25\\level25data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int ROWS = 137;
            int COLS = 139;
            char[][] grid = new char[ROWS][COLS];
            for (int i = 0; i < ROWS; ++i) {
                String line = scanner.nextLine();
                for (int j = 0; j < COLS; ++j) {
                    grid[i][j] = line.charAt(j);
                }
            }
            int count = 0;
            while (true) {
                //printGrid(grid);
                ++count;
                GridAndStepCount gridAndStepCount = incrementGrid(grid);
                if (gridAndStepCount.stepCount == 0) {
                    break;
                }
            }
            printGrid(grid);
            System.out.println("Answer: " + count);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level25\\level25data.txt");
        try {
            Scanner scanner = new Scanner(file);

            System.out.println("Answer: " );
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static void printGrid(char[][] grid) {
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private static GridAndStepCount incrementGrid(char[][] input) {
        char [][] intermediate = new char[input.length][input[0].length];
        int count = 0;
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[0].length; ++j) {
                if (input[i][j == 0 ? input[0].length - 1 : j-1] == '>'
                        && input[i][j] == '.') {
                    count++;
                    intermediate[i][j] = '>';
                    intermediate[i][j == 0 ? input[0].length - 1 : j-1] = '.';
                } else if (intermediate[i][j] == '\u0000') {
                    intermediate[i][j] = input[i][j];
                }
            }
        }
        for (int j = 0; j < input[0].length; ++j) {
            for (int i = 0; i < input.length; ++i) {
                input[i][j] = '\u0000';
            }
        }
        for (int j = 0; j < input[0].length; ++j) {
            for (int i = 0; i < input.length; ++i) {
                if (intermediate[i == 0 ? (input.length - 1) : i - 1][j] == 'v'
                        && intermediate[i][j] == '.') {
                    count++;
                    input[i][j] = 'v';
                    input[i == 0 ? (input.length - 1) : i - 1][j] = '.';
                } else if (input[i][j] == '\u0000') {
                    input[i][j] = intermediate[i][j];
                }
            }
        }
        return new GridAndStepCount(count, input);
    }

    static class GridAndStepCount {
        int stepCount;
        char[][] grid;

        GridAndStepCount(int stepCount, char[][] grid) {
            this.stepCount = stepCount;
            this.grid = grid;
        }
    }

}
