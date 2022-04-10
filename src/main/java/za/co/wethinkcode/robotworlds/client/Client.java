package za.co.wethinkcode.robotworlds.client;

import java.net.Socket;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

import java.net.*;
import java.io.*;

public class Client {
    public final String robotName;
    public final Socket socket;
    public Request currentRequest;
    public Response currentResponse;
    public static void main(String args[]) {
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            out.println("Hello WeThinkCode");
            out.flush();
            String messageFromServer = in.readLine();
            System.out.println("Response: "+messageFromServer);
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
