package com.malcolmdeck.adventofcode2021.levels.level6;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class Level6 {


    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level6\\level6data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] strs = line.split(",");
            int[] nums = new int[9];
            for (int i = 0; i < strs.length; ++i) {
                nums[Integer.parseInt(strs[i])]++;
            }
            for (int i = 0; i < 80; ++i) {
                int prev = nums[8];
                for (int j = 7; j > 0; --j) {
                    int temp = nums[j];
                    nums[j] = prev;
                    prev = temp;
                }
                int temp = nums[0];
                nums[0] = prev;
                nums[6] += temp;
                nums[8] = temp;
            }
            int sum = 0;
            for (int i = 0; i < 9; ++i) {
                sum += nums[i];
            }
            System.out.println("Sum: " + sum);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }


    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level6\\level6data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] strs = line.split(",");
            BigInteger[] nums = new BigInteger[9];
            for (int i = 0; i < nums.length; ++i) {
                nums[i] = new BigInteger("0");
            }
            for (int i = 0; i < strs.length; ++i) {
                nums[Integer.parseInt(strs[i])] = nums[Integer.parseInt(strs[i])].add(BigInteger.ONE);
            }
            for (int i = 0; i < 256; ++i) {
                BigInteger prev = nums[8];
                for (int j = 7; j > 0; --j) {
                    BigInteger temp = nums[j];
                    nums[j] = prev;
                    prev = temp;
                }
                BigInteger temp = nums[0];
                nums[0] = prev;
                nums[6] = nums[6].add(temp);
                nums[8] = temp;
            }
            BigInteger sum = BigInteger.ZERO;
            for (int i = 0; i < 9; ++i) {
                sum = sum.add(nums[i]);
            }
            System.out.println("Sum: " + sum);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

}
