package com.malcolmdeck.adventofcode2021.levels.level5;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level5 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level5\\level5data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[][] seafloor = new int[999][999];
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] points = line.split(" -> ");
                int x1 = Integer.parseInt(points[0].split(",")[0]);
                int y1 = Integer.parseInt(points[0].split(",")[1]);
                int x2 = Integer.parseInt(points[1].split(",")[0]);
                int y2 = Integer.parseInt(points[1].split(",")[1]);
                if (y1 == y2) {
                    for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); ++i) {
                        seafloor[i][y1]++;
                    }
                } else if (x1 == x2) {
                    for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); ++i) {
                        seafloor[x1][i]++;
                    }
                }
            }
            int count =0;
            for (int i = 0; i < 999; ++i) {
                for (int j = 0; j < 999; ++j) {
                    if (seafloor[i][j] > 1) {
                        ++count;
                    }
                }
            }
            System.out.println("Count: " + count);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }


    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level5\\level5data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[][] seafloor = new int[999][999];
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] points = line.split(" -> ");
                int x1 = Integer.parseInt(points[0].split(",")[0]);
                int y1 = Integer.parseInt(points[0].split(",")[1]);
                int x2 = Integer.parseInt(points[1].split(",")[0]);
                int y2 = Integer.parseInt(points[1].split(",")[1]);
                if (y1 == y2) {
                    for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); ++i) {
                        seafloor[i][y1]++;
                    }
                } else if (x1 == x2) {
                    for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); ++i) {
                        seafloor[x1][i]++;
                    }
                } else {
                    if (x1 < x2) {
                        if (y2 < y1) {
                            for (int i = 0; i <= (x2-x1); ++i) {
                                seafloor[x1 + i][y1 - i]++;
                            }
                        } else {
                            for (int i = 0; i <= (x2-x1); ++i) {
                                seafloor[x1 + i][y1 + i]++;
                            }
                        }
                    } else {
                        if (y1 < y2) {
                            for (int i = 0; i <= (x1-x2); ++i) {
                                seafloor[x2 + i][y2 - i]++;
                            }
                        } else {
                            for (int i = 0; i <= (x1-x2); ++i) {
                                seafloor[x2 + i][y2 + i]++;
                            }
                        }
                    }
                }
            }
            int count =0;
            for (int i = 0; i < 999; ++i) {
                for (int j = 0; j < 999; ++j) {
                    if (seafloor[i][j] > 1) {
                        ++count;
                    }
                }
            }
            System.out.println("Count: " + count);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

}
