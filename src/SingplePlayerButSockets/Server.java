package SingplePlayerButSockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static String fileName = "guess.txt";
    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket connection = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        String clientMessage = "";
        String serverMessage = "";
        BufferedWriter bw = null;
        boolean rightGuess = false;

        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Server started, listening on port 8888");

            connection = serverSocket.accept();
            System.out.println("Got a connection!");

            // Generate a random number and write it to the file
            int num = (int) (Math.random() * 21);
            bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(Integer.toString(num));
            bw.close();

            input = new DataInputStream(connection.getInputStream());
            output = new DataOutputStream(connection.getOutputStream());


            // Classes to receive messages from client
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true);

            // Write a message to the client
            serverMessage = "A random number has been generated between 0 and 20!";
            writer.println(serverMessage);
            serverMessage = "Take a guess, don't be shy!";
            writer.println(serverMessage);

            // While this boolean flag isn't triggered, play the game with the client
            while (!rightGuess) {
                clientMessage = reader.readLine();
                if (clientMessage == null) {
                    continue;
                }

                System.out.println(clientMessage);

                if (Integer.parseInt(clientMessage) == num) {
                    serverMessage = "Good job!";
                    writer.println(serverMessage);
                    rightGuess = true;
                }

                if (Integer.parseInt(clientMessage) < -1 ||
                    Integer.parseInt(clientMessage) > 21) {
                    serverMessage = "Out of bounds! Guess again: ";
                    writer.println(serverMessage);
                }

                if (Integer.parseInt(clientMessage) > num) {
                    serverMessage = "Too high! Guess again: ";
                    writer.println(serverMessage);
                } else {
                    serverMessage = "Too low! Guess again: ";
                    writer.println(serverMessage);
                }

            }

            writer.close();
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            serverSocket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}