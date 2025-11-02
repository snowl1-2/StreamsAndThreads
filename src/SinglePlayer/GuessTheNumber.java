package SinglePlayer;

import java.io.*;
import java.util.Scanner;

/**
 * A singleplayer guessing game that uses java threads.
 *
 * @author Lynn Snow
 *
 */
public final class GuessTheNumber {
    private final static String fileName = "guess.txt";
    private final static String[] loadingAnimation = {".", "..", "..."};

    public static void main(String[] args) {
        writer.start();
        loaderAndGuesser.start();
    }

    // Generates and writes a number into the file
    static Thread writer = new Thread(() -> {
        try {
            Thread.sleep(3000);
            int num = (int) (Math.random() * 21);
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(Integer.toString(num));
            bw.close();
            System.out.println("\nNumber generated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    static Thread loaderAndGuesser = new Thread(() -> {
        int guess;
        String stringGuess;
        Scanner sc = new Scanner(System.in);
        int i = 0;
        String value;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Waiting animation while writer thread is doing its thing
            while (writer.isAlive()) {
                System.out.print("\rwaiting" + loadingAnimation[i % 3]);
                i++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // Guessing.
            // Yes I tried doing it in the main method.
            // No it did not work.
            System.out.print("\nGuess a number between 0 and 20: ");
            guess = sc.nextInt();

            // Is the guess within the bounds
            while (!withinBounds(-1, 21, guess)) {
                System.out.print("Out of bounds! Guess again: ");
                guess = sc.nextInt();
            }

            stringGuess = Integer.toString(guess);
            value = br.readLine();
            amIEqualTo(stringGuess, value);

            // Yippee
            System.out.println("You did it! The mystery value is " + value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    });

    /**
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

    /**
     * Determines when the guess is equal to the answer.
     *
     * @param guess The number that is guessed
     * @param value The answer
     */
    private static void amIEqualTo(String guess, String value) {
        Scanner sc = new Scanner(System.in);
        int intGuess;
        while (!guess.equals(value)) {
            if (withinBounds(-1, 21, Integer.parseInt(guess))) {
                if (Integer.parseInt(guess) > Integer.parseInt(value)) {
                    System.out.print("Too high! Guess again: ");
                    intGuess = sc.nextInt();
                    guess = Integer.toString(intGuess);
                } else {
                    System.out.print("Too low! Guess again: ");
                    intGuess = sc.nextInt();
                    guess = Integer.toString(intGuess);
                }
            } else {
                System.out.print("Out of bounds! Guess again: ");
                intGuess = sc.nextInt();
                guess = Integer.toString(intGuess);
            }

        }
    }

}
