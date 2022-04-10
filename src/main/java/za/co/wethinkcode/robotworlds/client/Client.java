package za.co.wethinkcode.robotworlds.client;

import java.net.Socket;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

public class Client {
    public final String robotName;
    public final Socket socket;
    public Request currentRequest;
    public Response currentResponse;

    public Client(String robotName, Socket socket) {
        this.robotName = robotName;
        this.socket = socket;
    }

    void giveRequest(String input) {}

    void getResponse() {}

    public static void main(String[] args) {
        
    }

}
