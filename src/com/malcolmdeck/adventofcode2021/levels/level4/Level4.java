package com.malcolmdeck.adventofcode2021.levels.level4;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level4 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level4\\level4data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String[] orderArray = scanner.next().split(",");
            int score = 0;
            int howMany = Integer.MAX_VALUE;
            while (scanner.hasNext()) {
                Board currentBoard = new Board(scanner);
                Result currentResult = currentBoard.generateResult(orderArray);
                if (currentResult.howMany < howMany) {
                    howMany = currentResult.howMany;
                    score = currentResult.score;
                }
            }
            System.out.println("Score of winning board: " + score);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }


    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level4\\level4data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String[] orderArray = scanner.next().split(",");
            int score = 0;
            int howMany = Integer.MIN_VALUE;
            while (scanner.hasNext()) {
                Board currentBoard = new Board(scanner);
                Result currentResult = currentBoard.generateResult(orderArray);
                if (currentResult.howMany > howMany) {
                    howMany = currentResult.howMany;
                    score = currentResult.score;
                }
            }
            System.out.println("Score of losing board: " + score);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static class Board {

        int[][] numbers;
        boolean[][] alreadyCalled;

        Board(Scanner scanner) {
            numbers = new int[5][5];
            alreadyCalled = new boolean[5][5];
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 5; ++j) {
                    numbers[i][j] = scanner.nextInt();
                }
            }
        }

        Result generateResult(String[] pulledNumbers) {
            for (int i = 0; i < pulledNumbers.length; ++i) {
                applyNumber(Integer.parseInt(pulledNumbers[i]));
                if (isSolved()){
                    return new Result(i+1, generateScore(Integer.parseInt(pulledNumbers[i])));
                }
            }
            return null;
        }

        private void applyNumber(int num) {
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 5; ++j) {
                    if (numbers[i][j] == num) {
                        alreadyCalled[i][j] = true;
                    }
                }
            }
        }

        private boolean isSolved() {
            for (int i = 0; i < 5; ++i) {
                boolean rowCheck = true;
                boolean colCheck = true;
                for (int j = 0; j < 5; ++j) {
                    rowCheck = rowCheck && alreadyCalled[i][j];
                    colCheck = colCheck && alreadyCalled[j][i];
                }
                if (rowCheck || colCheck) {
                    return true;
                }
            }
            return false;
        }

        private int generateScore(int finalNumber) {
            int score = 0;
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 5; ++j) {
                    if (!alreadyCalled[i][j]) {
                        score += numbers[i][j];
                    }
                }
            }
            return score * finalNumber;
        }
    }

    private static class Result {
        int howMany;
        int score;
        Result(int howMany, int score) {
            this.howMany = howMany;
            this.score = score;
        }
    }

}
