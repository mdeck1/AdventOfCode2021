package com.malcolmdeck.adventofcode2021.levels.level10;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level10 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level10\\level10data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int score = 0;
            while (scanner.hasNextLine()) {
                score += getScoreForLine(scanner.nextLine());
            }
            System.out.println("Score: " + score);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static int getScoreForLine(String input) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < input.length(); ++i) {
            char currChar = input.charAt(i);
            if (currChar == '[' || currChar == '(' || currChar == '<' || currChar == '{') {
                stack.push(currChar);
            } else {
                char match = stack.pop();
                if ((match == '[' && currChar != ']')
                        || (match == '(' && currChar != ')')
                        || (match == '<' && currChar != '>')
                        || (match == '{' && currChar != '}')) {
                    return returnPointValueForChar(currChar);
                }
            }
        }
        return 0;
    }

    private static int returnPointValueForChar(char c) {
        switch (c) {
            case ')':
                return 3;
            case ']':
                return 57;
            case '}':
                return 1197;
            case '>':
                return 25137;
            default:
                throw new RuntimeException("Invalid char input: " + c);
        }
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level10\\level10data.txt");
        try {
            Scanner scanner = new Scanner(file);
            List<Long> scoreList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                Long result = getScoreForLineCompletion(scanner.nextLine());
                if (result != null) {
                    scoreList.add(result);
                }
            }
            Long[] listAsArray = new Long[scoreList.size()];
            scoreList.toArray(listAsArray);
            Arrays.sort(listAsArray);
            System.out.println("Score: " + listAsArray[listAsArray.length/2]);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static Long getScoreForLineCompletion(String input) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < input.length(); ++i) {
            char currChar = input.charAt(i);
            if (currChar == '[' || currChar == '(' || currChar == '<' || currChar == '{') {
                stack.push(currChar);
            } else {
                char match = stack.pop();
                if ((match == '[' && currChar != ']')
                        || (match == '(' && currChar != ')')
                        || (match == '<' && currChar != '>')
                        || (match == '{' && currChar != '}')) {
                    return null;
                }
            }
        }
        if (stack.size() == 0) {
            return null;
        }
        long score = 0l;
        while (!stack.isEmpty()) {
            score *= 5;
            switch (stack.pop()) {
                case '(':
                    score += 1;
                    break;
                case '[':
                    score += 2;
                    break;
                case '{':
                    score += 3;
                    break;
                case '<':
                    score += 4;
                    break;
            }
        }
        return score;
    }

}
