package main;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class choiceHelpers {
    /**
     * Load choice int
     *
     * @param message     the message you would like printed, preferably stating which nubmers correspond to which options
     * @param startRange  the start range, this is the start range for the input numbers
     * @param intEndRange the int end range, end range for input numbers
     * @return the int - the player's choice
     */
    public static int loadChoice(String message, int startRange, int intEndRange) {
        Scanner input = new Scanner(System.in);
        int option = 0;
        // create list of valid options based on the input range
        List<Integer> valid_options = IntStream.rangeClosed(startRange, intEndRange)
                .boxed().collect(Collectors.toList());

        // loop until player chooses a valid option
        while (!valid_options.contains(option)) {
            try {
                System.out.println(message);
                option = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                // if input invalid print this message
                System.out.println("Please enter an integer value for the inputs");
            }
        }
        return option;
    }

}
