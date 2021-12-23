package com.malcolmdeck.adventofcode2021.levels.level22;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level22 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level22\\level22data.txt");
        try {
            long time = System.nanoTime();
            Scanner scanner = new Scanner(file);
            int SIZE = 101;
            boolean[][][] grid = new boolean[SIZE][SIZE][SIZE];
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String command = line.split(" ")[0];
                boolean setToValue = command.equals("on") ? true : false;
                String xRange = line.split(" ")[1].split(",")[0];
                String yRange = line.split(" ")[1].split(",")[1];
                String zRange = line.split(" ")[1].split(",")[2];
                int xMin = Integer.parseInt(xRange.substring(2).split("\\.\\.")[0]);
                int xMax = Integer.parseInt(xRange.substring(2).split("\\.\\.")[1]);
                int yMin = Integer.parseInt(yRange.substring(2).split("\\.\\.")[0]);
                int yMax = Integer.parseInt(yRange.substring(2).split("\\.\\.")[1]);
                int zMin = Integer.parseInt(zRange.substring(2).split("\\.\\.")[0]);
                int zMax = Integer.parseInt(zRange.substring(2).split("\\.\\.")[1]);
                if (xMin < -50) {
                    xMin = -50;
                }
                if (yMin < -50) {
                    yMin = -50;
                }
                if (zMin < -50) {
                    zMin = -50;
                }
                if (xMax > 50) {
                    xMax = 50;
                }
                if (yMax > 50) {
                    yMax = 50;
                }
                if (zMax > 50) {
                    zMax = 50;
                }
                for (int i = xMin; i <= xMax; ++i) {
                    for (int j = yMin; j <= yMax; ++j) {
                        for (int k = zMin; k <= zMax; ++k) {
                            grid[i + 50][j + 50][k + 50] = setToValue;
                        }
                    }
                }
            }
            int count = 0;
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    for (int k = 0; k < SIZE; ++k) {
                        if (grid[i][j][k]) {
                            count++;
                        }
                    }
                }
            }
            System.out.println("Answer: " + count);
            System.out.println("Time elapsed: " + (System.nanoTime() - time));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    /**
     * Too big to do brute force, need to track volumes.
     */
    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level22\\level22data.txt");
        try {
            Scanner scanner = new Scanner(file);
            HashSet<Volume> volumeSet = new HashSet<>();
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                System.out.println("On line " + lineNumber++);
                System.out.println("Processed " + volumeSet.size() + " volumes so far");
                // Define new volume
                String line = scanner.nextLine();
                String command = line.split(" ")[0];
                boolean setToValue = command.equals("on") ? true : false;
                String xRange = line.split(" ")[1].split(",")[0];
                String yRange = line.split(" ")[1].split(",")[1];
                String zRange = line.split(" ")[1].split(",")[2];
                int xMin = Integer.parseInt(xRange.substring(2).split("\\.\\.")[0]);
                int xMax = Integer.parseInt(xRange.substring(2).split("\\.\\.")[1]);
                int yMin = Integer.parseInt(yRange.substring(2).split("\\.\\.")[0]);
                int yMax = Integer.parseInt(yRange.substring(2).split("\\.\\.")[1]);
                int zMin = Integer.parseInt(zRange.substring(2).split("\\.\\.")[0]);
                int zMax = Integer.parseInt(zRange.substring(2).split("\\.\\.")[1]);
                Volume currVolume = new Volume(setToValue, xMin, xMax, yMin, yMax, zMin, zMax);
                // For (existing volume)
                List<Volume> tempVolumes = new ArrayList<>();
                List<Volume> volumesToRemove = new ArrayList<>();
                for (Volume existingVolume : volumeSet) {
                    if (existingVolume.intersects(currVolume)) {
                        tempVolumes.addAll(existingVolume.returnNonIntersectingVolumes(currVolume));
                        volumesToRemove.add(existingVolume);
                    }
                }
                System.out.println("Removing this many volumes: " + volumesToRemove.size());
                volumeSet.removeAll(volumesToRemove);
                // insert volume into list
                System.out.println("Adding this many fractured volumes: " + tempVolumes.size());
                volumeSet.addAll(tempVolumes);
                if (currVolume.on) {
                    volumeSet.add(currVolume);
                }
            }
            // iterate over final volumes, adding if turned on
            long count = 0l;
            for (Volume v : volumeSet) {
                if (v.on) {
                    count += v.size();
                }
            }
            System.out.println("Answer: " + count);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static class Volume {
        boolean on;
        int xMin;
        int xMax;
        int yMin;
        int yMax;
        int zMin;
        int zMax;

        Volume(boolean on, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
            this.on = on;
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.zMin = zMin;
            this.zMax = zMax;
        }

        long size() {
            long size = 1;
            size *= (xMax - xMin + 1);
            size *= (yMax - yMin + 1);
            size *= (zMax - zMin + 1);
            if (size < 0) {
                throw new RuntimeException("overflow");
            }
            return size;
        }

        boolean intersects(Volume otherVolume) {
            if (this.xMin > otherVolume.xMax ||
                    this.xMax < otherVolume.xMin ||
                    this.yMin > otherVolume.yMax ||
                    this.yMax < otherVolume.yMin ||
                    this.zMin > otherVolume.zMax ||
                    this.zMax < otherVolume.zMin) {
                return false;
            }
            return true;
        }

        boolean intersects(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
            if (this.xMin > xMax ||
                    this.xMax < xMin ||
                    this.yMin > yMax ||
                    this.yMax < yMin ||
                    this.zMin > zMax ||
                    this.zMax < zMin) {
                return false;
            }
            return true;
        }

        // Assume intersects
        List<Volume> returnNonIntersectingVolumes(Volume otherVolume) {
//            int[] xVals = {this.xMin, this.xMax, otherVolume.xMin, otherVolume.xMax};
//            int[] yVals = {this.yMin, this.yMax, otherVolume.yMin, otherVolume.yMax};
//            int[] zVals = {this.zMin, this.zMax, otherVolume.zMin, otherVolume.zMax};
//            Arrays.sort(xVals);
//            Arrays.sort(yVals);
//            Arrays.sort(zVals);
//            List<Volume> newVolumes = new ArrayList<>();
//            for (int i = 0; i < 3; ++i) {
//                for (int j = 0; j < 3; ++j) {
//                    for (int k = 0; k < 3; ++k) {
//                        if (intersects(xVals[i], xVals[i+1], yVals[0], yVals[1], zVals[0], zVals[1])) {
//                            if (otherVolume.intersects(xVals[i], xVals[i+1], yVals[0], yVals[1], zVals[0], zVals[1])) {
//                                newVolumes.add(new Volume(otherVolume.on,
//                                        xVals[i], xVals[i+1], yVals[0], yVals[1], zVals[0], zVals[1]));
//                            } else {
//                                newVolumes.add(new Volume(on,
//                                        xVals[i], xVals[i+1], yVals[0], yVals[1], zVals[0], zVals[1]));
//                            }
//                        }
//                    }
//                }
//            }
            List<Volume> newVolumes = new ArrayList<>();
            if (xMin < otherVolume.xMin) {
                newVolumes.add(new Volume(on,
                        xMin, otherVolume.xMin - 1, yMin, yMax, zMin, zMax));
                xMin = otherVolume.xMin;
            }
            if (xMax > otherVolume.xMax) {
                newVolumes.add(new Volume(on,
                        otherVolume.xMax + 1, xMax, yMin, yMax, zMin, zMax));
                xMax = otherVolume.xMax;
            }
            if (yMin < otherVolume.yMin) {
                newVolumes.add(new Volume(on,
                        xMin, xMax, yMin, otherVolume.yMin - 1, zMin, zMax));
                yMin = otherVolume.yMin;
            }
            if (yMax > otherVolume.yMax) {
                newVolumes.add(new Volume(on,
                        xMin, xMax, otherVolume.yMax + 1, yMax, zMin, zMax));
                yMax = otherVolume.yMax;
            }
            if (zMin < otherVolume.zMin) {
                newVolumes.add(new Volume(on,
                        xMin, xMax, yMin, yMax, zMin, otherVolume.zMin - 1));
                zMin = otherVolume.zMin;
            }
            if (zMax > otherVolume.zMax) {
                newVolumes.add(new Volume(on,
                        xMin, xMax, yMin, yMax, otherVolume.zMax + 1, zMax));
                zMax = otherVolume.zMax;
            }
            return newVolumes;
        }
    }

}
