package com.malcolmdeck.adventofcode2021.levels.level13;

import com.malcolmdeck.adventofcode2021.levels.level12.Level12;
import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level13 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level13\\level13data.txt");
        try {
            Scanner scanner = new Scanner(file);
            boolean[][] grid = new boolean[1311][895];
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    break;
                }
                int x = Integer.parseInt(line.split(",")[0]);
                int y = Integer.parseInt(line.split(",")[1]);
                grid[x][y] = true;
            }
            grid = foldAlongX(grid, 655);
            System.out.println("Answer: " + count(grid));
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level13\\level13data.txt");
        try {
            Scanner scanner = new Scanner(file);
            boolean[][] grid = new boolean[1311][895];
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("")) {
                    break;
                }
                int x = Integer.parseInt(line.split(",")[0]);
                int y = Integer.parseInt(line.split(",")[1]);
                grid[x][y] = true;
            }
            // I know I should parse for this, but it was honestly easir to just hard code it.
            grid = foldAlongX(grid, 655);
            grid = foldAlongY(grid, 447);
            grid = foldAlongX(grid, 327);
            grid = foldAlongY(grid, 223);
            grid = foldAlongX(grid, 163);
            grid = foldAlongY(grid, 111);
            grid = foldAlongX(grid, 81);
            grid = foldAlongY(grid, 55);
            grid = foldAlongX(grid, 40);
            grid = foldAlongY(grid, 27);
            grid = foldAlongY(grid, 13);
            grid = foldAlongY(grid, 6);
            print(grid);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static boolean[][] foldAlongX(boolean[][] grid, int xSplit) {
        boolean[][] newGrid = new boolean[xSplit][grid[0].length];
        for (int i = 0; i < xSplit; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                newGrid[i][j] = grid[i][j] || grid[grid.length - i - 1][j];
            }
        }
        return newGrid;
    }

    private static boolean[][] foldAlongY(boolean[][] grid, int ySplit) {
        boolean[][] newGrid = new boolean[grid.length][ySplit];
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < ySplit; ++j) {
                newGrid[i][j] = grid[i][j] || grid[i][grid[0].length - j - 1];
            }
        }
        return newGrid;
    }

    private static int count(boolean[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j]) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static int print(boolean[][] grid) {
        int count = 0;
        for (int j = 0; j < grid[0].length; ++j) {
            for (int i = 0; i < grid.length; ++i) {
                if (grid[i][j]) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        return count;
    }

}
