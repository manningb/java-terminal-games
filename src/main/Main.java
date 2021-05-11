package main;

import main.menus.mainMenu;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws InterruptedException {
        // welcome the player
        mainMenu.welcome();

        // start the main menu
        mainMenu.startMenu();
    }


}
