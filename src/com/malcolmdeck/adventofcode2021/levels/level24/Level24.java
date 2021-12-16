package com.malcolmdeck.adventofcode2021.levels.level24;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level24 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level24\\level24data.txt");
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
        File file = FileHelper.getFile("level24\\level24data.txt");
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
