package com.malcolmdeck.adventofcode2021.levels.level18;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Level18 {

    private static final boolean DEBUG = true;

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level18\\level18data.txt");
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            Node node = buildTreeFromString(line, 0);
            while (scanner.hasNextLine()) {
                Node newRoot = new Node(0);
                node.increaseDepth();
                Node newNode = buildTreeFromString(scanner.nextLine(), 1);
                newRoot.left = node;
                newRoot.right = newNode;
                node = newRoot;
                node.reduce();
            }
            System.out.println("Magnitude: " + node.getMagnitude());
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level18\\level18data.txt");
        try {
            Scanner scanner = new Scanner(file);
            List<Node> nodeList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                nodeList.add(buildTreeFromString(scanner.nextLine(), 0));
            }
            int maxMagnitude = -1;
            for (int i = 0; i < nodeList.size(); ++i) {
                for (int j = 0; j < nodeList.size(); ++j) {
                    if (i != j) {
                        Node left = nodeList.get(i).copy();
                        Node right = nodeList.get(j).copy();
                        Node newRoot = new Node(0);
                        left.increaseDepth();
                        right.increaseDepth();
                        newRoot.left = left;
                        newRoot.right = right;
                        newRoot.reduce();
                        int newMagnitude = newRoot.getMagnitude();
                        maxMagnitude = newMagnitude > maxMagnitude ? newMagnitude : maxMagnitude;
                    }
                }
            }
            System.out.println("Answer: " + maxMagnitude);
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static Node buildTreeFromString(String input, int depth) throws Exception {
        Node newNode = new Node(depth);
        if (input.length() == 1 && Character.isDigit(input.charAt(0))) {
            newNode.value = Character.getNumericValue(input.charAt(0));
            return newNode;
        }
        int split = 1;
        if (Character.isDigit(input.charAt(1))) {
            newNode.left = buildTreeFromString(input.substring(1, 2), depth + 1);
        } else if (input.charAt(1) ==  '['){
            int count = 1;
            while (count > 0) {
                ++split;
                if (input.charAt(split) == ']') {
                    count--;
                } else if (input.charAt(split) == '[') {
                    count++;
                }
            }
            newNode.left = buildTreeFromString(input.substring(1, split+1), depth + 1);
        } else {
            throw new RuntimeException("Parsing error");
        }
        split += 2;
        newNode.right = buildTreeFromString(input.substring(split, input.length() - 1), depth + 1);
        return newNode;
    }

    private static class Node {
        Integer value = null;
        int depth = 0;
        Node left;
        Node right;

        Node(int depth) {
            this.depth = depth;
        }

        Node copy() {
            Node newNode = new Node(this.depth);
            if (this.value != null) {
                newNode.value = this.value;
            } else {
                newNode.left = this.left.copy();
                newNode.right = this.right.copy();
            }
            return newNode;
        }

        public String toString() {
            if (this.value != null) {
                return String.valueOf(value);
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("[");
                builder.append(left.toString());
                builder.append(",");
                builder.append(right.toString());
                builder.append("]");
                return builder.toString();
            }
        }

        void increaseDepth() {
            this.depth += 1;
            if (this.value == null) {
                this.left.increaseDepth();
                this.right.increaseDepth();
            }
        }

        int getMagnitude() {
            if (this.value != null) {
                return this.value;
            }
            return 3 * this.left.getMagnitude() + 2 * this.right.getMagnitude();
        }

        // Only to be called on the root
        void reduce() {
            boolean justReduced = true;
            while (justReduced) {
                if (DEBUG) {
                    System.out.println(this.toString());
                }
                justReduced &= reduceForDepth();
                if (!justReduced) {
                    justReduced = reduceForNumbers();
                }
            }
        }

        boolean reduceForDepth() {
            // Need 2 previous value nodes because to get to the pair we will have just seen the left child
            Node nodeTwoPrevious = null;
            Node nodePreviousWithValue = null;
            boolean foundPairToExplode = false;
            Integer valueToAddLater = null;
            Stack<Node> stack = new Stack<>();
            Node current = this;
            while (current != null || !stack.isEmpty()) {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                current = stack.pop();
                // Do logic with Node in order
                if (current.value != null) {
                    if (foundPairToExplode) {
                        current.value += valueToAddLater;
                        return true;
                    } else {
                        nodeTwoPrevious = nodePreviousWithValue;
                        nodePreviousWithValue = current;
                    }
                } else if (current.left.value != null && current.right.value != null && current.depth >= 4) {
                        foundPairToExplode = true;
                        if (nodeTwoPrevious != null) {
                            nodeTwoPrevious.value += current.left.value;
                        }
                        valueToAddLater = current.right.value;
                        current.left = null;
                        current.right = null;
                        current.value = 0;
                }
                current = current.right;
            }
            return foundPairToExplode;
        }

        boolean reduceForNumbers() {
            if (this.value != null) {
                if (this.value < 10) {
                    return false;
                }
                Node left = new Node(this.depth + 1);
                left.value = this.value / 2;
                Node right = new Node(this.depth + 1);
                right.value = this.value - left.value;
                this.left = left;
                this.right = right;
                this.value = null;
                return true;
            } else {
                if (this.left.reduceForNumbers()) {
                    return true;
                } else {
                    return this.right.reduceForNumbers();
                }
            }
        }
    }

}
