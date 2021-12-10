package com.malcolmdeck.adventofcode2021.levels.level9;

import com.malcolmdeck.adventofcode2021.levels.level8.Level8;
import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Level9 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level9\\level9data.txt");
        try {
            Scanner scanner = new Scanner(file);
            List<List<Integer>> map = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                List<Integer> row = new ArrayList<>();
                for (int i = 0; i < input.length(); ++i) {
                    row.add(Character.getNumericValue(input.charAt(i)));
                }
                map.add(row);
            }
            int sum = 0;
            int n = map.size();
            int m = map.get(0).size();
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    int curr = map.get(i).get(j);
                    // Above
                    if (i > 0 && map.get(i-1).get(j) <= curr) {
                        continue;
                    }
                    // Left
                    if (j > 0 && map.get(i).get(j-1) <= curr) {
                        continue;
                    }
                    // Right
                    if (j < m-1 && map.get(i).get(j+1) <= curr) {
                        continue;
                    }
                    // Below
                    if (i < n-1 && map.get(i+1).get(j) <= curr) {
                        continue;
                    }
                    sum += 1 + curr;
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
        File file = FileHelper.getFile("level9\\level9data.txt");
        try {
            Scanner scanner = new Scanner(file);
            List<List<Integer>> map = new ArrayList<>();
            List<List<Boolean>> alreadyPartOfBasin = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                List<Integer> row = new ArrayList<>();
                List<Boolean> booleanRow = new ArrayList<>();
                for (int i = 0; i < input.length(); ++i) {
                    row.add(Character.getNumericValue(input.charAt(i)));
                    booleanRow.add(input.charAt(i) != 9);
                }
                map.add(row);
                alreadyPartOfBasin.add(booleanRow);
            }
            int sum = 0;
            int n = map.size();
            int m = map.get(0).size();
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    if (map.get(i).get(j) == 9) {
                        alreadyPartOfBasin.get(i).set(j, true);
                    } else if (!alreadyPartOfBasin.get(i).get(j)) {

                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static int fillBasinAndReturnSize(List<List<Integer>> map, List<List<Boolean>> alreadyPartOfBasin, int startX, int startY) {
        Queue<Point> searchSpace = new ArrayBlockingQueue<Point>(1000);
        searchSpace.add(new Point(startX, startY));
        int size = 0;
        while (!searchSpace.isEmpty()) {
            Point current = searchSpace.poll();
            if (!alreadyPartOfBasin.get(current.x).get(current.y)) {
                if (map.get(current.x).get(current.y) == 9) {
                    alreadyPartOfBasin.get(current.x);
                }
                ++size;

            }
        }
        return 0;
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
