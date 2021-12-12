package com.malcolmdeck.adventofcode2021.levels.level12;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level12 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level12\\level12data.txt");
        try {
            Scanner scanner = new Scanner(file);
            Graph graph = new Graph();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                graph.addConnection(line.split("-")[0], line.split("-")[1]);
            }
            System.out.println("Number of paths: " + graph.howManyPaths());
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level12\\level12data.txt");
        try {
            Scanner scanner = new Scanner(file);
            Graph graph = new Graph();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                graph.addConnection(line.split("-")[0], line.split("-")[1]);
            }
            System.out.println("Number of paths: " + graph.howManyPathsWithDoublingBack());
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static class Graph {

        Map<String, Node> nodes;

        Graph() {
            nodes = new HashMap<>();
        }

        void addConnection(String a, String b) {
            Node nodeA = nodes.get(a);
            if (nodeA == null) {
                nodeA = new Node(a);
                nodes.put(a, nodeA);
            }
            Node nodeB = nodes.get(b);
            if (nodeB == null) {
                nodeB = new Node(b);
                nodes.put(b, nodeB);
            }
            nodeA.addNeighbor(nodeB);
            nodeB.addNeighbor(nodeA);

        }

        int howManyPaths() {
            Node start = nodes.get("start");
            Queue<Path> queue = new ArrayDeque<>();
            Path startPath = new Path();
            startPath = startPath.addMemberAndReturnCopyIfPossible(start);
            queue.add(startPath);
            int count = 0;
            List<Path> finalPaths = new ArrayList<>();
            while (!queue.isEmpty()) {
                Path currPath = queue.poll();
                List<Node> nextOnes = currPath.getLastVisited().neighbors;
                for (int i = 0; i < nextOnes.size(); ++i) {
                    if (nextOnes.get(i).name.equals("end")) {
                        ++count;
                        finalPaths.add(currPath.addMemberAndReturnCopyIfPossible(nextOnes.get(i)));
                    } else {
                        Path candidatePath = currPath.addMemberAndReturnCopyIfPossible(nextOnes.get(i));
                        if (candidatePath != null) {
                            queue.add(candidatePath);
                        }
                    }
                }
            }
            return count;
        }


        int howManyPathsWithDoublingBack() {
            Node start = nodes.get("start");
            Queue<ModifiedPath> queue = new ArrayDeque<>();
            ModifiedPath startPath = new ModifiedPath();
            startPath = startPath.addMemberAndReturnCopyIfPossible(start);
            queue.add(startPath);
            int count = 0;
            List<ModifiedPath> finalPaths = new ArrayList<>();
            while (!queue.isEmpty()) {
                ModifiedPath currPath = queue.poll();
                List<Node> nextOnes = currPath.getLastVisited().neighbors;
                for (int i = 0; i < nextOnes.size(); ++i) {
                    if (nextOnes.get(i).name.equals("end")) {
                        ++count;
                        finalPaths.add(currPath.addMemberAndReturnCopyIfPossible(nextOnes.get(i)));
                    } else {
                        ModifiedPath candidatePath = currPath.addMemberAndReturnCopyIfPossible(nextOnes.get(i));
                        if (candidatePath != null) {
                            queue.add(candidatePath);
                        }
                    }
                }
            }
            return count;
        }
    }

    private static class Path {
        List<Node> visited;

        Path() {
            visited = new ArrayList<>();
        }

        Node getLastVisited() {
            return visited.get(visited.size() - 1);
        }

        // Returns null if this is trying to visit a small Node again
        Path addMemberAndReturnCopyIfPossible(Node node) {
            if (visited.contains(node) && !node.isBig()) {
                return null;
            }
            Path newPath = copy();
            newPath.visited.add(node);
            return newPath;
        }

        private Path copy() {
            Path copy = new Path();
            copy.visited.addAll(visited);
            return copy;
        }
    }

    private static class ModifiedPath {
        List<Node> visited;
        boolean doubledBackAlready = false;

        ModifiedPath() {
            visited = new ArrayList<>();
        }


        Node getLastVisited() {
            return visited.get(visited.size() - 1);
        }

        // Returns null if this is trying to visit a small Node again
        ModifiedPath addMemberAndReturnCopyIfPossible(Node node) {
            if (visited.size() > 3) {
//                System.out.println("test");
            }
            if (visited.size() > 0 && node.name.equals("start")) {
                return null;
            }
            boolean newPathDoublesBack = false;
            if (visited.contains(node) && !node.isBig()) {
                if (!doubledBackAlready) {
                    newPathDoublesBack = true;
                } else {
                    return null;
                }
            }
            ModifiedPath newPath = copy(newPathDoublesBack || doubledBackAlready);
            newPath.visited.add(node);
            return newPath;
        }

        private ModifiedPath copy(boolean shouldDoubleBack) {
            ModifiedPath copy = new ModifiedPath();
            copy.visited.addAll(visited);
            copy.doubledBackAlready = shouldDoubleBack;
            return copy;
        }
    }

    private static class Node {
        String name;
        List<Node> neighbors;

        Node(String name) {
            this.name = name;
            neighbors = new ArrayList<>();
        }

        void addNeighbor(Node newNeighbor) {
            if (!neighbors.contains(newNeighbor)) {
                neighbors.add(newNeighbor);
            }
        }

        boolean isBig() {
            return Character.isUpperCase(name.toCharArray()[0]);
        }
    }
    
}
