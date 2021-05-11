package main.menus;

import main.games.blackJack;
import main.games.coinFlip;
import main.players.admin;
import main.players.player;
import main.players.vipPlayer;

import java.util.Scanner;

import static main.choiceHelpers.loadChoice;


/**
 * The type Main menu.
 */
abstract public class mainMenu {
    /**
     * The Input.
     */
    static Scanner input = new Scanner(System.in);

    /**
     * Welcome the player to the games menu
     */
    public static void welcome() {
        System.out.print("\n" +
                "█░░░█ █▀▀ █░░ █▀▀ █▀▀█ █▀▄▀█ █▀▀ 　 ▀▀█▀▀ █▀▀█ \n" +
                "█▄█▄█ █▀▀ █░░ █░░ █░░█ █░▀░█ █▀▀ 　 ░░█░░ █░░█ \n" +
                "░▀░▀░ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀ ▀░░░▀ ▀▀▀ 　 ░░▀░░ ▀▀▀▀");
        System.out.println("\n" +
                "▒█▀▀█ █▀▀█ ░▀░ █▀▀█ █▀▀▄ █ █▀▀ 　 ▒█▀▀█ █▀▀█ █▀▄▀█ █▀▀ █▀▀ \n" +
                "▒█▀▀▄ █▄▄▀ ▀█▀ █▄▄█ █░░█ ░ ▀▀█ 　 ▒█░▄▄ █▄▄█ █░▀░█ █▀▀ ▀▀█ \n" +
                "▒█▄▄█ ▀░▀▀ ▀▀▀ ▀░░▀ ▀░░▀ ░ ▀▀▀ 　 ▒█▄▄█ ▀░░▀ ▀░░░▀ ▀▀▀ ▀▀▀");
    }

    /**
     * Start menu.
     *
     * @throws InterruptedException the interrupted exception
     */
    public static void startMenu() throws InterruptedException {
        int option = loadChoice("Please choose an option:\n" +
                "1. New Player\n" +
                "2. Load Player\n" +
                "3. Quit", 1, 3);
        if (option == 1) {
            createPlayer();
        } else if (option == 2) {
            loadPlayer();
        } else {
            System.out.println("Thank you for playing!");
        }

    }

    /**
     * Let the player choose which game they'd like to play
     *
     * @param name           the name of the player
     * @param current_player the current player
     * @throws InterruptedException the interrupted exception
     */
    public static void chooseGame(String name, player current_player) throws InterruptedException {
        // list of games' names
        String[] games = {"Coin Flip", "Black Jack"};
        int keep_playing;
        do {
            for (int i = 0; i < games.length; i++) {
                System.out.println((i + 1) + ": " + games[i]);
            }
            int game_choice = loadChoice("Hello " + name + ". Please choose a game, or 3 to quit:", 1, games.length + 1);

            // if player chooses to quit
            if (game_choice == 3) {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }

            System.out.println("You have chosen to play " + games[game_choice - 1]);
            if (game_choice == 1) {
                coinFlip.start(current_player);
            } else {
                blackJack.start(current_player);
            }

            // after game is over update and display leaderboard
            System.out.println("Thank you for playing!");
            leaderBoard currentLeaderBoard = new leaderBoard();
            currentLeaderBoard.updateLeaderBoard(current_player);
            currentLeaderBoard.saveLeaderBoard();
            currentLeaderBoard.displayLeaderBoard();

            // if the player is an admin, give option to go back to admin menu
            if (current_player.getTypeOfPlayer() == 3) {
                keep_playing = loadChoice("Please choose an option:\n" +
                        "1. Keep Playing!\n" +
                        "2. Back to Admin Menu\n" +
                        "3. Quit", 1, 3);
                if (keep_playing == 3) {
                    System.exit(0);
                }
            } else {
                keep_playing = loadChoice("Please choose an option:\n" +
                        "1. Keep Playing!\n" +
                        "2. Quit", 1, 2);
            }
        } while (keep_playing == 1);
        System.out.println("Thanks for playing!");
    }

    private static void createPlayer() throws InterruptedException {
        int playerOption = loadChoice("Please choose an option:\n" +
                "1. Regular Player\n" +
                "2. VIP Player\n" +
                "3. Admin", 1, 3);
        String name;
        while (true) {
            try {
                System.out.println("Good choice! Please enter your name: ");
                name = input.nextLine();
                break;
            } catch (Exception e) {
                // if input invalid print this message
                System.out.println("Invalid Value Received, please try again!");
            }
        }
        // load player to type based on their chosen option
        // note there are special methods for each type
        if (playerOption == 1) {
            player current_player = new player(name);
            chooseGame(name, current_player);
        } else if (playerOption == 2) {
            vipPlayer current_player = new vipPlayer(name);
            current_player.updateBettingBalance();
            chooseGame(name, current_player);
        } else if (playerOption == 3) {
            admin current_player = new admin(name);
            current_player.adminMenu();
        }
    }

    private static void loadPlayer() throws InterruptedException {
        // load player from the leaderboard
        leaderBoard leader = new leaderBoard();

        // display the players in the leaderboard
        leader.loadLeaderBoard();
        leader.displayPlayers();
        System.out.println("Great choice! Please enter a name from the list above: ");
        String name = input.nextLine();

        // check the type and initialise the player accordingly
        int playerOption = player.checkType(name);
        if (playerOption == 1) {
            player current_player = player.loadPlayer(name);
            chooseGame(name, current_player);
        } else if (playerOption == 2) {
            vipPlayer current_player = vipPlayer.loadPlayer(name);
            current_player.updateBettingBalance();
            chooseGame(name, current_player);
        } else if (playerOption == 3) {
            admin current_player = admin.loadPlayer(name);
            current_player.adminMenu();
        } else {
            // if the player isn't there, create a regular player with that name
            System.out.println("Player not found.. Creating new player");
            player current_player = new player(name);
            chooseGame(name, current_player);
        }
    }


}