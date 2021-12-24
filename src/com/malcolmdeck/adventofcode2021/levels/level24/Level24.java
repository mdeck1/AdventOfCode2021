package com.malcolmdeck.adventofcode2021.levels.level24;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level24 {

    /**
     * Brute forcing this would take too long, so the first thing I did is
     * to make the program output the instruction as Java. That as an
     * ~1000x speedup, but still obviously not fast enough. It would have
     * taken O(1,000,000) seconds to complete. So I did some analysis of the
     * code it was running on the inputs to see if it was simplifiable.
     *
     * The first notable feature is that it repeats the same instruction 14
     * times with each input. The code generally says:
     * ```
     * w = input[index]
     * x = ((z%26 + {b} != w) ? 1 : 0
     * z /= {a}
     * z *= (25*x + 1)
     * z += (w + {c}) * x
     * ```
     * We basically have 2 cases to consider: the cases where x = 1 and the
     * cases where x = 0.
     * Assuming x = 1, then the program reads:
     * ```
     * w = input[index]
     * x = ((z%26 + {b} != w) ? 1 : 0
     * z /= {a}
     * z *= (26)
     * z += (w + {c})
     * ```
     * So z pushes up 26 and then adds (w+c). This will be true for any time
     * some older (w+c) + (current_b) != w, which should be true for b >= 10
     * since w is a single digit. In those cases, {a} is always 1, so we can
     * treat this like a stack of numbers.
     *
     * In the other case, {a} is 26. In those cases, we need x to be 0. That's
     * true if (top_of_stack) + b == w. Top of stack is some old (w+c), so
     * we can pair the digits and use one to solve for the other using the
     * {b} and {c} values in the program. For my input case:
     *
     * input[1]  - 1 = input[14]
     * input[2]  + 7 = input[13]
     * input[3]  - 5 = input[12]
     * input[4]  + 8 = input[9]
     * input[5]  + 4 = input[6]
     * input[7]  + 2 = input[8]
     * input[10] + 0 = input[11]
     *
     * This makes solving for the cases where we want the minimum and
     * maximum numbers trivial. For the minimum, make the left digits as small
     * as possible while keeping the digit on the right in [1,9]. For the
     * maximum, the same but using the maximum possible input that doesn't
     * break the right hand side.
     *
     * I validated my hand solution using the program I had and then input it.
     */
    public static void partOne() throws Exception {
        int[] modelNumberAsArray = new int[14];
        try {
//            printMonadAsJava();
            for (long l = 21611513911181l; l > 0; --l) {
                if (l % 100000000 == 99999999) {
                    System.out.println("Checking " + l);
                }
                String lStr = String.valueOf(l);
                if (lStr.contains("0")) {
                    continue;
                }
                putLongIntoArray(modelNumberAsArray, lStr);
                if (checkCandidateNumber(modelNumberAsArray)) {
                    System.out.println("Found model number: " + l);
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
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

    private static void putLongIntoArray(int[] array, String longStr) {
        for (int i = 0; i < longStr.length(); ++i) {
            array[i] = longStr.charAt(i) - '0';
        }
    }

    private static void printMonadAsJava() throws Exception {
        File file = FileHelper.getFile("level24\\level24data.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String fullCommand = scanner.nextLine();
                String command = fullCommand.split(" ")[0];
                if (command.equalsIgnoreCase("inp")) {
                    String var = fullCommand.split(" ")[1];
                    System.out.println(var + " = candidateNumber[index++];");
                } else if (command.equalsIgnoreCase("add")) {
                    String otherStr = fullCommand.split(" ")[2];
                    String var = fullCommand.split(" ")[1];
                    System.out.println(var + " += " + otherStr + ";");
                } else if (command.equalsIgnoreCase("mul")) {
                    String otherStr = fullCommand.split(" ")[2];
                    String var = fullCommand.split(" ")[1];
                    System.out.println(var + " *= " + otherStr + ";");
                } else if (command.equalsIgnoreCase("div")) {
                    String otherStr = fullCommand.split(" ")[2];
                    String var = fullCommand.split(" ")[1];
                    System.out.println(var + " /= " + otherStr + ";");
                } else if (command.equalsIgnoreCase("mod")) {
                    String otherStr = fullCommand.split(" ")[2];
                    String var = fullCommand.split(" ")[1];
                    System.out.println(var + " = " + var + " % " + otherStr + ";");
                } else if (command.equalsIgnoreCase("eql")) {
                    String otherStr = fullCommand.split(" ")[2];
                    String var = fullCommand.split(" ")[1];
                    System.out.println(var + " = " + var + " == " + otherStr + " ? 1 : 0;");
                }
            }
            System.out.println("return z == 0;");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static boolean checkCandidateNumber(int[] candidateNumber) throws Exception {
        File file = FileHelper.getFile("level24\\level24data.txt");
        try {
            int index = 0;
            long w = 0;
            long x = 0;
            long y = 0;
            long z = 0;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 1;
            x += 12;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 4;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 1;
            x += 11;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 11;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 1;
            x += 13;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 5;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 1;
            x += 11;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 11;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 1;
            x += 14;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 14;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 26;
            x += -10;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 7;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 1;
            x += 11;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 11;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 26;
            x += -9;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 4;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 26;
            x += -3;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 6;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 1;
            x += 13;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 5;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 26;
            x += -5;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 9;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 26;
            x += -10;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 12;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 26;
            x += -4;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 14;
            y *= x;
            z += y;
            w = candidateNumber[index++];

            x *= 0;
            x += z;
            x = x % 26;
            z /= 26;
            x += -5;
            x = x == w ? 1 : 0;
            x = x == 0 ? 1 : 0;
            y *= 0;
            y += 25;
            y *= x;
            y += 1;
            z *= y;
            y *= 0;
            y += w;
            y += 14;
            y *= x;
            z += y;
            return z == 0;
        } catch (Exception e) {
            System.out.println("FileNotFound: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
