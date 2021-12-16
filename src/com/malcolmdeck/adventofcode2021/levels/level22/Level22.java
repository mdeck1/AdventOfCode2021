package com.malcolmdeck.adventofcode2021.levels.level22;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level22 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level22\\level22data.txt");
        try {
            Scanner scanner = new Scanner(file);

            System.out.println("Answer: " );
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level22\\level22data.txt");
        try {
            Scanner scanner = new Scanner(file);

            System.out.println("Answer: " );
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

}
