package Multiplayer;

import java.io.*;
import java.util.Scanner;

/**
 * This is where player two guesses the value
 * that player one wrote in the file
 *
 * @author Lynn Snow
 */
public class Read {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new FileReader(Write.pubFileName));
            System.out.print("Guess the number typed in the file (1-20): ");
            int guess = sc.nextInt();
            while (!withinBounds(-1, 21, guess)) {
                System.out.print("Out of bounds! Guess the number typed in the file (1-20): ");
                guess = sc.nextInt();
            }
            String value = br.readLine();
            String stringGuess = Integer.toString(guess);
            readTheFile(stringGuess, value);
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    /**
     * Determines when the guess is equal to the answer.
     *
     * @param guess The number that is guessed
     * @param value The answer
     */
    private static void readTheFile(String guess, String value) {
        Scanner sc = new Scanner(System.in);
        while (!guess.equals(value)) {
            if (withinBounds(-1, 21, Integer.parseInt(guess))) {
                if (Integer.parseInt(guess) > Integer.parseInt(value)) {
                    System.out.print("Too high! Guess again: ");
                    int intGuess = sc.nextInt();
                    guess = Integer.toString(intGuess);
                } else {
                    System.out.print("Too low! Guess again: ");
                    int intGuess = sc.nextInt();
                    guess = Integer.toString(intGuess);
                }
            } else {
                System.out.print("Out of bounds! Guess again: ");
                int intGuess = sc.nextInt();
                guess = Integer.toString(intGuess);
            }
        }

        sc.close();

        System.out.println("You did it! The mystery value is " + value);
    }

    /**
     *
     * Determines whether the value is within the bounds
     *
     * @param min The low bound
     * @param max The high bound
     * @param value The value that's being checked.
     *
     * @return Whether the value is within the bounds
     */
    private static boolean withinBounds(int min, int max, int value) {
        return value > min && value < max;
    }


}
