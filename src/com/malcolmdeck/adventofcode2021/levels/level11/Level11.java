package com.malcolmdeck.adventofcode2021.levels.level11;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level11 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level11\\level11data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[][] grid = new int[10][10];
            for (int i = 0; i < 10; ++i) {
                String line = scanner.nextLine();
                for (int j = 0; j < 10; ++j) {
                    grid[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
            int sum = 0;
            for (int i = 0; i < 100; ++i) {
                sum += incrementGridAndReturnFlashCount(grid);
            }
            System.out.println(sum);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static final int incrementGridAndReturnFlashCount(int[][] grid) {
        boolean[][] hasFlashed = new boolean[10][10];
        Queue<Point> toBeRevisited = new ArrayDeque<>(1000);
        incrementAll(grid, toBeRevisited);
        int flashCount = 0;
        while (!toBeRevisited.isEmpty()) {
            Point currPoint = toBeRevisited.poll();
            if (!hasFlashed[currPoint.x][currPoint.y]) {
                incrementNextTo(grid, toBeRevisited, currPoint.x, currPoint.y);
                hasFlashed[currPoint.x][currPoint.y] = true;
                ++flashCount;
            }
        }
        setAboveNineToZero(grid);
        return flashCount;
    }

    private static final void incrementAll(int[][] input, Queue<Point> toBeRevisited) {
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[0].length; ++j) {
                input[i][j]++;
                if (input[i][j] > 9) {
                    toBeRevisited.add(new Point(i, j));
                }
            }
        }
    }

    private static final void setAboveNineToZero(int[][] input) {
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[0].length; ++j) {
                if (input[i][j] > 9) {
                    input[i][j] = 0;
                }
            }
        }
    }
    
    private static final void incrementNextTo(int[][] input, Queue<Point> toBeRevisited, int i, int j) {
        // Row above
        if (i > 0) {
            if (j > 0) {
                input[i-1][j-1]++;
                if (input[i-1][j-1] > 9) {
                    toBeRevisited.add(new Point(i-1, j-1));
                }
            }
            input[i-1][j]++;
            if (input[i-1][j] > 9) {
                toBeRevisited.add(new Point(i-1, j));
            }
            if (j < input[0].length - 1) {
                input[i-1][j+1]++;
                if (input[i-1][j+1] > 9) {
                    toBeRevisited.add(new Point(i-1, j+1));
                }
            }
        }
        // Left and Right
        if (j > 0) {
            input[i][j-1]++;
            if (input[i][j-1] > 9) {
                toBeRevisited.add(new Point(i, j-1));
            }
        }
        if (j < input[0].length - 1) {
            input[i][j+1]++;
            if (input[i][j+1] > 9) {
                toBeRevisited.add(new Point(i, j+1));
            }
        }
        // Row below
        if (i < input.length-1) {
            if (j > 0) {
                input[i+1][j-1]++;
                if (input[i+1][j-1] > 9) {
                    toBeRevisited.add(new Point(i+1, j-1));
                }
            }
            input[i+1][j]++;
            if (input[i+1][j] > 9) {
                toBeRevisited.add(new Point(i+1, j));
            }
            if (j < input[0].length - 1) {
                input[i+1][j+1]++;
                if (input[i+1][j+1] > 9) {
                    toBeRevisited.add(new Point(i+1, j+1));
                }
            }
        }
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level11\\level11data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[][] grid = new int[10][10];
            for (int i = 0; i < 10; ++i) {
                String line = scanner.nextLine();
                for (int j = 0; j < 10; ++j) {
                    grid[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
            int lastFlashCount = 0;
            int stepCount = 0;
            while(lastFlashCount != 100) {
                lastFlashCount = incrementGridAndReturnFlashCount(grid);
                ++stepCount;
            }
            System.out.println(stepCount);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static class Point {
        Integer x;
        Integer y;

        Point(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }
    }

}
