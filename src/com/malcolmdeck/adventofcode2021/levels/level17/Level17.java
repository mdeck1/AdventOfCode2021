package com.malcolmdeck.adventofcode2021.levels.level17;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level17 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level17\\level17data.txt");
        try {
            // This is just a math problem. The answer is that you need n = `|min(targety)| - 1` as the initial
            // velocity, which means that the top height is n*(n+1)/2 since it's just a sum of adjacent numbers.
            System.out.println("Answer: " );
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level17\\level17data.txt");
        try {
            int count = 0;
            // I know that there is a better way to do this, probably, but this is the trivial way to solve the
            // problem. Each of these will be at most 145*2 steps, so we're doing 144*294*290 "steps",
            // so this is obviously solveable in seconds.
            for (int x = 14; x < 158; ++x) {
                for (int y = -147; y < 146; ++y) {
                    Point point = new Point(x, y);
                    if (point.willLandWithinBounds(102, 157, -90, -146)) {
                        ++count;
                    }
                }
            }
            System.out.println("Answer: " + count);
        } catch (Exception e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static final class Point {
        int x;
        int y;
        int vx;
        int vy;

        Point(int vx, int vy) {
            this.vx = vx;
            this.vy = vy;
            x = 0;
            y = 0;
        }

        void takeStep() {
            x += vx;
            y += vy;
            if (vx > 0) {
                vx--;
            } else if (vx < 0) {
                vx++;
            }
            vy--;
        }

        boolean willLandWithinBounds(int targetx0, int targetx1, int targety0, int targety1) {
            while (y > Math.min(targety0, targety1) - 1) {
                if ((x <= targetx0 && x >= targetx1) || (x >= targetx0 && x <= targetx1) &&
                        (y <= targety0 && y >= targety1) || (y >= targety0 && y <= targety1)) {
                    return true;
                }
                takeStep();
            }
            if ((x <= targetx0 && x >= targetx1) || (x >= targetx0 && x <= targetx1) &&
                    (y <= targety0 && y >= targety1) || (y >= targety0 && y <= targety1)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "(x, y): (" + x + ", " + y + ")  "
                    + "(vx, vy): (" + vx + ", " + vy + ")";
        }
    }

}
