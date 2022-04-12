package za.co.wethinkcode.robotworlds.client;

import java.net.Socket;
import java.util.Scanner;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

import java.io.*;

public class Client {
    public final String robotName;
    public final Socket socket;
    public Request currentRequest;
    public Response currentResponse;

    public static void main(String args[]) {
        final int port = 5000;

        try (
                Socket socket = new Socket("localhost", port);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        )
        {
            Scanner scanner = new Scanner(System.in);
            String userInput;

            do {
                userInput = scanner.nextLine();
                out.println(userInput);
                out.flush();

                String messageFromServer = in.readLine();
                System.out.println("Response: "+messageFromServer);
                
            } while (!userInput.matches("quit"));

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Client(String robotName, Socket socket) {
        this.robotName = robotName;
        this.socket = socket;
    }

    void giveRequest(String input) {}

    void getResponse() {}
}
