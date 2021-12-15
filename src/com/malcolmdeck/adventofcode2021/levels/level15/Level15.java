package com.malcolmdeck.adventofcode2021.levels.level15;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level15 {
    
    private static final int SIZE = 100;
    private static final boolean DEBUG = false;

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level15\\level15data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[][] grid = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; ++i) {
                String line = scanner.next();
                for (int j = 0; j < SIZE; ++j) {
                    grid[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
            System.out.println("Min dist.: " + constructGraphFromGridAndFindShortestPath(grid));
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level15\\level15data.txt");
        try {
            Scanner scanner = new Scanner(file);
            int[][] grid = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; ++i) {
                String line = scanner.next();
                for (int j = 0; j < SIZE; ++j) {
                    grid[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
            grid = transformGrid(grid);
            System.out.println("Min dist.: " + constructGraphFromGridAndFindShortestPath(grid));
        } catch (
                FileNotFoundException e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static final int[][] transformGrid(int[][] grid) {
        int[][] newGrid = new int[SIZE * 5][SIZE * 5];
        for (int i = 0; i < SIZE * 5; ++i) {
            for (int j = 0; j < SIZE * 5; ++j) {
                int increment = (i / SIZE) + (j / SIZE);
                int newValue = grid[i%SIZE][j%SIZE] + increment;
                if (newValue > 9) {
                    newValue -= 9;
                }
                newGrid[i][j] = newValue;
            }
        }
        return newGrid;
    }

    private static int constructGraphFromGridAndFindShortestPath(int[][] grid) {
        int gridSize = grid.length;
        Map<Integer, Node> nodeMap = new HashMap<>(gridSize * gridSize);
        // Make nodes
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                nodeMap.put(i + (gridSize * j), new Node(String.valueOf(i + (gridSize * j))));
            }
        }
        // Attach nodes
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                Node currentNode = nodeMap.get(i + (gridSize * j));
                if (i > 0) {
                    Node neighborNode = nodeMap.get(i - 1 + (gridSize * j));
                    neighborNode.addDestination(currentNode, grid[i][j]);
                }
                if (i < gridSize - 1) {
                    Node neighborNode = nodeMap.get(i + 1 + (gridSize * j));
                    neighborNode.addDestination(currentNode, grid[i][j]);
                }
                if (j > 0) {
                    Node neighborNode = nodeMap.get(i + (gridSize * (j - 1)));
                    neighborNode.addDestination(currentNode, grid[i][j]);
                }
                if (j < gridSize - 1) {
                    Node neighborNode = nodeMap.get(i + (gridSize * (j + 1)));
                    neighborNode.addDestination(currentNode, grid[i][j]);
                }
            }
        }
        // Insert nodes
        Graph graph = new Graph();
        for (Node node : nodeMap.values()) {
            graph.addNode(node);
        }
        // Do Dijkstra on it
        calculateShortestPathFromSource(graph, nodeMap.get(0));
        Node finalNode = nodeMap.get(gridSize * gridSize - 1);
        if (DEBUG) {
            for (Node node : finalNode.shortestPath) {
                System.out.print(node.name + " -> ");
            }
        }
        System.out.println();
        return finalNode.distance;
    }

    private static class Graph {
        Set<Node> nodes = new HashSet<Node>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }
    }

    private static class Node {
        String name;
        List<Node> shortestPath = new LinkedList<>();
        Integer distance = Integer.MAX_VALUE;
        Map<Node, Integer> adjacentNodes = new HashMap<>();

        public Node(String name) {
            this.name = name;
        }

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

    }

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.distance = 0;

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair: currentNode.adjacentNodes.entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.distance;
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.distance;
        if (sourceDistance + edgeWeigh < evaluationNode.distance) {
            evaluationNode.distance = sourceDistance + edgeWeigh;
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.shortestPath);
            shortestPath.add(sourceNode);
            evaluationNode.shortestPath = shortestPath;
        }
    }
}
