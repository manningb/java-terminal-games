package main.players;

import main.menus.leaderBoard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Vip player.
 */
public class vipPlayer extends player implements Serializable {
    /**
     * Instantiates a new Vip player.
     *
     * @param name the name
     */
    public vipPlayer(String name) {
        super(name);
        this.setTypeOfPlayer(2);
        greeting();
    }

    /**
     * Load player as vip player.
     *
     * @param name the name
     * @return the vip player
     */
    public static vipPlayer loadPlayer(String name) {
        leaderBoard load_player = new leaderBoard();
        HashMap<player, Integer> players = load_player.getScores();
        for (Map.Entry<player, Integer> entry : players.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                vipPlayer loadedPlayer = (vipPlayer) entry.getKey();
                load_player.removePlayer(loadedPlayer);
                load_player.saveLeaderBoard();
                return loadedPlayer;
            }
        }
        System.out.println("Player not found");
        return new vipPlayer(name);
    }

    public void greeting() {
        System.out.println("\n" +
                "█░░█ █▀▀ █░░ █░░ █▀▀█ 　 ▀█░█▀ ░▀░ █▀▀█ \n" +
                "█▀▀█ █▀▀ █░░ █░░ █░░█ 　 ░█▄█░ ▀█▀ █░░█ \n" +
                "▀░░▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀ 　 ░░▀░░ ▀▀▀ █▀▀▀");
    }

    /**
     * Update betting balance.
     */
    public void updateBettingBalance() {
        this.setBettingBalance(this.getBettingBalance() * 2);
        System.out.println("Hey! We've doubled your betting balance because you're a VIP!");
    }

}
