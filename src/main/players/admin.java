package main.players;

import main.menus.leaderBoard;
import main.menus.mainMenu;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static main.choiceHelpers.loadChoice;
import static main.menus.leaderBoard.clearLeaderBoard;


/**
 * The type Admin.
 */
public class admin extends player {

    /**
     * Instantiates a new Admin.
     *
     * @param name the name
     */
    public admin(String name) {
        super(name);
        this.setTypeOfPlayer(3);
        greeting();
    }

    /**
     * Load player as admin.
     *
     * @param name the name
     * @return the admin
     */
    public static admin loadPlayer(String name) {
        leaderBoard load_player = new leaderBoard();
        HashMap<player, Integer> players = load_player.getScores();
        for (Map.Entry<player, Integer> entry : players.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                admin loadedPlayer = (admin) entry.getKey();
                load_player.removePlayer(loadedPlayer);
                load_player.saveLeaderBoard();
                return loadedPlayer;
            }
        }
        System.out.println("Player not found");
        return new admin(name);
    }

    public void greeting() {
        System.out.println("\n" +
                "▒█░▒█ █▀▀ █░░ █░░ █▀▀█ 　 ░█▀▀█ ▒█▀▀▄ ▒█▀▄▀█ ▀█▀ ▒█▄░▒█ █ \n" +
                "▒█▀▀█ █▀▀ █░░ █░░ █░░█ 　 ▒█▄▄█ ▒█░▒█ ▒█▒█▒█ ▒█░ ▒█▒█▒█ ▀ \n" +
                "▒█░▒█ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀ 　 ▒█░▒█ ▒█▄▄▀ ▒█░░▒█ ▄█▄ ▒█░░▀█ ▄");
    }
    /**
     * Admin menu.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void adminMenu() throws InterruptedException {
        while (true) {
            int option = loadChoice("Please choose an option:\n" +
                    "1. Clear Leaderboard\n" +
                    "2. Play Games\n" +
                    "3. Exit", 1, 3);
            if (option == 1) {
                clearLeaderBoard();
            } else if (option == 2) {
                mainMenu.chooseGame(this.getName(), this);
            } else {
                System.exit(0);
            }

        }
    }

}