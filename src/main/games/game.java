package main.games;

import main.players.player;

import java.util.Scanner;

import static main.choiceHelpers.loadChoice;

/**
 * The type Game methods.
 */
public abstract class game {


    /**
     * Betting float.
     *
     * @param player the player
     * @return float of how much the player bets
     */
    public static float betting(player player) {
        Scanner input = new Scanner(System.in);

        int option = loadChoice("Please choose an option:\n" +
                "1. Play for fun\n" +
                "2. Play for money\n" +
                "3. Quit this game", 1, 3);
        if (option == 1) {
            // play for fun
            return 0;
        } else if (option == 2) {
            // play for money - get players current balance
            float current_bal = player.getBettingBalance();

            // player must have positive balance to bet
            if (current_bal <= 0) {
                System.out.println("You're balance is " + current_bal + ". You will be unable to bet.");
                return 0;
            }
            float bettingAmount = 0;
            do {
                System.out.println("How much would you like to bet?\n" + "Your current balance is " + current_bal);
                try {
                    bettingAmount = input.nextFloat();
                } catch (Exception e) {
                    // catch bad input errors
                    System.out.println("Input was too large!");
                }

                // ensure betting amount is less than balance
                if (bettingAmount > current_bal) {
                    System.out.println("Betting amount must be less than or equal to your current balance of " + current_bal);
                }
            } while (bettingAmount > current_bal && bettingAmount > 0);

            // return betting amount
            System.out.println("You have chosen to bet: " + bettingAmount);
            return bettingAmount;
        } else {
            // quit
            return -1;
        }

    }

    /**
     * Betting calc.
     *
     * @param player        the new player
     * @param bettingAmount the betting amount
     * @param win           the win
     */
    public static void bettingCalc(player player, float bettingAmount, boolean win) {
        // if win, increase balance by betting amount
        if (win) {
            System.out.println("You won " + bettingAmount + "!");
            player.setBettingBalance(player.getBettingBalance() + bettingAmount);
        } else {
            // if lose decrease by betting amount
            System.out.println("You lost " + bettingAmount + "!");
            player.setBettingBalance(player.getBettingBalance() - bettingAmount);

        }
        System.out.println("You're new balance is " + player.getBettingBalance() + ".");
    }

}
