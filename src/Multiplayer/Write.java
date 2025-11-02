package Multiplayer;

import java.io.*;
import java.util.Scanner;

/**
 * This is where player one writes a number
 * between 1 and 20 to a file.
 *
 * @author Lynn Snow
 *

 */
public class Write {

    public static String pubFileName = "guess.txt";
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            BufferedWriter bw = new BufferedWriter(new FileWriter(pubFileName));
            System.out.print("Type a number between 0 and 20: ");
            int content = sc.nextInt();
            while (!withinBounds(-1, 21, content)) {
                System.out.print("Out of bounds! Type a number between 0 and 20: ");
                content = sc.nextInt();
            }
            bw.write(Integer.toString(content));
            System.out.println("Written!");
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Oh no it all went wrong!");
        }
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
