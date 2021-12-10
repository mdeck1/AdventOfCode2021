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
                    booleanRow.add(input.charAt(i) == '9');
                }
                map.add(row);
                alreadyPartOfBasin.add(booleanRow);
            }
            int sum = 0;
            int n = map.size();
            int m = map.get(0).size();
            List<Integer> sizeList = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    if (!alreadyPartOfBasin.get(i).get(j)) {
                        sizeList.add(fillBasinAndReturnSize(alreadyPartOfBasin, i, j));
                    }
                }
            }
            sizeList.sort(Integer::compare);
            int product = sizeList.get(sizeList.size()-1) * sizeList.get(sizeList.size()-2) * sizeList.get(sizeList.size()-3);
                    System.out.println("Size product: " + product);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static int fillBasinAndReturnSize(List<List<Boolean>> alreadyPartOfBasin, int startX, int startY) {
        Queue<Point> searchSpace = new ArrayBlockingQueue<Point>(1000);
        searchSpace.add(new Point(startX, startY));
        int size = 0;
        int n = alreadyPartOfBasin.size();
        int m = alreadyPartOfBasin.get(0).size();
        while (!searchSpace.isEmpty()) {
            Point current = searchSpace.poll();
            if (!alreadyPartOfBasin.get(current.x).get(current.y)) {
                ++size;
                alreadyPartOfBasin.get(current.x).set(current.y, true);
                if (current.x > 0) {
                    searchSpace.add(new Point(current.x-1, current.y));
                }
                if (current.x < n-1) {
                    searchSpace.add(new Point(current.x+1, current.y));
                }
                if (current.y > 0) {
                    searchSpace.add(new Point(current.x, current.y-1));
                }
                if (current.y < m - 1) {
                    searchSpace.add(new Point(current.x, current.y+1));
                }
            }
        }
        return size;
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
