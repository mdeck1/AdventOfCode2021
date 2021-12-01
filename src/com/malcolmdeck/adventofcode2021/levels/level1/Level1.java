package com.malcolmdeck.adventofcode2021.levels.level1;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level1 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level1\\level1data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int prev = scanner.nextInt();
            int count = 0;
            while (scanner.hasNextLine()) {
                int curr = scanner.nextInt();
                if (prev < curr) {
                    count++;
                }
                prev = curr;
            }
            System.out.println("Number of depth increases: " + count);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level1\\level1data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int prev3 = scanner.nextInt();
            int prev2 = scanner.nextInt();
            int prev1 = scanner.nextInt();
            int count = 0;
            while (scanner.hasNextLine()) {
                int curr = scanner.nextInt();
                // Difference in sums is just difference of the only elements that weren't common between them
                if (prev3 < curr) {
                    count++;
                }
                prev3 = prev2;
                prev2 = prev1;
                prev1 = curr;
            }
            System.out.println("Number of window increases: " + count);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

}
