package main.players;

import main.menus.leaderBoard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Player.
 */
public class player implements Serializable {
    // player's name
    private String name;

    // lose, draw, win count
    private Integer[] points = {0, 0, 0};

    // betting balance, default 10
    private float bettingBalance = 10;

    // date the player is created
    private Date dateCreated = new Date();

    // type of player, 1: regular (default), 2: vip, 3: admin
    private int typeOfPlayer = 1;

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public player(String name) {
        this.name = name;
    }

    /**
     * Check type int.
     *
     * @param name the name
     * @return the int
     */
    public static int checkType(String name) {
        leaderBoard load_player = new leaderBoard();
        HashMap<player, Integer> players = load_player.getScores();
        for (Map.Entry<player, Integer> entry : players.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                player loadedPlayer = entry.getKey();
                return loadedPlayer.getTypeOfPlayer();
            }
        }
        return -1;
    }

    /**
     * Load player based on their name
     *
     * @param name the name
     * @return the player
     */
    public static player loadPlayer(String name) {
        // use the leaderboard to load the player
        leaderBoard load_player = new leaderBoard();
        HashMap<player, Integer> players = load_player.getScores();
        for (Map.Entry<player, Integer> entry : players.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                // initialise player
                player loadedPlayer = entry.getKey();
                // player is removed from the leaderboard as it will be put in later
                load_player.removePlayer(loadedPlayer);

                // leaderboard state is saved
                load_player.saveLeaderBoard();
                return loadedPlayer;
            }
        }

        // if player not found return a regular player with that name
        System.out.println("Player not found");
        return new player(name);
    }

    /**
     * Gets type of player.
     *
     * @return the type of player
     */
    public int getTypeOfPlayer() {
        return typeOfPlayer;
    }

    /**
     * Sets type of player.
     *
     * @param typeOfPlayer the type of player
     */
    public void setTypeOfPlayer(int typeOfPlayer) {
        this.typeOfPlayer = typeOfPlayer;
    }

    /**
     * Gets betting balance.
     *
     * @return the betting balance
     */
    public float getBettingBalance() {
        return bettingBalance;
    }

    /**
     * Sets betting balance.
     *
     * @param bettingBalance the betting balance
     */
    public void setBettingBalance(float bettingBalance) {
        this.bettingBalance = bettingBalance;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get points integer [ ].
     *
     * @return the integer [ ]
     */
    public Integer[] getPoints() {
        return points;
    }

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(Integer[] points) {
        this.points = points;
    }

    /**
     * Update points.
     *
     * @param pointsType the points type
     */
    public void updatePoints(int pointsType) {
        this.points[pointsType] += 1;
    }

    /**
     * Gets date created.
     *
     * @return the date created
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets date created.
     *
     * @param dateCreated the date created
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    private void writeObject(ObjectOutputStream oos)
            throws IOException {
        // default serialization
        oos.defaultWriteObject();
        // write the object
        ArrayList<Object> loc = new ArrayList<>();
        loc.add(name);
        loc.add(points);
        loc.add(bettingBalance);
        loc.add(dateCreated);
        loc.add(typeOfPlayer);
        oos.writeObject(loc);
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();
        ArrayList<?> loc = (ArrayList<?>) ois.readObject(); // Replace with real deserialization
        if ((int) loc.get(4) == 1) {
            this.setName((String) loc.get(0));
            this.setPoints((Integer[]) loc.get(1));
            this.setBettingBalance((float) loc.get(2));
            this.setDateCreated((Date) loc.get(3));
        } else if ((int) loc.get(4) == 2) {
            new vipPlayer((String) loc.get(0));
        }

    }

    /**
     * Gets total points.
     *
     * @return the total points
     */
    public int getTotalPoints() {
        return 3 * points[2] + points[1] - points[0];
    }

    /**
     * Greeting.
     */
    public void greeting() {
        System.out.println("\n" +
                "▒█░▒█ █▀▀ █░░ █░░ █▀▀█ \n" +
                "▒█▀▀█ █▀▀ █░░ █░░ █░░█ \n" +
                "▒█░▒█ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀");
    }

}