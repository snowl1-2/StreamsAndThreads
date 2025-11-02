package SingplePlayerButSockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket client = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        String clientMessage = "";
        String serverMessage = "";
        Scanner in = null;
        String[] servMP = {"Take a guess, don't be shy!",
                "Too high! Guess again: ",
                "Too low! Guess again: ",
                "Out of bounds! Guess again: "};

        try {
            client = new Socket("localhost", 8888);
            System.out.println("Connected to server!");
            in = new Scanner(System.in);

            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true);

            // Don't close the connection with the server
            while (client.isConnected()) {
                serverMessage = reader.readLine();

                // "Hold" position until server message isn't null
                if (serverMessage == null) {
                    continue;
                }

                System.out.println(serverMessage);

                if (serverMessage.equals(servMP[0]) ||
                        serverMessage.equals(servMP[1]) ||
                        serverMessage.equals(servMP[2]) ||
                        serverMessage.equals(servMP[3])) {
                    clientMessage = in.nextLine();
                    writer.println(clientMessage);
                }

                if (serverMessage.equals("Good job!")) {
                    break;
                }

            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            input.close();
            output.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
