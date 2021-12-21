package com.malcolmdeck.adventofcode2021.levels.level21;

import com.malcolmdeck.adventofcode2021.util.FileHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Level21 {

    public static void partOne() throws Exception {
        File file = FileHelper.getFile("level21\\level21data.txt");
        try {
            int playerOne = 10;
            int playerTwo = 3;
            int playerOneScore = 0;
            int playerTwoScore = 0;
            int rollCount = 0;
            int rollAmount = 1;
            while (playerOneScore < 1000 && playerTwoScore < 1000) {
                if (rollCount % 2 == 0) {
                    playerOne += rollAmount;
                    rollAmount = increaseRollAmount(rollAmount);
                    playerOne += rollAmount;
                    rollAmount = increaseRollAmount(rollAmount);
                    playerOne += rollAmount;
                    rollAmount = increaseRollAmount(rollAmount);
                    playerOne = playerOne % 10;
                    if (playerOne == 0) {
                        playerOne = 10;
                    }
                    playerOneScore += playerOne;
                } else {
                    playerTwo += rollAmount;
                    rollAmount = increaseRollAmount(rollAmount);
                    playerTwo += rollAmount;
                    rollAmount = increaseRollAmount(rollAmount);
                    playerTwo += rollAmount;
                    rollAmount = increaseRollAmount(rollAmount);
                    playerTwo = playerTwo % 10;
                    if (playerTwo == 0) {
                        playerTwo = 10;
                    }
                    playerTwoScore += playerTwo;
                }
                rollCount += 3;
            }
            System.out.println("Answer: " + (Math.min(playerOneScore, playerTwoScore) * rollCount));
        } catch (Exception e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    public static void partTwo() throws Exception {
        File file = FileHelper.getFile("level21\\level21data.txt");
        try {
            GameRepresentation gameRepresentation;
            Queue<GameRepresentation> queue = new ArrayDeque<>(100000);
            queue.add(new GameRepresentation(
                    true,
                    10,
                    3,
                    0,
                    0,
                    1l));
            long playerOneWins = 0l;
            long playerTwoWins = 0l;
            while (!queue.isEmpty()) {
                gameRepresentation = queue.poll();
                List<GameRepresentation> list = gameRepresentation.produceSubsequentGames();
                for (GameRepresentation game : list) {
                    if (game.isOver()) {
                        if (game.playerOneScore > 20) {
                            playerOneWins += game.numGamesRepresented;
                        } else {
                            playerTwoWins += game.numGamesRepresented;
                        }
                    } else {
                        queue.add(game);
                    }
                }
            }
            System.out.println("Answer: " + Math.max(playerOneWins, playerTwoWins));
        } catch (Exception e) {
            System.out.println("FileNotFound: " + e.getMessage());
        }
        return;
    }

    private static int increaseRollAmount(int rollAmount) {
        if (rollAmount < 100) {
            return rollAmount + 1;
        }
        return 1;
    }

    private static int incrementTrackPosition(int position) {
        if (position < 10) {
            return position + 1;
        }
        return 1;
    }


    private static final class GameRepresentation {
        boolean isPlayOnesTurn;
        int playerOneSpace;
        int playerTwoSpace;
        int playerOneScore;
        int playerTwoScore;
        long numGamesRepresented;

        static final int[] counts = {1,3,6,7,6,3,1};

        GameRepresentation(boolean isPlayOnesTurn,
                           int playerOneSpace,
                           int playerTwoSpace,
                           int playerOneScore,
                           int playerTwoScore,
                           long numGamesRepresented) {
            this.isPlayOnesTurn = isPlayOnesTurn;
            this.playerOneSpace = playerOneSpace;
            this.playerTwoSpace = playerTwoSpace;
            this.playerOneScore = playerOneScore;
            this.playerTwoScore = playerTwoScore;
            this.numGamesRepresented = numGamesRepresented;
        }

        boolean isOver() {
            return playerOneScore > 20 || playerTwoScore > 20;
        }

        List<GameRepresentation> produceSubsequentGames() {
            List<GameRepresentation> list = new ArrayList<>(7);
            if (isPlayOnesTurn) {
                playerOneSpace = incrementTrackPosition(playerOneSpace);
                playerOneSpace = incrementTrackPosition(playerOneSpace);
                playerOneSpace = incrementTrackPosition(playerOneSpace);
                for (int i = 3; i < 10; ++i) {
                    list.add(new GameRepresentation(
                            false,
                            playerOneSpace,
                            playerTwoSpace,
                            playerOneScore + playerOneSpace,
                            playerTwoScore,
                            numGamesRepresented * counts[i - 3]));
                    playerOneSpace = incrementTrackPosition(playerOneSpace);
                }
            } else {
                playerTwoSpace = incrementTrackPosition(playerTwoSpace);
                playerTwoSpace = incrementTrackPosition(playerTwoSpace);
                playerTwoSpace = incrementTrackPosition(playerTwoSpace);
                for (int i = 3; i < 10; ++i) {
                    list.add(new GameRepresentation(
                            true,
                            playerOneSpace,
                            playerTwoSpace,
                            playerOneScore,
                            playerTwoScore + playerTwoSpace,
                            numGamesRepresented * counts[i - 3]));
                    playerTwoSpace = incrementTrackPosition(playerTwoSpace);
                }
            }
            return list;
        }
    }

}
