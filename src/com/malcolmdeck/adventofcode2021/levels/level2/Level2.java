package com.malcolmdeck.adventofcode2021.levels.level2;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level2 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level2\\level2data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int x = 0;
            int y = 0;
            while (scanner.hasNextLine()) {
                String direction = scanner.next();
                int amount = scanner.nextInt();
                switch (direction) {
                    case "forward":
                        x += amount;
                        break;
                    case "down":
                        y += amount;
                        break;
                    case "up":
                        y -= amount;
                        break;
                }
            }
            System.out.println("(x, y): (" + x + ", " + y + ")");
            System.out.println("product: " + (x*y));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level2\\level2data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int x = 0;
            int y = 0;
            int aim = 0;
            while (scanner.hasNextLine()) {
                String direction = scanner.next();
                int amount = scanner.nextInt();
                switch (direction) {
                    case "forward":
                        x += amount;
                        y += amount*aim;
                        break;
                    case "down":
                        aim += amount;
                        break;
                    case "up":
                        aim -= amount;
                        break;
                }
            }
            System.out.println("(x, y): (" + x + ", " + y + ")");
            System.out.println("product: " + (x*y));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

}
