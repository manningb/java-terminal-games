package main.games;

import main.players.player;

import java.util.Random;

import static main.choiceHelpers.loadChoice;

/**
 * The type Coin flip.
 */
public abstract class coinFlip extends game {

    /**
     * Starts the game of coin flip
     *
     * @param current_player the new player
     * @throws InterruptedException the interrupted exception
     */
    public static void start(player current_player) throws InterruptedException {
        // greet the player with a welcome message
        System.out.print("\n" +
                "█░░░█ █▀▀ █░░ █▀▀ █▀▀█ █▀▄▀█ █▀▀ 　 ▀▀█▀▀ █▀▀█ \n" +
                "█▄█▄█ █▀▀ █░░ █░░ █░░█ █░▀░█ █▀▀ 　 ░░█░░ █░░█ \n" +
                "░▀░▀░ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀ ▀░░░▀ ▀▀▀ 　 ░░▀░░ ▀▀▀▀");
        System.out.println("\n" +
                "▒█▀▀█ █▀▀█ ░▀░ █▀▀▄ 　 ▒█▀▀▀ █░░ ░▀░ █▀▀█ █ \n" +
                "▒█░░░ █░░█ ▀█▀ █░░█ 　 ▒█▀▀▀ █░░ ▀█▀ █░░█ ▀ \n" +
                "▒█▄▄█ ▀▀▀▀ ▀▀▀ ▀░░▀ 　 ▒█░░░ ▀▀▀ ▀▀▀ █▀▀▀ ▄");

        // initialise variable for while loop
        int keepPlaying;
        do {
            // get betting amount
            float betting_amount = betting(current_player);
            if (betting_amount == -1) {
                // betting_amount -1 means quit
                return;
            } else {
                // play coin flip
                int coinFlipResult = play();

                // update players points with result
                current_player.updatePoints(coinFlipResult);

                // if the player bet, get their new balance
                if (betting_amount > 0) {
                    // check if win or lose
                    boolean boolResult = (coinFlipResult == 2);
                    // use this along with the result of the game to update their balance
                    bettingCalc(current_player, betting_amount, boolResult);
                }
            }
            // player now has the option to keep playing
            keepPlaying = loadChoice("Would you like to keep playing Coin Flip?\n" +
                    "Please choose an option:\n" +
                    "1. Yes\n" +
                    "2. No", 1, 2);
        } while (keepPlaying == 1);
    }

    /**
     * Play coin flip.
     *
     * @return int, 0: lose, 2: win
     */
    private static int play() throws InterruptedException {
        // get choice of heads or tails from player
        int option = loadChoice("Please choose an option:\n" +
                "1. Heads\n" +
                "2. Tails", 1, 2);

        // print flipping message to screen
        System.out.println("Coin is being flipped\n");
        for (int i = 0; i < 3; i++) {
            long millis = System.currentTimeMillis();
            System.out.println("Flipping coin..");
            Thread.sleep(1000 - millis % 1000);
        }

        // get random number of either 1 or 2
        Random rand = new Random();
        int coin_toss = rand.nextInt(2) + 1;
        System.out.println("It landed on...");
        if (coin_toss == 1) {
            System.out.println("\n" +
                    "█░░█ █▀▀ █▀▀█ █▀▀▄ █▀▀ \n" +
                    "█▀▀█ █▀▀ █▄▄█ █░░█ ▀▀█ \n" +
                    "▀░░▀ ▀▀▀ ▀░░▀ ▀▀▀░ ▀▀▀");
        } else {
            System.out.println("\n" +
                    "▀▀█▀▀ █▀▀█ ░▀░ █░░ █▀▀ \n" +
                    "░░█░░ █▄▄█ ▀█▀ █░░ ▀▀█ \n" +
                    "░░▀░░ ▀░░▀ ▀▀▀ ▀▀▀ ▀▀▀");
        }
        // return output based on coin flip
        if (coin_toss == option) {
            System.out.println("You guessed correctly!");
            return 2;
        } else {
            System.out.println("You guessed wrong!");
            return 0;
        }
    }
}
