package main.games;

import main.players.player;

import java.util.*;

import static main.choiceHelpers.loadChoice;


/**
 * The type Black jack.
 */
public abstract class blackJack extends game {


    /**
     * Starts the game of blackjack
     *
     * @param current_player the new player
     */
    public static void start(player current_player) {
        // greet the player with a welcome message
        System.out.print("\n" +
                "█░░░█ █▀▀ █░░ █▀▀ █▀▀█ █▀▄▀█ █▀▀ 　 ▀▀█▀▀ █▀▀█ \n" +
                "█▄█▄█ █▀▀ █░░ █░░ █░░█ █░▀░█ █▀▀ 　 ░░█░░ █░░█ \n" +
                "░▀░▀░ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀▀ ▀░░░▀ ▀▀▀ 　 ░░▀░░ ▀▀▀▀");
        System.out.println("\n" +
                "▒█▀▀█ █░░ █▀▀█ █▀▀ █░█ 　 ░░░▒█ █▀▀█ █▀▀ █░█ █ \n" +
                "▒█▀▀▄ █░░ █▄▄█ █░░ █▀▄ 　 ░▄░▒█ █▄▄█ █░░ █▀▄ ▀ \n" +
                "▒█▄▄█ ▀▀▀ ▀░░▀ ▀▀▀ ▀░▀ 　 ▒█▄▄█ ▀░░▀ ▀▀▀ ▀░▀ ▄");

        // initialise variable for while loop
        int keepPlaying;
        do {
            // get betting amount
            float betting_amount = betting(current_player);
            if (betting_amount == -1) {
                // betting_amount -1 means quit
                return;
            } else {
                // play black jack
                int blackJackResult = play();

                // update players points with result
                current_player.updatePoints(blackJackResult);

                // if the player bet, get their new balance
                if (betting_amount > 0) {
                    if (blackJackResult != 1) {
                        // check if win or lose
                        boolean boolResult = (blackJackResult == 2);
                        // use this along with the result of the game to update their balance
                        bettingCalc(current_player, betting_amount, boolResult);
                    } else {
                        System.out.println("Tie Breaker!");
                    }
                }
            }
            // player now has the option to keep playing
            keepPlaying = loadChoice("Would you like to keep playing Black Jack?\n" +
                    "Please choose an option:\n" +
                    "1. Yes\n" +
                    "2. No", 1, 2);
        } while (keepPlaying == 1);
    }

    /**
     * Play black jack.
     *
     * @return int, 0: lose, 1: tiebreaker, 2: win
     */
    private static int play() {
        // Initialise the Deck
        Deck playingCards = new Deck();

        // hit the player with two cards
        System.out.println("You are being dealt cards..");
        int playerPoints = playingCards.hitMe();
        playerPoints += playingCards.hitMe();

        // Hit the dealer with two cards
        System.out.println("Dealer is being dealt cards..");
        int dealerPoints = playingCards.hitMe();
        dealerPoints += playingCards.hitMe();


        while (true) {
            // print out the current state of points
            System.out.println("Your current points: " + playerPoints);
            System.out.println("Dealer's current points: " + dealerPoints);

            // Round - Stand or Hit me
            int option = loadChoice("Please choose an option:\n" +
                    "1. Stand\n" +
                    "2. Hit Me", 1, 2);

            // if player chooses 2 they will be hit with another card
            if (option == 2) {
                System.out.println("You are being dealt a card..");
                playerPoints += playingCards.hitMe();
            }

            // rules from wikipedia: https://en.wikipedia.org/wiki/Blackjack#Rules

            // dealer only gets another card when points greater than 17
            if (dealerPoints < 17) {
                System.out.println("Dealer is being dealt a card..");
                dealerPoints += playingCards.hitMe();
            } else if (option == 1) {
                // if dealer's points are greater than 17 and player has chosen to stand the game is over
                if (playerPoints == dealerPoints) {
                    // check tie break
                    return 1;
                } else {
                    // check if player's points are less than or equal to 21 and also greater than dealers points
                    return (playerPoints <= 21) && (playerPoints > dealerPoints) ? 2 : 0;
                }
            }

            // if the player has points greater than 21 they are bust
            if (playerPoints > 21) {
                System.out.println("Bust!");
                return 0;

                // if dealer then has points greater than 21, player wins
            } else if (dealerPoints > 21) {
                System.out.println("Dealer Bust");
                return 2;

                // if both the player and dealer tie, tie breaker
            } else if (playerPoints == 21 && dealerPoints == 21) {
                System.out.println("Tie Breaker!");
                return 1;

                // check for blackjack
            } else if (playerPoints == 21) {
                System.out.println("You win!");
                return 2;
            } else if (dealerPoints == 21) {
                System.out.println("Dealer wins!");
                return 0;
            }
        }
    }

    /**
     * The type Deck.
     */
    static class Deck {
        // stores a map relating card to its value
        private Map<String, Integer> Deck;

        // list of the current order of cards
        private List<String> cardOrder;

        /**
         * Instantiates a new Deck.
         */
        public Deck() {
            this.setDeck();
            this.setCardOrder();
        }

        private void setDeck() {
            // suits set as their images
            String[] Suits = {"♣", "♦", "♥", "♠"};

            // numbers set as strings
            String[] stringNumber = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

            // value of the above stringNumbers
            int[] numberValue = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

            // set the deck to be combinations of both the suits and the numbers
            Map<String, Integer> Deck = new HashMap<>();
            for (String suit : Suits) {
                for (int j = 0; j < stringNumber.length; j++) {
                    Deck.put(stringNumber[j] + "" + suit, numberValue[j]);
                }
            }

            // make the internal deck equal to the deck we have created
            this.Deck = Deck;
        }

        /**
         * Sets a random card order.
         */
        public void setCardOrder() {
            List<String> cards = new ArrayList<>(Deck.keySet());
            Collections.shuffle(cards);
            this.cardOrder = cards;
        }

        /**
         * Hit me int.
         *
         * @return the int value of the card
         * prints the card which is hit
         */
        public int hitMe() {
            String choice = cardOrder.get(0);
            int choiceValue = Deck.get(choice);
            cardOrder.remove(choice);
            cardOrder.add(choice);
            System.out.println(choice);
            return choiceValue;
        }
    }
}
