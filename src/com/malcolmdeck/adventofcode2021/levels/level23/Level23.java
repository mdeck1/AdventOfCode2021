package com.malcolmdeck.adventofcode2021.levels.level23;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level23 {

    static int examineCounter = 0;

    public static void partOne() throws Exception {
        try {
            Set<Amphipod> baseSet = new HashSet<>(8);
            baseSet.add(new Amphipod(1, false, 1, 2));
            baseSet.add(new Amphipod(1000, false, 2, 2));
            baseSet.add(new Amphipod(100, false, 1, 4));
            baseSet.add(new Amphipod(1000, false, 2, 4));
            baseSet.add(new Amphipod(10, false, 1, 6));
            baseSet.add(new Amphipod(1, false, 2, 6));
            baseSet.add(new Amphipod(10, false, 1, 8));
            baseSet.add(new Amphipod(100, false, 2, 8));
            State state = new State(baseSet, 0, new ArrayList<>());
            int minCost = 18170; // Min spit out so far to make computation truncation happen faster
            System.out.println("Answer: " + getMinCost(state, minCost));
        } catch (Exception e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        try {
            Set<Amphipod> baseSet = new HashSet<>(8);
            baseSet.add(new Amphipod(1, false, 1, 2));
            baseSet.add(new Amphipod(1000, false, 2, 2));
            baseSet.add(new Amphipod(1000, false, 3, 2));
            baseSet.add(new Amphipod(1000, false, 4, 2));
            baseSet.add(new Amphipod(100, false, 1, 4));
            baseSet.add(new Amphipod(100, false, 2, 4));
            baseSet.add(new Amphipod(10, false, 3, 4));
            baseSet.add(new Amphipod(1000, false, 4, 4));
            baseSet.add(new Amphipod(10, false, 1, 6));
            baseSet.add(new Amphipod(10, false, 2, 6));
            baseSet.add(new Amphipod(1, false, 3, 6));
            baseSet.add(new Amphipod(1, false, 4, 6));
            baseSet.add(new Amphipod(10, false, 1, 8));
            baseSet.add(new Amphipod(1, false, 2, 8));
            baseSet.add(new Amphipod(100, false, 3, 8));
            baseSet.add(new Amphipod(100, false, 4, 8));
            State state = new State(baseSet, 0, new ArrayList<>());
            int minCost = Integer.MAX_VALUE; // Min spit out so far to make computation truncation happen faster
            System.out.println("Answer: " + getMinCost(state, minCost));
        } catch (Exception e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    static int getMinCost(State state, int minCostSeenSoFar) {
        if (examineCounter++ % 1000000 == 0) {
            System.out.println("Examined " + examineCounter + " states");
        }
        if (state.costSoFar >= minCostSeenSoFar) {
            return minCostSeenSoFar;
        }
        if (state.isDone()) {
            System.out.println("Found a done state with lower cost: " + state.costSoFar);
            System.out.println("Moves: ");
            for (String move : state.moves) {
                System.out.println(move);
            }
            return state.costSoFar;
        }
        for (Amphipod a : state.amphipods) {
            Set<Amphipod> copy = new HashSet<>(state.amphipods);
            copy.remove(a);
            List<Amphipod> newPositions = a.produceMovesGivenState(state.amphipods);
            for (Amphipod newSpot : newPositions) {
                copy.add(newSpot);
                List<String> moveCopy = new ArrayList<>(state.moves.size() + 1);
                for (String move : state.moves) {
                    moveCopy.add(new String(move));
                }
                moveCopy.add("" + newSpot.cost + ": from (" + a.row + ", " + a.col + ") to (" + newSpot.row + ", " + newSpot.col + ") newCost: " + (state.costSoFar + a.costDelta(newSpot)));
                minCostSeenSoFar =
                        getMinCost(
                                new State(
                                        copy,
                                        state.costSoFar + a.costDelta(newSpot),
                                        moveCopy),
                                minCostSeenSoFar);
                copy.remove(newSpot);
            }
        }
        return minCostSeenSoFar;
    }

    private static class State {
        Set<Amphipod> amphipods;
        int costSoFar;
        List<String> moves;

        State(Set<Amphipod> amphipods, int costSoFar, List<String> moves) {
            this.amphipods = new HashSet<>();
            for (Amphipod a : amphipods) {
                this.amphipods.add(a.copy());
            }
            this.costSoFar = costSoFar;
            this.moves = moves;
        }

        boolean isDone() {
            for (Amphipod a : amphipods) {
                if (!a.isHome()) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class Amphipod {

        int cost;
        boolean hasMoved;
        int row;
        int col;

        Amphipod(int cost, boolean hasMoved, int row, int col) {
            this.cost = cost;
            this.hasMoved = hasMoved;
            this.row = row;
            this.col = col;
        }

        int costDelta(Amphipod other) {
            if (this.cost != other.cost) {
                throw new RuntimeException("Mismatched costs");
            }
            if (this.row != 0 && other.row != 0) {
                return (this.row + other.row + Math.abs(this.col - other.col)) * cost;
            }
            return (Math.abs(this.row - other.row) + Math.abs(this.col - other.col)) * cost;
        }

        List<Amphipod> produceMovesGivenState(Set<Amphipod> amphipods) {
            List<Amphipod> list = new ArrayList<>();
            if (!hasMoved) {
                // Leave your cave

                // If blocked from above, you can't move
                if (row > 1) {
                    for (Amphipod a : amphipods) {
                        if (a.col == this.col && a.row > 0 && a.row < row) {
                            return list;
                        }
                    }
                }
                // Find left and right bounds in the cave from other Amphipods
                int left = 0;
                int right = 10;
                for (Amphipod a : amphipods) {
                    if (a.row == 0) {
                        if (a.col < this.col && a.col >= left) {
                            left = a.col + 1;
                        } else if (a.col > this.col && a.col <= right) {
                            right = a.col - 1;
                        }
                    }
                }
                // Go back to your target cave, if possible
                int targetCol = getTargetCol();
                int deepestSlotAvailable = 4;
                boolean canGoToTargetCave = true;
                for (Amphipod a : amphipods) {
                    if (a.col == targetCol) {
                        if (a.cost != this.cost) {
                            // Something is in the cubby and is the wrong cost
                            canGoToTargetCave = false;
                            break;
                        } else {
                            deepestSlotAvailable = Math.min(a.row - 1, deepestSlotAvailable);
                        }
                    } else if (a.row == 0 &&
                            ((a.col > this.col && a.col < targetCol) ||
                                    (a.col < this.col && a.col > targetCol))) {
                        // Something is in the
                        canGoToTargetCave = false;
                        return list;
                    }
                }
                if (canGoToTargetCave) {
                    list.add(new Amphipod(
                            this.cost,
                            true,
                            deepestSlotAvailable,
                            targetCol));
                }
                // Add possible spaces above you, if any exist
                for (int i = left; i <= right; ++i) {
                    if (i%2 == 0 && !(i == 0 || i == 10)) {
                        continue;
                    }
                    list.add(new Amphipod(this.cost, true, 0, i));
                }
                list.sort(new Comparator<Amphipod>() {
                    @Override
                    public int compare(Amphipod o1, Amphipod o2) {
                        return (Math.abs(o1.col - Amphipod.this.col) + Math.abs(o1.row - Amphipod.this.row)) -
                                (Math.abs(o2.col - Amphipod.this.col) + Math.abs(o2.row - Amphipod.this.row));
                    }
                });
                return list;
            } else {
                // Already home
                if (this.row != 0) {
                    return list;
                }
                // Go back to your target cave, if possible
                int targetCol = getTargetCol();
                int deepestSlotAvailable = 4;
                for (Amphipod a : amphipods) {
                    if (a.col == targetCol) {
                        if (a.cost != this.cost) {
                            // Something is in the cubby and is the wrong cost
                            return list;
                        } else {
                            deepestSlotAvailable = Math.min(a.row - 1, deepestSlotAvailable);
                        }
                    } else if (a.row == 0 &&
                            ((a.col > this.col && a.col < targetCol) ||
                                    (a.col < this.col && a.col > targetCol))) {
                        // Something is in the
                        return list;
                    }
                }
                list.add(new Amphipod(
                        this.cost,
                        true,
                        deepestSlotAvailable,
                        targetCol));
                return list;
            }
        }

        int getTargetCol() {
            int targetCol = -999;
            switch (this.cost) {
                case 1:
                    targetCol = 2;
                    break;
                case 10:
                    targetCol = 4;
                    break;
                case 100:
                    targetCol = 6;
                    break;
                case 1000:
                    targetCol = 8;
                    break;
                default:
                    throw new RuntimeException("Invalid cost");
            }
            return targetCol;
        }

        boolean isHome() {
            return this.col == getTargetCol();
        }

        Amphipod copy() {
            return new Amphipod(this.cost, this.hasMoved, this.row, this.col);
        }

    }

}
