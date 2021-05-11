package main.menus;

import main.players.player;

import java.io.*;
import java.util.*;

/**
 * The type Leader board.
 */
public class leaderBoard {

    // hashmap to store the leaderboard
    private HashMap<player, Integer> scores = new HashMap<>();

    /**
     * Sort leader board hash map.
     *
     * @param scores the scores of the leaderboard
     * @return the hash map of the sorted scores
     */
    public static HashMap<player, Integer> sortLeaderBoard(HashMap<player, Integer> scores) {
        // Create a list from elements of HashMap
        List<Map.Entry<player, Integer>> list =
                new LinkedList<>(scores.entrySet());

        // Sort the list based on their scores
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        // put data from sorted list to hashmap
        HashMap<player, Integer> sortedScores = new LinkedHashMap<>();
        for (Map.Entry<player, Integer> aa : list) {
            sortedScores.put(aa.getKey(), aa.getValue());
        }
        return sortedScores;
    }

    /**
     * Update leader board with points of active player
     *
     * @param current_player the player
     */
    public void updateLeaderBoard(player current_player) {
        // ensure the leaderboard is loaded
        loadLeaderBoard();

        // loop through the scores
        for (player key : scores.keySet()) {
            // if player found in the current scores
            if (key.getName().equals(current_player.getName())) {
                // if their values are empty, put in their current values
                if (scores.get(key) == null) {
                    scores.put(current_player, current_player.getTotalPoints());
                } else {
                    // put in and remove the current values plus the previous values
                    scores.put(current_player, scores.get(key) + current_player.getTotalPoints());
                    scores.remove(key);
                }
                // update players points
                current_player.setPoints(sumPoints(current_player.getPoints(), key.getPoints()));
                key.setPoints(sumPoints(current_player.getPoints(), key.getPoints()));
                break;
            }
        }
        // put in new player into the leader board
        scores.put(current_player, current_player.getTotalPoints());
    }

    private Integer[] sumPoints(Integer[] current, Integer[] newPoints) {
        // get sum of two Integer arrays
        Integer[] sum = new Integer[3];
        for (int i = 0; i < 3; i++) {
            sum[i] = current[i] + newPoints[i];
        }
        return sum;
    }

    /**
     * Remove player from the leaderboard
     *
     * @param Player the player
     */
    public void removePlayer(player Player) {
        scores.remove(Player);
    }

    /**
     * Display players in the list
     * Used when loading a player
     */
    public void displayPlayers() {
        // sort the scores
        scores = sortLeaderBoard(scores);

        // iterate through the scores, printing the players' names
        Set<?> set = scores.entrySet();
        Iterator<?> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<player, Integer> mapEntry = (Map.Entry<player, Integer>) iterator.next();
            player current_player = mapEntry.getKey();
            System.out.println(current_player.getName());
        }
    }


    /**
     * Display leader board.
     */
    public void displayLeaderBoard() {
        // leaderboard welcome messages
        System.out.println("\n" +
                "░█─── █▀▀ █▀▀█ █▀▀▄ █▀▀ █▀▀█ █▀▀▄ █▀▀█ █▀▀█ █▀▀█ █▀▀▄ \n" +
                "░█─── █▀▀ █▄▄█ █──█ █▀▀ █▄▄▀ █▀▀▄ █──█ █▄▄█ █▄▄▀ █──█ \n" +
                "░█▄▄█ ▀▀▀ ▀──▀ ▀▀▀─ ▀▀▀ ▀─▀▀ ▀▀▀─ ▀▀▀▀ ▀──▀ ▀─▀▀ ▀▀▀─");

        // sort the scores
        scores = sortLeaderBoard(scores);

        // iterate through the leaderboard, printing relevant values
        Set<?> set = scores.entrySet();
        Iterator<?> iterator = set.iterator();
        System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%n", new String[]{"Name", "Total", "Losses", "Draws", "Wins", "Date"});
        while (iterator.hasNext()) {
            Map.Entry<player, Integer> mapEntry = (Map.Entry<player, Integer>) iterator.next();
            player current_player = mapEntry.getKey();
            Integer[] scores = current_player.getPoints();
            Integer total = current_player.getTotalPoints();
            System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%n", new String[]{current_player.getName(), String.valueOf(total), String.valueOf(scores[0]), String.valueOf(scores[1]), String.valueOf(scores[2]), String.valueOf(current_player.getDateCreated())});
        }

    }

    /**
     * Load leader board from serialised file
     */
    public void loadLeaderBoard() {
        try {
            // read leaderboard into scores variable
            FileInputStream fis = new FileInputStream("src/main/leaderboard.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            scores = (HashMap<player, Integer>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            //  if leaderboard not found, create it, then load it
            saveLeaderBoard();
            loadLeaderBoard();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save leader board to file
     */
    public void saveLeaderBoard() {
        try {
            // save the leaderboard to this file
            FileOutputStream fos =
                    new FileOutputStream("src/main/leaderboard.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // write the scores variable
            oos.writeObject(scores);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Gets scores.
     *
     * @return the scores
     */
    public HashMap<player, Integer> getScores() {
        loadLeaderBoard();
        return scores;
    }

    /**
     * Clear leader board.
     * Used by admin
     */
    public static void clearLeaderBoard() {
        try {
            File file = new File("src/main/leaderboard.ser");
            if (file.delete()) {
                System.out.println("Leaderboard Cleared!");
            } else {
                System.out.println("Leaderboard could not be cleared.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

