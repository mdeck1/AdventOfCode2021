package com.malcolmdeck.adventofcode2021.levels.level19;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level19 {

    private static final boolean DEBUG = false;
    private static boolean SECOND_PASS = false;

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level19\\level19data.txt");
        try {
            Scanner scanner = new Scanner(file);
            List<Beacon> beaconList = new ArrayList<>(31);
            while (scanner.hasNextLine()) {
                scanner.nextLine(); // --- scanner # ---
                String line;
                List<Vector> vectorList = new ArrayList<>(30);
                while (scanner.hasNextLine() && (line = scanner.nextLine()) != ""){
                    vectorList.add(new Vector(
                            Integer.parseInt(line.split(",")[0]),
                            Integer.parseInt(line.split(",")[1]),
                            Integer.parseInt(line.split(",")[2])));
                }
                beaconList.add(new Beacon(vectorList));
            }
//            Collections.shuffle(beaconList);
            beaconList.get(0).setPositionAndRotation(0, 0, 0, 0);
            Queue<Beacon> beaconQueue = new ArrayDeque<>(31);
            beaconQueue.add(beaconList.get(0));
            Set<Beacon> unorientedBeacons = new HashSet<>(beaconList);
            unorientedBeacons.remove(beaconQueue.peek());
            beaconQueue.add(beaconList.get(0));
            Set<Beacon> toBeRemoved = new HashSet<>();
            while (!beaconQueue.isEmpty()) {
                Beacon currentBeacon = beaconQueue.poll();
                if (currentBeacon.absolutePoints == null) {
                    throw new RuntimeException("Beacon inserted into queue without absolutePoints. Problematic Beacon " +
                            "has first coordinate " + currentBeacon.beaconListList.get(0).get(0).toString());
                }
                for (Beacon unorientedBeacon : unorientedBeacons) {
                    if (unorientedBeacon.orientationIndex == null) {
                        attemptToOrient(currentBeacon, unorientedBeacon);
                    }
                    if (unorientedBeacon.orientationIndex != null) {
                        toBeRemoved.add(unorientedBeacon);
                        beaconQueue.add(unorientedBeacon);
                    }
                }
                unorientedBeacons.removeAll(toBeRemoved);
                toBeRemoved.clear();
            }
            // Second attempt for some reason
            SECOND_PASS = true;
            for (Beacon unorientedBeacon : unorientedBeacons) {
                for (Beacon knownBeacon : beaconList) {
                    if (knownBeacon.orientationIndex != null) {
                        attemptToOrient(knownBeacon, unorientedBeacon);
                    }
                }
            }
            Set<Vector> finalBeacons = new HashSet<>();
            for (Beacon knownBeacon : beaconList) {
                if (knownBeacon.absolutePoints != null) {
                    finalBeacons.addAll(knownBeacon.absolutePoints);
                }
            }
            List<Vector> finalList = new ArrayList<>(finalBeacons);
            finalList.sort(
                    (Vector v1, Vector v2)->
                            (v1.x-v2.x)*10000 + (v1.y-v2.y)*100 + (v1.z-v2.z));
            System.out.println("Answer: " + finalBeacons.size());
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level19\\level19data.txt");
        try {
            Scanner scanner = new Scanner(file);
            List<Beacon> beaconList = new ArrayList<>(31);
            while (scanner.hasNextLine()) {
                scanner.nextLine(); // --- scanner # ---
                String line;
                List<Vector> vectorList = new ArrayList<>(30);
                while (scanner.hasNextLine() && (line = scanner.nextLine()) != ""){
                    vectorList.add(new Vector(
                            Integer.parseInt(line.split(",")[0]),
                            Integer.parseInt(line.split(",")[1]),
                            Integer.parseInt(line.split(",")[2])));
                }
                beaconList.add(new Beacon(vectorList));
            }
//            Collections.shuffle(beaconList);
            beaconList.get(0).setPositionAndRotation(0, 0, 0, 0);
            Queue<Beacon> beaconQueue = new ArrayDeque<>(31);
            beaconQueue.add(beaconList.get(0));
            Set<Beacon> unorientedBeacons = new HashSet<>(beaconList);
            unorientedBeacons.remove(beaconQueue.peek());
            beaconQueue.add(beaconList.get(0));
            Set<Beacon> toBeRemoved = new HashSet<>();
            while (!beaconQueue.isEmpty()) {
                Beacon currentBeacon = beaconQueue.poll();
                if (currentBeacon.absolutePoints == null) {
                    throw new RuntimeException("Beacon inserted into queue without absolutePoints. Problematic Beacon " +
                            "has first coordinate " + currentBeacon.beaconListList.get(0).get(0).toString());
                }
                for (Beacon unorientedBeacon : unorientedBeacons) {
                    if (unorientedBeacon.orientationIndex == null) {
                        attemptToOrient(currentBeacon, unorientedBeacon);
                    }
                    if (unorientedBeacon.orientationIndex != null) {
                        toBeRemoved.add(unorientedBeacon);
                        beaconQueue.add(unorientedBeacon);
                    }
                }
                unorientedBeacons.removeAll(toBeRemoved);
                toBeRemoved.clear();
            }
            // Second attempt for some reason
            SECOND_PASS = true;
            for (Beacon unorientedBeacon : unorientedBeacons) {
                for (Beacon knownBeacon : beaconList) {
                    if (knownBeacon.orientationIndex != null) {
                        attemptToOrient(knownBeacon, unorientedBeacon);
                    }
                }
            }
            Set<Vector> finalBeacons = new HashSet<>();
            for (Beacon knownBeacon : beaconList) {
                if (knownBeacon.absolutePoints != null) {
                    finalBeacons.addAll(knownBeacon.absolutePoints);
                }
            }
            List<Vector> finalList = new ArrayList<>(finalBeacons);
            int greatestManhattanDist = 0;
            for (int i = 0; i < beaconList.size(); ++i) {
                for (int j = i + 1; j < beaconList.size(); ++j) {
                    int newManhattanDist =
                            Math.abs(beaconList.get(i).x - beaconList.get(j).x) +
                                    Math.abs(beaconList.get(i).y - beaconList.get(j).y) +
                                    Math.abs(beaconList.get(i).z - beaconList.get(j).z);
                    if (newManhattanDist > greatestManhattanDist) {
                        greatestManhattanDist = newManhattanDist;
                        System.out.println("New max found between " + beaconList.get(i) +
                                " and " + beaconList.get(j));
                    }
                }
            }
            System.out.println("Answer: " + greatestManhattanDist);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static void attemptToOrient(Beacon knownBeacon, Beacon unknownBeacon) {
        int maxIntersectionSize = 1;
        // For every orientation of the unknownBeacon
        for (int suggestedOrientationIndex = 0; suggestedOrientationIndex < 48; ++suggestedOrientationIndex) {
            // For every pair of points selected from each beacon
            for (Vector knownPoint : knownBeacon.absolutePoints) {
                for (Vector suggestedPoint : unknownBeacon.beaconListList.get(suggestedOrientationIndex)) {
                    // Assume they're the same point, set temp <x,y,z> of beacon
                    int computedX = knownPoint.x - suggestedPoint.x;
                    int computedY = knownPoint.y - suggestedPoint.y;
                    int computedZ = knownPoint.z - suggestedPoint.z;
                    // See if at least 12 points line up with each other
                    Set<Vector> intersection = new HashSet<>(knownBeacon.absolutePoints);
                    List<Vector> newPoints = new ArrayList<>();
                    for (Vector unknownPoint : unknownBeacon.beaconListList.get(suggestedOrientationIndex)) {
                        newPoints.add(new Vector(
                                computedX + unknownPoint.x,
                                computedY + unknownPoint.y,
                                computedZ + unknownPoint.z));
                    }
                    intersection.retainAll(newPoints);
                    if (intersection.size() >= 12) {
                        unknownBeacon.setPositionAndRotation(computedX, computedY, computedZ, suggestedOrientationIndex);
                        System.out.println("Found intersection of at least 12 between known Beacon " +
                                "<" + knownBeacon.x + "," + knownBeacon.y + "," + knownBeacon.z + "> and unknown beacon with " +
                                "initial coordinates " + unknownBeacon.beaconListList.get(0).get(0).toString() + ". New coordinates: " +
                                "<" + unknownBeacon.x + "," + unknownBeacon.y + "," + unknownBeacon.z + ">. " +
                                "Number of intersected points: " + intersection.size());
                        return;
                    } else if (intersection.size() > 3) {
                        if (DEBUG) {
                            System.out.println("Close but no cigar");
                        }
                        maxIntersectionSize = intersection.size();
                    }else if (intersection.size() > 1) {
                        if (DEBUG) {
                            System.out.println("Close but no cigar");
                        }
                        maxIntersectionSize = intersection.size();
                    }
                }
            }
        }
        if (SECOND_PASS) {
            System.out.println("Failed to find intersection of at least 12 between known Beacon " +
                    "<" + knownBeacon.x + "," + knownBeacon.y + "," + knownBeacon.z + "> and unknown beacon with first" +
                    "coordinate " + unknownBeacon.beaconListList.get(0).get(0).toString() + ". Max number of intersected " +
                    "points: " + maxIntersectionSize);
        }
    }

    private static class Beacon {
        Integer x = null;
        Integer y = null;
        Integer z = null;
        // includes all orientations
        List<List<Vector>> beaconListList;
        List<Vector> absolutePoints;
        Integer orientationIndex = null;

        Beacon(List<Vector> vectorList) {
            beaconListList = produceAllRotations(vectorList);
        }

        void setPositionAndRotation(int x, int y, int z, int orientationIndex) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.orientationIndex = orientationIndex;
            absolutePoints = new ArrayList<>(beaconListList.get(0).size());
            for (Vector v : beaconListList.get(orientationIndex)) {
                absolutePoints.add(new Vector(x + v.x, y + v.y, z + v.z));
            }
        }

        public String toString() {
            return "Beacon: <"+ x + "," + y + "," + z + ">";
        }
    }

    private static List<List<Vector>> produceAllRotations(List<Vector> vectorList) {
        List<List<Vector>> vectorListList = new ArrayList<>();
        for (int i = 0; i < 48; ++i) {
            vectorListList.add(new ArrayList<>(vectorList.size()));
        }
        for (Vector v: vectorList) {
            List<Vector> allRotations = produceAllRotationsOfOneVector(v);
            for (int i = 0; i < 48; ++i) {
                vectorListList.get(i).add(allRotations.get(i));
            }
        }
        return vectorListList;
    }

    static List<Vector> produceAllRotationsOfOneVector(Vector v) {
        List<Vector> vectorList = new ArrayList<>();
        // Right side up
//        vectorList.add(v.produceRotatedCopy(0, 0, 0));
//        vectorList.add(v.produceRotatedCopy(0, 0, 1));
//        vectorList.add(v.produceRotatedCopy(0, 0, 2));
//        vectorList.add(v.produceRotatedCopy(0, 0, 3));
//        // Upside down
//        vectorList.add(v.produceRotatedCopy(2, 0, 0));
//        vectorList.add(v.produceRotatedCopy(2, 0, 1));
//        vectorList.add(v.produceRotatedCopy(2, 0, 2));
//        vectorList.add(v.produceRotatedCopy(2, 0, 3));
//        // Forward
//        vectorList.add(v.produceRotatedCopy(1, 0, 0));
//        vectorList.add(v.produceRotatedCopy(1, 1, 0));
//        vectorList.add(v.produceRotatedCopy(1, 2, 0));
//        vectorList.add(v.produceRotatedCopy(1, 3, 0));
//        // Backward
//        vectorList.add(v.produceRotatedCopy(3, 0, 0));
//        vectorList.add(v.produceRotatedCopy(3, 1, 0));
//        vectorList.add(v.produceRotatedCopy(3, 2, 0));
//        vectorList.add(v.produceRotatedCopy(3, 3, 0));
//        // Right
//        vectorList.add(v.produceRotatedCopy(0, 1, 0));
//        vectorList.add(v.produceRotatedCopy(1, 1, 0));
//        vectorList.add(v.produceRotatedCopy(2, 1, 0));
//        vectorList.add(v.produceRotatedCopy(3, 1, 0));
//        // Left
//        vectorList.add(v.produceRotatedCopy(0, 3, 0));
//        vectorList.add(v.produceRotatedCopy(1, 3, 0));
//        vectorList.add(v.produceRotatedCopy(2, 3, 0));
//        vectorList.add(v.produceRotatedCopy(3, 3, 0));
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 8; ++j) {
                vectorList.add(v.produceRotatedCopy(i, j));
            }
        }
        return vectorList;
    }

    private static class Vector {
        int x;
        int y;
        int z;

        Vector(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Rotations in multiples of pi/2
        // https://en.wikipedia.org/wiki/Rotation_matrix#General_rotations
        Vector produceRotatedCopy(int xRotations, int yRotations, int zRotations) {
            double xRot = xRotations * PI / 2;
            double yRot = yRotations * PI / 2;
            double zRot = zRotations * PI / 2;
            int newX = (int) Math.round(
                    cos(zRot) * cos(yRot) * this.x +
                            (cos(zRot)*sin(yRot)*sin(xRot) - sin(zRot)*cos(xRot)) * this.y +
                            (cos(zRot)*sin(yRot)*cos(xRot) + sin(zRot)*sin(xRot)) * this.z);
            int newY = (int) Math.round(
                    sin(zRot) * cos(yRot) * this.x +
                            (sin(zRot)*sin(yRot)*sin(xRot) + cos(zRot)*cos(xRot)) * this.y +
                            (sin(zRot)*sin(yRot)*cos(xRot) - cos(zRot)*sin(xRot)) * this.z);
            int newZ = (int) Math.round(
                    -1 * sin(yRot) * this.x +
                            cos(yRot) * sin(xRot) * this.y +
                            cos(yRot) * cos(xRot) * this.z);
            if ((Math.abs(newX) + Math.abs(newY) + Math.abs(newZ)) !=
                    (Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z))) {
                throw new RuntimeException("FOUND A ROUNDING ERROR");
            }
            return new Vector(newX, newY, newZ);
        }

        // Rotations in multiples of pi/2
        // https://en.wikipedia.org/wiki/Rotation_matrix#General_rotations
        Vector produceRotatedCopy(int shuffle, int negation) {
            int newX = this.x;
            int newY = this.y;
            int newZ = this.z;
            switch (shuffle) {
                case 0:
                    break;
                case 1:
                    newZ = this.y;
                    newY = this.z;
                    break;
                case 2:
                    newX = this.y;
                    newY = this.x;
                    break;
                case 3:
                    newX = this.y;
                    newY = this.z;
                    newZ = this.x;
                    break;
                case 4:
                    newX = this.z;
                    newY = this.x;
                    newZ = this.y;
                    break;
                case 5:
                    newX = this.z;
                    newZ = this.x;
                    break;
            }
            if (negation < 4) {
                newX *= -1;
            }
            if ((negation % 4) / 2 == 0) {
                newY *= -1;
            }
            if (negation % 2 == 0) {
                newZ *= -1;
            }
            return new Vector(newX, newY, newZ);
        }


        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (! (o instanceof Vector)) {
                return false;
            }
            Vector other =  (Vector) o;
            return this.x == other.x && this.y == other.y && this.z == other.z;
        }

        @Override
        public int hashCode() {
            return x*10000 + y*100 + z;
        }

        public String toString() {
            return "<"+ x + "," + y + "," + z + ">";
        }
    }

}
